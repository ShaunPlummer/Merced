package com.washuTechnologies.merced.ui.launchlist

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.washuTechnologies.merced.api.launches.RocketLaunch
import com.washuTechnologies.merced.ui.theme.MercedTheme
import timber.log.Timber

/**
 * Display a list of rocket launches.
 */
@Composable
fun RocketLaunchListScreen(viewModel: LaunchListViewModel = hiltViewModel()) {
    val list = viewModel.uiState.collectAsState()
    RocketLaunchListScreen(launchList = list.value)
}

@Composable
fun RocketLaunchListScreen(launchList: LaunchListUiState) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Timber.d("RocketLaunchList collect $launchList")
        when (launchList) {
            is LaunchListUiState.Success -> {
                LaunchList(launchList = launchList.launchList)
            }
            is LaunchListUiState.Error -> {
                Text(text = "Error")
            }
            is LaunchListUiState.Loading -> {
                Text(text = "Loading")
            }
        }
    }
}

@Composable
private fun LaunchList(launchList: Array<RocketLaunch>) {
    LazyColumn {
        items(items = launchList) { item ->
            LaunchSummary(launch = item)
        }
    }
}

@Composable
private fun LaunchSummary(modifier: Modifier = Modifier, launch: RocketLaunch) {
    Row(
        modifier = modifier
            .padding(vertical = 8.dp)
            .padding(start = 16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LaunchNumber(modifier = Modifier.padding(end = 16.dp), flightNumber = launch.flightNumber)
        Text(text = launch.name, style = MaterialTheme.typography.subtitle1)
    }
}

@Composable
private fun LaunchNumber(modifier: Modifier = Modifier, flightNumber: Int) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colors.primary)
        )
        Text(
            text = "$flightNumber",
            color = contentColorFor(backgroundColor = MaterialTheme.colors.primary)
        )
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
        RocketLaunchListScreen(
            launchList = LaunchListUiState.Success(
                arrayOf(
                    RocketLaunch(1, "FalconSat", "2006-03-24T22:30:00.000Z"),
                    RocketLaunch(2, "DemoSat", "2007-03-21T01:10:00.000Z"),
                    RocketLaunch(3, "Trailblazer", "2008-09-28T23:15:00.000Z")
                )
            )
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun LoadingPreview() {
    MercedTheme {
        RocketLaunchListScreen(
            launchList = LaunchListUiState.Loading
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ErrorPreview() {
    MercedTheme {
        RocketLaunchListScreen(
            launchList = LaunchListUiState.Error
        )
    }
}