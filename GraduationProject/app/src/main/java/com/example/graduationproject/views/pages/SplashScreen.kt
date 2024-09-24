package com.example.graduationproject.views.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onTimeout: () -> Unit) {

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = com.example.graduationproject.ui.theme.Appbarcolor,
        darkIcons = false
    )
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("splash.json"))


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier.fillMaxSize()
        )
    }


    LaunchedEffect(Unit) {
        delay(3000)
        onTimeout()
    }
}
