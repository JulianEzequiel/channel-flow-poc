package com.poc.flowchannel.app.detail.model

import com.poc.flowchannel.app.core.model.TweetInteraction

data class ScreenTweetInteraction(
    val responsesQuantity: Int,
    val rtsQuantity: Int,
    val favoritesQuantity: Int
)

fun TweetInteraction.toScreenTweetInteraction() =
    ScreenTweetInteraction(
        replies, retweets, favorites
    )