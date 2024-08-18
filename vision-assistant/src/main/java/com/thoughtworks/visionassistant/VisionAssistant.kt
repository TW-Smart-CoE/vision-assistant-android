package com.thoughtworks.visionassistant

import org.opencv.android.OpenCVLoader

object VisionAssistant {
    fun initialize(): Boolean {
        return OpenCVLoader.initLocal()
    }
}