package com.thoughtworks.visionassistant.app.ui.views.opencv

import com.thoughtworks.visionassistant.app.foundation.mvi.model.Action
import com.thoughtworks.visionassistant.app.foundation.mvi.model.Event
import com.thoughtworks.visionassistant.app.foundation.mvi.model.State

data class OpenCVState(
    val isInited: Boolean,
) : State

sealed class OpenCVEvent : Event

sealed interface OpenCVAction : Action {
    data object NavigateBack : OpenCVAction
    data class OnInitResult(val result: Boolean) : OpenCVAction
}