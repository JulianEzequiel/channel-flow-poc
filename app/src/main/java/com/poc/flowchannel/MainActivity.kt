package com.poc.flowchannel

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.poc.flowchannel.app.home.ui.HomeActivity
import com.poc.flowchannel.examples.ui.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        goToFlowButton.setOnClickListener {
            startActivity(Intent(this, FlowActivity::class.java))
        }
        goToChannelButton.setOnClickListener {
            startActivity(Intent(this, ChannelActivity::class.java))
        }
        goToStateFlowButton.setOnClickListener {
            startActivity(Intent(this, StateFlowActivity::class.java))
        }
        goToChannelReceiversButton.setOnClickListener {
            startActivity(Intent(this, ChannelReceiversActivity::class.java))
        }
        goToBroadcastChannelButton.setOnClickListener {
            startActivity(Intent(this, BroadcastChannelActivity::class.java))
        }

        goToAppButton.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }

}
