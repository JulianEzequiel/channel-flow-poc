package com.poc.flowchannel.examples.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel.Factory.BUFFERED
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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

    override fun onCleared() {
        super.onCleared()
        broadcastChannel.cancel()
        asFlowBroadcastChannel.cancel()
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
                channel.send(value.toString())
                value++
                delay(1000)
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
                receiver.value = channelSubscription.receive()
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
