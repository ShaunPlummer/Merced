package com.washuTechnologies.merced.ui.launchdetail

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.washuTechnologies.merced.R
import com.washuTechnologies.merced.ui.components.MercedScaffold
import com.washuTechnologies.merced.ui.theme.MercedTheme
import com.washuTechnologies.merced.util.SampleData

/**
 * Display a list of rocket launches.
 */
@Composable
fun RocketLaunchDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: RocketLaunchViewModel = hiltViewModel()
) {
    val launchState = viewModel.uiState.collectAsState()
    RocketLaunchDetailScreen(
        modifier = modifier,
        rocketLaunchState = launchState.value
    )
}

@Composable
fun RocketLaunchDetailScreen(
    modifier: Modifier = Modifier,
    rocketLaunchState: RocketLaunchUiState
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        when (rocketLaunchState) {
            is RocketLaunchUiState.Success -> {
                RocketDetail(launch = rocketLaunchState)
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

@Composable
fun RocketDetail(modifier: Modifier = Modifier, launch: RocketLaunchUiState.Success) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Header(launchName = launch.name, photo = launch.image)
        Text(text = launch.details ?: "", style = MaterialTheme.typography.body1)
        Spacer(modifier = Modifier.height(50.dp))
    }
}

@Composable
fun Header(modifier: Modifier = Modifier, launchName: String, photo: String?) {
    if (photo != null) {
        Row(modifier = modifier) {
            AsyncImage(
                modifier = Modifier
                    .heightIn(max = 180.dp)
                    .fillMaxWidth()
                    .clip(shape = MaterialTheme.shapes.medium),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(photo)
                    .crossfade(300)
                    .build(),
                placeholder = painterResource(R.drawable.image_placeholder),
                contentDescription = stringResource(R.string.launch_image_content_description),
                contentScale = ContentScale.Crop,
            )
        }
    }
    Text(text = launchName, style = MaterialTheme.typography.h3)
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
        MercedScaffold {
            RocketLaunchDetailScreen(
                rocketLaunchState = RocketLaunchUiState.fromRocketLaunch(SampleData.rocketLaunch)
            )
        }
    }
}
