package com.example.trackmate.View

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trackmate.Data.HabitInfoWithBooleanValue
import com.example.trackmate.ViewModel.HomeScreenViewModel


@Composable
fun HabitList(
    viewModel: HomeScreenViewModel,
    onClickHabitCard: (Long) -> Unit,
    onClickCheckBoxToDelete: (Long) -> Unit,
    onClickCheckBoxToAdd: (Long) -> Unit
){

    val habitList = viewModel.habitList.collectAsState(initial = emptyList())
//    val habitList by viewModel.dummyHabitList.collectAsState(initial = emptyList())

    LazyColumn {
        /*items(habitList.value){
            HabitItem(
                habit = it,
                onClickHabitCard = {habitId-> onClickHabitCard(habitId)},
                onClickIconButton = {habitId->
                    viewModel.updateHabitList(habitId)
                }
            )
        }*/
        items(habitList.value){
            HabitItem(
                habit = it,
                onClickHabitCard = {habitId-> onClickHabitCard(habitId)},
                onClickIconButtonToDelete = { habitId->
                    onClickCheckBoxToDelete(habitId)
                },
                onClickIconButtonToAdd = { habitId->
                    onClickCheckBoxToAdd(habitId)
                }
            )
        }
    }
}

@Composable
fun HabitItem(
    habit: HabitInfoWithBooleanValue,
    onClickHabitCard: (Long) -> Unit,
    onClickIconButtonToDelete: (Long) -> Unit,
    onClickIconButtonToAdd: (Long) -> Unit
){

    Card(
        onClick = {
            onClickHabitCard(habit.habit.id)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .border(
                color = if (habit.isDoneToday) Color.Transparent else Colors.LIGHT_GREY,
                width = 2.dp,
                shape = RoundedCornerShape(15)
            ),
        colors = if(habit.isDoneToday){
            CardDefaults.cardColors(
                containerColor = Colors.LIGHT_GREY,
                disabledContainerColor = Colors.LIGHT_GREY,
                contentColor = Color.Black,
                disabledContentColor = Color.Black
            )
        }else{
            CardColors(
                containerColor = Color.White,
                disabledContainerColor = Color.White,
                contentColor = Color.Black,
                disabledContentColor = Color.Black
            )
        }
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = habit.habit.habitName,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.padding(vertical = 4.dp))

                Text(
                    text = habit.habit.timeSet,
                    fontSize = 12.sp,
                    color = Colors.MODERATE_GREY
                )
            }

            IconButton(
                onClick = {
                    //if checkbox was ticked when button was pressed
                    if(habit.isDoneToday){
                        onClickIconButtonToDelete(habit.habit.id)
                    }else{
                        onClickIconButtonToAdd(habit.habit.id)
                    }
                },

            ) {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = null,
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            shape = CircleShape,
                            color = if (habit.isDoneToday) Color.Transparent else Colors.MODERATE_GREY
                        )
                        .background(
                            color = if (habit.isDoneToday) Colors.RED else Color.Transparent,
                            shape = CircleShape
                        )
                        .padding(2.dp)
                        .size(16.dp),
                    tint = if(habit.isDoneToday) Color.White else Colors.MODERATE_GREY
                )
            }
        }
    }
}












