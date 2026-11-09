package com.projectatlas.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PlaceEntity::class, JournalEntryEntity::class, PhotoEntity::class], version = 1, exportSchema = false)
abstract class AtlasDatabase : RoomDatabase() {
    abstract fun atlasDao(): AtlasDao

    companion object {
        @Volatile private var INSTANCE: AtlasDatabase? = null
        fun getInstance(context: Context): AtlasDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context.applicationContext, AtlasDatabase::class.java, "project_atlas.db")
                    .fallbackToDestructiveMigration()
                    .build().also { INSTANCE = it }
            }
    }
}
