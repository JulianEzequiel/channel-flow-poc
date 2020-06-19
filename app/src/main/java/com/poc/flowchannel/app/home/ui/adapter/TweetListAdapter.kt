package com.poc.flowchannel.app.home.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.poc.flowchannel.R
import com.poc.flowchannel.app.core.usecase.GetTweetInteractionUseCase
import com.poc.flowchannel.app.detail.model.toScreenTweetInteraction
import com.poc.flowchannel.app.home.model.ScreenTweet
import kotlinx.android.synthetic.main.tweet_item_layout.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class TweetListAdapter(
    var items: List<ScreenTweet>? = listOf(),
    private val getTweetInteractionUseCase: GetTweetInteractionUseCase,
    private val tweetListCallback: TweetListCallback,
    private val lifecycleCoroutineScope: LifecycleCoroutineScope
) : RecyclerView.Adapter<TweetListViewHolder>() {

    private lateinit var inflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetListViewHolder {
        if (!::inflater.isInitialized) inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.tweet_item_layout, parent, false)

        view.setOnClickListener {
            tweetListCallback.onTweetSelected(view.getTag(R.id.list_tweet) as ScreenTweet)
        }

        return TweetListViewHolder(lifecycleCoroutineScope, getTweetInteractionUseCase, view)
    }

    override fun getItemCount(): Int = items?.size ?: 0

    override fun onBindViewHolder(holder: TweetListViewHolder, position: Int) =
        holder.bind(items?.get(position)!!)
}

class TweetListViewHolder(
    private val coroutineScope: CoroutineScope,
    private val getTweetInteractionUseCase: GetTweetInteractionUseCase,
    view: View
) : RecyclerView.ViewHolder(view) {

    private var tweetInteractionJob: Job? = null

    fun bind(screenTweet: ScreenTweet) {
        tweetInteractionJob?.cancel()

        itemView.firstNameLetterText.text = screenTweet.username.first().toUpperCase().toString()
        itemView.usernameText.text = screenTweet.username
        itemView.tweetText.text = screenTweet.tweet

        tweetInteractionJob = coroutineScope.launch {
            getTweetInteractionUseCase.execute(screenTweet.id)
                .map { it.toScreenTweetInteraction() }
                .collect {
                    itemView.repliesText.text = it.responsesQuantity.toString()
                    itemView.retweetsText.text = it.rtsQuantity.toString()
                    itemView.favsText.text = it.favoritesQuantity.toString()
                }
        }

        itemView.setTag(R.id.list_tweet, screenTweet)
    }

}

interface TweetListCallback {
    fun onTweetSelected(screenTweet: ScreenTweet)
}