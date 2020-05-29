package com.poc.flowchannel.app.core.model

data class TweetInteraction(
    var replies: Int,
    var retweets: Int,
    var favorites: Int
)