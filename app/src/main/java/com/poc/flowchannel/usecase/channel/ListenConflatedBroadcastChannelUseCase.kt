package com.poc.flowchannel.usecase.channel

import com.poc.flowchannel.repository.StreamsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class ListenConflatedBroadcastChannelUseCase(
    private val streamsRepository: StreamsRepository
) {

    fun execute() = streamsRepository.getConflatedBroadcastChannel()

}