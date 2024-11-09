package com.thoughtworks.visionassistant.opencvkit.views

import android.Manifest
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2
import org.opencv.android.JavaCameraView

@Composable
fun CameraView(
    modifier: Modifier = Modifier,
    cameraId: Int = 0,
    cameraViewListener: CvCameraViewListener2,
    placeholderView: @Composable () -> Unit = { Text("Requesting camera permission...") },
) {
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    var lifecycleObserver by remember { mutableStateOf<CameraLifecycleObserver?>(null) }
    var hasCameraPermission by remember { mutableStateOf(false) }

    fun removeLifecycleObserver() {
        lifecycleObserver?.let {
            lifecycleOwner.lifecycle.removeObserver(it)
            lifecycleObserver = null
        }
    }

    DisposableEffect(lifecycleOwner) {
        onDispose {
            removeLifecycleObserver()
        }
    }

    PermissionView(permissions = listOf(Manifest.permission.CAMERA)) { result ->
        hasCameraPermission = result[Manifest.permission.CAMERA] == true
    }

    if (hasCameraPermission) {
        AndroidView(
            factory = { context ->
                JavaCameraView(context, cameraId).apply {
                    setCvCameraViewListener(cameraViewListener)
                    setCameraPermissionGranted()
                    enableView()

                    removeLifecycleObserver()
                    lifecycleObserver = CameraLifecycleObserver(this)
                    lifecycleOwner.lifecycle.addObserver(lifecycleObserver!!)
                }
            },
            modifier = modifier
        )
    } else {
        placeholderView()
    }
}

class CameraLifecycleObserver(private val cameraView: JavaCameraView) : LifecycleEventObserver {
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_RESUME -> cameraView.enableView()
            Lifecycle.Event.ON_PAUSE -> cameraView.disableView()
            Lifecycle.Event.ON_DESTROY -> cameraView.disableView()
            else -> {}
        }
    }
}
