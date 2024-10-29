package com.example.nascar_app.data.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface EventDao {
    @Insert
    suspend fun insert(entity: EventModel)

    @Query("SELECT * FROM events")
    suspend fun getAll(): List<EventModel>

    @Query("SELECT * FROM events WHERE isFavorite = 1")
    suspend fun getFavoriteEvents(): List<EventModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addEventToFavorites(event: EventModel) {
        val updatedEvent = event.copy(isFavorite = 1)
        insert(updatedEvent) // Call the insert method to update the event in the database
    }

    @Query("DELETE FROM events WHERE id = :eventId")
    suspend fun removeEventFromFavorites(eventId: Int)

    @Query("SELECT * FROM events WHERE id = :id")
    suspend fun getById(id: Int): EventModel
}