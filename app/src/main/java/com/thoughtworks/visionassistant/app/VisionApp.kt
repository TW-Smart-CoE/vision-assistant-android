package com.thoughtworks.visionassistant.app

import android.app.Application
import com.thoughtworks.visionassistant.app.di.Dependency
import com.thoughtworks.visionassistant.app.di.DependencyImpl

class VisionApp : Application() {
    private lateinit var dependency: DependencyImpl

    override fun onCreate() {
        super.onCreate()
        dependency = DependencyImpl(this)
    }

    fun getDependency(): Dependency {
        return dependency
    }
}