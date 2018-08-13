package com.icabanas.parkinggaragechallenge.ui.spots

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.icabanas.parkinggaragechallenge.ParkingApplication
import com.icabanas.parkinggaragechallenge.R
import com.icabanas.parkinggaragechallenge.ui.spots.detail.SpotDetailActivity
import kotlinx.android.synthetic.main.activity_spots.*
import kotlinx.android.synthetic.main.base_toolbar.*
import javax.inject.Inject

class SpotsActivity : AppCompatActivity() {

    @Inject
    lateinit var spotsViewModelFactory: SpotsViewModelFactory
    private lateinit var spotsViewModel: SpotsViewModel

    private var levelId: Int = 0

    private var spotsAdapter: SpotsAdapter = SpotsAdapter(emptyList()) {
        startActivity(SpotDetailActivity.newIntent(this, levelId, it.id))
        overridePendingTransition(R.anim.enter_anim, R.anim.exit_anim)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spots)
        setSupportActionBar(toolbar)

        // Dagger Injection
        ParkingApplication.appComponent.inject(this)

        //Get Level Id from Intent Parameter
        levelId = intent.getIntExtra(INTENT_LEVEL_ID, 0)

        spotsList.adapter = spotsAdapter

        // Initialize ViewModel and Listen to changes
        spotsViewModel = ViewModelProviders.of(this, spotsViewModelFactory).get(SpotsViewModel::class.java)
        spotsViewModel.level.observe(this, Observer { value ->
            value?.let {
                level ->
                    supportActionBar?.title = level.name
                    spotsAdapter.items = level.spots
                    spotsAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        spotsViewModel.setId(levelId)
    }

    companion object {
        private val INTENT_LEVEL_ID = "level_id"

        fun newIntent(context: Context, levelId: Int): Intent {
            val intent = Intent(context, SpotsActivity::class.java)
            intent.putExtra(INTENT_LEVEL_ID, levelId)
            return intent
        }
    }
}
