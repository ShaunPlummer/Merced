package com.washuTechnologies.merced

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.washuTechnologies.merced.ui.launchlist.RocketLaunchListScreen
import com.washuTechnologies.merced.ui.theme.MercedTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MercedTheme {
                RocketLaunchListScreen()
            }
        }
    }
}
