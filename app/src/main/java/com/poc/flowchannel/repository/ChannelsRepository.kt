package com.poc.flowchannel.repository

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.channels.Channel.Factory.RENDEZVOUS
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow

private const val FIXED_BUFFER_SIZE = 2

@ExperimentalCoroutinesApi
@FlowPreview
class StreamsRepository(
    private val coroutineScope: CoroutineScope = GlobalScope,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    private var conflatedBroadcastChannel = ConflatedBroadcastChannel<String>()
    private val channelsMap = HashMap<Int, Channel<String>>()

    init {
        channelsMap[CONFLATED] = Channel(CONFLATED)
    }

    // region Channels
    fun getConflatedBroadcastChannel() = conflatedBroadcastChannel.asFlow()

    fun stopChannelEmissions() {
        conflatedBroadcastChannel.close()

        channelsMap.keys.forEach {
            channelsMap[it]?.close()
        }
    }

    fun initChannelEmissions() {
        conflatedBroadcastChannel = ConflatedBroadcastChannel()
        channelsMap[RENDEZVOUS] = Channel(RENDEZVOUS)
        channelsMap[UNLIMITED] = Channel(UNLIMITED)
        channelsMap[BUFFERED] = Channel(BUFFERED)
        channelsMap[FIXED_BUFFER_SIZE] = Channel(FIXED_BUFFER_SIZE)

        initConflatedBroadcastOffering()
        sendValues(channelsMap[CONFLATED]!!)
        sendValues(channelsMap[RENDEZVOUS]!!)
        sendValues(channelsMap[UNLIMITED]!!)
        sendValues(channelsMap[BUFFERED]!!)
        sendValues(channelsMap[FIXED_BUFFER_SIZE]!!)
    }

    suspend fun listenChannelEmissions(channelType: Int) = channelsMap[channelType]?.receive()

    private fun initConflatedBroadcastOffering() {
        coroutineScope.launch(dispatcher) {
            var value = 0

            while (true) {
                if (!conflatedBroadcastChannel.isClosedForSend) {
                    conflatedBroadcastChannel.send(value.toString())
                    value++
                    delay(1000)
                } else {
                    return@launch
                }
            }
        }
    }

    private fun sendValues(channel: Channel<String>) {
        coroutineScope.launch(dispatcher) {
            var value = 0

            while (true) {
                if (!channel.isClosedForSend) {
                    channel.send(value.toString())
                    value++
                    delay(1000)
                } else {
                    return@launch
                }
            }
        }
    }

    // endregion Channels

}