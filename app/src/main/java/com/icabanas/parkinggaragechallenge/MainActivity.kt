package com.icabanas.parkinggaragechallenge

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.icabanas.parkinggaragechallenge.ui.ParkingViewModel
import com.icabanas.parkinggaragechallenge.ui.ParkingViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var parkingViewModelFactory: ParkingViewModelFactory
    lateinit var parkingViewModel: ParkingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ParkingApplication.appComponent.inject(this)

        parkingViewModel = ViewModelProviders.of(this, parkingViewModelFactory).get(ParkingViewModel::class.java)
        parkingViewModel.parking.observe(this, Observer {
            value -> placeholder.text = value?.status.toString()
        })

        parkingViewModel.initNetworkRequest()

    }
}
