package com.example.trackmate.View

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.trackmate.ProgressScreenHabit
import com.example.trackmate.SCREENS
import com.example.trackmate.ViewModel.HomeScreenViewModel


@Composable
fun HomeScreen(
    navController: NavController,
    screenId: SCREENS
){
    val tag = "NAMASTE"

    Log.d(tag, "Inside Home Screen")

    val homeScreenViewModel: HomeScreenViewModel = hiltViewModel()

    val topBarHeading by homeScreenViewModel.topBarHeading

    var selectedDate by remember{ mutableLongStateOf(homeScreenViewModel.currentDate) }

    Log.d(tag, "Inside Home screen selected date initialised to: $selectedDate")

    LayoutStructure(
        topBarHeading = topBarHeading,
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
                Log.d(tag, "Value of selectedDate passed to CalendarRow: $selectedDate")
                CalendarRow(homeScreenViewModel, selectedDate){ date->
                    selectedDate = date
                    homeScreenViewModel.getHabitList(selectedDate)

                }
            }

            //Habits
            Column {
                HabitList(
                    viewModel = homeScreenViewModel,
                    onClickHabitCard = {habitId->
                        ProgressScreenHabit.id = habitId
                        navController.navigate(SCREENS.PROGRESS)
                    },
                    onClickCheckBoxToDelete = {habitId->
                        homeScreenViewModel.deleteHabitJournal(
                            deletedHabitId = habitId,
                            date = selectedDate
                        )
                    },
                    onClickCheckBoxToAdd = {
                        //todo: when  checkbox is ticked back we need time from between
                        //todo: which the activity was performed hence we need to create
                        //todo: a dialog/screen to get that time before creating an entry
                    }
                )
            }


        }
    }
}