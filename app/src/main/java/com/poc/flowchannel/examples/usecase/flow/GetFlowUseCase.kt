package com.poc.flowchannel.examples.usecase.flow

import com.poc.flowchannel.examples.repository.FlowsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class GetFlowUseCase constructor(
    private val flowsRepository: FlowsRepository
) {

    fun execute() = flowsRepository.getFlowEmitter()

}