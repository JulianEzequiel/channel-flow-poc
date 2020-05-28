package com.poc.flowchannel.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.poc.flowchannel.R
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
    }

}
