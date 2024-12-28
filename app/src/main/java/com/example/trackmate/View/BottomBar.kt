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
import androidx.compose.ui.graphics.Color
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

    val screenParams = mapOf(
        SCREENS.HOME to R.drawable.outline_home_24,
        SCREENS.PROGRESS to R.drawable.outline_assessment_24,
        SCREENS.SETTINGS to R.drawable.outline_settings_24
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        screenParams.forEach { (screenName, screenIcon) ->
            BottomBarButton(
                onClick = { screen->
                    navController.navigate(route = screen)
                },
                iconColor = if(screenName.value == screenId.value) Colors.LIGHT_RED else Colors.MODERATE_GREY ,
                screenHeading = screenName.value,
                iconId = screenIcon
            )
        }

    }
}

@Composable
fun BottomBarButton(
    onClick: (String) -> Unit,
    iconColor: Color,
    screenHeading: String,
    iconId: Int
){

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.clickable { onClick(screenHeading) }
    ) {
        Content(iconColor, screenHeading, iconId)
    }

}

@Composable
fun Content(
    iconColor: Color,
    screenHeading: String,
    iconId: Int
){
    Icon(
        painter = painterResource(id = iconId),
        contentDescription = null,
        tint = iconColor
    )
    Text(
        text = screenHeading,
        color = iconColor
    )
}
