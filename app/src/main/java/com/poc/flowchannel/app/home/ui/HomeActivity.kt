package com.poc.flowchannel.app.home.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.poc.flowchannel.R
import com.poc.flowchannel.app.detail.ui.TweetDetailActivity
import com.poc.flowchannel.app.home.model.ScreenTweet
import com.poc.flowchannel.app.home.ui.adapter.TweetListAdapter
import com.poc.flowchannel.app.home.ui.adapter.TweetListCallback
import com.poc.flowchannel.app.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
class HomeActivity : AppCompatActivity(), TweetListCallback {

    private val homeViewModel: HomeViewModel by viewModels()

    private val tweetsAdapter = TweetListAdapter(tweetListCallback = this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        configureRecycler()
        bindObservers()
    }

    override fun onTweetSelected(screenTweet: ScreenTweet) {
        startActivity(TweetDetailActivity.get(this, screenTweet))
    }

    private fun configureRecycler() {
        tweetList.layoutManager = LinearLayoutManager(this)
        tweetList.adapter = tweetsAdapter
    }

    private fun bindObservers() {
        homeViewModel.tweets.observe(this, Observer {
            tweetsAdapter.items = it
            tweetsAdapter.notifyDataSetChanged()
        })

        homeViewModel.unreadMessages.observe(this, Observer {
            unreadMessagesText.text = it.toString()
        })
    }

}