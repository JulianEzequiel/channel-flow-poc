package com.poc.flowchannel.examples.usecase.flow

import com.poc.flowchannel.examples.repository.FlowsRepository

class GetCallbackFlowUseCase(
    private val flowsRepository: FlowsRepository
) {

    fun execute() = flowsRepository.getCallbackFlowEmitter()

}