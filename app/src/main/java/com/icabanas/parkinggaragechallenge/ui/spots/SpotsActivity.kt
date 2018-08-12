package com.icabanas.parkinggaragechallenge.ui.spots

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.icabanas.parkinggaragechallenge.ParkingApplication
import com.icabanas.parkinggaragechallenge.R
import com.icabanas.parkinggaragechallenge.vo.Level
import kotlinx.android.synthetic.main.activity_spots.*
import kotlinx.android.synthetic.main.base_toolbar.*
import javax.inject.Inject

class SpotsActivity : AppCompatActivity() {

    @Inject
    lateinit var spotsViewModelFactory: SpotsViewModelFactory
    private lateinit var spotsViewModel: SpotsViewModel

    private var spotsAdapter: SpotsAdapter = SpotsAdapter(emptyList()) {
        //startActivity(newIntent(this, it))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spots)
        setSupportActionBar(toolbar)

        ParkingApplication.appComponent.inject(this)

        spotsList.adapter = spotsAdapter

        spotsViewModel = ViewModelProviders.of(this, spotsViewModelFactory).get(SpotsViewModel::class.java)
        spotsViewModel.level.observe(this, Observer { value ->
            value?.let {
                supportActionBar?.title = it.name
                spotsAdapter.items = it.spots
            }
        })

        spotsViewModel.setId(intent.getIntExtra(INTENT_LEVEL_ID, 0))
    }

    companion object {
        private val INTENT_LEVEL_ID = "level_id"

        fun newIntent(context: Context, level: Level): Intent {
            val intent = Intent(context, SpotsActivity::class.java)
            intent.putExtra(INTENT_LEVEL_ID, level.id)
            return intent
        }
    }
}
