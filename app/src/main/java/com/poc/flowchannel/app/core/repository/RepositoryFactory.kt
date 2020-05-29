package com.poc.flowchannel.app.core.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
class RepositoryFactory {

    private val tweetRepository: TweetRepository = TweetRepository()

    companion object {
        private val _selfInstance = RepositoryFactory()

        fun tweetRepository() = _selfInstance.tweetRepository
    }

}