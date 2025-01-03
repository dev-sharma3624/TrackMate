package com.example.trackmate.View

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.trackmate.Data.Habit
import com.example.trackmate.Data.HabitInfoWithJournal
import com.example.trackmate.SCREENS
import com.example.trackmate.ViewModel.ProgressViewModel
import kotlin.math.abs

val tagPs = "NAMASTE"

@SuppressLint("UnrememberedMutableState", "DefaultLocale")
@Composable
fun ProgressScreen(
    navController: NavController,
    screenId: SCREENS
){


    val progressViewModel: ProgressViewModel = hiltViewModel()

    val journalEntries by progressViewModel.habitInfoWithJournalEntries.collectAsState(
        HabitInfoWithJournal(
            Habit(habitName = "", createdOn = 0L, timeSet = ""),
            emptyList()
        )
    )

    if(journalEntries.journal.isNotEmpty()){
        progressViewModel.setLatestActivityTime()
        progressViewModel.setGraphPlottingValues()
        progressViewModel.setDeviationFromLastWeek()
    }

    val graphPlottingValues by progressViewModel.graphPlottingValues.collectAsState()

    val latestActivityTime by progressViewModel.latestActivityTime.collectAsState()


    Log.d(tagPs, "value of graph plotting values: ${graphPlottingValues.toString()}")

    val maxGraphValues by derivedStateOf {
        Pair(graphPlottingValues?.maxOf {
            it.second
        },
        graphPlottingValues?.maxOf {
            it.first
        })
    }

    Log.d(tagPs, "Value of maxGraphValue: $maxGraphValues")

    val deviationFromLastWeek by progressViewModel.deviationFromLastWeek.collectAsState()



    LayoutStructure(
        topBarHeading = journalEntries.habit.habitName,
        topBarIcon = Icons.Default.MoreVert,
        dropDownController = false,
        changeDropdownState = {},
        onClickDropdownItem = {},
        bottomBar = {BottomBar(navController = navController, screenId = screenId)},
        isBackButtonRequired = true,
        backButtonAction = {navController.navigate(SCREENS.HOME.value)},
        topBarButtonAction = {},
        content = {padding->

            Column(
                modifier = Modifier.padding(padding)
            ) {

                Column(
                    modifier = Modifier.weight(0.2f)
                ) {
                    Spacer(modifier = Modifier.padding(vertical = 24.dp))

                    //Time/Number display
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .weight(0.8f)
                    ) {
                        Log.d(tagPs, "inside timer")

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                //big size time display
                                Text(
                                    text = latestActivityTime,
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = 60.sp,
                                    color = Colors.DARK_BLUE
                                )


                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = null,
                                    tint = Colors.RED
                                )
                            }

                            Spacer(modifier = Modifier.padding(8.dp))

                            Row(
                                modifier = Modifier.weight(1f),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Text(
                                    text = if(deviationFromLastWeek > 0){
                                        "+${deviationFromLastWeek.toInt()}m"
                                    }else if (deviationFromLastWeek == 0f){
                                        "No change"
                                    }else{
                                        "${deviationFromLastWeek.toInt()}m"
                                    },
                                    color = Colors.RED,
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(end = 4.dp)
                                )

                                Text(
                                    text = " this week",
                                    color = Colors.MODERATE_GREY,
                                    fontSize = 16.sp
                                )
                            }

                        }

                        //Activity text
                        Text(
                            text = "Minutes",
                            color = Colors.MODERATE_GREY,
                            fontSize = 15.sp,
                            modifier = Modifier.padding(start = 6.dp).weight(0.2f)
                        )

                    }


                }

                Column(
                    modifier = Modifier.weight(0.3f)
                ) {
                    //Week + Graph selector
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .weight(0.1f),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Log.d(tagPs, "inside graph selector")

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Graph",
                                color = Colors.MODERATE_GREY,
                                fontWeight = FontWeight.Bold
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = Colors.LIGHT_RED
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Lines",
                                color = Colors.MODERATE_GREY,
                                fontWeight = FontWeight.Bold
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = Colors.LIGHT_RED
                            )
                        }
                    }

                    //Graph

                    //Line Graph
                    Row(
                        modifier = Modifier
                            .weight(0.9f)
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        graphPlottingValues?.forEach {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Canvas(modifier = Modifier
                                    .weight(0.8f)
                                    .padding(16.dp)) {

                                    drawLine(
                                        color = if(maxGraphValues.second!! == it.first)
                                            Colors.RED else Colors.DARK_BLUE,
                                        start = Offset(x = size.width,
                                            y = if(it.second > maxGraphValues.first!!) maxGraphValues.first!!
                                            else it.second * 6f
                                        ),
                                        end = Offset(x = size.width, y = size.height),
                                        strokeWidth = 60f,
                                        cap = StrokeCap.Round
                                    )

                                }

                                Text(
                                    text = progressViewModel.getDayTextForGraph(it.first),
                                    modifier = Modifier.weight(0.1f),
                                    textAlign = TextAlign.Justify
                                )

                                Text(
                                    text = progressViewModel.getDateTextForGraph(it.first),
                                    modifier = Modifier.weight(0.1f),
                                    textAlign = TextAlign.Justify
                                )
                            }
                        }

                    }
                }


                Column(
                    modifier = Modifier.weight(0.4f)
                ) {

                    //Entries headline + pdf downloader
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .weight(0.2f),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Log.d(tagPs, "inside entry headline")
                        Text(
                            text = "Entries"
                        )
                        Text(
                            text = "Pdf",
                            color = Color.White,
                            modifier = Modifier
                                .background(
                                    color = Colors.RED,
                                    shape = CircleShape
                                )
                                .padding(horizontal = 8.dp)
                        )
                    }

                    //Entries List
                    LazyColumn(
                        modifier = Modifier.weight(0.8f)
                    ) {
                        Log.d(tagPs, "inside entry list")
                        items(journalEntries.journal){
                            EntryCard(
                                activityTime = progressViewModel.convertFloatToTimeString(it.timePeriod),
                                activityTimeStamp = progressViewModel.createTimeStamp(it.doneOn, it.timePeriod),
                                dateStamp = progressViewModel.createDateStamp(it.doneOn)
                            )
                        }
                    }
                }
            }

        }
    )

}

@Composable
fun EntryCard(
    activityTime: String,
    activityTimeStamp: String,
    dateStamp: String
){

    Card(
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
        colors = CardColors(
            containerColor = Colors.LIGHT_GREY,
            disabledContainerColor = Colors.LIGHT_GREY,
            contentColor = Color.Unspecified,
            disabledContentColor = Color.Unspecified
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            //Activity timeline/ last done log
            Text(
                text = activityTime,
                fontSize = 15.sp
            )

            //Activity timeStamp
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = activityTimeStamp,
                    color = Colors.MODERATE_GREY
                )
                Text(
                    text = dateStamp,
                    color = Colors.MODERATE_GREY
                )
            }
        }
    }
}