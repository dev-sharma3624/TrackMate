package com.example.trackmate.View

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun LayoutStructure(
    topBarHeading: String,
    isBackButtonRequired: Boolean,
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (padding: PaddingValues) -> Unit,
    topBarButtonAction: () -> Unit
){
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                heading = topBarHeading,
                isBackButtonRequired = isBackButtonRequired,
                iconButtonAction = topBarButtonAction
            )
        },
        bottomBar = bottomBar
    ) { padding ->

        content(padding)

    }
}



















