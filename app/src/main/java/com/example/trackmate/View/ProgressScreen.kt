package com.example.trackmate.View

import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.trackmate.SCREENS


@Preview(showBackground = true)
@Composable
fun ProgressScreenPreview(){
    ProgressScreen(navController = rememberNavController(), screenId = SCREENS.PROGRESS)
}

@Composable
fun ProgressScreen(
    navController: NavController,
    screenId: SCREENS
){

    LayoutStructure(
        topBarHeading = "Morning Jog",
        bottomBar = {BottomBar(navController = navController, screenId = screenId)},
        isBackButtonRequired = true
    ) {padding->

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
                        Text(
                            text = "59:21",
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
                        text = "Minutes jogged",
                        color = Colors.MODERATE_GREY,
                        fontSize = 15.sp
                    )
                }

                Row {
                    Text(
                        text = "+12m ",
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

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                items(count = 5){
                    Column(
                        modifier = Modifier
                            .height(250.dp)
                            .width(40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Canvas(modifier = Modifier
                            .width(12.dp)
                            .height(180.dp)) {

                            val canvasHeight = size.height
                            val canvasWidth = size.width

                            drawRoundRect(
                                color = Colors.DARK_BLUE,
                                size = Size(width = canvasWidth, height = 300f),
                                topLeft = Offset(x = 0f, y = canvasHeight - 300f),
                                cornerRadius = CornerRadius(100f)
                            )
                        }

                        Column {
                            Text(
                                modifier = Modifier.padding(2.dp),
                                text = "Mon",
                                color = Colors.MODERATE_GREY
                            )

                            Text(
                                text = "09/11",
                                color = Colors.MODERATE_GREY
                            )
                        }
                    }
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
                items(count = 5){
                    EntryCard()
                }
            }
        }

    }

}

@Composable
fun EntryCard(){

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
                text = "59:21",
                fontSize = 15.sp
            )

            //Activity timeStamp
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "08:20 AM - 09:10 Am",
                    color = Colors.MODERATE_GREY
                )
                Text(
                    text = "Sep 9, Thu",
                    color = Colors.MODERATE_GREY
                )
            }
        }
    }
}