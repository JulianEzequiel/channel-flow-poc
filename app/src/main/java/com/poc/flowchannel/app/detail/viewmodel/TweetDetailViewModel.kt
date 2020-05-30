package com.poc.flowchannel.app.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poc.flowchannel.app.core.usecase.CoreUseCaseFactory
import com.poc.flowchannel.app.detail.model.ScreenTweetInteraction
import com.poc.flowchannel.app.detail.usecase.TweetDetailUseCaseFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class TweetDetailViewModel : ViewModel() {

    private val getTweetInteractionUseCase = TweetDetailUseCaseFactory.getTweetInteractionsUseCase()
    private val getUnreadMessagesUseCase = CoreUseCaseFactory.getUnreadMessagesUseCase()

    private val _unreadMessages = MutableLiveData<Int>()
    val unreadMessages: LiveData<Int> get() = _unreadMessages

    private val _tweetInteractions = MutableLiveData<ScreenTweetInteraction>()
    val tweetInteractions: LiveData<ScreenTweetInteraction> get() = _tweetInteractions

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
            while(true) {
                _unreadMessages.value = getUnreadMessagesUseCase.execute()
                delay(1200)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }

}