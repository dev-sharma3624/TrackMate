package com.example.trackmate.View

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerColors
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.trackmate.Data.Habit
import com.example.trackmate.ProgressScreenHabit
import com.example.trackmate.SCREENS
import com.example.trackmate.ViewModel.HomeScreenViewModel
import java.util.Calendar


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

    var insertDialogController by remember{ mutableStateOf(false) }

    Log.d(tag, "Inside Home screen selected date initialised to: $selectedDate")

    LayoutStructure(
        topBarHeading = topBarHeading,
        bottomBar = { BottomBar(navController = navController, screenId = screenId) },
        isBackButtonRequired = false,
        topBarButtonAction = {insertDialogController = true},
        content = {padding->

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

                if (insertDialogController){

                    InsertDialog(
                        onDismissRequest = { insertDialogController = false },
                        confirmButton = {habit ->
                            insertDialogController = false
                            homeScreenViewModel.insertHabit(habit)
                        }
                    )

                }


            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun InsertDialogPreview(){
    Column(
        Modifier.fillMaxSize()
    ) {
        InsertDialog({},{} )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertDialog(
    onDismissRequest: () -> Unit,
    confirmButton: (Habit) -> Unit
){
    val currentTime = Calendar.getInstance()
    
    var habitNameText by remember{ mutableStateOf("") }
    val habitTime = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = false
    )

    AlertDialog(
        onDismissRequest = { onDismissRequest() },
        confirmButton = {},
        title = { Text("Create new habit: ")},
        text = {
            Column {
                Text(text = "Enter new habit name: ")
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                TextField(
                    value = habitNameText,
                    onValueChange = {habitNameText = it}
                )

                Spacer(modifier = Modifier.padding(vertical = 16.dp))

                Text(text = "Enter time")
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                TimePicker(
                    state = habitTime,
                    colors = TimePickerDefaults.colors()
                )
                
                Spacer(modifier = Modifier.padding(vertical = 16.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = {
                        val time = habitTime.hour.toString().apply {
                            plus(":")
                            plus(habitTime.minute.toString())
                            if(currentTime.get(Calendar.AM_PM) == Calendar.AM){
                                plus(" AM")
                            }else{
                                plus(" PM")
                            }
                        }

                        val habit = Habit(
                            habitName =  habitNameText,
                            createdOn = currentTime.timeInMillis,
                            timeSet = time
                        )

                        confirmButton(habit)
                    }) {
                        Text(text = "Submit")
                    }
                }

            }
        }
    )
}