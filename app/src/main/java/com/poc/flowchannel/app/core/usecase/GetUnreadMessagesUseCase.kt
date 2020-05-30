package com.poc.flowchannel.app.core.usecase

import com.poc.flowchannel.app.core.repository.TweetRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class GetUnreadMessagesUseCase(
    private val tweetRepository: TweetRepository
) {

    suspend fun execute() = tweetRepository.getUnreadMessages()

}