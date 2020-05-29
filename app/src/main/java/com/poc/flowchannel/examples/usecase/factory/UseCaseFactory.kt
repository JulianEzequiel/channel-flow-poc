package com.poc.flowchannel.examples.usecase.factory

import com.poc.flowchannel.examples.repository.factory.RepositoryFactory.Companion.flowsRepository
import com.poc.flowchannel.examples.repository.factory.RepositoryFactory.Companion.streamsRepository
import com.poc.flowchannel.examples.usecase.channel.CloseChannelsUseCase
import com.poc.flowchannel.examples.usecase.channel.InitChannelsUseCase
import com.poc.flowchannel.examples.usecase.channel.ListenChannelUseCase
import com.poc.flowchannel.examples.usecase.channel.ListenConflatedBroadcastChannelUseCase
import com.poc.flowchannel.examples.usecase.flow.GetCallbackFlowUseCase
import com.poc.flowchannel.examples.usecase.flow.GetFlowUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
object UseCaseFactory {

    fun getFlowUseCase() =
        GetFlowUseCase(flowsRepository())

    fun getCallbackFlowUseCase() =
        GetCallbackFlowUseCase(flowsRepository())

    fun getBroadcastChannelEmissionUseCase() =
        ListenConflatedBroadcastChannelUseCase(streamsRepository())

    fun listenConflatedChannelUseCase() =
        ListenChannelUseCase(
            streamsRepository()
        )

    fun initChannelsUseCase() = InitChannelsUseCase(streamsRepository())

    fun closeChannelsUseCase() = CloseChannelsUseCase(streamsRepository())

}