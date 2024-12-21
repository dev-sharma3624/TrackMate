package com.example.trackmate.View

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.trackmate.ViewModel.HomeScreenViewModel

@Composable
fun CalendarRow(
    viewModel: HomeScreenViewModel,
    selectedDate: Long,
    onValueChanged: (Long) -> Unit
){

    val tag = "NAMASTE"

    val lazyRowState = rememberLazyListState(
        initialFirstVisibleItemIndex = viewModel.dateList.indexOf(selectedDate) - 4
    )

    LazyRow(
        state = lazyRowState
    ){
        items(viewModel.dateList){
            Log.d(tag, "Boolean value $selectedDate and $it")
            Log.d(tag, (selectedDate == it).toString())
            DateCard(
                dateInLong = it,
                isSelected = selectedDate == it,
                onClickDateCard = {
                    date->
                    onValueChanged(date)
                    viewModel.topBarHeadingDecider(it)
                    viewModel.getHabitList(it)
                },
                date = viewModel.getDateInString(it),
                day = viewModel.getDayInString(it)
            )
        }
    }
}

@Composable
fun DateCard(
    dateInLong: Long,
    isSelected: Boolean,
    onClickDateCard: (Long)->Unit,
    date: String,
    day: String
){

    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(56.dp),
        onClick = {
            onClickDateCard(dateInLong)
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
                text = date,
                color = if(isSelected) Colors.LIGHT_RED else Color.Unspecified
            )
            Spacer(modifier = Modifier.padding(vertical = 2.dp))
            Text(
                text = day,
                color = if(isSelected) Colors.LIGHT_RED else Color.Unspecified
            )
        }
    }

}
























