package com.poc.flowchannel.app.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poc.flowchannel.app.core.usecase.CoreUseCaseFactory
import com.poc.flowchannel.app.detail.model.ScreenTweetInteraction
import com.poc.flowchannel.app.detail.model.toScreenTweetInteraction
import com.poc.flowchannel.app.detail.usecase.TweetDetailUseCaseFactory
import com.poc.flowchannel.app.home.model.ScreenTweet
import com.poc.flowchannel.app.home.model.toScreenTweet
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class TweetDetailViewModel : ViewModel() {

    private val getTweetInteractionUseCase = TweetDetailUseCaseFactory.getTweetInteractionsUseCase()
    private val getUnreadMessagesUseCase = CoreUseCaseFactory.getUnreadMessagesUseCase()

    private val _unreadMessages = MutableLiveData<Int>()
    val unreadMessages: LiveData<Int> get() = _unreadMessages

    private val _tweet = MutableLiveData<ScreenTweet>()
    val tweet: LiveData<ScreenTweet> get() = _tweet

    private val _tweetInteractions = MutableLiveData<ScreenTweetInteraction>()
    val tweetInteractions: LiveData<ScreenTweetInteraction> get() = _tweetInteractions

    init {
        getUnreadMessages()
    }

    fun getTweet(screenTweetId: Long) {
        viewModelScope.launch {
            val tweet = getTweetInteractionUseCase.execute(screenTweetId)

            _tweet.value = tweet.toScreenTweet()

            tweet.interaction
                .map { it.toScreenTweetInteraction() }
                .collect {
                    _tweetInteractions.value = it
                }
        }
    }

    private fun getUnreadMessages() {
        viewModelScope.launch {
            getUnreadMessagesUseCase.execute().collect {
                _unreadMessages.value = it
            }
        }
    }

}