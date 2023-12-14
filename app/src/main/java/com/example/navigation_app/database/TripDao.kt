package com.example.navigation_app.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.navigation_app.entities.TripDetails

@Dao
interface TripDao {
    @Query("SELECT * FROM trip_table order by pick_up_time ASC")
    fun getAllTrip(): LiveData<List<TripDetails>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tripDetails: TripDetails)

    @Delete
    suspend fun delete(tripDetails: TripDetails)

    @Query("DELETE FROM trip_table")
    suspend fun deleteAll()
}