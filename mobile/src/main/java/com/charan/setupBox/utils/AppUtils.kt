package com.charan.setupBox.utils

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

object AppUtils {

    fun getColorForPlaceHolderBackground(): Color {
        val random = Random(System.currentTimeMillis())
        return Color(
            red = 204,
            green = 202,
            blue = 176
        )
    }

    fun getTextColorForPlaceholder() : Color{
        return Color(
            red = 51,
            green = 54,
            blue = 61
        )
    }

}