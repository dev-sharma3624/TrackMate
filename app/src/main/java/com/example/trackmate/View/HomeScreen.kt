package com.example.trackmate.View

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.trackmate.Data.HabitJournal
import com.example.trackmate.ProgressScreenHabit
import com.example.trackmate.SCREENS
import com.example.trackmate.ViewModel.HomeScreenViewModel
import java.util.Calendar
import kotlin.random.Random


val tag = "NAMASTE"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    screenId: SCREENS
){

    val homeScreenViewModel: HomeScreenViewModel = hiltViewModel()

    val topBarHeading by homeScreenViewModel.topBarHeading

    var selectedDate by remember{ mutableLongStateOf(homeScreenViewModel.currentDate) }

    var insertDialogController by remember{ mutableStateOf(false) }

    var topBarIcon by remember { mutableStateOf(Icons.Default.AddCircle) }

    var selectionOperation by remember { mutableStateOf<HomeScreenSelectionOperation?>(null) }

    var dropDownController by remember { mutableStateOf(false) }



    LayoutStructure(
        topBarHeading = topBarHeading,
        topBarIcon = topBarIcon,
        dropDownController = dropDownController,
        changeDropdownState = {dropDownController = !dropDownController},
        onClickDropdownItem = {selectionOperation = it},
        bottomBar = { BottomBar(navController = navController, screenId = screenId) },
        isBackButtonRequired = topBarIcon == Icons.Default.MoreVert,
        backButtonAction = {
            selectionOperation = HomeScreenSelectionOperation.UNSELECT_ALL
        },
        topBarButtonAction = {
            if (topBarIcon == Icons.Default.MoreVert){
                dropDownController = true
            }else{
                insertDialogController = true
            }
        },
        content = {padding->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {

                //Calendar Row
                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Absolute.Right
                ) {
                    CalendarRow(homeScreenViewModel, selectedDate){ date->
                        Log.d(tag, "Value of expression ${topBarIcon != Icons.Default.MoreVert}")
                        if(topBarIcon != Icons.Default.MoreVert){
                            selectedDate = date
                            homeScreenViewModel.getHabitList(selectedDate)
                            homeScreenViewModel.topBarHeadingDecider(selectedDate)
                        }
                    }
                }

                //Habits
                Column {
                    HabitList(
                        viewModel = homeScreenViewModel,
                        onClickHabitCard = {habitId->
                            if(topBarIcon != Icons.Default.MoreVert){
                                ProgressScreenHabit.id = habitId
                                navController.navigate(SCREENS.PROGRESS.value)
                            }
                        },
                        onClickCheckBoxToDelete = {habitId->
                            if(topBarIcon != Icons.Default.MoreVert){
                                homeScreenViewModel.deleteHabitJournal(
                                    deletedHabitId = habitId,
                                    date = selectedDate
                                )
                            }
                        },
                        onClickCheckBoxToAdd = {habitId->
                            if(topBarIcon != Icons.Default.MoreVert){
                                homeScreenViewModel.insertHabitJournal(
                                    HabitJournal(
                                        habitId =  habitId,
                                        doneOn = selectedDate,
                                        timePeriod = Random.nextInt(30, 50) + Random.nextFloat()
                                    )
                                )
                            }
                        },
                        isCardSelectionActivated = {activationStatus->
                            topBarIcon = if (activationStatus){
                                Icons.Default.MoreVert
                            }else{
                                selectionOperation = null
                                Icons.Default.AddCircle
                            }
                        },
                        selectionOperation = selectionOperation
                    )
                }

                if (insertDialogController){

                    InsertDialog(
                        onDismissRequest = {
                            insertDialogController = false
                        },
                        confirmButton = {name, time ->
                            insertDialogController = false

                            homeScreenViewModel.createHabitForInsertion(
                                habitName = name,
                                hour = time.hour,
                                min = time.minute
                            )
                        }
                    )

                }

                else if (topBarIcon == Icons.Default.MoreVert){
                    BackHandler {
                        selectionOperation = HomeScreenSelectionOperation.UNSELECT_ALL
                    }
                }

            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertDialog(
    onDismissRequest: () -> Unit,
    confirmButton: (String, TimePickerState) -> Unit
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
                    Button(onClick = {confirmButton(habitNameText, habitTime)}) {
                        Text(text = "Submit")
                    }
                }

            }
        }
    )
}