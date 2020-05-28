package com.poc.flowchannel.viewmodel

import androidx.lifecycle.*
import com.poc.flowchannel.usecase.channel.ListenChannelUseCase
import com.poc.flowchannel.usecase.factory.UseCaseFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class ChannelViewModel : ViewModel() {

    private val initChannelsUseCase = UseCaseFactory.initChannelsUseCase()
    private val listenChannelUseCase = UseCaseFactory.listenConflatedChannelUseCase()
    private val closeChannelsUseCase = UseCaseFactory.closeChannelsUseCase()

    private val getBroadcastChannelEmissionUseCase =
        UseCaseFactory.getBroadcastChannelEmissionUseCase()

    val rendezvousValue = MutableLiveData<String>()
    val conflatedValue = MutableLiveData<String>()
    val unlimitedValue = MutableLiveData<String>()
    val bufferedValue = MutableLiveData<String>()
    val otherValue = MutableLiveData<String>()

    fun initEmissions() {
        initChannelsUseCase.execute()
    }

    fun receiveRendezvous() {
        viewModelScope.launch {
            rendezvousValue.value =
                listenChannelUseCase.execute(ListenChannelUseCase.ListenType.RENDEZVOUS)
        }
    }

    fun receiveConflated() {
        viewModelScope.launch {
            conflatedValue.value =
                listenChannelUseCase.execute(ListenChannelUseCase.ListenType.CONFLATED)
        }
    }

    fun receiveUnlimited() {
        viewModelScope.launch {
            unlimitedValue.value =
                listenChannelUseCase.execute(ListenChannelUseCase.ListenType.UNLIMITED)
        }
    }

    fun receiveBuffered() {
        viewModelScope.launch {
            bufferedValue.value =
                listenChannelUseCase.execute(ListenChannelUseCase.ListenType.BUFFERED)
        }
    }

    fun receiveOther() {
        viewModelScope.launch {
            otherValue.value = listenChannelUseCase.execute(ListenChannelUseCase.ListenType.OTHER)
        }
    }

    fun timerValues(): LiveData<String> = getBroadcastChannelEmissionUseCase.execute().asLiveData()

    override fun onCleared() {
        super.onCleared()
        closeChannelsUseCase.execute()
    }

}