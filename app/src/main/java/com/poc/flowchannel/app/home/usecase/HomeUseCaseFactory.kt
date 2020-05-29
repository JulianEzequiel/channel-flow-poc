package com.poc.flowchannel.app.home.usecase

import com.poc.flowchannel.app.core.repository.RepositoryFactory.Companion.tweetRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
object HomeUseCaseFactory {

    fun getTweetsUseCase() = GetTweetsUseCase(tweetRepository())

}