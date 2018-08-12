package com.icabanas.parkinggaragechallenge.ui.spots

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.icabanas.parkinggaragechallenge.ParkingApplication
import com.icabanas.parkinggaragechallenge.R
import com.icabanas.parkinggaragechallenge.vo.Level
import javax.inject.Inject

class SpotsActivity : AppCompatActivity() {

    @Inject
    lateinit var spotsViewModelFactory: SpotsViewModelFactory
    private lateinit var spotsViewModel: SpotsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spots)

        ParkingApplication.appComponent.inject(this)

        spotsViewModel = ViewModelProviders.of(this, spotsViewModelFactory).get(SpotsViewModel::class.java)
        spotsViewModel.level.observe(this, Observer {
            value ->
            Toast.makeText(this, value?.name ,Toast.LENGTH_SHORT).show()
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
