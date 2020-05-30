package com.poc.flowchannel.app.detail.usecase

import com.poc.flowchannel.app.core.repository.TweetRepository
import com.poc.flowchannel.app.detail.model.toScreenTweetInteraction
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.map

@FlowPreview
@ExperimentalCoroutinesApi
class GetTweetInteractionsUseCase constructor(
    private val tweetRepository: TweetRepository
) {

    fun execute(tweetId: Long) = tweetRepository
        .getTweetInteractions(tweetId)
        .map { it.toScreenTweetInteraction() }

}