package com.example.navigation_app.repo

import androidx.lifecycle.LiveData
import com.example.navigation_app.database.TripDao
import com.example.navigation_app.entities.TripDetails

class TripsRepo(private val todoDao: TripDao) {

    val allTodos: LiveData<List<TripDetails>> = todoDao.getAllTrip()

    suspend fun insert(todo: TripDetails) {
        todoDao.insert(todo)
    }

    suspend fun deleteAll() {
        todoDao.deleteAll()
    }
}