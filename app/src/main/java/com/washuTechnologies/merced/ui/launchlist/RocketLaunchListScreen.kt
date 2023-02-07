package com.washuTechnologies.merced.ui.launchlist

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.washuTechnologies.merced.R
import com.washuTechnologies.merced.data.launches.model.RocketLaunch
import com.washuTechnologies.merced.ui.components.LoadingScreen
import com.washuTechnologies.merced.ui.theme.MercedTheme
import com.washuTechnologies.merced.util.SampleData
import timber.log.Timber

/**
 * Screen level composable for viewing a list of all rocket launches.
 */
@Composable
fun RocketLaunchListScreen(
    launchList: LaunchListUiState,
    onLaunchSelected: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        when (launchList) {
            is LaunchListUiState.Success -> {
                LaunchList(launchList = launchList.launchList, onLaunchSelected)
            }
            is LaunchListUiState.Error -> {
                Text(text = stringResource(id = R.string.error_generic_message))
            }
            is LaunchListUiState.Loading -> {
                LoadingScreen()
            }
        }
    }
}

@Composable
private fun LaunchList(
    launchList: Array<RocketLaunch>,
    onLaunchSelected: (String) -> Unit
) {
    LazyColumn {
        items(items = launchList) { item ->
            LaunchSummary(
                modifier = Modifier.clickable {
                    val selectedId = item.id
                    Timber.d("Flight id $selectedId selected")
                    onLaunchSelected(selectedId)
                },
                launch = item
            )
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
            launchList = LaunchListUiState.Success(SampleData.launchList)
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