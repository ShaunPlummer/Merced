package com.washuTechnologies.merced.ui.launchlist

import android.content.res.Configuration
import android.util.Log
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
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.washuTechnologies.merced.api.Result
import com.washuTechnologies.merced.api.launches.RocketLaunch

/**
 * Display a list of rocket launches.
 */
@Composable
fun RocketLaunchListScreen(viewModel: LaunchListViewModel = viewModel()) {
    val list = viewModel.launchList.collectAsState()
    RocketLaunchListScreen(launchList = list.value)
}

@Composable
fun RocketLaunchListScreen(launchList: Result<Array<RocketLaunch>>) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Surface {
            Log.d("UI", "RocketLaunchList collect $launchList")
            when (launchList) {
                is Result.Success -> {
                    LaunchList(launchList = launchList.result)
                }
                is Result.Error -> {
                    Text(text = "Error")
                }
                is Result.Loading -> {
                    Text(text = "Loading")
                }
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
        Text(text = "$flightNumber")
    }
}

@Preview(
    name = "Dark theme Launch List",
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    name = "Light theme Launch List in German",
    showSystemUi = true,
    locale = "DE",
    fontScale = 2f,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun Preview() {
    RocketLaunchListScreen(
        launchList = Result.Success(
            arrayOf(
                RocketLaunch(1, "FalconSat", "2006-03-24T22:30:00.000Z"),
                RocketLaunch(2, "DemoSat", "2007-03-21T01:10:00.000Z"),
                RocketLaunch(3, "Trailblazer", "2008-09-28T23:15:00.000Z")
            )
        )
    )
}

@Preview(
    showSystemUi = true
)
@Composable
private fun LoadingPreview() {
    Column {
        RocketLaunchListScreen(
            launchList = Result.Loading
        )
    }
}

@Preview(
    showSystemUi = true
)
@Composable
private fun ErrorPreview() {
    Column {
        RocketLaunchListScreen(
            launchList = Result.Error
        )
    }
}