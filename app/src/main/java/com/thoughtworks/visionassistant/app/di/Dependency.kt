package com.thoughtworks.visionassistant.app.di

import android.content.Context
import com.google.gson.Gson
import com.thoughtworks.visionassistant.app.data.DataSource
import com.thoughtworks.visionassistant.app.utils.navigator.Navigator
import com.thoughtworks.visionassistant.app.foundation.dispatcher.CoroutineDispatchers
import kotlinx.coroutines.CoroutineScope

interface Dependency {
    fun setNavigator(navigator: Navigator)

    val navigator: Navigator
    val context: Context
    val coroutineDispatchers: CoroutineDispatchers
    val coroutineScope: CoroutineScope
    val gson: Gson
    val dataSource: DataSource
}