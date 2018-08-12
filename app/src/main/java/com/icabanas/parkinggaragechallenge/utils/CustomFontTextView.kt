package com.icabanas.parkinggaragechallenge.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView
import com.icabanas.parkinggaragechallenge.R
import timber.log.Timber

class CustomFontTextView: TextView {

    constructor(context: Context): super(context) {
        applyCustomFont(context)
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        applyCustomFont(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int): super(context, attrs, defStyle) {
        applyCustomFont(context, attrs)
    }

    private fun applyCustomFont(context: Context, attrs: AttributeSet? = null) {
        includeFontPadding = false

        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomFontTextView)
            val textStyle = typedArray.getString(R.styleable.CustomFontTextView_textStyle)

            try {
                val customFont: Typeface? = if (textStyle != null) {
                    when (textStyle) {
                        "bold" -> FontCache.getTypeFace("OpenSans-Bold", context)
                        "light" -> FontCache.getTypeFace("OpenSans-Light", context)
                        "semibold" -> FontCache.getTypeFace("OpenSans-Semibold", context)
                        else -> FontCache.getTypeFace("OpenSans-Regular", context)
                    }
                } else {
                    FontCache.getTypeFace("OpenSans-Regular", context)
                }

                typeface = customFont
            } catch (e: Exception) {
                Timber.e(e.message)
            }

            typedArray.recycle()
        }

    }
}