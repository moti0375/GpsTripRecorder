package com.dunihuliapps.myglidingassistant.domain.formatters

import android.text.SpannableString


interface UnitsFormatter {
    fun formatUnits(data: Double) : SpannableString
}