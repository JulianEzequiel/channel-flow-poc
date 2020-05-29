package com.poc.flowchannel.app.home.model

import android.os.Parcelable
import com.poc.flowchannel.app.core.model.Tweet
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ScreenTweet(
    val id: Long,
    val username: String,
    val tweet: String,
    val responsesQuantity: Int,
    val rtsQuantity: Int,
    val favoritesQuantity: Int
) : Parcelable

fun Tweet.toScreenTweet() = ScreenTweet(id, user, tweet, responses, retweets, favorites)