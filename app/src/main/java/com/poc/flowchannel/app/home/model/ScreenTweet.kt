package com.poc.flowchannel.app.home.model

import com.poc.flowchannel.app.core.model.TweetInteraction
import com.poc.flowchannel.app.core.model.TweetWithInteraction
import kotlinx.coroutines.flow.Flow

data class ScreenTweet(
    val id: Long,
    val username: String,
    val tweet: String,
    val interaction: Flow<TweetInteraction>
)

fun TweetWithInteraction.toScreenTweet() = ScreenTweet(
    tweet.id, tweet.user, tweet.tweet, interaction
)