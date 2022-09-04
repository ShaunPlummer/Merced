package com.washuTechnologies.merced

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.washuTechnologies.merced.ui.MercedApp
import dagger.hilt.android.AndroidEntryPoint

/**
 * Single entry point for the application.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MercedApp()
        }
    }
}
