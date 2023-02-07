package com.washuTechnologies.merced.ui.launchdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.washuTechnologies.merced.data.launches.RocketLaunchRepository
import com.washuTechnologies.merced.di.IoDispatcher
import com.washuTechnologies.merced.ui.navigation.RocketLaunchArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * View model for the list of rocket launches
 */
@HiltViewModel
class RocketLaunchViewModel @Inject constructor(
    launchRepository: RocketLaunchRepository,
    savedStateHandle: SavedStateHandle,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val args = RocketLaunchArgs(savedStateHandle)
    private val launchId by lazy { args.launchId }

    private val _uiState = MutableStateFlow<RocketLaunchUiState>(RocketLaunchUiState.Loading)

    /**
     * Exposes the current state of the launch list screen.
     */
    val uiState: StateFlow<RocketLaunchUiState>
        get() = _uiState

    init {
        viewModelScope.launch(dispatcher) {
            if (launchId.isBlank()) {
                Timber.e("Unable to show launch info for a blank id")
                _uiState.emit(RocketLaunchUiState.InvalidId)
                return@launch
            }
            // Prevent a flicker where the loading animation isn't displayed long enough to be seen
            // Let users see the fancy animation for a moment.
            delay(300)
            launchRepository.getRocketLaunch(launchId).collect {
                Timber.d("Launch list state $it.")
                val state = if (it != null) {
                    RocketLaunchUiState.fromRocketLaunch(it)
                } else {
                    RocketLaunchUiState.Error
                }
                _uiState.emit(state)
            }
        }
    }
}

