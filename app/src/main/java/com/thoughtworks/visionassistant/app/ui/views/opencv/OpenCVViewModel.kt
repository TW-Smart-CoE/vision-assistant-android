package com.thoughtworks.visionassistant.app.ui.views.opencv

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thoughtworks.visionassistant.app.di.Dependency
import com.thoughtworks.visionassistant.app.foundation.mvi.DefaultStore
import com.thoughtworks.visionassistant.app.foundation.mvi.MVIViewModel
import com.thoughtworks.visionassistant.app.foundation.mvi.Store
import com.thoughtworks.visionassistant.opencvkit.OpenCVKit
import com.thoughtworks.visionassistant.opencvkit.analyzers.FaceDetector
import com.thoughtworks.visionassistant.opencvkit.analyzers.FaceRect

class OpenCVViewModel(
    dependency: Dependency,
    store: Store<OpenCVState, OpenCVEvent, OpenCVAction> =
        DefaultStore(
            initialState = OpenCVState(
                isInited = false
            )
        ),
) : MVIViewModel<OpenCVState, OpenCVEvent, OpenCVAction>(store), FaceDetector.FaceDetectorListener {
    private val navigator = dependency.navigator

    init {
        sendAction(OpenCVAction.OnInitResult(OpenCVKit.initialize()))
    }

    override fun reduce(currentState: OpenCVState, action: OpenCVAction): OpenCVState {
        return when (action) {
            is OpenCVAction.OnInitResult -> {
                currentState.copy(isInited = action.result)
            }

            else -> {
                currentState
            }
        }
    }

    override fun runSideEffect(action: OpenCVAction, currentState: OpenCVState) {
        when (action) {
            OpenCVAction.NavigateBack -> {
                navigator.navigateBack()
            }

            else -> {
            }
        }
    }

    override fun onFaceDetected(
        imageWidth: Int,
        imageHeight: Int,
        faceRectangles: List<FaceRect>,
    ) {
        Log.d(TAG, "onFaceDetected: $faceRectangles")
    }

    companion object {
        private const val TAG = "OpenCVViewModel"
    }
}

class OpenCVViewModelFactory(
    private val dependency: Dependency,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OpenCVViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OpenCVViewModel(dependency) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
