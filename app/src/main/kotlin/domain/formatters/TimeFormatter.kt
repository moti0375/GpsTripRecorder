package com.dunihuliapps.myglidingassistant.domain.formatters

interface TimeFormatter {
    fun formatTime(millis: Long) : String
}