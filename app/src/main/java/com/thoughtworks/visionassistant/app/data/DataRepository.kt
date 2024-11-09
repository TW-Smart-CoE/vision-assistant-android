package com.thoughtworks.visionassistant.app.data

import android.content.Context
import com.google.gson.Gson
import com.thoughtworks.visionassistant.app.data.local.LocalStorageImpl

class DataRepository(
    context: Context,
    gson: Gson,
) : DataSource {
    private val localStorage = LocalStorageImpl(context, gson)
}