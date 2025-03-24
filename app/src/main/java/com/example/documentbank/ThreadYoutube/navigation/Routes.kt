package com.example.documentbank.ThreadYoutube.navigation

sealed class Routes(
    val routes: String
) {

    object Home : Routes("home")
    object Splash : Routes("splash")
    object Search : Routes("search")
    object Notification : Routes("notification")
    object Profile : Routes("profile")
    object AddThreads : Routes("addThreads")
    object BottomNav : Routes("bottomNav")
    object Login : Routes("login")
    object Register : Routes("register")
    object AllUser : Routes("all_users/{data}")
}