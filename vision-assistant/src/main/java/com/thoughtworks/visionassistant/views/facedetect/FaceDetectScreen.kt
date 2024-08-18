package com.thoughtworks.visionassistant.views.facedetect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.thoughtworks.visionassistant.analyzers.FaceDetector
import com.thoughtworks.visionassistant.views.CameraScreen

@Composable
fun FaceDetectScreen(
    modifier: Modifier = Modifier,
    faceDetectorListener: ((count: Int) -> Unit)? = null,
) {
    val context = LocalContext.current
    val faceDetector = remember { FaceDetector(context, faceDetectorListener) }

    CameraScreen(
        modifier = modifier,
        faceDetector,
    )
}