package com.example.trackmate.View

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TopBar(
    heading: String,
    isBackButtonRequired: Boolean
){

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
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
            fontSize = if(isBackButtonRequired) 20.sp else 32.sp,
            fontWeight = if(isBackButtonRequired) null else FontWeight.Bold
        )


        IconButton(
            onClick = { /*TODO*/ },
            modifier = Modifier.padding(end = 16.dp, top = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.AddCircle,
                contentDescription = null,
                tint = Colors.RED,
                modifier = Modifier.size(30.dp)
            )
        }

    }

}