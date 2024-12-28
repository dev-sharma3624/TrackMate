package com.example.trackmate.View

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopBar(
    heading: String,
    icon: ImageVector,
    isBackButtonRequired: Boolean,
    backButtonAction: () -> Unit,
    iconButtonAction: () -> Unit,
    dropDownController: Boolean,
    changeDropdownState: () -> Unit,
    onClickDropdownItem: (HomeScreenSelectionOperation) -> Unit
){

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        if(isBackButtonRequired){
            IconButton(onClick = { backButtonAction() }) {
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
            fontSize = if(isBackButtonRequired) 20.sp else 32.sp,
            fontWeight = if(isBackButtonRequired) null else FontWeight.Bold
        )

        Box {

            IconButton(
                onClick = { iconButtonAction() },
                modifier = Modifier.padding(end = 16.dp, top = 4.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Colors.RED,
                    modifier = Modifier.size(30.dp)
                )
            }

            DropdownMenu(
                expanded = dropDownController,
                onDismissRequest = { changeDropdownState() }
            ) {

                DropdownMenuItem(text = { Text(text = "Select all") },
                    onClick = {
                        onClickDropdownItem(HomeScreenSelectionOperation.SELECT_ALL)
                        changeDropdownState()
                    })

                DropdownMenuItem(text = { Text(text = "Delete selected") },
                    onClick = { onClickDropdownItem(HomeScreenSelectionOperation.DELETE_SELECTION)
                        changeDropdownState()
                    })

                DropdownMenuItem(text = { Text(text = "Unselect all") },
                    onClick = { onClickDropdownItem(HomeScreenSelectionOperation.UNSELECT_ALL)
                        changeDropdownState()
                    })
            }

        }



    }

}