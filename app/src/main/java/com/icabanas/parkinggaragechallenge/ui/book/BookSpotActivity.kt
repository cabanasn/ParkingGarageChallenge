package com.icabanas.parkinggaragechallenge.ui.book

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.icabanas.parkinggaragechallenge.R

class BookSpotActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_spot)
    }

    companion object {
        private val INTENT_SPOT_ID = "spot_id"
        private val INTENT_LEVEL_ID = "level_id"

        fun newIntent(context: Context, levelId: Int, spotId: Int): Intent {
            val intent = Intent(context, BookSpotActivity::class.java)
            intent.putExtra(INTENT_LEVEL_ID, levelId)
            intent.putExtra(INTENT_SPOT_ID, spotId)
            return intent
        }
    }

}
