package com.washuTechnologies.merced.ui.launchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.washuTechnologies.merced.data.launches.model.RocketLaunch
import com.washuTechnologies.merced.di.IoDispatcher
import com.washuTechnologies.merced.usecases.GetRocketListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * View model for the list of rocket launches
 */
@HiltViewModel
class LaunchListViewModel @Inject constructor(
    getRocketListUseCase: GetRocketListUseCase,
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
            getRocketListUseCase()
                .sortByLaunchDate()
                .collect {
                    Timber.d("Launch list state $it.")
                    val state = if (it.isNotEmpty()) {
                        LaunchListUiState.Success(it)
                    } else {
                        LaunchListUiState.Error
                    }
                    _uiState.emit(state)
                }
        }
    }

    private fun Flow<Array<RocketLaunch>>.sortByLaunchDate() = transform { list ->
        list.sortByDescending { it.launchDateUTC }
        emit(list)
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
