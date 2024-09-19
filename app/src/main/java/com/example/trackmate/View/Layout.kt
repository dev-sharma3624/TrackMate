package com.example.trackmate.View

import android.provider.CalendarContract.Calendars
import androidx.annotation.ColorRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.CalendarLocale
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trackmate.R

@Preview(showBackground = true)
@Composable
fun LayoutStructure(){
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {TopBar(heading = "Today", isBackButtonRequired = true)}
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.Red)
        ) {

            //Calendar Row
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Yellow)
            ) {

            }

            //Habits


        }

    }
}


@Composable
fun CalendarRow(){

}

@Composable
fun TopBar(
    heading: String,
    isBackButtonRequired: Boolean
){

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Cyan),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        if(isBackButtonRequired){
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = null,
                    modifier = Modifier
                        .size(36.dp)
                        .padding(top = 4.dp)
                )
            }

        }

        Text(
            text = heading,
            modifier = Modifier.padding(16.dp),
            fontSize = 20.sp,
            fontWeight = if(isBackButtonRequired) null else FontWeight.Bold,
            fontFamily = FontFamily.Monospace
        )


        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.padding(end = 16.dp, top = 4.dp)
        ) {
            Icon(imageVector = Icons.Default.AddCircle,
                contentDescription = null,
                tint = Color(0xFFFF0814)
            )
        }

    }

}














