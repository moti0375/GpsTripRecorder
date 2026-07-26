package com.dunihuliapps.myglidingassistant.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gliders")
data class Glider(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val type: String,
    val callsign: String,
    val seats: Int,
    val ratio: Int,
    val gliderImage: String? = null
)