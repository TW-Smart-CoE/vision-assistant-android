package com.thoughtworks.visionassistant.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.thoughtworks.visionassistant.app.di.Dependency
import com.thoughtworks.visionassistant.app.ui.navigation.Navigation
import com.thoughtworks.visionassistant.app.ui.theme.VisionassistantandroidTheme

class MainActivity : ComponentActivity() {
    private lateinit var dependency: Dependency

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        enableEdgeToEdge()
        setContent {
            VisionassistantandroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Navigation(dependency)
                }
            }
        }
    }

    private fun initialize() {
        dependency = (application as VisionApp).getDependency()
    }
}