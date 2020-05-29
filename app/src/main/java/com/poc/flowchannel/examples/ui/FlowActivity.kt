package com.poc.flowchannel.examples.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.poc.flowchannel.R
import com.poc.flowchannel.examples.viewmodel.FlowViewModel
import kotlinx.android.synthetic.main.activity_flow.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class FlowActivity : AppCompatActivity() {

    private val flowViewModel: FlowViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flow)

        bindObservers()
        bindListeners()
    }

    private fun bindObservers() {
        flowViewModel.flowValues.observe(this, Observer {
            flowValues.text = it
        })
        flowViewModel.callbackFlowValues.observe(this, Observer {
            callbackFlowValues.text = it
        })
    }

    private fun bindListeners() {
        initFlows.setOnClickListener { flowViewModel.initEmissions() }
    }

}