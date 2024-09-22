package com.example.trackmate

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trackmate.View.HomeScreen
import com.example.trackmate.View.ProgressScreen
import com.example.trackmate.View.SettingsScreen


@Composable
fun Navigation(){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = SCREENS.HOME.value) {

        composable(route = SCREENS.HOME.value){
            HomeScreen(
                navController = navController,
                screenId = SCREENS.HOME
            )
        }

        composable(route = SCREENS.PROGRESS.value){
            ProgressScreen(
                navController = navController,
                screenId = SCREENS.PROGRESS
            )
        }

        composable(route = SCREENS.SETTINGS.value){
            SettingsScreen(navController = navController, screenId = SCREENS.SETTINGS)
        }

    }
}