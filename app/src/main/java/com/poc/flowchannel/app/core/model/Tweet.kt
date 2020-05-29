package com.poc.flowchannel.app.core.model

data class Tweet(
    val id: Long,
    val user: String,
    val tweet: String,
    val responses: Int,
    val retweets: Int,
    val favorites: Int
)
