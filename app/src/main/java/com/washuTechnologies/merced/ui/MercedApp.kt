package com.washuTechnologies.merced.ui

import androidx.compose.runtime.Composable
import com.washuTechnologies.merced.ui.components.MercedScaffold
import com.washuTechnologies.merced.ui.navigation.MercedNavGraph
import com.washuTechnologies.merced.ui.theme.MercedTheme

@Composable
fun MercedApp() {
    MercedTheme {
        MercedScaffold {
            MercedNavGraph()
        }
    }
}
