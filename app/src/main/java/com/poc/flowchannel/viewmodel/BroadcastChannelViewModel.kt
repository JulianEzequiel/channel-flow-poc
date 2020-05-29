package com.poc.flowchannel.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect

@FlowPreview
@ExperimentalCoroutinesApi
class BroadcastChannelViewModel : ViewModel() {

    private val broadcastChannel = BroadcastChannel<String>(BUFFERED)
    val broadcastChannelReceiver1 = MutableLiveData<String>()
    val broadcastChannelReceiver2 = MutableLiveData<String>()

    private val asFlowBroadcastChannel = BroadcastChannel<String>(BUFFERED)
    val asFlowBroadcastChannelReceiver1 = MutableLiveData<String>()
    val asFlowBroadcastChannelReceiver2 = MutableLiveData<String>()

    fun initEmissions() {
        initBroadcastChannel()
        initAsFlowBroadcastChannel()
    }

    private fun initAsFlowBroadcastChannel() {
        initChannelEmissions(asFlowBroadcastChannel)
        launchReceiverAsFlow(asFlowBroadcastChannelReceiver1, broadcastChannel)
        launchReceiverAsFlow(asFlowBroadcastChannelReceiver2, broadcastChannel)
    }

    private fun initBroadcastChannel() {
        initChannelEmissions(broadcastChannel)
        launchReceiver(broadcastChannelReceiver1, broadcastChannel)
        launchReceiver(broadcastChannelReceiver2, broadcastChannel)
    }

    private fun initChannelEmissions(channel: BroadcastChannel<String>) {
        viewModelScope.launch {
            var value = 0
            while (true) {
                if (isActive) {
                    channel.send(value.toString())
                    value++
                    delay(1000)
                } else {
                    return@launch
                }
            }
        }
    }

    private fun launchReceiver(
        receiver: MutableLiveData<String>,
        channel: BroadcastChannel<String>
    ) {
        viewModelScope.launch {
            val channelSubscription = channel.openSubscription()
            while (true) {
                if (isActive) {
                    receiver.value = channelSubscription.receive()
                } else {
                    return@launch
                }
            }
        }
    }

    private fun launchReceiverAsFlow(
        receiver: MutableLiveData<String>,
        channel: BroadcastChannel<String>
    ) {
        viewModelScope.launch {
            channel.asFlow().collect {
                receiver.value = it
            }
        }
    }


}
