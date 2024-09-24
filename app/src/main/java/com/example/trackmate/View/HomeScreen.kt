package com.example.trackmate.View

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.trackmate.SCREENS
import com.example.trackmate.ViewModel.HomeScreenViewModel
import kotlin.random.Random

@Preview(showBackground = true)
@Composable
fun HomeScreen(
    navController: NavController = rememberNavController(),
    screenId: SCREENS = SCREENS.HOME
){

    val homeScreenViewModel = HomeScreenViewModel()

    val topBarHeading = remember{ mutableStateOf(homeScreenViewModel.topBarHeading) }

    LayoutStructure(
        topBarHeading = topBarHeading.value,
        bottomBar = { BottomBar(navController = navController, screenId = screenId) },
        isBackButtonRequired = false
    ) {padding->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            //Calendar Row
            Row (
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                CalendarRow(homeScreenViewModel)
            }

            //Habits
            Column {
                HabitList(homeScreenViewModel){
                    //TODO: Navigate to progress screen with the particular activity
                }
            }


        }
    }
}