package com.washuTechnologies.merced.ui.launchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.washuTechnologies.merced.api.Result
import com.washuTechnologies.merced.api.launches.RocketLaunch
import com.washuTechnologies.merced.api.launches.RocketLaunchRepository
import com.washuTechnologies.merced.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * View model for the list of rocket launches
 */
@HiltViewModel
class LaunchListViewModel @Inject constructor(
    launchRepository: RocketLaunchRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _uiState = MutableStateFlow<LaunchListUiState>(LaunchListUiState.Loading)

    /**
     * Exposes the current state of the launch list screen.
     */
    val uiState: StateFlow<LaunchListUiState>
        get() = _uiState

    init {
        viewModelScope.launch(dispatcher) {
            launchRepository.getLaunchList().collect {
                when (it) {
                    is Result.Success -> _uiState.emit(LaunchListUiState.Success(it.result))
                    is Result.Loading -> _uiState.emit(LaunchListUiState.Loading)
                    is Result.Error -> _uiState.emit(LaunchListUiState.Error)
                }
            }
        }
    }
}

/**
 * The various UI states of the rocket launch list screen.
 */
sealed class LaunchListUiState {

    /**
     * Indicates the list of rocket launches could be successfully loaded.
     */
    data class Success(val launchList: Array<RocketLaunch>) : LaunchListUiState() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Success

            if (!launchList.contentEquals(other.launchList)) return false

            return true
        }

        override fun hashCode(): Int {
            return launchList.contentHashCode()
        }
    }

    /**
     * Indicates an error occurred obtaining rocket launch information.
     */
    object Error : LaunchListUiState()

    /**
     * Indicates the launch list is still loading.
     */
    object Loading : LaunchListUiState()
}
