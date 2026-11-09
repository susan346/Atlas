package com.projectatlas.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "places")
data class PlaceEntity(
    @PrimaryKey val id: String,
    val name: String,
    val subtitle: String? = null,
    val xPercent: Float,
    val yPercent: Float,
    val colorHex: String = "#D96B43",
    val visitedDate: String,
    val createdAt: Long = System.currentTimeMillis()
)
