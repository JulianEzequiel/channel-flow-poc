package com.poc.flowchannel.examples.viewmodel

import androidx.lifecycle.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlin.collections.set

private const val FIXED_BUFFER_SIZE = 2

@FlowPreview
@ExperimentalCoroutinesApi
class ChannelViewModel : ViewModel() {

    private var conflatedBroadcastChannel = BroadcastChannel<String>(Channel.CONFLATED)
    private val channelsMap = HashMap<ListenType, Channel<String>>()

    val rendezvousValue = MutableLiveData<String>()
    val conflatedValue = MutableLiveData<String>()
    val unlimitedValue = MutableLiveData<String>()
    val bufferedValue = MutableLiveData<String>()
    val otherValue = MutableLiveData<String>()

    override fun onCleared() {
        super.onCleared()
        stopChannelEmissions()
    }

    fun initEmissions() {
        initChannelEmissions()
    }

    fun receiveRendezvous() {
        viewModelScope.launch {
            rendezvousValue.value = channelsMap[ListenType.RENDEZVOUS]?.receive()
        }
    }

    fun receiveConflated() {
        viewModelScope.launch {
            conflatedValue.value = channelsMap[ListenType.CONFLATED]?.receive()
        }
    }

    fun receiveUnlimited() {
        viewModelScope.launch {
            unlimitedValue.value = channelsMap[ListenType.UNLIMITED]?.receive()
        }
    }

    fun receiveBuffered() {
        viewModelScope.launch {
            bufferedValue.value = channelsMap[ListenType.BUFFERED]?.receive()
        }
    }

    fun receiveOther() {
        viewModelScope.launch {
            otherValue.value = channelsMap[ListenType.OTHER]?.receive()
        }
    }

    fun timerValues(): LiveData<String> = conflatedBroadcastChannel.asFlow().asLiveData()

    private fun initChannelEmissions() {
        conflatedBroadcastChannel = BroadcastChannel(Channel.BUFFERED)
        channelsMap[ListenType.RENDEZVOUS] = Channel(Channel.RENDEZVOUS)
        channelsMap[ListenType.UNLIMITED] = Channel(Channel.UNLIMITED)
        channelsMap[ListenType.BUFFERED] = Channel(Channel.BUFFERED)
        channelsMap[ListenType.OTHER] = Channel(FIXED_BUFFER_SIZE)
        channelsMap[ListenType.CONFLATED] = Channel(Channel.CONFLATED)

        initConflatedBroadcastOffering()
        sendValues(channelsMap[ListenType.RENDEZVOUS]!!)
        sendValues(channelsMap[ListenType.UNLIMITED]!!)
        sendValues(channelsMap[ListenType.BUFFERED]!!)
        sendValues(channelsMap[ListenType.OTHER]!!)
        sendValues(channelsMap[ListenType.CONFLATED]!!)
    }

    private fun initConflatedBroadcastOffering() {
        viewModelScope.launch(Dispatchers.IO) {
            var value = 0

            while (true) {
                conflatedBroadcastChannel.send(value.toString())
                value++
                delay(1000)
            }
        }
    }

    private fun sendValues(channel: Channel<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            var value = 0

            while (true) {
                channel.send(value.toString())
                value++
                delay(1000)
            }
        }
    }

    private fun stopChannelEmissions() {
        conflatedBroadcastChannel.cancel()

        channelsMap.keys.forEach {
            channelsMap[it]?.cancel()
        }
    }

    private enum class ListenType {
        RENDEZVOUS,
        UNLIMITED,
        CONFLATED,
        BUFFERED,
        OTHER
    }
}