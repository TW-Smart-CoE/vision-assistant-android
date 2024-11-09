package com.thoughtworks.visionassistant.opencvkit

import org.opencv.android.OpenCVLoader

object OpenCVKit {
    fun initialize(): Boolean {
        return OpenCVLoader.initLocal()
    }
}