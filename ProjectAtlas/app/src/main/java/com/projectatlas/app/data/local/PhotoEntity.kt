package com.projectatlas.app.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "place_photos",
    foreignKeys = [ForeignKey(entity = PlaceEntity::class, parentColumns = ["id"], childColumns = ["placeId"], onDelete = ForeignKey.CASCADE)],
    indices = [Index("placeId"), Index("takenAt")]
)
data class PhotoEntity(
    @PrimaryKey val id: String,
    val placeId: String,
    val localFilePath: String, // 仅存磁盘路径，严禁把大图直接存入 SQLite
    val caption: String? = null,
    val takenAt: String,
    val isCameraCapture: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
