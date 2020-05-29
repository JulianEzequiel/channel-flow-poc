package com.poc.flowchannel.app.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poc.flowchannel.app.core.model.TweetInteraction
import com.poc.flowchannel.app.core.usecase.CoreUseCaseFactory
import com.poc.flowchannel.app.detail.usecase.TweetDetailUseCaseFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class TweetDetailViewModel : ViewModel() {

    private val getTweetInteractionUseCase = TweetDetailUseCaseFactory.getTweetInteractionsUseCase()
    private val getUnreadMessagesUseCase = CoreUseCaseFactory.getUnreadMessagesUseCase()

    private val _unreadMessages = MutableLiveData<Int>()
    val unreadMessages: LiveData<Int> get() = _unreadMessages

    private val _tweetInteractions = MutableLiveData<TweetInteraction>()
    val tweetInteractions: LiveData<TweetInteraction> get() = _tweetInteractions

    init {
        getUnreadMessages()
    }

    fun getTweetInteractions(tweetId: Long) {
        viewModelScope.launch {
            getTweetInteractionUseCase.execute(tweetId).collect {
                _tweetInteractions.value = it
            }
        }
    }

    private fun getUnreadMessages() {
        viewModelScope.launch {
            getUnreadMessagesUseCase.execute()
                .collect {
                    _unreadMessages.value = it
                }
        }
    }

}