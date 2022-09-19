package com.washuTechnologies.merced.ui.launchdetail

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Article
import androidx.compose.material.icons.sharp.Description
import androidx.compose.material.icons.sharp.Event
import androidx.compose.material.icons.sharp.Movie
import androidx.compose.material.icons.sharp.Public
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.washuTechnologies.merced.R
import com.washuTechnologies.merced.data.AppState
import com.washuTechnologies.merced.ui.components.LoadingScreen
import com.washuTechnologies.merced.ui.components.MercedScaffold
import com.washuTechnologies.merced.ui.components.NotConnectedCard
import com.washuTechnologies.merced.ui.components.WebLinkButton
import com.washuTechnologies.merced.ui.theme.MercedTheme
import com.washuTechnologies.merced.util.SampleData

/**
 * Display a list of rocket launches.
 */
@Composable
fun RocketLaunchDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: RocketLaunchViewModel = hiltViewModel(),
    appState: AppState
) {
    val launchState = viewModel.uiState.collectAsState()
    RocketLaunchDetailScreen(
        modifier = modifier,
        rocketLaunchState = launchState.value,
        isInternetConnected = appState.isInternetConnected
    )
}

@Composable
private fun RocketLaunchDetailScreen(
    modifier: Modifier = Modifier,
    rocketLaunchState: RocketLaunchUiState,
    isInternetConnected: Boolean
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        when (rocketLaunchState) {
            is RocketLaunchUiState.Success -> {
                RocketDetail(
                    launch = rocketLaunchState,
                    isInternetConnected = isInternetConnected
                )
            }
            is RocketLaunchUiState.Error -> {
                Text(text = stringResource(id = R.string.error_generic_message))
            }
            is RocketLaunchUiState.Loading -> {
                LoadingScreen()
            }
            RocketLaunchUiState.InvalidId -> {
                Text(text = stringResource(id = R.string.invalid_launch_id_message))
            }
        }
    }
}

@Composable
private fun RocketDetail(
    modifier: Modifier = Modifier,
    launch: RocketLaunchUiState.Success,
    isInternetConnected: Boolean
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!isInternetConnected) {
            NotConnectedCard()
        }
        Header(
            modifier = Modifier.fillMaxWidth(),
            launchName = launch.name,
            imageUrl = launch.image
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = launch.details ?: "",
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center
        )
        LaunchInfo(launch = launch)
        Links(links = launch.links)
    }
}

@Composable
private fun Header(
    modifier: Modifier = Modifier,
    launchName: String,
    imageUrl: String?
) {
    Column(modifier = modifier) {
        // If image loading fails remove it from the composition
        // Better support offline mode where the launch info may be available
        // but the imager has not previously been cached.
        var imageLoadFiled by remember { mutableStateOf(false) }
        if (imageUrl != null && !imageLoadFiled) {
            AsyncImage(
                modifier = Modifier
                    .heightIn(max = 180.dp)
                    .fillMaxWidth()
                    .clip(shape = MaterialTheme.shapes.medium),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(300)
                    .build(),
                placeholder = painterResource(R.drawable.image_placeholder),
                contentDescription = stringResource(R.string.launch_image_content_description),
                contentScale = ContentScale.Crop,
                onError = {
                    imageLoadFiled = true
                }
            )
        }
        Text(
            text = launchName,
            style = MaterialTheme.typography.h3,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun LaunchInfo(modifier: Modifier = Modifier, launch: RocketLaunchUiState.Success) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            modifier = modifier.padding(start = 4.dp),
            text = stringResource(id = R.string.launch_detail_heading),
            style = MaterialTheme.typography.subtitle1,
        )
        Spacer(modifier = Modifier.size(8.dp))
        LaunchDetail(
            icon = Icons.Sharp.Event,
            label = stringResource(id = R.string.launch_date_heading),
            value = launch.launchDate
        )
        if (launch.staticFireDate != null) {
            LaunchDetail(
                icon = Icons.Sharp.Public,
                label = stringResource(id = R.string.static_fire_date_heading),
                value = launch.staticFireDate
            )
        }
    }
}

@Composable
private fun Links(modifier: Modifier = Modifier, links: LaunchLinks) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            modifier = modifier.padding(start = 4.dp),
            text = stringResource(id = R.string.launch_links_heading),
            style = MaterialTheme.typography.subtitle1,
        )
        Spacer(modifier = Modifier.size(8.dp))
        if (links.wikipedia != null) {
            WebLinkButton(
                icon = Icons.Sharp.Public,
                label = stringResource(id = R.string.link_wiki),
                url = links.wikipedia
            )
        }
        if (links.pressKit != null) {
            WebLinkButton(
                icon = Icons.Sharp.Description,
                label = stringResource(id = R.string.link_press_kit),
                url = links.pressKit
            )
        }
        if (links.article != null) {
            WebLinkButton(
                icon = Icons.Sharp.Article,
                label = stringResource(id = R.string.link_article),
                url = links.article
            )
        }
        if (links.video != null) {
            WebLinkButton(
                icon = Icons.Sharp.Movie,
                label = stringResource(id = R.string.link_youtube),
                url = links.video
            )
        }
    }
}

@Composable
private fun LaunchDetail(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(modifier = Modifier.size(40.dp), imageVector = icon, contentDescription = label)
        Spacer(modifier = Modifier.size(16.dp))
        Column {
            Text(text = label, fontWeight = FontWeight.Bold)
            Text(
                text = value,
                style = MaterialTheme.typography.caption,
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
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
        MercedScaffold {
            RocketLaunchDetailScreen(
                rocketLaunchState = RocketLaunchUiState.fromRocketLaunch(SampleData.rocketLaunch),
                isInternetConnected = false
            )
        }
    }
}
