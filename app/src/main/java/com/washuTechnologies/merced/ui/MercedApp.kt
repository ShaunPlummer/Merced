package com.washuTechnologies.merced.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.washuTechnologies.merced.data.AppState
import com.washuTechnologies.merced.ui.components.MercedScaffold
import com.washuTechnologies.merced.ui.navigation.MercedNavGraph
import com.washuTechnologies.merced.ui.theme.MercedTheme

/**
 * Root composable.
 */
@Composable
fun MercedApp(
    viewModel: AppViewModel = hiltViewModel(),
) {
    val state = viewModel.appState.collectAsState(AppState.DEFAULT)
    MercedTheme {
        MercedScaffold {
            MercedNavGraph(appState = state.value)
        }
    }
}
