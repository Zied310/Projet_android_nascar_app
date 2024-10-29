package com.example.nascar_app.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventModel(
    val image: Int,
    @PrimaryKey(autoGenerate = true) val id: Int = 5,
    val title: String,
    val date: String,
    val isFavorite: Boolean = false
)
