package com.poc.flowchannel.app.home.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.poc.flowchannel.R
import com.poc.flowchannel.app.detail.model.toScreenTweetInteraction
import com.poc.flowchannel.app.home.model.ScreenTweet
import kotlinx.android.synthetic.main.tweet_item_layout.view.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class TweetListAdapter(
    var items: List<ScreenTweet>? = listOf(),
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

        return TweetListViewHolder(view)
    }

    override fun getItemCount(): Int = items?.size ?: 0

    override fun onBindViewHolder(holder: TweetListViewHolder, position: Int) =
        holder.bind(items?.get(position)!!, lifecycleCoroutineScope)
}

class TweetListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(screenTweet: ScreenTweet, lifecycleCoroutineScope: LifecycleCoroutineScope) {
        itemView.firstNameLetterText.text = screenTweet.username.first().toUpperCase().toString()
        itemView.usernameText.text = screenTweet.username
        itemView.tweetText.text = screenTweet.tweet

        screenTweet.interaction
            .map { it.toScreenTweetInteraction() }
            .onEach {
                itemView.repliesText.text = it.responsesQuantity.toString()
                itemView.retweetsText.text = it.rtsQuantity.toString()
                itemView.favsText.text = it.favoritesQuantity.toString()
            }
            .launchIn(lifecycleCoroutineScope)

        itemView.setTag(R.id.list_tweet, screenTweet)
    }

}

interface TweetListCallback {
    fun onTweetSelected(screenTweet: ScreenTweet)
}