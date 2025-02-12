package com.example.documentbank.DocumentBank.domain.ui.Screens.documentBankScreen

import android.net.Uri
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.DropdownMenu
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.documentbank.R

@Composable
fun DocumentEachRow(
    imageUrl: String? = null,
    imageUri: Uri? = null,
    onUpload: () -> Unit,
    onReject: () -> Unit

) {
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
                if (imageUrl != null) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Fetched Image",
                        alignment = Center,
                        modifier = Modifier.padding(0.dp),
                        contentScale = ContentScale.Fit
                    )
                } else if (imageUri != null) {
                    AsyncImage(
                        model = imageUri,
                        contentDescription = "Selected Image",
                        alignment = Center,
                        modifier = Modifier.padding(0.dp),
                        contentScale = ContentScale.Fit
                    )

                }
            }

            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
            ) {
                DocumentItemSearchDropDown(
                )
                Spacer(modifier = Modifier.height(5.dp))
                DocumentItemSearchDropDown(

                )

            }
            Image(
                painter = painterResource(id = R.drawable.ic_upload),
                contentDescription = "",
                modifier = Modifier
                    .padding(start = 10.dp)
                    .clickable {
                        onUpload()
                    }

            )
            Image(
                painter = painterResource(id = R.drawable.ic_delete),
                contentDescription = "",
                modifier = Modifier
                    .padding(start = 5.dp)
                    .clickable {
                        onReject()
                    }
            )
        }

    }


}

@Composable
fun DocumentItemSearchDropDown(
) {
    var searchText by remember { mutableStateOf("") }

    var expanded by remember { mutableStateOf(false) }


    val icon = if (expanded) Icons.Filled.KeyboardArrowUp
    else Icons.Filled.KeyboardArrowDown


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
            color = Black,
            textAlign = TextAlign.End,
            fontWeight = FontWeight.Bold,
        ),
        decorationBox = {
            Box(
                contentAlignment = Alignment.CenterStart,

                ) {
                val text =
                    if (searchText!!.isEmpty()) "Search Document" else searchText
                Text(
                    text,
                    color = Color(0xFF626262),
                    fontSize = 12.sp,
                    modifier = Modifier
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
            modifier = Modifier
                .padding(8.dp)
                .border(
                    BorderStroke(1.dp, Color(0xFFDCDCDC)),
                    shape = RoundedCornerShape(4.dp)
                )


        )

    }
}


