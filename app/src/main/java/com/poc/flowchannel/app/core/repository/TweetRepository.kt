package com.poc.flowchannel.app.core.repository

import com.poc.flowchannel.app.core.model.Tweet
import com.poc.flowchannel.app.core.model.TweetInteraction
import com.poc.flowchannel.app.core.model.TweetWithInteraction
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlin.random.Random

@FlowPreview
@ExperimentalCoroutinesApi
class TweetRepository(
    private val coroutineScope: CoroutineScope = GlobalScope
) {

    // Channel not scoped to any screen
    private val unreadMessagesStream = MutableStateFlow(0)

    private val tweetList: List<Tweet>

    init {
        tweetList = createTweets()
        startReceivenUnreadMessageAlerts()
    }

    @FlowPreview
    fun getUnreadMessages(): StateFlow<Int> = unreadMessagesStream

    private fun startReceivenUnreadMessageAlerts() {
        coroutineScope.launch {
            while (true) {
                unreadMessagesStream.value++
                delay(1100)
            }
        }
    }

    fun getTweets(): List<TweetWithInteraction> {
        return tweetList
            .map {
                TweetWithInteraction(it, getInteractionFlow(it.replies, it.retweets, it.favs))
            }
    }

    fun getTweet(tweetId: Long) = tweetList
        .filter { it.id == tweetId }
        .map {
            TweetWithInteraction(it, getInteractionFlow(it.replies, it.retweets, it.favs))
        }.first()

    private fun createTweets(): List<Tweet> = listOf(
        Tweet(
            1,
            "Julian",
            "Preparing Reactive Kotlin examples!",
            3, 10, 21
        ),
        Tweet(2, "Facu", "Going live in 5420 minutes!", 3, 19, 3),
        Tweet(3, "Eze", "Does flow has flow?", 1, 123, 100),
        Tweet(
            4,
            "Mari",
            "Setting up my computer for transmition :D",
            3, 10, 9
        ),
        Tweet(5, "Ricky", "I love bad boy Vasily", 1, 12, 5),
        Tweet(
            6,
            "Android Devs Bs As",
            "All done for the next meetup!",
            0, 20, 30
        ),
        Tweet(
            7,
            "Android Devs",
            "Check out our new MVVVMASDUA Architecture components!",
            0, -28, -90
        ),
        Tweet(
            8,
            "Android Studio",
            "Download Android Studio 4.0 !",
            190, 100, 28
        ),
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

    private fun getInteractionFlow(responses: Int, retweets: Int, favorites: Int) =
        callbackFlow {
            val tweetInteraction = TweetInteraction(responses, retweets, favorites)
            send(tweetInteraction)

            val delayForReply = Random.nextLong(1000, 2000)
            val delayForRetweet = Random.nextLong(1000, 2000)
            val delayForFavorite = Random.nextLong(1000, 2000)

            launch {
                while (true) {
                    delay(delayForReply)
                    tweetInteraction.replies++
                    send(tweetInteraction)
                }
            }

            launch {
                while (true) {
                    delay(delayForRetweet)
                    tweetInteraction.retweets++
                    send(tweetInteraction)
                }
            }

            launch {
                while (true) {
                    delay(delayForFavorite)
                    tweetInteraction.favorites++
                    send(tweetInteraction)
                }
            }

            awaitClose {
                //This block is added because we want the Flow to stay open.
            }
        }

}