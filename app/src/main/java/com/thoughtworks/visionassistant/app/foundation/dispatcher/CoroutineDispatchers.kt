package com.thoughtworks.visionassistant.app.foundation.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineDispatchers {
    val defaultDispatcher: CoroutineDispatcher
    val ioDispatcher: CoroutineDispatcher
    val mainDispatcher: CoroutineDispatcher
    val mainImmediateDispatcher: CoroutineDispatcher
}