package com.poc.flowchannel.app.core.usecase

import com.poc.flowchannel.app.core.model.TweetInteraction
import com.poc.flowchannel.app.core.repository.TweetRepository
import kotlinx.coroutines.flow.Flow

class GetTweetInteractionUseCase(
    private val tweetRepository: TweetRepository
) {

    fun execute(tweetId: Long): Flow<TweetInteraction> {
        return tweetRepository.getTweetInteraction(tweetId)
    }

}