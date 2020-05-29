package com.poc.flowchannel.examples.repository.factory

import com.poc.flowchannel.examples.repository.FlowsRepository
import com.poc.flowchannel.examples.repository.StreamsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
class RepositoryFactory {

    private val streamsRepository: StreamsRepository = StreamsRepository()
    private val flowsRepository: FlowsRepository = FlowsRepository()

    companion object {

        private val _selfInstance = RepositoryFactory()

        fun streamsRepository() = _selfInstance.streamsRepository

        fun flowsRepository() = _selfInstance.flowsRepository

    }

}