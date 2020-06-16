package com.poc.flowchannel.examples.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.channels.Channel.Factory.RENDEZVOUS
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class ChannelReceiversViewModel : ViewModel() {

    private val rendezvousChannel = Channel<String>(RENDEZVOUS)
    val rendezvousChannelReceiver1 = MutableLiveData<String>()
    val rendezvousChannelReceiver2 = MutableLiveData<String>()

    private val unlimitedChannel = Channel<String>(UNLIMITED)
    val unlimitedChannelReceiver1 = MutableLiveData<String>()
    val unlimitedChannelReceiver2 = MutableLiveData<String>()

    private val conflatedChannel = Channel<String>(CONFLATED)
    val conflatedChannelReceiver1 = MutableLiveData<String>()
    val conflatedChannelReceiver2 = MutableLiveData<String>()

    fun initEmission() {
        initRendezvous()
        initUnlimited()
        initConflated()
    }

    override fun onCleared() {
        super.onCleared()
        conflatedChannel.cancel()
        unlimitedChannel.cancel()
        rendezvousChannel.cancel()
    }

    private fun initRendezvous() {
        initChannelEmissions(rendezvousChannel)
        launchReceiver(rendezvousChannelReceiver1, rendezvousChannel)
        launchReceiver(rendezvousChannelReceiver2, rendezvousChannel)
    }

    private fun initUnlimited() {
        initChannelEmissions(unlimitedChannel)
        launchReceiver(unlimitedChannelReceiver1, unlimitedChannel)
        launchReceiver(unlimitedChannelReceiver2, unlimitedChannel)
    }

    private fun initConflated() {
        initChannelEmissions(conflatedChannel)
        launchReceiver(conflatedChannelReceiver1, conflatedChannel)
        launchReceiver(conflatedChannelReceiver2, conflatedChannel)
    }

    private fun initChannelEmissions(channel: Channel<String>) {
        viewModelScope.launch {
            var value = 0
            while (true) {
                channel.send(value.toString())
                value++
                delay(1000)
            }
        }
    }

    private fun launchReceiver(receiver: MutableLiveData<String>, channel: Channel<String>) {
        viewModelScope.launch {
            while (true) {
                receiver.value = channel.receive()
            }
        }
    }

}