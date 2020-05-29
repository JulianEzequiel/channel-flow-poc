package com.poc.flowchannel.examples.repository

import android.util.Log
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class FlowsRepository {

    private val flowEmitter: Flow<String> = createFlow()
    private val callbackFlowEmitter: Flow<String> = createCallbackFlow()

    fun getFlowEmitter() = flowEmitter

    fun getCallbackFlowEmitter() = callbackFlowEmitter

    private fun createFlow(): Flow<String> = flow {
        var value = 0

        while (true) {
            emit(value.toString())
            value++
            delay(1000)
        }
    }

    private fun createCallbackFlow(): Flow<String> = callbackFlow {
        var coroutine1Value = 0
        var coroutine2Value = 0

        launch {
            while (true) {
                offer("Coroutine1: $coroutine1Value\nCoroutine2: $coroutine2Value")
                coroutine1Value++
                delay(1000)
            }
        }

        launch {
            while (true) {
                offer("Coroutine1: $coroutine1Value\nCoroutine2: $coroutine2Value")
                coroutine2Value++
                delay(600)
            }
        }

        awaitClose {
            Log.d("AwaitClose", "Closed")
        }
    }
}