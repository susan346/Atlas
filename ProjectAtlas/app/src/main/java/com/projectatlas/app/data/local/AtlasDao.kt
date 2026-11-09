package com.projectatlas.app.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AtlasDao {
    @Query("SELECT * FROM places ORDER BY visitedDate DESC")
    fun getAllPlacesFlow(): Flow<List<PlaceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlace(place: PlaceEntity)

    @Update
    suspend fun updatePlace(place: PlaceEntity)

    @Query("UPDATE places SET xPercent = :xPercent, yPercent = :yPercent WHERE id = :id")
    suspend fun updatePlaceCoordinates(id: String, xPercent: Float, yPercent: Float)

    @Query("DELETE FROM places WHERE id = :id")
    suspend fun deletePlaceById(id: String)

    @Query("SELECT * FROM journal_entries WHERE placeId = :placeId ORDER BY date DESC")
    fun getJournalsForPlaceFlow(placeId: String): Flow<List<JournalEntryEntity>>

    @Query("SELECT * FROM journal_entries ORDER BY date DESC")
    fun getAllJournalsFlow(): Flow<List<JournalEntryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJournal(journal: JournalEntryEntity)

    @Query("DELETE FROM journal_entries WHERE id = :id")
    suspend fun deleteJournalById(id: String)

    @Query("SELECT * FROM place_photos WHERE placeId = :placeId ORDER BY takenAt DESC")
    fun getPhotosForPlaceFlow(placeId: String): Flow<List<PhotoEntity>>

    @Query("SELECT * FROM place_photos ORDER BY takenAt DESC")
    fun getAllPhotosFlow(): Flow<List<PhotoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(photo: PhotoEntity)

    @Query("DELETE FROM place_photos WHERE id = :id")
    suspend fun deletePhotoById(id: String)
}
