package com.washuTechnologies.merced.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable

/**
 * Main scaffold for use across the app.
 */
@Composable
fun MercedScaffold(
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        content = content
    )
}
