package com.projectatlas.app.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "journal_entries",
    foreignKeys = [ForeignKey(entity = PlaceEntity::class, parentColumns = ["id"], childColumns = ["placeId"], onDelete = ForeignKey.CASCADE)],
    indices = [Index("placeId"), Index("date")]
)
data class JournalEntryEntity(
    @PrimaryKey val id: String,
    val placeId: String,
    val content: String,
    val mood: String = "peaceful",
    val date: String,
    val createdAt: Long = System.currentTimeMillis()
)
