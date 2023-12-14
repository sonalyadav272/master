package com.example.navigation_app.ui.trip_info

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.navigation_app.AppUtils
import com.example.navigation_app.entities.TripDetails
import com.example.navigation_app.ui.TripViewModel
import com.example.navigation_app.ui.trip_list.TripListScreen
import com.example.roomdb.R
import com.example.roomdb.databinding.ActivityTripInfoBinding

class TripInfoScreen : AppCompatActivity() {
    private lateinit var binding: ActivityTripInfoBinding
    private lateinit var viewModel: TripViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[TripViewModel::class.java]

        manageNextBtnVisibility()

        binding.btnSave.setOnClickListener {
            saveTrip()
        }
        binding.btnClear.setOnClickListener {
            clearData()
        }
        binding.btnRemoveAll.setOnClickListener {
            removeAllTrip()
        }
        binding.btnNext.setOnClickListener {
            goToNextScreen()
        }
    }

    private fun manageNextBtnVisibility() {
        viewModel.allTodo.observe(this) { list ->
            if (list.size > AppUtils.TRIP_LIMIT) {
                binding.btnNext.visibility = View.VISIBLE
            } else {
                binding.btnNext.visibility = View.INVISIBLE
            }
        }
    }

    private fun goToNextScreen() {
        startActivity(Intent(this, TripListScreen::class.java))
    }

    private fun saveTrip() {
        val customerName = binding.etCustomerName.text.toString()
        val pickUpAddress = binding.etPickupAddress.text.toString()
        val dropOffAddress = binding.etDropOffAddress.text.toString()
        val price = binding.etPrice.text.toString()
        val pickUpTime = binding.etPickUpTime.text.toString()

        if (customerName.isNotEmpty() &&
            pickUpAddress.isNotEmpty() &&
            dropOffAddress.isNotEmpty() &&
            price.isNotEmpty() &&
            pickUpTime.isNotEmpty()
        ) {
            val tripDetails =
                TripDetails(
                    null,
                    customerName,
                    pickUpAddress,
                    dropOffAddress,
                    price,
                    pickUpTime.toInt(),
                    false
                )
            viewModel.insertTrip(tripDetails)
            Toast.makeText(
                this@TripInfoScreen,
                R.string.toast_trip_added_successfully,
                Toast.LENGTH_LONG
            ).show()
            clearData()
        } else {
            Toast.makeText(
                this@TripInfoScreen,
                R.string.toast_enter_all_the_data,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun clearData() {
        binding.etCustomerName.text.clear()
        binding.etPickupAddress.text.clear()
        binding.etDropOffAddress.text.clear()
        binding.etPrice.text.clear()
        binding.etPickUpTime.text.clear()
    }

    private fun removeAllTrip() {
        viewModel.deleteAllTrips()
        Toast.makeText(this@TripInfoScreen, R.string.toast_removed_successfully, Toast.LENGTH_LONG)
            .show()
    }
}