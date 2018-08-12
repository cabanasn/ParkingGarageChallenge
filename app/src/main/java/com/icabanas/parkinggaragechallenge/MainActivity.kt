package com.icabanas.parkinggaragechallenge

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.icabanas.parkinggaragechallenge.ui.ParkingViewModel
import com.icabanas.parkinggaragechallenge.ui.ParkingViewModelFactory
import com.icabanas.parkinggaragechallenge.ui.spots.SpotsActivity
import com.icabanas.parkinggaragechallenge.ui.levels.LevelsAdapter
import com.icabanas.parkinggaragechallenge.vo.Status
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.collapsing_toolbar_with_img.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var parkingViewModelFactory: ParkingViewModelFactory
    private lateinit var parkingViewModel: ParkingViewModel

    private var levelsAdapter: LevelsAdapter = LevelsAdapter(emptyList()) {
        startActivity(SpotsActivity.newIntent(this, it.id))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // Dagger Injection
        ParkingApplication.appComponent.inject(this)

        levelList.adapter = levelsAdapter

        //Initialize ViewmModel and listen to changes
        parkingViewModel = ViewModelProviders.of(this, parkingViewModelFactory).get(ParkingViewModel::class.java)
        parkingViewModel.parking.observe(this, Observer {
            value ->
                if (value?.status == Status.LOADING) {
                    progressBar.visibility = View.VISIBLE
                } else {
                    progressBar.visibility = View.GONE
                    value?.let { resource ->
                        levelsAdapter.items = resource.data?.levels!!
                    }
                }
        })

    }
}
