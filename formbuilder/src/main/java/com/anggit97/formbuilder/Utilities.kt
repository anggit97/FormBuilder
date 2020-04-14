package com.anggit97.formbuilder

import android.content.Context
import android.util.DisplayMetrics

object Utilities {

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return An int value to represent px equivalent to dp depending on device density
     */
    fun convertDpToPixel(dp: Float, context: Context): Int {
        val resources = context.resources
        val metrics = resources.displayMetrics
        val px =
            dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        return px.toInt()
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return An int value to represent dp equivalent to px value
     */
    fun convertPixelsToDp(px: Float, context: Context): Int {
        val resources = context.resources
        val metrics = resources.displayMetrics
        val dp =
            px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
        return dp.toInt()
    }
}