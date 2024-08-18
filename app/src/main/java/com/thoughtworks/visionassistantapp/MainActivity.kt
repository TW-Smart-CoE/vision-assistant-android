package com.thoughtworks.visionassistantapp

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thoughtworks.visionassistant.VisionAssistant
import com.thoughtworks.visionassistant.views.facedetect.FaceDetectScreen
import com.thoughtworks.visionassistantapp.ui.theme.VisionassistantandroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContent {
            VisionassistantandroidTheme {
                var isOpenCVInitialized by remember { mutableStateOf(false) }

                LaunchedEffect(Unit) {
                    isOpenCVInitialized = VisionAssistant.initialize()
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    if (isOpenCVInitialized) {
                        FaceDetectScreen(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp),
                            faceDetectorListener = { count ->
                                Log.d(TAG, "$count face detected")
                            }
                        )
                    } else {
                        Text("Initializing...")
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VisionassistantandroidTheme {
        Greeting("Android")
    }
}