package com.example.navigation_app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.navigation_app.AppUtils
import com.example.navigation_app.entities.TripDetails

@Database(entities = [TripDetails::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tripDao(): TripDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    AppUtils.DATABASE_NAME
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}