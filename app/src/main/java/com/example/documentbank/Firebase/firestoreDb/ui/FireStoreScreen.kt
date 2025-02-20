package com.example.documentbank.Firebase.firestoreDb.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.documentbank.Firebase.firestoreDb.FirestoreModelResponse
import com.example.documentbank.utils.CommonDialog
import com.example.documentbank.utils.Resource
import com.example.documentbank.utils.showMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun FireStoreScreen(
    isInput: MutableState<Boolean>, viewModel: FirestoreViewModel = hiltViewModel()
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val isDialog = remember { mutableStateOf(false) }
    val res = viewModel.res.value
    val isUpdate = remember { mutableStateOf(false) }


    if (isDialog.value) CommonDialog()

    if (isInput.value) {
        AlertDialog(onDismissRequest = {
            isInput.value = false
        }, text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                TextField(value = title, onValueChange = {
                    title = it
                }, placeholder = {
                    Text(text = "Enter Title")
                }, keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                )
                )
                Spacer(Modifier.height(10.dp))
                TextField(value = description, onValueChange = {
                    description = it
                }, placeholder = {
                    Text(text = "Enter Description")
                }, keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                )
                )

            }
        }, buttons = {
            Box(
                modifier = Modifier.fillMaxWidth(), contentAlignment = Center
            ) {
                Button(onClick = {
                    if (title.isEmpty()) {
                        context.showMessage(message = "Please Enter Title")
                        return@Button
                    }
                    if (description.isEmpty()) {
                        context.showMessage(message = "Please Enter Description")
                        return@Button
                    }
                    scope.launch(Dispatchers.Main) {
                        viewModel.insert(
                            FirestoreModelResponse.FirestoreItem(
                                title = title, description = description
                            )
                        ).collect {
                            when (it) {
                                is Resource.DataError -> {
                                    context.showMessage(message = it.message.toString())
                                    isDialog.value = false
                                }

                                is Resource.Empty -> {
                                    context.showMessage(message = it.message.toString())
                                    isDialog.value = false
                                }

                                is Resource.Loading -> {
                                    isDialog.value = true
                                }

                                is Resource.Success -> {
                                    context.showMessage(message = it.data!!)
                                    isDialog.value = false
                                    isInput.value = false
                                    viewModel.getItems()


                                }
                            }
                        }
                    }
                }) {
                    Text(text = "Save")
                    title = ""
                    description = ""

                }

            }
        })

    }

    if (isUpdate.value) {
        updateFireStoreData(
            item = viewModel.updateData.value,
            viewModel = viewModel,
            isUpdate = isUpdate,
            isDialog = isDialog
        )
    }

    if (res.data.isNotEmpty()) {
        LazyColumn {
            items(res.data, key = {
                it.key!!
            }) { items ->
                EachRowFireStore(
                    itemState = items,
                    onDelete = {
                        scope.launch(Dispatchers.Main) {
                            viewModel.delete(
                                items.key!!
                            ).collect {
                                when (it) {
                                    is Resource.DataError -> {
                                        context.showMessage(message = it.message.toString())
                                        isDialog.value = false

                                    }

                                    is Resource.Empty -> {
                                        isDialog.value = false
                                        context.showMessage(message = it.message.toString())

                                    }

                                    is Resource.Loading -> {
                                        isDialog.value = true
                                    }

                                    is Resource.Success -> {
                                        isDialog.value = false
                                        context.showMessage(message = it.data!!)
                                        viewModel.getItems()
                                    }
                                }
                            }
                        }
                    },
                    onUpdate = {
                        isUpdate.value = true
                        viewModel.setData(items)
                    })

            }
        }
    }
    if (res.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Center
        ) { CircularProgressIndicator() }
    }
    if (res.error.isNotEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Center
        ) {
            Text(text = res.error)
        }
    }

}

@Composable
fun EachRowFireStore(
    itemState: FirestoreModelResponse, onUpdate: () -> Unit = {}, onDelete: () -> Unit = {}
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onUpdate()
            }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = itemState.item?.title!!, style = TextStyle(
                            fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black
                        )
                    )
                    IconButton(
                        onClick = {
                            onDelete()
                        }, modifier = Modifier.align(CenterVertically)
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "delete Button",
                            tint = Color.Red
                        )
                    }

                }
                Text(
                    text = itemState.item?.description!!, style = TextStyle(
                        fontSize = 14.sp, fontWeight = FontWeight.Normal, color = Color.Gray
                    )
                )

            }

        }

    }

}

@Composable
fun updateFireStoreData(
    item: FirestoreModelResponse,
    viewModel: FirestoreViewModel,
    isUpdate: MutableState<Boolean>,
    isDialog: MutableState<Boolean>
) {

    var title by remember { mutableStateOf(item.item?.title) }
    var dec by remember { mutableStateOf(item.item?.description) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    AlertDialog(onDismissRequest = {
        isUpdate.value = false
    }, text = {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            TextField(value = title!!, onValueChange = {
                title = it
            }, placeholder = {
                Text(text = "Enter Title")
            }, keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            )
            )
            Spacer(Modifier.height(10.dp))
            TextField(value = dec!!, onValueChange = {
                dec = it
            }, placeholder = {
                Text(text = "Enter Description")
            }, keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            )
            )
        }
    }, buttons = {
        Box(
            modifier = Modifier.fillMaxWidth(), contentAlignment = Center
        ) {
            Button(onClick = {
                scope.launch(Dispatchers.Main) {
                    viewModel.update(
                        FirestoreModelResponse(
                            item = FirestoreModelResponse.FirestoreItem(
                                title = title, description = dec
                            ),
                            key = item.key
                        )
                    ).collect {
                        when (it) {
                            is Resource.DataError -> {
                                context.showMessage(message = it.message.toString())
                                isDialog.value = false
                            }

                            is Resource.Empty -> {
                                isDialog.value = false
                                context.showMessage(message = it.message.toString())
                            }

                            is Resource.Loading -> {
                                isDialog.value = true
                            }

                            is Resource.Success -> {
                                context.showMessage(message = it.data!!)
                                isUpdate.value = false
                                isDialog.value = false
                                viewModel.getItems()
                            }
                        }
                    }

                }
            }) {
                Text(text = "Update")
            }


        }
    })
}