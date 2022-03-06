package com.washuTechnologies.merced.ui.launchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.washuTechnologies.merced.api.Result
import com.washuTechnologies.merced.api.launches.RocketLaunch
import com.washuTechnologies.merced.api.launches.RocketLaunchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * View model for the list of rocket launches
 */
@HiltViewModel
class LaunchListViewModel @Inject constructor(
    repo: RocketLaunchRepository
) : ViewModel() {

    /**
     * Retrieve the complete list of rocket launches. [Result] is used to expose loading and
     * error state information.
     */
    val launchList: StateFlow<Result<Array<RocketLaunch>>> = repo.getLaunchList().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        Result.Loading
    )
}
