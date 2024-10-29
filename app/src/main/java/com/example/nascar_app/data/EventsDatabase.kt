package com.example.nascar_app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.example.nascar_app.data.models.EventDao
import com.example.nascar_app.data.models.EventModel

@Database(entities = [EventModel::class], version = 1)
abstract class EventsDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao

    companion object {
        @Volatile
        private var INSTANCE: EventsDatabase? = null

        fun getDatabase(context: Context): EventsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = databaseBuilder(
                    context.applicationContext,
                    EventsDatabase::class.java,
                    "events_db"
                )
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}