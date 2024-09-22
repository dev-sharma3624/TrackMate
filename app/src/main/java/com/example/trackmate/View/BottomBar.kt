package com.example.trackmate.View

import android.hardware.camera2.CameraExtensionSession.StillCaptureLatency
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.trackmate.R
import com.example.trackmate.SCREENS

@Preview(showBackground = true)
@Composable
fun BottomBarPreview(){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        BottomBar(
            navController = rememberNavController(),
            screenId = SCREENS.HOME
        )
    }
}

@Composable
fun BottomBar(
    navController: NavController,
    screenId: SCREENS
) {

    Row(
        modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        //HomeButton
        BottomBarButton(
            content = {
                Icon(
                    painter = painterResource(id = R.drawable.outline_home_24),
                    contentDescription = null,
                    tint = if(screenId == SCREENS.HOME) Colors.LIGHT_RED else Colors.MODERATE_GREY
                )
                Text(
                    text = SCREENS.HOME.value,
                    color = if(screenId == SCREENS.HOME) Colors.LIGHT_RED else Colors.MODERATE_GREY
                )
            },
            onClick = {
                navController.navigate(route = SCREENS.HOME.value)
            }
        )

        //ProgressButton
        BottomBarButton(
            content = {
                Icon(
                    painter = painterResource(id = R.drawable.outline_assessment_24),
                    contentDescription = null,
                    tint = if(screenId == SCREENS.PROGRESS) Colors.LIGHT_RED else Colors.MODERATE_GREY
                )
                Text(
                    text = SCREENS.PROGRESS.value,
                    color = if(screenId == SCREENS.PROGRESS) Colors.LIGHT_RED else Colors.MODERATE_GREY
                )
            },
            onClick = {
                navController.navigate(route = SCREENS.PROGRESS.value)
            }
        )

        //SettingsButton
        BottomBarButton(
            content = {
                Icon(
                    painter = painterResource(id = R.drawable.outline_settings_24),
                    contentDescription = null,
                    tint = if(screenId == SCREENS.SETTINGS) Colors.LIGHT_RED else Colors.MODERATE_GREY
                )
                Text(
                    text = SCREENS.SETTINGS.value,
                    color = if(screenId == SCREENS.SETTINGS) Colors.LIGHT_RED else Colors.MODERATE_GREY
                )
            },
            onClick = {
                navController.navigate(route = SCREENS.SETTINGS.value)
            }
        )

    }
}

@Composable
fun BottomBarButton(
    content: @Composable () -> Unit,
    onClick: () -> Unit
){

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.clickable { onClick() }
    ) {
        content()
    }

}
