package com.thoughtworks.visionassistant.app.di

import android.content.Context
import com.google.gson.Gson
import com.thoughtworks.visionassistant.app.data.DataRepository
import com.thoughtworks.visionassistant.app.data.DataSource
import com.thoughtworks.visionassistant.app.utils.navigator.Navigator
import com.thoughtworks.visionassistant.app.foundation.dispatcher.AppDispatchers
import com.thoughtworks.visionassistant.app.foundation.dispatcher.CoroutineDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class DependencyImpl(context: Context) : Dependency {
    private var _navigator: Navigator? = null
    private val _context = context
    private val _coroutineDispatchers: CoroutineDispatchers = AppDispatchers()
    private val _coroutineScope: CoroutineScope =
        CoroutineScope(SupervisorJob() + _coroutineDispatchers.defaultDispatcher)
    private val _gson: Gson = Gson()
    private val _dataSource: DataSource = DataRepository(context, _gson)

    override fun setNavigator(navigator: Navigator) {
        _navigator = navigator
    }

    override val navigator: Navigator
        get() = _navigator!!

    override val context: Context
        get() = _context

    override val coroutineDispatchers: CoroutineDispatchers
        get() = _coroutineDispatchers

    override val coroutineScope: CoroutineScope
        get() = _coroutineScope

    override val gson: Gson
        get() = _gson

    override val dataSource: DataSource
        get() = _dataSource
}