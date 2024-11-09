package com.thoughtworks.visionassistant.app.ui.views.abilityconfig

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thoughtworks.visionassistant.app.di.Dependency
import com.thoughtworks.visionassistant.app.foundation.mvi.DefaultStore
import com.thoughtworks.visionassistant.app.foundation.mvi.MVIViewModel
import com.thoughtworks.visionassistant.app.foundation.mvi.Store


class AbilityConfigViewModel(
    dependency: Dependency,
    store: Store<AbilityConfigState, AbilityConfigEvent, AbilityConfigAction> =
        DefaultStore(
            initialState = AbilityConfigState(
                data = "dummy"
            )
        ),
) : MVIViewModel<AbilityConfigState, AbilityConfigEvent, AbilityConfigAction>(store) {
    private val navigator = dependency.navigator

    override fun reduce(
        currentState: AbilityConfigState,
        action: AbilityConfigAction,
    ): AbilityConfigState {
        return when (action) {
            else -> {
                currentState
            }
        }
    }

    override fun runSideEffect(action: AbilityConfigAction, currentState: AbilityConfigState) {
        when (action) {
            is AbilityConfigAction.ShowOpenCV -> {
                navigator.navigateToOpenCVScreen()
            }

            else -> {
            }
        }
    }
}

class AbilityConfigViewModelFactory(
    private val dependency: Dependency,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AbilityConfigViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AbilityConfigViewModel(dependency) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
