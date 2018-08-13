package com.icabanas.parkinggaragechallenge.ui.spots.search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import com.icabanas.parkinggaragechallenge.ParkingApplication
import com.icabanas.parkinggaragechallenge.R
import com.icabanas.parkinggaragechallenge.databinding.ActivitySearchSpotBinding
import com.icabanas.parkinggaragechallenge.ui.book.BookSpotActivity
import com.icabanas.parkinggaragechallenge.ui.spots.detail.SearchSpotViewModel
import com.icabanas.parkinggaragechallenge.ui.spots.detail.SearchSpotViewModelFactory
import kotlinx.android.synthetic.main.activity_search_spot.*
import kotlinx.android.synthetic.main.base_toolbar.*
import javax.inject.Inject

class SearchSpotActivity : AppCompatActivity() {

    @Inject
    lateinit var searchSpotViewModelFactory: SearchSpotViewModelFactory
    private lateinit var searchSpotViewModel: SearchSpotViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivitySearchSpotBinding>(this, R.layout.activity_search_spot)

        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.search_title)

        // Dagger Injection
        ParkingApplication.appComponent.inject(this)

        // Initialize ViewModel and Listen to changes
        searchSpotViewModel = ViewModelProviders.of(this, searchSpotViewModelFactory).get(SearchSpotViewModel::class.java)

        searchSpotViewModel.spot.observe(this, Observer {
            spot ->
                binding.spot = spot
        })

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (s.isNotEmpty()) {
                    noResultsFound.text = getString(R.string.big_enough_spot_error)
                    searchSpotViewModel.setSize(s.toString().toInt())
                } else {
                    noResultsFound.text = ""
                    binding.spot = null
                }
            }
        })

        bookBtn.setOnClickListener {
            startActivity(BookSpotActivity.newIntent(this,
                    binding.spot?.levelId!!,
                    binding.spot?.id!!,
                    searchEditText.text.toString().toInt()))
            overridePendingTransition(R.anim.enter_anim, R.anim.exit_anim)
        }
    }

    override fun onResume() {
        super.onResume()
        searchEditText.text.clear()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, SearchSpotActivity::class.java)
            return intent
        }
    }
}
