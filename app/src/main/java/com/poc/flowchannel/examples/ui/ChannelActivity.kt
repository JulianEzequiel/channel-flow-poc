package com.poc.flowchannel.examples.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.poc.flowchannel.R
import com.poc.flowchannel.examples.viewmodel.ChannelViewModel
import kotlinx.android.synthetic.main.activity_channel.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
class ChannelActivity : AppCompatActivity() {

    private val channelViewModel: ChannelViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_channel)

        bindObservers()
        bindListeners()
    }

    private fun bindObservers() {
        channelViewModel.bufferedValue.observe(this, Observer {
            bufferedValues.text = it
        })
        channelViewModel.conflatedValue.observe(this, Observer {
            conflatedValues.text = it
        })
        channelViewModel.otherValue.observe(this, Observer {
            otherValues.text = it
        })
        channelViewModel.rendezvousValue.observe(this, Observer {
            rendezvousValues.text = it
        })
        channelViewModel.unlimitedValue.observe(this, Observer {
            unlimitedValues.text = it
        })
    }

    private fun bindListeners() {
        initChannels.setOnClickListener {
            channelViewModel.initEmissions()

            channelViewModel.timerValues().observe(this, Observer {
                normalEmissionValues.text = it
            })
        }

        receiveRendezvous.setOnClickListener { channelViewModel.receiveRendezvous() }
        receiveBuffered.setOnClickListener { channelViewModel.receiveBuffered() }
        receiveConflated.setOnClickListener { channelViewModel.receiveConflated() }
        receiveOther.setOnClickListener { channelViewModel.receiveOther() }
        receiveUnlimited.setOnClickListener { channelViewModel.receiveUnlimited() }
    }


}