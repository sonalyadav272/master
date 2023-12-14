package com.example.navigation_app.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.navigation_app.database.AppDatabase
import com.example.navigation_app.entities.TripDetails
import com.example.navigation_app.repo.TripsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TripViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TripsRepo
    val allTodo: LiveData<List<TripDetails>>

    init {
        val dao = AppDatabase.getDatabase(application).tripDao()
        repository = TripsRepo(dao)
        allTodo = repository.allTodos
    }

    fun insertTrip(todo: TripDetails) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(todo)
    }

    fun deleteAllTrips() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }
}