package com.example.trackmate.View

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.trackmate.Data.DateUtils

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
    val dateUtils = DateUtils()
    val dateList = dateUtils.createDateList()

    var selectedDate by remember{ mutableLongStateOf(dateUtils.getCurrentDateInLong()) }

    val lazyRowState = rememberLazyListState(
        initialFirstVisibleItemIndex = 47
    )

    LazyRow(
        state = lazyRowState
    ){
        items(dateList){
            DateCard(
                dateUtils = dateUtils,
                dateInLong = it,
                isSelected = selectedDate == it,
                onClickCard = {
                    date->
                    selectedDate = date
                }
            )
        }
    }
}

@Composable
fun DateCard(
    dateUtils: DateUtils,
    dateInLong: Long,
    isSelected: Boolean,
    onClickCard: (Long)->Unit
){

    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(56.dp),
        onClick = {
            onClickCard(dateInLong)
        },
        colors = CardColors(
            containerColor = if(isSelected) Color.White else Colors.LIGHT_GREY,
            contentColor = Colors.DARK_GREY,
            disabledContentColor = Colors.DARK_GREY,
            disabledContainerColor = Colors.LIGHT_GREY
        ),
        border = if(isSelected) BorderStroke(width = 1.dp, color = Colors.LIGHT_RED) else null,

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = dateUtils.getDate(dateInLong),
                color = if(isSelected) Colors.LIGHT_RED else Color.Unspecified
            )
            Spacer(modifier = Modifier.padding(vertical = 2.dp))
            Text(
                text = dateUtils.getDay(dateInLong),
                color = if(isSelected) Colors.LIGHT_RED else Color.Unspecified
            )
        }
    }

}
























