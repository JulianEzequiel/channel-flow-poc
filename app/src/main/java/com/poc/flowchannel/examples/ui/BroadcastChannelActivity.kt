package com.poc.flowchannel.examples.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.poc.flowchannel.R
import com.poc.flowchannel.examples.viewmodel.BroadcastChannelViewModel
import kotlinx.android.synthetic.main.activity_broadcast_channels.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
class BroadcastChannelActivity: AppCompatActivity() {

    private val viewModel: BroadcastChannelViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_broadcast_channels)

        bindObservers()
        bindListeners()
    }

    private fun bindObservers() {
        viewModel.broadcastChannelReceiver1.observe(this, Observer {
            openSuscriptionReceiver1Values.text = it
        })
        viewModel.broadcastChannelReceiver2.observe(this, Observer {
            openSuscriptionReceiver2Values.text = it
        })

        viewModel.asFlowBroadcastChannelReceiver1.observe(this, Observer {
            asFlowReceiver1Values.text = it
        })
        viewModel.asFlowBroadcastChannelReceiver2.observe(this, Observer {
            asFlowReceiver2Values.text = it
        })

    }

    private fun bindListeners() {
        initChannelEmission.setOnClickListener {
            viewModel.initEmissions()
        }
    }

}