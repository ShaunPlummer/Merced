package com.washuTechnologies.merced.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun WebLinkButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    label: String,
    url: String
) {
    val uriHandler = LocalUriHandler.current
    LabeledIconButton(
        modifier = modifier.clickable {
            uriHandler.openUri(url)
        },
        icon = icon,
        label = label
    )
}


@Composable
fun LabeledIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    label: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(modifier = Modifier.size(40.dp), imageVector = icon, contentDescription = label)
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = label)
        Spacer(modifier = Modifier.size(16.dp))
    }
}

@Preview
@Composable
private fun Preview() {
    LabeledIconButton(
        icon = Icons.Filled.Description,
        label = "Press Kit"
    )
}
