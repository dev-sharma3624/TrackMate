package com.example.trackmate.View

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

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



















