package com.thoughtworks.visionassistant.app.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TestData(
    val type: String,
) : Parcelable
