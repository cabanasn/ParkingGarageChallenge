package com.icabanas.parkinggaragechallenge

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.icabanas.parkinggaragechallenge.ui.levels.LevelsViewModel
import com.icabanas.parkinggaragechallenge.ui.levels.LevelsViewModelFactory
import com.icabanas.parkinggaragechallenge.ui.spots.SpotsActivity
import com.icabanas.parkinggaragechallenge.ui.levels.LevelsAdapter
import com.icabanas.parkinggaragechallenge.ui.spots.search.SearchSpotActivity
import com.icabanas.parkinggaragechallenge.vo.Status
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.collapsing_toolbar_with_img.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var levelsViewModelFactory: LevelsViewModelFactory
    private lateinit var levelsViewModel: LevelsViewModel

    private var levelsAdapter: LevelsAdapter = LevelsAdapter(emptyList()) {
        startActivity(SpotsActivity.newIntent(this, it.id))
        overridePendingTransition(R.anim.enter_anim, R.anim.exit_anim)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // Dagger Injection
        ParkingApplication.appComponent.inject(this)

        levelList.adapter = levelsAdapter

        //Initialize ViewmModel and listen to changes
        levelsViewModel = ViewModelProviders.of(this, levelsViewModelFactory).get(LevelsViewModel::class.java)
        levelsViewModel.parking.observe(this, Observer {
            value ->
                when (value?.status) {
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        value.let { resource ->
                            levelsAdapter.items = resource.data?.levels!!
                            levelsAdapter.notifyDataSetChanged()
                        }
                    }
                    Status.ERROR -> {
                        progressBar.visibility = View.GONE
                        value.let { resource ->
                            Toast.makeText(this@MainActivity, resource.message, Toast.LENGTH_SHORT).show()
                            resource.data.let { parking ->
                                levelsAdapter.items = parking?.levels!!
                                levelsAdapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
        })

        searchBtn.setOnClickListener {
            startActivity(SearchSpotActivity.newIntent(this))
            overridePendingTransition(R.anim.enter_anim, R.anim.exit_anim)
        }

    }

    override fun onResume() {
        super.onResume()
        levelsViewModel.refreshParking()
    }
}
