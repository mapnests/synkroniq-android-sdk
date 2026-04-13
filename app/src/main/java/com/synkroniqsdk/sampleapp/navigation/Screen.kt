package com.synkroniqsdk.sampleapp.navigation

sealed class Screen(val route: String){
    object Categories: Screen("categories")
    object Tickets: Screen("tickets")
    object Articles: Screen("articles")
}
