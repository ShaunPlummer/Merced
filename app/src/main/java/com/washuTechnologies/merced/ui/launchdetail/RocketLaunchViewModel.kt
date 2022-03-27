package com.washuTechnologies.merced.ui.launchdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.washuTechnologies.merced.api.Result
import com.washuTechnologies.merced.api.launches.RocketLaunch
import com.washuTechnologies.merced.api.launches.RocketLaunchRepository
import com.washuTechnologies.merced.di.IoDispatcher
import com.washuTechnologies.merced.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * View model for the list of rocket launches
 */
@HiltViewModel
class RocketLaunchViewModel @Inject constructor(
    launchRepository: RocketLaunchRepository,
    savedStateHandle: SavedStateHandle,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val launchId: String = savedStateHandle.get<String>(
        Screen.LaunchDetail.launchIdKey
    ) ?: ""

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
            launchRepository.getRocketLaunch(launchId).collect {
                when (it) {
                    is Result.Success -> _uiState.emit(RocketLaunchUiState.Success(it.result))
                    is Result.Loading -> _uiState.emit(RocketLaunchUiState.Loading)
                    is Result.Error -> _uiState.emit(RocketLaunchUiState.Error)
                }
            }
        }
    }
}

/**
 * The various UI states of the rocket launch list screen.
 */
sealed class RocketLaunchUiState {

    /**
     * Indicates the rocket launch details could be successfully loaded.
     */
    data class Success(val launch: RocketLaunch) : RocketLaunchUiState()

    /**
     * Indicates an error occurred obtaining rocket launch information.
     */
    object Error : RocketLaunchUiState()

    /**
     * Indicates the passed id is not in the correct format.
     */
    object InvalidId : RocketLaunchUiState()

    /**
     * Indicates the detail screen is still loading.
     */
    object Loading : RocketLaunchUiState()
}
