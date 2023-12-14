package com.example.navigation_app.ui.trip_list

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.navigation_app.AppUtils
import com.example.navigation_app.entities.TripDetails
import com.example.navigation_app.ui.TripViewModel
import com.example.roomdb.R
import com.example.roomdb.databinding.ActivityTripListBinding

class TripListScreen : AppCompatActivity() {
    private lateinit var binding: ActivityTripListBinding
    lateinit var viewModel: TripViewModel
    private lateinit var adapter: TripAdapter
    private val list = arrayListOf<TripDetails>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        initRecyclerview()
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[TripViewModel::class.java]
        viewModel.allTodo.observe(this) { list ->
            list?.let {
                adapter.updateList(list)
            }
        }
        binding.btnNavigate.setOnClickListener {
            startNavigation()
        }
    }

    private fun initRecyclerview() {
        adapter = TripAdapter(this)
        binding.rvTripList.adapter = adapter
        adapter.onCheckBoxClick = { item ->
            if (item.selected) {
                list.add(item)
            }
        }
    }

    private fun startNavigation() {
        val filteredList = list.filter { item -> item.selected }

        if (filteredList.isNotEmpty()) {
            val str = StringBuilder(AppUtils.MAP_PREFIX_URL)
            for (i in filteredList) {
                str.append(i.pickUpAddress + "/")
                if (i == filteredList.last()) {
                    str.append(i.dropOffAddress)
                } else str.append(i.dropOffAddress + "/")
            }
            navigateToMap(str.toString())
        } else {
            Toast.makeText(this, R.string.toast_select_the_trip, Toast.LENGTH_LONG).show()
        }
    }

    private fun navigateToMap(parseUrl: String) {
        try {
            val uri = Uri.parse(parseUrl)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage(AppUtils.MAP_PACKAGE)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            val uri = Uri.parse(AppUtils.MAP_GOTO_PLAY_STORE_URL)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}