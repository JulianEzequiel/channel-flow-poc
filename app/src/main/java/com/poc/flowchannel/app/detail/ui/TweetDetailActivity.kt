package com.poc.flowchannel.app.detail.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.poc.flowchannel.R
import com.poc.flowchannel.app.detail.viewmodel.TweetDetailViewModel
import com.poc.flowchannel.app.home.model.ScreenTweet
import kotlinx.android.synthetic.main.activity_home.unreadMessagesText
import kotlinx.android.synthetic.main.activity_tweet_detail.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
class TweetDetailActivity : AppCompatActivity() {

    private val viewModel: TweetDetailViewModel by viewModels()

    companion object {

        private const val SCREEN_TWEET_ID_KEY = "SCREEN_TWEET_ID_KEY"

        fun get(context: Context, screenTweet: ScreenTweet): Intent {
            val bundle = Bundle()
            bundle.putLong(SCREEN_TWEET_ID_KEY, screenTweet.id)
            val intent = Intent(context, TweetDetailActivity::class.java)
            intent.putExtras(bundle)
            return intent
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweet_detail)

        bindObservers()
    }

    override fun onResume() {
        super.onResume()
        val screenTweetId = intent.extras?.getLong(SCREEN_TWEET_ID_KEY)!!
        viewModel.getTweet(screenTweetId)
    }

    private fun bindObservers() {
        viewModel.unreadMessages.observe(this, Observer {
            unreadMessagesText.text = it.toString()
        })

        viewModel.tweet.observe(this, Observer {
            firstNameLetterText.text = it.username.first().toUpperCase().toString()
            usernameText.text = it.username
            tweetText.text = it.tweet
        })

        viewModel.tweetInteractions.observe(this, Observer {
            repliesText.text = it.responsesQuantity.toString()
            retweetsText.text = it.rtsQuantity.toString()
            favsText.text = it.favoritesQuantity.toString()
        })
    }


}