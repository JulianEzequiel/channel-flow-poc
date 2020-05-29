package com.poc.flowchannel.app.home.usecase

import com.poc.flowchannel.app.core.repository.TweetRepository
import com.poc.flowchannel.app.home.model.ScreenTweet
import com.poc.flowchannel.app.home.model.toScreenTweet
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
class GetTweetsUseCase(
    private val tweetRepository: TweetRepository
) {

    suspend fun execute(): List<ScreenTweet> {
        val tweets = tweetRepository.getTweets()
        return tweets.map { it.toScreenTweet() }
    }

}