package com.poc.flowchannel.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.poc.flowchannel.usecase.factory.UseCaseFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class FlowViewModel : ViewModel() {

    private val getFlowUseCase = UseCaseFactory.getFlowUseCase()
    private val getCallbackFlowUseCase = UseCaseFactory.getCallbackFlowUseCase()

    val flowValues = MutableLiveData<String>()
    val callbackFlowValues = MutableLiveData<String>()

    fun initEmissions() {
        observeFlow()
        observeCallbackFlow()
    }

    fun observeFlow() {
        viewModelScope.launch {
            getFlowUseCase.execute()
                .collect {
                    flowValues.value = it
                }
        }
    }

    private fun observeCallbackFlow() {
        viewModelScope.launch {
            getCallbackFlowUseCase.execute()
                .collect {
                    callbackFlowValues.value = it
                }
        }
    }

}