package com.example.trackmate.View

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.trackmate.SCREENS

@Composable
fun SettingsScreen(
    navController: NavController,
    screenId: SCREENS
){
    LayoutStructure(
        topBarHeading = "Settings",
        bottomBar = { BottomBar(navController = navController, screenId = screenId)},
        isBackButtonRequired = false,
        topBarButtonAction = {},
        content = {}
    )
}