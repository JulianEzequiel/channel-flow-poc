package com.poc.flowchannel.examples.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class StateFlowViewModel : ViewModel() {

    private val stateFlow = MutableStateFlow(0)

    fun initEmissions() {
        initStateFlow()
    }

    fun addStateFlowCollector(): LiveData<String> {
        val liveData = MutableLiveData<String>()
        val collector = initNewCollector()
        viewModelScope.launch { collector.collect { liveData.value = it } }
        return liveData
    }

    private fun initStateFlow() {
        viewModelScope.launch {
            while (true) {
                stateFlow.value++
                delay(1000)
            }
        }
    }

    private fun initNewCollector() = stateFlow
        .map { it.toString() }

}