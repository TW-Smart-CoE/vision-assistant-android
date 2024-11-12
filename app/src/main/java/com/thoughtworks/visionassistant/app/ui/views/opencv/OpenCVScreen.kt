package com.thoughtworks.visionassistant.app.ui.views.opencv

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thoughtworks.visionassistant.app.R
import com.thoughtworks.visionassistant.app.di.Dependency
import com.thoughtworks.visionassistant.opencvkit.views.facedetect.FaceDetectView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenCVScreen(
    dependency: Dependency,
) {
    val factory = remember { OpenCVViewModelFactory(dependency) }
    val viewModel: OpenCVViewModel = viewModel(factory = factory)
    val state = viewModel.uiState.collectAsState()

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = context.getString(R.string.opencv),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { viewModel.sendAction(OpenCVAction.NavigateBack) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back_24_black),
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Column {
                    if (state.value.isInited) {
                        FaceDetectView(
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .align(alignment = Alignment.CenterHorizontally)
                                .width(320.dp)
                                .height(320.dp),
                            flipVertical = false,
                            cameraId = 0,
                            isCamera2 = false,
                            frameSkip = 0,
                            faceDetectorListener = viewModel,
                        )
                    } else {
                        Text(
                            text = context.getString(R.string.opencv_not_initialized),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    )
}