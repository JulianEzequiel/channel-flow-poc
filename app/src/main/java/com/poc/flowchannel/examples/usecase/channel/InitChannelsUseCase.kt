package com.poc.flowchannel.examples.usecase.channel

import com.poc.flowchannel.examples.repository.StreamsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
class InitChannelsUseCase(
    private val streamsRepository: StreamsRepository
) {

    fun execute() = streamsRepository.initChannelEmissions()

}