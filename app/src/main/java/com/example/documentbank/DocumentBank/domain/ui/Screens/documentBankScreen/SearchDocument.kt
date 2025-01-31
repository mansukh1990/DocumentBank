package com.example.documentbank.DocumentBank.domain.ui.Screens.documentBankScreen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.documentbank.DocumentBank.data.model.documentbanklist.DocumentBankListRequest
import com.example.documentbank.DocumentBank.data.model.documentbanklist.DocumentBankListResponse
import com.example.documentbank.DocumentBank.data.model.documentbanklist.DocumentTypeResponse
import com.example.documentbank.DocumentBank.domain.ui.viewmodel.DocumentBankListViewModel
import com.example.documentbank.DocumentBank.utils.ApiState
import com.example.documentbank.R
import com.google.android.gms.common.api.Api

@Composable
fun SearchableDropdownDocumentList() {

    val viewModel: DocumentBankListViewModel = hiltViewModel()
    val documentListState by viewModel.documents.collectAsState()

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            it
        }
    }
    LaunchedEffect(key1 = Unit) {
        val request = DocumentBankListRequest(
            modelId = "2016",
            fileType = "image",
            model = "Load",
            collection = "document-bank"
        )
        viewModel.fetchDocuments(request)
    }
    val listState = rememberLazyListState()

    Row(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 10.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.weight(8f),
        ) {
            SearchableDropdown()
        }

        NewImageUpload(
            modifier = Modifier
                .weight(4f)
                .padding(start = 10.dp)
                .clickable {
                    galleryLauncher.launch("")
                }


        )


    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()


    ) {
//        items(documentListState) { document ->
//            DocumentListItem(document)
//
//
//        }
    }


}

@Composable
fun DocumentTypeDropdown(
    viewModel: DocumentBankListViewModel = hiltViewModel()
) {
    val documentTypes by viewModel.documentTypes.collectAsState()
    val selectedType by viewModel.selectedDocumentType.collectAsState()

    var expanded by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.fetchDocumentTypes()
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        BasicTextField(
            value = selectedType?.name ?: searchText,
            onValueChange = { searchText = it },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
            textStyle = MaterialTheme.typography.bodyLarge,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        if (selectedType?.name == null && searchText.isEmpty()) {
                            Text(
                                "Select Document Type",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        }
                        innerTextField()
                    }
                    Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = "Toggle Dropdown"
                    )
                }
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            when (documentTypes) {
                is ApiState.Success -> {
                    val filteredTypes =
                        (documentTypes as ApiState.Success<List<DocumentTypeResponse>>).data.filter {
                            it.name.contains(
                                searchText,
                                ignoreCase = true
                            )
                        }

                    filteredTypes.forEach { type ->
                        DropdownMenuItem(
                            onClick = {
                                viewModel.setSelectedDocumentType(type)
                                searchText = ""
                                expanded = false


                            }
                        ) {
                            Text(text = type.name)
                        }

                    }
                }

                is ApiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is ApiState.Failure -> {
                    (documentTypes as ApiState.Failure).message.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun SearchableDropdown(
    viewModel: DocumentBankListViewModel = hiltViewModel(),
) {
    var searchText by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }

    val documentTypes by viewModel.documentTypes.collectAsState()
    val selectedType by viewModel.selectedDocumentType.collectAsState()


    val icon = if (expanded) Icons.Filled.KeyboardArrowUp
    else Icons.Filled.KeyboardArrowDown

    LaunchedEffect(Unit) {
        viewModel.fetchDocumentTypes()
    }

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BasicTextField(
            value = searchText,
            readOnly = true,
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start

                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_search),
                            contentDescription = "",
                            modifier = Modifier
                                .size(20.dp)
                        )
                        val text =
                            if (selectedType?.name == null) "Search Document" else selectedType?.name
                        Text(
                            text!!,
                            color = Color(0xFF626262),
                            fontSize = 12.sp,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 30.dp)

                        )
                        Icon(
                            icon,
                            contentDescription = "",
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .clickable { expanded = !expanded }
                        )
                    }
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
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(White)
                .weight(0.8f)

        ) {
            when (documentTypes) {
                is ApiState.Success -> {
                    val filteredItems =
                        (documentTypes as ApiState.Success<List<DocumentTypeResponse>>).data!!.filter {
                            it.name.contains(
                                searchText,
                                ignoreCase = true
                            )
                        }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedTextField(
                            value = searchText,
                            onValueChange = { searchText = it },
                            maxLines = 1,
                            placeholder = { Text("Search Document") },
                            trailingIcon = {
                                if (searchText.isNotEmpty()) {
                                    Icon(Icons.Default.Clear, contentDescription = "close",
                                        modifier = Modifier
                                            .clickable { searchText = "" })
                                }

                            }


                        )

                        filteredItems.forEach { type ->
                            DropdownMenuItem(
                                onClick = {
                                    viewModel.setSelectedDocumentType(type)
                                    searchText = ""
                                    expanded = false
                                },
                            ) {
                                Text(type.name)
                            }
                        }
                    }


                }

                is ApiState.Failure -> {
                    (documentTypes as ApiState.Failure).message?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                is ApiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

            }

        }
    }


}


