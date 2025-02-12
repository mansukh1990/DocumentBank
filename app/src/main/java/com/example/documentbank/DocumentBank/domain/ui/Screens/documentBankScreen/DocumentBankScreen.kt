package com.example.documentbank.DocumentBank.domain.ui.Screens.documentBankScreen

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
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
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.documentbank.DocumentBank.Navigation.vehicleExpenseSettlementScreen
import com.example.documentbank.DocumentBank.data.model.documentbanklist.DocumentBankListRequest
import com.example.documentbank.DocumentBank.data.model.documentbanklist.DocumentBankListResponse
import com.example.documentbank.DocumentBank.data.model.documentbanklist.DocumentTypeResponse
import com.example.documentbank.DocumentBank.domain.ui.viewmodel.DocumentBankListViewModel
import com.example.documentbank.DocumentBank.utils.ApiState
import com.example.documentbank.R
import kotlinx.coroutines.flow.collectLatest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import kotlin.math.roundToInt


@Composable
fun DocumentBank(
    navHostController: NavHostController,
    viewModel: DocumentBankListViewModel = hiltViewModel()
) {
    val documents by viewModel.documents.collectAsState()

    val status = viewModel.uploadDocumentBankMediaFileLiveData.value

    Log.e("__D", documents.toString())

    val scrollState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope()

    var showImageDialog by remember { mutableStateOf(false) }

    var selectedDocument by remember { mutableStateOf("") }


    val documentTypes = viewModel.documentTypes.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val imageList by viewModel.imageList.collectAsState()

    var selectedDocumentType by remember { mutableStateOf<DocumentTypeResponse?>(null) }

    val documentList by remember { mutableStateOf<DocumentBankListResponse?>(null) }
    val selectedType = viewModel.selectedDocumentType.collectAsState()

    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    val dcListRequest = DocumentBankListRequest(
        modelId = "2004",
        model = "Load",
        fileType = "image",
        collection = "document-bank",
    )
    val uploadState by viewModel.uploadState.collectAsState()

    val context = LocalContext.current

    var showPreview by remember { mutableStateOf(false) }
    var selectedUri by remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                viewModel.addImage(uri)
                val file = uriToFile(context, it)
                viewModel.uploadImage(file)
                viewModel.uploadImage(file)
                Log.e("TAG", viewModel.uploadImage(file).toString())
            }
        }
    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            bitmap?.let {
                val uri = saveBitmapToMediaStore(context, it)
                viewModel.addImage(uri!!)
                val file = saveBitmapToFile(context, it)
                viewModel.uploadImage(file)
            }
        }

    LaunchedEffect(Unit) {
        snapshotFlow { viewModel.documents.value }.collectLatest {
            viewModel.fetchDocuments(request = dcListRequest)
        }
    }
    LaunchedEffect(key1 = Unit) {
        viewModel.fetchDocumentTypes()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navHostController.navigate(vehicleExpenseSettlementScreen)
            },
                modifier = Modifier
                    .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }

            ) {

                Image(
                    painter = painterResource(id = R.drawable.ic_hand_shake),
                    contentDescription = "handShake",
                    modifier = Modifier
                        .clip(CircleShape)
                        .border(BorderStroke(2.dp, Color(0xFF776BF1)), shape = CircleShape),
                    contentScale = ContentScale.Fit
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
                    val result = documentTypes.value
                    when (result) {
                        is ApiState.Failure -> {
                            val errorMsg = result.message
                            Text(text = "Error: $errorMsg", color = Red)
                        }

                        ApiState.Loading -> {
                            Box(modifier = Modifier.fillMaxSize()) {
                                CircularProgressIndicator()
                            }
                        }

                        is ApiState.Success -> {
                            SearchableDropdowns(
                                items = result.data,
                                onDocumentSelected = { selectedDocument = it },
                                selectedItem = selectedDocument

                            )
                        }
                    }


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
                    if (showImageDialog)
                        ImagePickerDialog(
                            onDismiss = {
                                showImageDialog = false
                            }, onPickFromGallery = {
                                galleryLauncher.launch("image/*")
                                showImageDialog = false

                            }, onPickFromCamera = {
                                cameraLauncher.launch()
                                showImageDialog = false

                            }
                        )


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
                            state = scrollState,
                            modifier = Modifier.fillMaxSize(),
                        ) {

                            items(imageList) { uri ->
                                DocumentEachRow(imageUri = uri, onUpload = {}, onReject = {
                                    viewModel.removeImage(uri)
                                })

                            }
                            items(result) { document ->
                                DocumentEachRow(imageUrl = document.file_url,
                                    onUpload = {},
                                    onReject = {
                                        viewModel.deleteDocument(document.id)
                                    })
                            }


                        }

                    }


                }
            }


        }


    }
}

