package com.poc.flowchannel.examples.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Observer
import com.poc.flowchannel.R
import com.poc.flowchannel.examples.viewmodel.StateFlowViewModel
import kotlinx.android.synthetic.main.activity_stateflow.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class StateFlowActivity : AppCompatActivity() {

    private val viewModel: StateFlowViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stateflow)

        bindListeners()
    }

    private fun bindListeners() {
        initFlows.setOnClickListener {
            viewModel.initEmissions()
        }

        addReceiver.setOnClickListener {
            val textView = createTextView()
            viewModel.addStateFlowCollector().observe(this, Observer {
                textView.text = "Value: $it"
            })
        }
    }

    private fun createTextView(): AppCompatTextView {
        val textView = AppCompatTextView(this)
        stateFlowValuesContainer.addView(textView)
        return textView
    }

}