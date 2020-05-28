package com.poc.flowchannel.usecase.flow

import com.poc.flowchannel.repository.FlowsRepository

class GetCallbackFlowUseCase(
    private val flowsRepository: FlowsRepository
) {

    fun execute() = flowsRepository.getCallbackFlowEmitter()

}