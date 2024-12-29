package com.thoughtworks.visionassistant.opencvkit.views.facedetect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.thoughtworks.visionassistant.core.DefaultLogger
import com.thoughtworks.visionassistant.core.Logger
import com.thoughtworks.visionassistant.opencvkit.analyzers.FaceDetector
import com.thoughtworks.visionassistant.opencvkit.views.CameraView

@Composable
fun FaceDetectView(
    modifier: Modifier = Modifier,
    flipVertical: Boolean = false,
    cameraId: Int = 0,
    isCamera2: Boolean,
    frameSkip: Int = 0,
    faceDetectorListener: FaceDetector.FaceDetectorListener,
    logger: Logger = DefaultLogger(),
) {
    val context = LocalContext.current
    val faceDetector = remember(cameraId, isCamera2, flipVertical, frameSkip) {
        FaceDetector(
            context,
            flipVertical,
            frameSkip,
            faceDetectorListener,
            logger
        )
    }

    CameraView(
        modifier = modifier,
        cameraId = cameraId,
        isCamera2 = isCamera2,
        faceDetector,
    )
}