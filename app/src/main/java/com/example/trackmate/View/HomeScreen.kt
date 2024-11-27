package com.example.trackmate.View

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.trackmate.SCREENS
import com.example.trackmate.ViewModel.HomeScreenViewModel
import dagger.hilt.android.AndroidEntryPoint


@Composable
fun HomeScreen(
    navController: NavController,
    screenId: SCREENS
){

    val homeScreenViewModel: HomeScreenViewModel = hiltViewModel()

    val topBarHeading by homeScreenViewModel.topBarHeading

    var selectedDate by remember{ mutableLongStateOf(homeScreenViewModel.currentDate) }

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
                CalendarRow(homeScreenViewModel, selectedDate){ date->
                    selectedDate = date
                }
            }

            //Habits
            Column {
                HabitList(
                    viewModel = homeScreenViewModel,
                    onClickHabitCard = {},
                    onClickCheckBoxToDelete = {},
                    onClickCheckBoxToAdd = {}
                )
            }


        }
    }
}