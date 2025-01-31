package com.example.documentbank.DocumentBank.domain.ui.Screens.documentBankScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.documentbank.DocumentBank.data.model.documentbanklist.DocumentBankListResponse
import com.example.documentbank.R

@Composable
fun DocumentEachRow(
    document: DocumentBankListResponse,
    onUpdate: () -> Unit,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
    onReject: () -> Unit

) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .background(Color.White)
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
                    .border(1.dp, Color(0xFFEEEEEE), shape = RoundedCornerShape(8.dp)),
                contentAlignment = Center,
            ) {
                Image(
                    painter = rememberAsyncImagePainter(document.file_url),
                    contentDescription = "",
                    alignment = Alignment.TopCenter,
                    modifier = Modifier.padding(5.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
            ) {
                SearchableDropdowns(
                    items = listOf(
                        "POD",
                        "POD Extra Document",
                        "Opening KM",
                        "Closing KM",
                        "Mountain Start KM",
                        "POD",
                        "POD Extra Document",
                        "Opening KM",
                        "Closing KM",
                        "Mountain Start KM",
                    ),
                    onDocumentSelected = { },
                    selectedItem = "",
                    readOnly = true
                )
                Spacer(modifier = Modifier.height(5.dp))
                SearchableDropdowns(
                    items = listOf(
                        "POD",
                        "POD Extra Document",
                        "Opening KM",
                        "Closing KM",
                        "Mountain Start KM",
                        "POD",
                        "POD Extra Document",
                        "Opening KM",
                        "Closing KM",
                        "Mountain Start KM",
                    ),
                    onDocumentSelected = { },
                    selectedItem = "",
                    readOnly = true
                )

            }
            Image(
                painter = painterResource(id = R.drawable.ic_upload),
                contentDescription = "",
                modifier = Modifier
                    .padding(start = 10.dp)
                    .clickable {
                        onUpdate()
                    }

            )
            Image(
                painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = "",
                modifier = Modifier
                    .padding(start = 5.dp)
                    .clickable {
                        onDelete()
                    }
            )
        }

    }


}

