package com.example.trackmate

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.trackmate.View.HomeScreen
import com.example.trackmate.View.ProgressScreen
import com.example.trackmate.View.SettingsScreen


@Composable
fun Navigation(){

    val tag = "NAMASTE"

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = SCREENS.HOME.value) {

        composable(route = SCREENS.HOME.value){
            HomeScreen(
                navController = navController,
                screenId = SCREENS.HOME
            )
        }

        composable(route = SCREENS.PROGRESS.value){
            Log.d(tag, "Inside Navigation going to Progress Screen")
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