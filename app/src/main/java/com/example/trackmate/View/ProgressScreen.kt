package com.example.trackmate.View

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.trackmate.SCREENS
import com.example.trackmate.ViewModel.ProgressViewModel


@Composable
fun ProgressScreen(
    navController: NavController,
    screenId: SCREENS
){

    val progressViewModel: ProgressViewModel = hiltViewModel()


    val latestActivityTime by progressViewModel.latestActivityTime.collectAsState()
    val deviationFromLastWeek by progressViewModel.deviationFromLastWeek.collectAsState()
    val graphPlottingValues by progressViewModel.graphPlottingValues.collectAsState()

    val maxGraphValue by remember { mutableFloatStateOf(graphPlottingValues.maxOf { it.second }) }

    val journalEntries by progressViewModel.habitInfoWithJournalEntries.collectAsState()

    /*val habitInfoWithJournal by progressViewModel.habitInfoWithJournalEntries.collectAsState(initial = HabitInfoWithJournal(
        Habit(habitName = "", createdOn = 0L, timeSet = ""),
        emptyList()
    )
    )*/

    LayoutStructure(
        topBarHeading = progressViewModel.topBarHeading,
        bottomBar = {BottomBar(navController = navController, screenId = screenId)},
        isBackButtonRequired = true,
        topBarButtonAction = {},
        content = {padding->

            Column(
                modifier = Modifier.padding(padding)
            ) {

                Spacer(modifier = Modifier.padding(vertical = 24.dp))

                //Time/Number display
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {

                            //big size time display
                            Text(
                                text = latestActivityTime,
                                fontFamily = FontFamily.Monospace,
                                fontSize = 60.sp
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = Colors.RED
                            )
                        }

                        //Activity text
                        Text(
                            text = "Minutes",
                            color = Colors.MODERATE_GREY,
                            fontSize = 15.sp
                        )
                    }

                    Row {
                        Text(
                            text = if(deviationFromLastWeek > 0){
                                "+" + deviationFromLastWeek.toString() + "m"
                            }else if (deviationFromLastWeek == 0f){
                                "No change"
                            }else{
                                "-" + deviationFromLastWeek.toString() + "m"
                            },
                            color = Colors.RED,
                            fontSize = 20.sp
                        )

                        Text(
                            text = "this week",
                            color = Colors.MODERATE_GREY,
                            fontSize = 20.sp
                        )
                    }
                }

                //Week + Graph selector
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

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

                /*Canvas(modifier = Modifier.fillMaxWidth().height(250.dp)) {
                    drawRoundRect(
                        color = Colors.DARK_BLUE,
                        size = this.size/5f
                    )
                }*/

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    graphPlottingValues.forEach {
                        LineGraph(
                            height = if(it.second == maxGraphValue){
                                250f
                            }else{
                                it.second * 250f/maxGraphValue
                            },
                            dayText = progressViewModel.getDayTextForGraph(it.first),
                            dateText = progressViewModel.getDateTextForGraph(it.first)
                        )
                    }

                }

                //Entries headline + pdf downloader
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
                LazyColumn {
                    items(journalEntries.journal){
                        EntryCard(
                            activityTime = progressViewModel.formatString(it.timePeriod.toString()),
                            activityTimeStamp = progressViewModel.createTimeStamp(it.doneOn, it.timePeriod),
                            dateStamp = progressViewModel.createDateStamp(it.doneOn)
                        )
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


@Composable
fun LineGraph(
    height: Float,
    dayText: String,
    dateText: String
){


    Column(
        modifier = Modifier
            .height(250.dp)
            .width(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Canvas(modifier = Modifier.weight(0.7f)) {

            val canvasHeight = size.height
            val canvasWidth = size.width

            drawRoundRect(
                color = Colors.DARK_BLUE,
                size = Size(width = canvasWidth, height = height),
                topLeft = Offset(x = 0f, y = canvasHeight - height),
                cornerRadius = CornerRadius(100f)
            )
        }

        Column(
        ) {
            Text(
                modifier = Modifier.padding(2.dp),
                text = dayText,
                color = Colors.MODERATE_GREY
            )

            Text(
                text = dateText,
                color = Colors.MODERATE_GREY
            )
        }
    }
}

@Preview(showBackground = true)
@Composable fun Test(){
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .border(2.dp, Color.Red),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {

            LineGraph(
                300f,
                "Mon",
                "09/11"
            )

        }
    }
}