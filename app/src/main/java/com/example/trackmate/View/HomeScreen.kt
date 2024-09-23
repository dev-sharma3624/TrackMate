package com.example.trackmate.View

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.trackmate.SCREENS

@Composable
fun HomeScreen(
    navController: NavController,
    screenId: SCREENS
){

    LayoutStructure(
        topBarHeading = "Today",
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
                CalendarRow()
            }

            //Habits
            Column {
                HabitList(){
                    //TODO: Add action for onClick of card, i.e., update database isChecked property
                }
            }


        }
    }
}