package com.example.trackmate.View

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Preview(showBackground = true)
@Composable
fun CalendarRowPreview(){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CalendarRow()
    }
}


@Composable
fun CalendarRow(){
    val calendar = Calendar.getInstance()
    val currentDate = calendar.get(Calendar.DAY_OF_MONTH)
    val day = calendar.get(Calendar.MONTH)
    /*LazyRow {

    }*/
    DateCard(currentDate = currentDate, currentDay = day.toString())
}

@Composable
fun DateCard(
    currentDate: Int,
    currentDay: String
){

    Card(
        modifier = Modifier
            .padding(8.dp),
        onClick = { /*TODO*/ },
        colors = CardColors(
            containerColor = Colors.LIGHT_GREY,
            contentColor = Colors.DARK_GREY,
            disabledContentColor = Colors.DARK_GREY,
            disabledContainerColor = Colors.LIGHT_GREY
        )
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            Text(text = currentDay)
            Text(text = currentDate.toString())
        }
    }

}
























