package com.poc.flowchannel.app.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poc.flowchannel.app.core.usecase.CoreUseCaseFactory
import com.poc.flowchannel.app.home.model.ScreenTweet
import com.poc.flowchannel.app.home.usecase.HomeUseCaseFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class HomeViewModel : ViewModel() {

    private val getUnreadMessagesUseCase = CoreUseCaseFactory.getUnreadMessagesUseCase()
    private val getTweetsUseCase = HomeUseCaseFactory.getTweetsUseCase()

    private val _tweets = MutableLiveData<List<ScreenTweet>>()
    val tweets: LiveData<List<ScreenTweet>> get() = _tweets

    private val _unreadMessages = MutableLiveData<Int>()
    val unreadMessages: LiveData<Int> get() = _unreadMessages

    init {
        getUnreadMessages()
        getTweets()
    }

    private fun getTweets() {
        viewModelScope.launch {
            _tweets.value = getTweetsUseCase.execute()
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