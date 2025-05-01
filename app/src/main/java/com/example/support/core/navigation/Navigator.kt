package com.example.support.core.navigation

import com.example.support.core.navigation.model.NavigationEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

@Suppress("UNCHECKED_CAST")
class Navigator {
    private val _navigationEvents = MutableSharedFlow<NavigationEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    private val results = mutableMapOf<String, MutableSharedFlow<Any>>()

    suspend fun navigate(event: NavigationEvent) {
        _navigationEvents.emit(event)
    }

    fun <T> getResultFlow(key: String): SharedFlow<T> {
        val flow = results.getOrPut(key) { MutableSharedFlow(replay = 1) }
        return flow as SharedFlow<T>
    }

    suspend fun <T> setResult(key: String, value: T) {
        val flow = results.getOrPut(key) { MutableSharedFlow(replay = 1) }
        (flow as MutableSharedFlow<T>).emit(value)
    }
}
