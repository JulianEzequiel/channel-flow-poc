package com.poc.flowchannel.examples.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.poc.flowchannel.R
import com.poc.flowchannel.examples.viewmodel.ChannelReceiversViewModel
import kotlinx.android.synthetic.main.activity_channels_receivers.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@ExperimentalCoroutinesApi
@FlowPreview
class ChannelReceiversActivity : AppCompatActivity() {

    private val viewModel: ChannelReceiversViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channels_receivers)

        bindObservers()
        bindListeners()
    }

    private fun bindObservers() {
        viewModel.rendezvousChannelReceiver1.observe(this, Observer {
            rendezvousReceiver1Values.text = it
        })
        viewModel.rendezvousChannelReceiver2.observe(this, Observer {
            rendezvousReceiver2Values.text = it
        })

        viewModel.unlimitedChannelReceiver1.observe(this, Observer {
            unlimitedReceiver1Values.text = it
        })
        viewModel.unlimitedChannelReceiver2.observe(this, Observer {
            unlimitedReceiver2Values.text = it
        })

        viewModel.conflatedChannelReceiver1.observe(this, Observer {
            conflatedReceiver1Values.text = it
        })
        viewModel.conflatedChannelReceiver2.observe(this, Observer {
            conflatedReceiver2Values.text = it
        })
    }

    private fun bindListeners() {
        initChannelEmission.setOnClickListener {
            viewModel.initEmission()
        }
    }

}