package com.example.documentbank.ThreadYoutube.item_view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.documentbank.ThreadYoutube.model.ThreadModel
import com.example.documentbank.ThreadYoutube.model.UserModel

@Composable
fun ThreadItem(
    thread: ThreadModel,
    users: UserModel,
    navHostController: NavHostController,
    userId: String
) {
    Column {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            val (userImage, userName, date, time, title, image) = createRefs()

            Image(
                painter = rememberAsyncImagePainter(model = users.imageUrl),
                contentDescription = "attach",
                modifier = Modifier
                    .constrainAs(userImage) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                    .clip(CircleShape)
                    .size(36.dp),
                contentScale = ContentScale.Crop)

            Text(
                text = users.userName,
                style = TextStyle(
                    fontSize = 20.sp
                ),
                modifier = Modifier.constrainAs(userName) {
                    top.linkTo(userImage.top)
                    start.linkTo(userImage.end, margin = 12.dp)
                    bottom.linkTo(userImage.bottom)
                }
            )
            Text(
                text = thread.thread,
                style = TextStyle(
                    fontSize = 12.sp
                ),
                modifier = Modifier.constrainAs(title) {
                    top.linkTo(userName.bottom, margin = 8.dp)
                    start.linkTo(userName.start)
                }
            )

            if (thread.imageUrl != "") {
                Card(
                    modifier = Modifier
                        .padding(10.dp)
                        .constrainAs(image) {
                            top.linkTo(title.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = thread.imageUrl),
                        contentDescription = "image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.Fit
                    )

                }


            }

        }

        Divider(
            color = Color.LightGray,
            thickness = 1.dp
        )
    }


}
