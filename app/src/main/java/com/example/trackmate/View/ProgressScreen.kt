package com.example.trackmate.View

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.trackmate.SCREENS

@Composable
fun ProgressScreen(
    navController: NavController,
    screenId: SCREENS
){

    LayoutStructure(
        topBarHeading = "Progress",
        bottomBar = {BottomBar(navController = navController, screenId = screenId)},
        isBackButtonRequired = false
    ) {

    }

}