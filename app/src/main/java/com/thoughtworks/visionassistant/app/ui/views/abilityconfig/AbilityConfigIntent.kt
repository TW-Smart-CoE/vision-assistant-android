package com.thoughtworks.visionassistant.app.ui.views.abilityconfig

import com.thoughtworks.visionassistant.app.foundation.mvi.model.Action
import com.thoughtworks.visionassistant.app.foundation.mvi.model.Event
import com.thoughtworks.visionassistant.app.foundation.mvi.model.State

data class AbilityConfigState(
    val data: String,
) : State

sealed class AbilityConfigEvent : Event

sealed interface AbilityConfigAction : Action {
    data object ShowOpenCV : AbilityConfigAction
}