package com.thoughtworks.visionassistant.ncnnkit

class NativeLib {

    /**
     * A native method that is implemented by the 'ncnnkit' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'ncnnkit' library on application startup.
        init {
            System.loadLibrary("ncnnkit")
        }
    }
}