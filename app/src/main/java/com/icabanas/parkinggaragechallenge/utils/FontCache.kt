package com.icabanas.parkinggaragechallenge.utils

import android.content.Context
import android.graphics.Typeface
import timber.log.Timber

class FontCache {
    companion object {
        private val fontCache: HashMap<String, Typeface> = HashMap()

        fun getTypeFace(fontName: String, context: Context): Typeface? {
            var typeface = fontCache[fontName]

            if (typeface == null) {
                try {
                    typeface = Typeface.createFromAsset(context.assets, fontName)
                } catch (e: Exception) {
                    Timber.e(e.message)
                    return null
                }
                fontCache[fontName] = typeface
            }
            return typeface
        }
    }
}