package com.poc.flowchannel.usecase.factory

import com.poc.flowchannel.repository.factory.RepositoryFactory.Companion.flowsRepository
import com.poc.flowchannel.repository.factory.RepositoryFactory.Companion.streamsRepository
import com.poc.flowchannel.usecase.channel.CloseChannelsUseCase
import com.poc.flowchannel.usecase.channel.ListenConflatedBroadcastChannelUseCase
import com.poc.flowchannel.usecase.channel.InitChannelsUseCase
import com.poc.flowchannel.usecase.flow.GetFlowUseCase
import com.poc.flowchannel.usecase.channel.ListenChannelUseCase
import com.poc.flowchannel.usecase.flow.GetCallbackFlowUseCase
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