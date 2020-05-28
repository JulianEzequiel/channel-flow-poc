package com.poc.flowchannel.usecase.flow

import com.poc.flowchannel.repository.FlowsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class GetFlowUseCase constructor(
    private val flowsRepository: FlowsRepository
) {

    fun execute() = flowsRepository.getFlowEmitter()

}