package com.poc.flowchannel.app.core.repository

import com.poc.flowchannel.app.core.model.Tweet
import com.poc.flowchannel.app.core.model.TweetInteraction
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

@FlowPreview
@ExperimentalCoroutinesApi
class TweetRepository(
    private val coroutineScope: CoroutineScope = GlobalScope
) {

    // Channel not scoped to any screen
    private val unreadMessagesChannel = Channel<Int>(CONFLATED)

    init {
        startReceivenUnreadMessageAlerts()
    }

    // Flow created
    fun getTweetInteractions(tweetId: Long) = callbackFlow {
        val tweet = getTweets().find { it.id == tweetId }!!
        val tweetInteraction = TweetInteraction(tweet.responses, tweet.retweets, tweet.favorites)
        send(tweetInteraction)

        launch {
            while (true) {
                delay(1500)
                tweetInteraction.replies++
                send(tweetInteraction)
            }
        }

        launch {
            while (true) {
                delay(800)
                tweetInteraction.retweets++
                send(tweetInteraction)
            }
        }

        launch {
            while (true) {
                delay(500)
                tweetInteraction.favorites++
                send(tweetInteraction)
            }
        }

        awaitClose {
            //This block is added because we want the Flow to stay open.
        }
    }

    // Channel emmiting and retrieving messages.
    @FlowPreview
    suspend fun getUnreadMessages() = unreadMessagesChannel.receive()

    private fun startReceivenUnreadMessageAlerts() {
        coroutineScope.launch {
            var unreadMessages = 1
            while (true) {
                unreadMessagesChannel.send(unreadMessages)
                unreadMessages++
                delay(1100)
            }
        }
    }

    // One-shot operation
    fun getTweets(): List<Tweet> = listOf(
        Tweet(1, "Julian", "Preparing Reactive Kotlin examples!", 3, 10, 21),
        Tweet(2, "Facu", "Going live in 5420 minutes!", 3, 19, 3),
        Tweet(3, "Eze", "Does flow has flow?", 1, 123, 100),
        Tweet(4, "Mari", "Setting up my computer for transmition :D", 3, 10, 9),
        Tweet(5, "Ricky", "I love bad boy Vasily", 1, 12, 5),
        Tweet(6, "Android Devs Bs As", "All done for the next meetup!", 0, 20, 30),
        Tweet(
            7,
            "Android Devs",
            "Check out our new MVVVMASDUA Architecture components!",
            0,
            -28,
            -90
        ),
        Tweet(8, "Android Studio", "Download Android Studio 4.0 !", 190, 100, 28),
        Tweet(9, "Flutter", "Trying to beat React Native", 10, 121, 109),
        Tweet(
            10,
            "Kotlin",
            "Excuse me, I'm working on new experimental previews for flows and channels!",
            0,
            -20,
            -12
        )
    )

}