package com.icabanas.parkinggaragechallenge.ui.book

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.icabanas.parkinggaragechallenge.ParkingApplication
import com.icabanas.parkinggaragechallenge.R
import com.icabanas.parkinggaragechallenge.databinding.ActivityBookSpotBinding
import com.icabanas.parkinggaragechallenge.utils.UIUtils
import com.icabanas.parkinggaragechallenge.vo.Spot
import com.icabanas.parkinggaragechallenge.vo.Vehicle
import kotlinx.android.synthetic.main.activity_spot_detail.*
import kotlinx.android.synthetic.main.base_toolbar.*
import java.util.*
import javax.inject.Inject

class BookSpotActivity : AppCompatActivity() {

    @Inject
    lateinit var bookSpotViewModelFactory: BookSpotViewModelFactory
    private lateinit  var bookSpotViewModel: BookSpotViewModel

    private lateinit var binding: ActivityBookSpotBinding

    private var levelId: Int = 0
    private var spotId: Int = 0
    private var sizeExtra: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_spot)

        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.book_spot_title)
        // Dagger Injection
        ParkingApplication.appComponent.inject(this)

        //Get Level Id and Spot Id from Intent Parameter
        levelId = intent.getIntExtra(INTENT_LEVEL_ID, 0)
        spotId = intent.getIntExtra(INTENT_SPOT_ID, 0)
        sizeExtra = intent.getIntExtra(INTENT_SIZE, 0)
        if (sizeExtra > 0) {
            size.text = "$sizeExtra"
            size.isEnabled = false
        } else {
            size.isEnabled = true
        }

        // Initialize ViewModel and Listen to changes
        bookSpotViewModel = ViewModelProviders.of(this, bookSpotViewModelFactory).get(BookSpotViewModel::class.java)

        bookSpotViewModel.spot.observe(this, Observer {
            value -> value?.let {
                spot ->
                binding.spot = spot
            }
        })

        bookSpotViewModel.bookSpotResult.observe(this, Observer {
            value -> value?.let {
            if (value) {
                Toast.makeText(this@BookSpotActivity, getString(R.string.spot_booked_successfully), Toast.LENGTH_SHORT).show()
                this@BookSpotActivity.finish()
            } else {
                Toast.makeText(this@BookSpotActivity, getString(R.string.error_booking_spot), Toast.LENGTH_SHORT).show()
            }
        }
        })

        bookSpotViewModel.setIds(levelId, spotId)

        bookBtn.setOnClickListener {
           if (isValidForm()) bookSpot()
        }
    }

    private fun isValidForm(): Boolean {
        var message = ""
        if (plate.text.isEmpty())
            message += "Plate cannot be empty\n"
        if (brand.text.isEmpty())
            message += "Brand cannot be empty\n"
        if (color.text.isEmpty())
            message += "Color cannot be empty\n"
        if (size.text.isEmpty()) {
            message += "Size cannot be empty\n"
        } else {
            if (size.text.toString().toInt() > binding.spot?.size!!)
                message += "Vehicle size is bigger than spot size\n"
        }
        if (message.isNotEmpty())
            UIUtils.showErrorDialog(this, message)
        return message.isEmpty()
    }

    private fun bookSpot() {
        bookSpotViewModel.bookSpot(Vehicle(
                plate.text.toString(),
                brand.text.toString(),
                color.text.toString(),
                size.text.toString().toInt(),
                Date()
        ))
    }

    companion object {
        private val INTENT_SPOT_ID = "spot_id"
        private val INTENT_LEVEL_ID = "level_id"
        private val INTENT_SIZE = "size"

        fun newIntent(context: Context, levelId: Int, spotId: Int, size: Int = 0): Intent {
            val intent = Intent(context, BookSpotActivity::class.java)
            intent.putExtra(INTENT_LEVEL_ID, levelId)
            intent.putExtra(INTENT_SPOT_ID, spotId)
            if (size > 0) {
                intent.putExtra(INTENT_SIZE, size)
            }
            return intent
        }
    }

}
