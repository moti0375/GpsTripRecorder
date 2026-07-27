package com.dunihuliapps.myglidingassistant.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "airfields")
data class Airfield(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val isHome: Boolean = false,
)
