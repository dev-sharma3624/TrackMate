package com.example.trackmate.View

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.trackmate.Data.HabitInfoWithBooleanValue
import com.example.trackmate.ViewModel.HomeScreenViewModel

val taghl = "NAMASTE"

@Composable
fun HabitList(
    viewModel: HomeScreenViewModel,
    onClickHabitCard: (Long) -> Unit,
    onClickCheckBoxToDelete: (Long) -> Unit,
    onClickCheckBoxToAdd: (Long) -> Unit,
    isCardSelectionActivated: (Boolean) -> Unit,
    selectionOperation: HomeScreenSelectionOperation?
){

    val habitList = viewModel.habitList.collectAsState(initial = emptyList())

//    val habitList by viewModel.dummyHabitList.collectAsState(initial = emptyList())

    val haptics = LocalHapticFeedback.current

    var selectedHabitCards by remember { mutableStateOf(listOf<Long>()) }
    var cardSelectionController by remember { mutableStateOf(false) }

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
                },
                onLongPressCard = {id->

                    selectedHabitCards = selectedHabitCards.plus(id)
                    cardSelectionController = true
                    isCardSelectionActivated(cardSelectionController)
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                },
                cardSelectionController = cardSelectionController,
                habitStatus = it.habit.id in selectedHabitCards,
                removeHabitFromSelection = {id->
                    selectedHabitCards = selectedHabitCards.minus(id)
                    if (selectedHabitCards.isEmpty()){
                        cardSelectionController = false
                        isCardSelectionActivated(cardSelectionController)
                    }
                },
                addHabitToSelection = {id->
                    selectedHabitCards = selectedHabitCards.plus(id)
                }
            )
        }
    }

    when (selectionOperation){
        HomeScreenSelectionOperation.SELECT_ALL -> {
            val tempList = mutableListOf<Long>()
            habitList.value.forEach {
                tempList.add(it.habit.id)
            }
            selectedHabitCards = tempList
        }

        HomeScreenSelectionOperation.DELETE_SELECTION ->{
            selectedHabitCards.forEach {
                viewModel.deleteHabit(it)
            }
        }

        HomeScreenSelectionOperation.UNSELECT_ALL ->{
            selectedHabitCards = emptyList()
            cardSelectionController = false
            isCardSelectionActivated(cardSelectionController)
        }

        else -> {}
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HabitItem(
    habit: HabitInfoWithBooleanValue,
    onClickHabitCard: (Long) -> Unit,
    onClickIconButtonToDelete: (Long) -> Unit,
    onClickIconButtonToAdd: (Long) -> Unit,
    onLongPressCard: (Long) -> Unit,
    cardSelectionController: Boolean,
    habitStatus: Boolean,
    removeHabitFromSelection: (Long) -> Unit,
    addHabitToSelection: (Long) -> Unit
){

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        if(cardSelectionController){
            CustomCheckBox(
                habitStatus = habitStatus,
                onClickIconButtonToDelete = { removeHabitFromSelection(habit.habit.id) },
                onClickIconButtonToAdd = { addHabitToSelection(habit.habit.id) }
            )
        }

        Card(
            onClick = {},
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
            Box(
                modifier = Modifier.combinedClickable(
                    onClick = { onClickHabitCard(habit.habit.id) },
                    onLongClick = {
                        onLongPressCard(habit.habit.id)
                    }
                )
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

                    CustomCheckBox(
                        habitStatus = habit.isDoneToday,
                        onClickIconButtonToAdd = {onClickIconButtonToAdd(habit.habit.id)},
                        onClickIconButtonToDelete = {onClickIconButtonToDelete(habit.habit.id)}
                    )
                }
            }
        }

    }
}

@Composable
fun CustomCheckBox(
    habitStatus: Boolean,
    onClickIconButtonToDelete: () -> Unit,
    onClickIconButtonToAdd: () -> Unit
){

    IconButton(
        onClick = {
            //if checkbox was ticked when button was pressed
            if(habitStatus){
                onClickIconButtonToDelete()
            }else{
                onClickIconButtonToAdd()
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
                    color = if (habitStatus) Color.Transparent else Colors.MODERATE_GREY
                )
                .background(
                    color = if (habitStatus) Colors.RED else Color.Transparent,
                    shape = CircleShape
                )
                .padding(2.dp)
                .size(16.dp),
            tint = if(habitStatus) Color.White else Colors.MODERATE_GREY
        )
    }

}












