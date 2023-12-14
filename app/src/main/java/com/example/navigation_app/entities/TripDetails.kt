package com.example.navigation_app.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "trip_table")
data class TripDetails(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "customer_name") val customerName: String?,
    @ColumnInfo(name = "pickup_address") val pickUpAddress: String?,
    @ColumnInfo(name = "drop_off_address") val dropOffAddress: String?,
    @ColumnInfo(name = "price") val price: String?,
    @ColumnInfo(name = "pick_up_time") val pickUpTime: Int?,
    @ColumnInfo(name = "selected") var selected: Boolean = false
)