@Composable
fun NewImageUpload(
    modifier: Modifier = Modifier,
) {

    BasicTextField(
        value = "Click to upload image",
        onValueChange = {
        },
        enabled = true,
        maxLines = 1,
        readOnly = true,
        singleLine = true,
        decorationBox = {
            Row(
                verticalAlignment = Alignment.CenterVertically

            ) {
                Image(
                    painter = painterResource(R.drawable.ic_file_add),
                    contentDescription = ""
                )
                Text(
                    "New Image",
                    color = Color(0xFFFFFFFF),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = modifier.padding(end = 5.dp)


                )
            }


        },
        modifier = modifier
            .clickable {
            }
            .clip(shape = RoundedCornerShape(4.dp))
            .background(Color(0xFF776BF1))
            .padding(horizontal = 10.dp, vertical = 10.dp)
    )

}

@Composable
fun DocumentListItem(
    document: DocumentBankListResponse,
) {
    val viewModel: DocumentBankListViewModel = hiltViewModel()
    var searchText by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }

    val documentTypes by viewModel.documentTypes.collectAsState()
    val selectedType by viewModel.selectedDocumentType.collectAsState()


    val icon = if (expanded) Icons.Filled.KeyboardArrowUp
    else Icons.Filled.KeyboardArrowDown

    LaunchedEffect(Unit) {
        viewModel.fetchDocumentTypes()
    }
    val items = listOf(
        "Select Name",
        "POD",
        "POD Extra Document",
        "Opening KM",
        "Closing KM",
        "Mountain Start KM"
    )
    var selectedItem by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
            .padding(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(1.dp, Color.White),
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = 5.dp, vertical = 10.dp
                ),
        ) {
            Box(
                modifier = Modifier
                    .padding(end = 10.dp, start = 5.dp)
                    .size(50.dp)
                    .background(Color(0xFFEEEEEE))
                    .clip(shape = RoundedCornerShape(10.dp)),
                contentAlignment = Center,
            ) {
                Image(
                    painter = rememberAsyncImagePainter(document.file_url),
                    contentDescription = "",
                    alignment = Alignment.TopCenter,
                    modifier = Modifier.padding(2.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                DocumentListItemBasicText(items = items,
                    selectedItem = selectedItem,
                    onItemSelected = {
                        selectedItem = it
                    }
                )
                Spacer(modifier = Modifier.height(5.dp))
                DocumentListItemBasicText(
                    items = items,
                    selectedItem = selectedItem,
                    onItemSelected = {
                        selectedItem = it
                    }
                )

            }
            Image(
                painter = painterResource(id = R.drawable.ic_upload),
                contentDescription = "",
                modifier = Modifier
                    .padding(start = 10.dp)
                    .clickable {

                    }

            )
            Image(
                painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = "",
                modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp)
                    .clickable {
                    }
            )

        }


    }

}

@Composable
fun DocumentListItemBasicText(
    modifier: Modifier = Modifier,
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    var mExpanded by remember { mutableStateOf(false) }

    val filteredItems = remember(searchQuery) {
        items.filter { it.contains(searchQuery, ignoreCase = true) }
    }

    val icon = if (mExpanded) Icons.Filled.KeyboardArrowUp
    else Icons.Filled.KeyboardArrowDown

    BasicTextField(
        value = selectedItem,
        onValueChange = {
        },
        readOnly = true,
        maxLines = 1,
        singleLine = true,
        textStyle = TextStyle(
            fontSize = 14.sp,
            color = Color.Black,
            textAlign = TextAlign.End,
            fontWeight = FontWeight.Bold,
        ),
        decorationBox = {
            Row(
                verticalAlignment = Alignment.CenterVertically

            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    val text = if (selectedItem.isEmpty()) "Search Document" else selectedItem
                    Text(
                        text,
                        color = Color(0xFF626262),
                        fontSize = 10.sp,
                        modifier = modifier
                            .align(Alignment.CenterStart)


                    )
                    Icon(
                        icon,
                        contentDescription = "",
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .clickable { mExpanded = !mExpanded }
                    )
                }
            }


        },
        modifier = Modifier
            .clickable {
                mExpanded = true
            }
            .border(
                BorderStroke(1.dp, Color(0xFFDCDCDC)),
                shape = RoundedCornerShape(4.dp)
            )
            .padding(8.dp)
    )
    DropdownMenu(
        expanded = mExpanded,
        onDismissRequest = { mExpanded = false },
        modifier = Modifier.background(White)


    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            maxLines = 1,
            placeholder = { Text("") },
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth()


        )
        if (filteredItems.isEmpty()) {
            DropdownMenuItem(onClick = { }) {
                Text("No results found")
            }
        } else {
            filteredItems.forEach { item ->
                DropdownMenuItem(onClick = {
                    onItemSelected(item)
                    searchQuery = ""
                    mExpanded = false
                }) {
                    Text(item)
                }
            }
        }
    }

}






