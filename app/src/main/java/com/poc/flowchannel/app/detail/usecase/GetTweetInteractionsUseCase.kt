package com.poc.flowchannel.app.detail.usecase

import com.poc.flowchannel.app.core.repository.TweetRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class GetTweetInteractionsUseCase constructor(
    private val tweetRepository: TweetRepository
) {

    fun execute(tweetId: Long) = tweetRepository.getTweetInteractions(tweetId)

}