package com.example.documentbank.ThreadYoutube.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.documentbank.ThreadYoutube.screens.AddThreads
import com.example.documentbank.ThreadYoutube.screens.AllUser
import com.example.documentbank.ThreadYoutube.screens.BottomNav
import com.example.documentbank.ThreadYoutube.screens.Home
import com.example.documentbank.ThreadYoutube.screens.Login
import com.example.documentbank.ThreadYoutube.screens.Notification
import com.example.documentbank.ThreadYoutube.screens.Profile
import com.example.documentbank.ThreadYoutube.screens.Register
import com.example.documentbank.ThreadYoutube.screens.Search
import com.example.documentbank.ThreadYoutube.screens.Splash

@Composable
fun NavGraph(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = Routes.Splash.routes
    ) {
        composable(Routes.Splash.routes) {
            Splash(navController)
        }
        composable(Routes.Home.routes) {
            Home(navController)
        }
        composable(Routes.Search.routes) {
            Search(navController)
        }
        composable(Routes.Profile.routes) {
            Profile(navController)
        }
        composable(Routes.AddThreads.routes) {
            AddThreads(navController)
        }
        composable(Routes.Notification.routes) {
            Notification()
        }
        composable(Routes.BottomNav.routes) {
            BottomNav(navController)
        }
        composable(Routes.Login.routes) {
            Login(navController)
        }
        composable(Routes.Register.routes) {
            Register(navController)
        }
        composable(Routes.AllUser.routes) {
            val data = it.arguments!!.getString("data")
            AllUser(navController, data!!)
        }

    }

}