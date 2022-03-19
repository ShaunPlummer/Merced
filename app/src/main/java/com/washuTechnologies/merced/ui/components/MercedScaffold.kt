package com.washuTechnologies.merced.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.washuTechnologies.merced.R

/**
 * Main scaffold for use across the app.
 */
@Composable
fun MercedScaffold(
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(
                        stringResource(id = R.string.app_name),
                        color = contentColorFor(backgroundColor = MaterialTheme.colors.primary)
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colors.primary
                )
            )
        },
        content = content
    )
}
