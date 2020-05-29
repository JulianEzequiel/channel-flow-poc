package com.poc.flowchannel.app.core.usecase

import com.poc.flowchannel.app.core.repository.RepositoryFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
object CoreUseCaseFactory {

    fun getUnreadMessagesUseCase() =
        GetUnreadMessagesUseCase(
            RepositoryFactory.tweetRepository()
        )

}