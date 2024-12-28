package com.example.trackmate.View

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
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
        topBarIcon = Icons.Default.MoreVert,
        dropDownController = false,
        changeDropdownState = {},
        onClickDropdownItem = {},
        bottomBar = { BottomBar(navController = navController, screenId = screenId)},
        isBackButtonRequired = false,
        backButtonAction = {},
        topBarButtonAction = {},
        content = {}
    )
}