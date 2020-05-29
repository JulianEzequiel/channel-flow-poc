package com.poc.flowchannel.examples.usecase.channel

import com.poc.flowchannel.examples.repository.StreamsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class ListenConflatedBroadcastChannelUseCase(
    private val streamsRepository: StreamsRepository
) {

    fun execute() = streamsRepository.getConflatedBroadcastChannel()

}