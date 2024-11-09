package com.thoughtworks.visionassistant.app.data.local

import android.content.Context
import com.google.gson.Gson

class LocalStorageImpl(
    private val context: Context,
    private val gson: Gson,
) : LocalStorage {
    private val sharedPreferenceUtil = SharedPreferenceUtils(context)

    companion object {
        private const val KEY_TEST = "test"
    }
}