package com.washuTechnologies.merced.ui.launchdetail

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.washuTechnologies.merced.R
import com.washuTechnologies.merced.ui.theme.MercedTheme
import com.washuTechnologies.merced.util.SampleData

/**
 * Display a list of rocket launches.
 */
@Composable
fun RocketLaunchDetailScreen(
    viewModel: RocketLaunchViewModel = hiltViewModel()
) {
    val launchState = viewModel.uiState.collectAsState()
    RocketLaunchDetailScreen(rocketLaunchState = launchState.value)
}

@Composable
fun RocketLaunchDetailScreen(
    rocketLaunchState: RocketLaunchUiState
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        when (rocketLaunchState) {
            is RocketLaunchUiState.Success -> {
                Text(text = "$rocketLaunchState")
            }
            is RocketLaunchUiState.Error -> {
                Text(text = stringResource(id = R.string.error_generic_message))
            }
            is RocketLaunchUiState.Loading -> {
                Text(text = stringResource(id = R.string.loading_generic_message))
            }
            RocketLaunchUiState.InvalidId -> {
                Text(text = stringResource(id = R.string.invalid_launch_id_message))
            }
        }
    }
}

@Preview(
    name = "Dark theme Launch List",
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Preview(
    name = "Light theme Launch List in German",
    locale = "DE",
    fontScale = 2f
)
@Composable
private fun Preview() {
    MercedTheme {
        RocketLaunchDetailScreen(
            rocketLaunchState = RocketLaunchUiState.Success(
                SampleData.rocketLaunch,
            )
        )
    }
}
