package com.washuTechnologies.merced.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.washuTechnologies.merced.R

/**
 * Composable to display an animated loading screen.
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.rocket_loading))
        LottieAnimation(composition, iterations = LottieConstants.IterateForever)
        Text(
            text = stringResource(id = R.string.loading_generic_message),
            style = MaterialTheme.typography.h4
        )
    }
}

@Preview
@Composable
private fun Preview() {
    LoadingScreen()
}
