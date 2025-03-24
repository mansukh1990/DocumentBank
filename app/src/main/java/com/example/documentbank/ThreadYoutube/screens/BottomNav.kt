package com.example.documentbank.ThreadYoutube.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.documentbank.ThreadYoutube.navigation.BottomNavItem
import com.example.documentbank.ThreadYoutube.navigation.Routes
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNav(
    navHostController: NavHostController
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            MyBottomBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.Home.routes,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.Splash.routes) {
                Splash(navHostController)
            }
            composable(Routes.Home.routes) {
                Home(navController)
            }
            composable(Routes.Search.routes) {
                Search(navController)
            }
            composable(Routes.Profile.routes) {
                Profile(navHostController)
            }
            composable(Routes.AddThreads.routes) {
                AddThreads(navController)
            }
            composable(Routes.Notification.routes) {
                Notification()
            }
            composable(Routes.AllUser.routes) {
                AllUser(navController, uid = FirebaseAuth.getInstance().currentUser!!.uid)
            }
        }

    }


}

@Composable
fun MyBottomBar(navController: NavHostController) {

    val backStackEntry = navController.currentBackStackEntryAsState()

    val list = listOf(
        BottomNavItem(
            title = "Home",
            route = Routes.Home.routes,
            icon = Icons.Rounded.Home
        ),
        BottomNavItem(
            title = "Search",
            route = Routes.Search.routes,
            icon = Icons.Rounded.Search
        ),
        BottomNavItem(
            title = "Add Threads",
            route = Routes.AddThreads.routes,
            icon = Icons.Rounded.Add
        ),
        BottomNavItem(
            title = "Notification",
            route = Routes.Notification.routes,
            icon = Icons.Rounded.Notifications
        ),
        BottomNavItem(
            title = "profile",
            route = Routes.Profile.routes,
            icon = Icons.Rounded.Person
        )
    )

    BottomAppBar {
        list.forEach {
            val selected = it.route == backStackEntry.value?.destination?.route

            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(it.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = it.icon, contentDescription = it.title
                    )

                }
            )
        }
    }

}
