package com.icabanas.parkinggaragechallenge.ui.spots.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.icabanas.parkinggaragechallenge.ParkingApplication
import com.icabanas.parkinggaragechallenge.R
import com.icabanas.parkinggaragechallenge.databinding.ActivitySpotDetailBinding
import com.icabanas.parkinggaragechallenge.ui.book.BookSpotActivity
import com.icabanas.parkinggaragechallenge.utils.UIUtils
import kotlinx.android.synthetic.main.activity_spot_detail.*
import kotlinx.android.synthetic.main.base_toolbar.*
import javax.inject.Inject

class SpotDetailActivity : AppCompatActivity() {

    @Inject
    lateinit var spotDetailViewModelFactory: SpotDetailViewModelFactory
    private lateinit var spotDetailViewModel: SpotDetailViewModel

    private var levelId: Int = 0
    private var spotId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivitySpotDetailBinding>(this, R.layout.activity_spot_detail)

        setSupportActionBar(toolbar)

        // Dagger Injection
        ParkingApplication.appComponent.inject(this)

        //Get Level Id and Spot Id from Intent Parameter
        levelId = intent.getIntExtra(INTENT_LEVEL_ID, 0)
        spotId = intent.getIntExtra(INTENT_SPOT_ID, 0)


        // Initialize ViewModel and Listen to changes
        spotDetailViewModel = ViewModelProviders.of(this, spotDetailViewModelFactory).get(SpotDetailViewModel::class.java)

        spotDetailViewModel.spot.observe(this, Observer {
            value -> value?.let {
                spot ->
                supportActionBar?.title = "Spot #${spot.id}"
                binding.spot = spot
            }
        })

        spotDetailViewModel.releaseSpotResult.observe(this, Observer {
            value -> value?.let {
                if (value) {
                    spotDetailViewModel.setIds(levelId, spotId)
                } else {
                    Toast.makeText(this@SpotDetailActivity, getString(R.string.error_releasing_spot), Toast.LENGTH_SHORT).show()
                }
            }
        })

        spotDetailViewModel.setIds(levelId, spotId)

        // Buttons on click handling
        releaseBtn.setOnClickListener {
            UIUtils.showConfirmationDialog(this@SpotDetailActivity,
                    getString(R.string.release_dialog_title),
                    getString(R.string.release_dialog_description),
                    DialogInterface.OnClickListener { _, _ -> spotDetailViewModel.releaseSpot() })
        }

        bookBtn.setOnClickListener {
            startActivity(BookSpotActivity.newIntent(this, levelId, spotId))
        }

    }

    companion object {
        private val INTENT_SPOT_ID = "spot_id"
        private val INTENT_LEVEL_ID = "level_id"

        fun newIntent(context: Context, levelId: Int, spotId: Int): Intent {
            val intent = Intent(context, SpotDetailActivity::class.java)
            intent.putExtra(INTENT_LEVEL_ID, levelId)
            intent.putExtra(INTENT_SPOT_ID, spotId)
            return intent
        }
    }

}
