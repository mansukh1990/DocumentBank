package com.example.documentbank.ThreadYoutube.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.documentbank.ThreadYoutube.item_view.ThreadItem
import com.example.documentbank.ThreadYoutube.model.UserModel
import com.example.documentbank.ThreadYoutube.navigation.Routes
import com.example.documentbank.ThreadYoutube.utils.SharedPref
import com.example.documentbank.ThreadYoutube.viewmodel.AuthViewModel
import com.example.documentbank.ThreadYoutube.viewmodel.UserViewModel

@Composable
fun Profile(
    navHostController: NavHostController
) {

    val viewModel: AuthViewModel = viewModel()
    val firebaseUser by viewModel.firebaseUser.observeAsState(null)

    val userViewModel: UserViewModel = viewModel()
    val threads by userViewModel.threads.observeAsState()

    val context = LocalContext.current

    if (firebaseUser != null) {
        userViewModel.fetchThreads(uid = firebaseUser!!.uid)
    }

    LaunchedEffect(firebaseUser) {
        if (firebaseUser == null) {
            navHostController.navigate(Routes.Login.routes) {
                popUpTo(navHostController.graph.startDestinationId)
                launchSingleTop = true
            }
        }

    }

    val user = UserModel(
        name = SharedPref.getName(context)!!,
        userName = SharedPref.getUserName(context)!!,
        imageUrl = SharedPref.getImageUrl(context)!!
    )



    LazyColumn {
        item {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                val (text, logo, userName, bio, followers, following, logoutBtn) = createRefs()

                Text(
                    text = SharedPref.getName(context)!!,
                    style = TextStyle(
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 24.sp
                    ),
                    modifier = Modifier.constrainAs(text) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                )
                Image(
                    painter = rememberAsyncImagePainter(model = SharedPref.getImageUrl(context)),
                    contentDescription = "attach",
                    modifier = Modifier
                        .constrainAs(logo) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        }
                        .clip(CircleShape)
                        .size(50.dp),
                    contentScale = ContentScale.Crop)

                Text(
                    text = SharedPref.getUserName(context)!!,
                    style = TextStyle(
                        fontSize = 20.sp
                    ), modifier = Modifier
                        .constrainAs(userName) {
                            top.linkTo(text.bottom)
                            start.linkTo(text.start)
                        }
                )
                Text(
                    text = SharedPref.getBio(context)!!,
                    style = TextStyle(
                        fontSize = 20.sp
                    ), modifier = Modifier
                        .constrainAs(bio) {
                            top.linkTo(userName.bottom)
                            start.linkTo(parent.start)
                        }
                )
                Text(
                    text = "0 followers",
                    style = TextStyle(
                        fontSize = 20.sp
                    ), modifier = Modifier
                        .constrainAs(followers) {
                            top.linkTo(bio.bottom)
                            start.linkTo(parent.start)
                        }
                )
                Text(
                    text = "0 following",
                    style = TextStyle(
                        fontSize = 20.sp
                    ), modifier = Modifier
                        .constrainAs(following) {
                            top.linkTo(followers.bottom)
                            start.linkTo(parent.start)
                        }
                )
                ElevatedButton(
                    onClick = {
                        viewModel.logout()
                    },
                    modifier = Modifier
                        .constrainAs(logoutBtn) {
                            top.linkTo(following.bottom, margin = 10.dp)
                            start.linkTo(parent.start)

                        },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.LightGray
                    )
                ) {
                    Text(
                        text = "Logout", style = TextStyle(color = Color.White)

                    )

                }


            }
        }
        items(threads ?: emptyList()) { pair ->
            ThreadItem(
                thread = pair,
                users = user,
                navHostController = navHostController,
                userId = SharedPref.getUserName(context)!!
            )

        }
    }


}
