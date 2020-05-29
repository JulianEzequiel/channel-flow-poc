package com.poc.flowchannel.app.detail.usecase

import com.poc.flowchannel.app.core.repository.RepositoryFactory.Companion.tweetRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
object TweetDetailUseCaseFactory {

    fun getTweetInteractionsUseCase() = GetTweetInteractionsUseCase(tweetRepository())

}