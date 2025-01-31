package com.example.documentbank.DocumentBank.domain.ui.Screens.documentBankScreen

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.documentbank.DocumentBank.Navigation.vehicleExpenseSettlementScreen
import com.example.documentbank.DocumentBank.data.model.documentbanklist.DocumentBankListRequest
import com.example.documentbank.DocumentBank.data.model.documentbanklist.DocumentBankListResponse
import com.example.documentbank.DocumentBank.domain.ui.viewmodel.DocumentBankListViewModel
import com.example.documentbank.DocumentBank.utils.ApiState
import com.example.documentbank.R
import kotlinx.coroutines.flow.distinctUntilChanged


@Composable
fun DocumentBank(
    navHostController: NavHostController,
    viewModel: DocumentBankListViewModel = hiltViewModel()
) {

    val documentList = listOf(
        "POD",
        "POD Extra Document",
        "Opening KM",
        "Closing KM",
        "Mountain Start KM"
    )
    val documents by viewModel.documents.collectAsState()
    Log.e("__D", documents.toString())

    val lazyListState = rememberLazyListState()
    val context = LocalContext.current
    var showImagePickerDialog by remember { mutableStateOf(false) }
    var tempImageUri by remember { mutableStateOf<Uri?>(null) }
    val coroutineScope = rememberCoroutineScope()

    val document = mutableListOf<DocumentBankListResponse>()
    var showImageDialog by remember { mutableStateOf(false) }

    var selectedDocument by remember { mutableStateOf("") }


    val documentTypes = viewModel.documentTypes.collectAsState()
    val selectedType = viewModel.selectedDocumentType.collectAsState()

    val dcListRequest = DocumentBankListRequest(
        modelId = "2016",
        model = "Load",
        fileType = "image",
        collection = "document-bank",

    )
    LaunchedEffect(key1 = Unit) {
        viewModel.fetchDocuments(request = dcListRequest)
    }
    // Scroll listener for pagination
    LaunchedEffect(lazyListState) {
        snapshotFlow { lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .distinctUntilChanged()
            .collect { index ->
                if (index == lazyListState.layoutInfo.totalItemsCount - 1) {
                    viewModel.fetchDocuments(dcListRequest)
                }
            }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navHostController.navigate(vehicleExpenseSettlementScreen)
            }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_hand_shake),
                    contentDescription = "handShake",
                    modifier = Modifier
                        .clip(CircleShape)
                        .border(BorderStroke(2.dp, Color(0xFF776BF1)), shape = CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

        },
        floatingActionButtonPosition = FabPosition.Center,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp, vertical = 10.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SearchableDropdowns(
                        items = documentList,
                        onDocumentSelected = { selectedDocument = it },
                        selectedItem = selectedDocument,
                        readOnly = false

                    )
                    Button(
                        onClick = {
                            showImageDialog = true
                        },
                        modifier = Modifier
                            .padding(start = 10.dp)

                    ) {
                        Text(
                            "New Image",
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.W600,
                            style = TextStyle(textAlign = TextAlign.Center)
                        )

                    }
                }
                when (documents) {
                    is ApiState.Failure -> {
                        val errorMsg = (documents as ApiState.Failure).message
                        Text(text = "Error: $errorMsg", color = Red)
                    }

                    is ApiState.Loading -> {
                        CircularProgressIndicator()
                    }

                    is ApiState.Success -> {
                        val result =
                            (documents as ApiState.Success<List<DocumentBankListResponse>>).data
                        LazyColumn(
                            state = lazyListState,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            items(result) { document ->
                                DocumentEachRow(
                                    document = document,
                                    onDelete = {},
                                    onEdit = {},
                                    onReject = {},
                                    onUpdate = {})

                            }

                        }
                    }


                }
            }


        }


    }
}


@Composable
fun SearchableDropdowns(
    modifier: Modifier = Modifier,
    items: List<String>,
    selectedItem: String,
    readOnly: Boolean = false,
    onDocumentSelected: (String) -> Unit
) {
    var searchText by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }

    val filteredItems = remember(searchText) {
        items.filter { it.contains(searchText, ignoreCase = true) }
    }

    val icon = if (expanded) Icons.Filled.KeyboardArrowUp
    else Icons.Filled.KeyboardArrowDown

    BasicTextField(
        value = searchText,
        readOnly = readOnly,
        onValueChange = {
            searchText = it
        },
        maxLines = 1,
        singleLine = true,
        textStyle = TextStyle(
            fontSize = 14.sp,
            color = Color.Black,
            textAlign = TextAlign.End,
            fontWeight = FontWeight.Bold,
        ),
        decorationBox = {
            Box(
                contentAlignment = Alignment.CenterStart,

                ) {
//                        Image(
//                            painter = painterResource(R.drawable.ic_search),
//                            contentDescription = "",
//                            modifier = Modifier
//                                .size(20.dp)
//                        )
                val text =
                    if (selectedItem.isEmpty()) "Search Document" else selectedItem
                Text(
                    text,
                    color = Color(0xFF626262),
                    fontSize = 12.sp,
                    modifier = modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 10.dp)


                )
                Icon(
                    icon,
                    contentDescription = "",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable { expanded = !expanded }
                )
            }


        },
        modifier = Modifier
            .clickable {
                expanded = true
            }
            .border(
                BorderStroke(1.dp, Color(0xFFDCDCDC)),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(8.dp)
            .fillMaxWidth(0.7f)


    )
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier
            .background(White)


    ) {
        androidx.compose.material.OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            maxLines = 1,
            textStyle = TextStyle(fontSize = 15.sp),
            placeholder = { Text("Search Document") },
            colors = androidx.compose.material.TextFieldDefaults.textFieldColors(
                backgroundColor = White,
                focusedIndicatorColor = Transparent,
                disabledIndicatorColor = Transparent,
                unfocusedIndicatorColor = Transparent,
                cursorColor = Black
            ),
            modifier = modifier
                .padding(8.dp)
                .border(
                    BorderStroke(1.dp, Color(0xFFDCDCDC)),
                    shape = RoundedCornerShape(4.dp)
                )


        )
        if (filteredItems.isEmpty()) {
            androidx.compose.material.DropdownMenuItem(
                onClick = { },
            ) {
                Text("No results found")
            }
        } else {
            filteredItems.forEach { item ->
                androidx.compose.material.DropdownMenuItem(
                    onClick = {
                        onDocumentSelected(item)
                        searchText = ""
                        expanded = false
                    },
                ) {
                    Text(item)
                }
            }
        }
    }
}

@Composable
fun ImagePickerDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onImageSelected: (Uri?) -> Unit
) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Gallery picker
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        onImageSelected(uri)
    }

    // Camera picker
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) {
        bitmap = it
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Image") },
        text = {
            Column {
                Button(onClick = {
                    galleryLauncher.launch("image/*")
                })
                { Text("Pick from gallery") }
                Button(onClick = {
                    //cameraLauncher.launch()
                }) { Text("Pick from Camera") }
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) { Text("Cancel") }
        }
    )
}




