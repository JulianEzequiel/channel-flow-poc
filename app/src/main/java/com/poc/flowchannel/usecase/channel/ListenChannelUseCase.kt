package com.poc.flowchannel.usecase.channel

import com.poc.flowchannel.repository.StreamsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel

@ExperimentalCoroutinesApi
@FlowPreview
class ListenChannelUseCase(
    private val streamsRepository: StreamsRepository
) {

    suspend fun execute(listenType: ListenType) =
        streamsRepository.listenChannelEmissions(listenType.value)

    enum class ListenType(val value: Int) {
        RENDEZVOUS(Channel.RENDEZVOUS),
        UNLIMITED(Channel.UNLIMITED),
        CONFLATED(Channel.CONFLATED),
        BUFFERED(Channel.BUFFERED),
        OTHER(2)
    }
}