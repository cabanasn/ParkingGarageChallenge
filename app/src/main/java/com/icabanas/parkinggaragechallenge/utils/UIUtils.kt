package com.icabanas.parkinggaragechallenge.utils

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import com.icabanas.parkinggaragechallenge.R

object UIUtils {

    fun showConfirmationDialog(context: Context,
                               title:String,
                               message: String,
                               confirmCallback: DialogInterface.OnClickListener) {

        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(context.getString(R.string.ok), confirmCallback)
        builder.setNegativeButton(context.getString(R.string.cancel), null)
        builder.show()
    }
}