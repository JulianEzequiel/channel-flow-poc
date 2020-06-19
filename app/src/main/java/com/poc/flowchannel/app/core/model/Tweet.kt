package com.poc.flowchannel.app.core.model

import kotlinx.coroutines.flow.Flow

data class Tweet(
    val id: Long,
    val user: String,
    val tweet: String,
    var replies: Int,
    var retweets: Int,
    var favs: Int
)

data class TweetWithInteraction(
    val tweet: Tweet,
    val interaction: Flow<TweetInteraction>
)
