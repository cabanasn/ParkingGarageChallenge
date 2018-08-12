package com.icabanas.parkinggaragechallenge.ui

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.icabanas.parkinggaragechallenge.R
import com.icabanas.parkinggaragechallenge.vo.Level

class SpotsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spots)
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