@Composable
fun ImagePreviewDialog(imageUri: Uri, onDismiss: () -> Unit) {
    AlertDialog(onDismissRequest = { onDismiss() },
        buttons = {},
        title = { Text("Image Preview") },
        text = {
            Image(
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = "Preview Image",
                modifier = Modifier.fillMaxWidth()
            )
        })
}


@Composable
fun SearchableDropdowns(
    modifier: Modifier = Modifier, items: List<DocumentTypeResponse>,
    selectedItem: String,
    onDocumentSelected: (String) -> Unit
) {
    var searchText by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }

    val filteredItems = remember(searchText) {
        items.filter { it.name.contains(searchText, ignoreCase = true) }
    }

    val icon = if (expanded) Icons.Filled.KeyboardArrowUp
    else Icons.Filled.KeyboardArrowDown

    BasicTextField(value = searchText, readOnly = true,
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
                BorderStroke(1.dp, Color(0xFFDCDCDC)), shape = RoundedCornerShape(4.dp)
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
        OutlinedTextField(
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
                    BorderStroke(1.dp, Color(0xFFDCDCDC)), shape = RoundedCornerShape(4.dp)
                )


        )
        if (filteredItems.isEmpty()) {
            DropdownMenuItem(
                onClick = { }, modifier = Modifier.fillMaxWidth()
            ) {
                Text("No results found")
            }
        } else {
            filteredItems.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        onDocumentSelected(item.name)
                        searchText = ""
                        expanded = false
                    }, modifier = Modifier.fillMaxWidth()
                ) {
                    Text(item.name)
                }
            }
        }
    }

}


@Composable
fun ImagePickerDialog(
    onDismiss: () -> Unit,
    onPickFromGallery: () -> Unit,
    onPickFromCamera: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss, shape = RoundedCornerShape(8.dp), backgroundColor = White,
        text = {
            Column {
                TextButton(onClick = {}) {
                    Text(
                        text = "Select File",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Blue
                    )
                }
                TextButton(
                    onClick = onPickFromCamera, modifier = Modifier.padding(top = 5.dp)
                ) {
                    Text(
                        "Take a photo", fontSize = 16.sp, color = Black
                    )
                }
                TextButton(onClick = onPickFromGallery) {
                    Text(
                        "Select image from gallery", fontSize = 16.sp, color = Black
                    )
                }
                TextButton(
                    onClick = onDismiss,
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .background(Black, shape = RoundedCornerShape(8.dp))
                ) {
                    Text(
                        "Cancel",
                        fontSize = 16.sp,
                        color = White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }
        },
        confirmButton = {},
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = true
        )
    )
}

@Composable
fun MoveableImageButton() {
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_hand_shake), // Replace with your image
            contentDescription = "Moveable Button",
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .size(80.dp)
                .clip(CircleShape)
                .background(Color.White)
                .clickable { }
        )
    }
}

@Composable
fun ImageItem(uri: Uri) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .background(White)
            .clip(shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFFDCDCDC), shape = RoundedCornerShape(8.dp))
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color(0xFFEEEEEE))
                    .border(1.dp, Color(0xFFEEEEEE), shape = RoundedCornerShape(10.dp)),
                contentAlignment = Center,
            ) {
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = "",
                    alignment = Center,
                    modifier = Modifier.padding(0.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Column(
                modifier = Modifier.padding(start = 10.dp)
            ) {
                DocumentItemSearchDropDown(
                )
                Spacer(modifier = Modifier.height(5.dp))
                DocumentItemSearchDropDown(

                )

            }
            Image(painter = painterResource(id = R.drawable.ic_upload),
                contentDescription = "",
                modifier = Modifier
                    .padding(start = 10.dp)
                    .clickable {

                    }

            )
            Image(painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = "",
                modifier = Modifier
                    .padding(start = 5.dp)
                    .clickable {})
        }

    }


}

fun saveBitmapToMediaStore(context: Context, bitmap: Bitmap): Uri? {
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "captured_${System.currentTimeMillis()}.jpg")
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
    }

    val resolver = context.contentResolver
    val imageUri: Uri? =
        resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

    imageUri?.let { uri ->
        resolver.openOutputStream(uri)?.use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        }
    }
    return imageUri
}

fun createImageUri(context: Context): Uri {
    val file = File(context.cacheDir, "${System.currentTimeMillis()}.jpg")
    return FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
}

fun saveBitmapToFile(context: Context, bitmap: Bitmap): File {
    val file = File(context.cacheDir, "image_${System.currentTimeMillis()}.jpg")
    file.outputStream().use { bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it) }
    return file
}

fun uriToFile(context: Context, uri: Uri): File {
    val file = File(context.cacheDir, "image_${System.currentTimeMillis()}.jpg")
    val inputStream = context.contentResolver.openInputStream(uri)
    file.outputStream().use { output -> inputStream?.copyTo(output) }
    return file
}








