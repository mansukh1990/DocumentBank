package com.example.documentbank.Firebase.firebaseRealtimeDb.ui

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.documentbank.Firebase.firebaseRealtimeDb.RealtimeModelResponse
import com.example.documentbank.utils.Resource
import com.example.documentbank.utils.showMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun RealtimeScreen(
    isInsert: MutableState<Boolean>,
    viewModel: RealtimeViewModel = hiltViewModel()
) {

    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val isProgressDialog = remember { mutableStateOf(false) }
    val res = viewModel.res.value
    val isUpdateDialog = remember { mutableStateOf(false) }

    if (isInsert.value) {
        AlertDialog(onDismissRequest = { isInsert.value = false }, text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TextField(
                    value = title.value,
                    onValueChange = {
                    title.value = it
                }, placeholder = {
                    Text(text = "Title")
                })
                Spacer(Modifier.height(10.dp))
                TextField(
                    value = description.value,
                    onValueChange = {
                    description.value = it
                }, placeholder = {
                    Text(text = "Description")
                })

            }
        }, buttons = {
            Box(
                modifier = Modifier.fillMaxWidth(), contentAlignment = Center
            ) {
                Button(onClick = {
                    scope.launch(Dispatchers.Main) {
                        viewModel.insert(
                            RealtimeModelResponse.RealtimeItems(
                                title = title.value,
                                description = description.value
                            )
                        ).collect {
                            when (it) {
                                is Resource.DataError -> {
                                    context.showMessage(message = it.message.toString())
                                    isProgressDialog.value = false
                                }

                                is Resource.Empty -> {
                                    isProgressDialog.value = false
                                }

                                is Resource.Loading -> {
                                    isProgressDialog.value = true

                                }

                                is Resource.Success -> {
                                    context.showMessage(message = it.data!!)
                                    isProgressDialog.value = false
                                    isInsert.value = false
                                }
                            }
                        }
                    }
                }) {
                    Text(text = "Save")
                    title.value = ""
                    description.value = ""
                }

            }
        })
    }
    if (isUpdateDialog.value) {
        Update(
            isUpdate = isUpdateDialog,
            itemState = viewModel.updateRes.value,
            viewModel = viewModel
        )
    }

    if (res.item.isNotEmpty()) {
        LazyColumn {
            items(res.item, key = {
                it.key!!
            }) { res ->
                EachRow(
                    itemState = res.items!!,
                    onUpdate = {
                        isUpdateDialog.value = true
                        viewModel.setData(res)
                    },
                    onDelete = {
                        scope.launch(Dispatchers.Main) {
                            viewModel.delete(res.key!!).collect {
                                when (it) {
                                    is Resource.DataError -> {
                                        context.showMessage(
                                            message = it.message.toString()
                                        )
                                        isProgressDialog.value = false
                                    }

                                    is Resource.Empty -> {
                                        isProgressDialog.value = false
                                    }

                                    is Resource.Loading -> {
                                        isProgressDialog.value = true
                                    }

                                    is Resource.Success -> {
                                        context.showMessage(
                                            message = it.data!!
                                        )
                                        isProgressDialog.value = false
                                    }
                                }
                            }
                        }
                    }
                )
            }
        }

    }
    if (res.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Center
        ) {
            CircularProgressIndicator()

        }
    }
    if (res.error.isNotEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Center
        ) {
            Text(res.error)
        }

    }

}

@Composable
fun EachRow(
    itemState: RealtimeModelResponse.RealtimeItems,
    onUpdate: () -> Unit = {},
    onDelete: () -> Unit = {}
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        elevation = CardDefaults.cardElevation(1.dp),
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
                        text = itemState.title!!, style = TextStyle(
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
                    text = itemState.description!!, style = TextStyle(
                        fontSize = 14.sp, fontWeight = FontWeight.Normal, color = Color.Gray
                    )
                )

            }

        }

    }

}

@Composable
fun Update(
    isUpdate: MutableState<Boolean>,
    itemState: RealtimeModelResponse,
    viewModel: RealtimeViewModel
) {
    val title = remember { mutableStateOf(itemState.items?.title) }
    val description = remember { mutableStateOf(itemState.items?.description) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    if (isUpdate.value) {
        AlertDialog(
            onDismissRequest = {
                isUpdate.value = false
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    TextField(
                        value = title.value!!,
                        onValueChange = {
                            title.value = it
                        }, placeholder = {
                            Text(
                                text = "Title"
                            )
                        })
                    Spacer(Modifier.height(10.dp))
                    TextField(
                        value = description.value!!,
                        onValueChange = {
                            description.value = it
                        }, placeholder = {
                            Text(text = "Description")
                        })

                }
            },
            buttons = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Center
                ) {
                    Button(
                        onClick = {
                            scope.launch(Dispatchers.Main) {
                                viewModel.update(
                                    items = RealtimeModelResponse(
                                        items = RealtimeModelResponse.RealtimeItems(
                                            title = title.value,
                                            description = description.value
                                        ),
                                        key = itemState.key
                                    )
                                ).collect {
                                    when (it) {
                                        is Resource.DataError -> {
                                            context.showMessage(
                                                message = it.message.toString()
                                            )
                                            isUpdate.value = false
                                        }

                                        is Resource.Empty -> {
                                            isUpdate.value = false
                                        }

                                        is Resource.Loading -> {
                                            isUpdate.value = true
                                        }

                                        is Resource.Success -> {
                                            context.showMessage(
                                                message = it.data!!
                                            )
                                            isUpdate.value = false
                                        }
                                    }
                                }

                            }
                        }) {
                        Text(text = "Save")
                    }
                }
            })
    }

}