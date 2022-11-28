package com.washuTechnologies.merced.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.washuTechnologies.merced.data.AppState
import com.washuTechnologies.merced.domain.connectivity.GetConnectivityStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * View model for globally held state information.
 */
@HiltViewModel
class AppViewModel @Inject constructor(
    getConnectivityStateUseCase: GetConnectivityStateUseCase
) : ViewModel() {

    val appState: Flow<AppState> = getConnectivityStateUseCase().map {
        AppState(it)
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        AppState.DEFAULT
    )
}
