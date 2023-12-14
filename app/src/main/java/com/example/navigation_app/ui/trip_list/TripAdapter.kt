package com.example.navigation_app.ui.trip_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.navigation_app.entities.TripDetails
import com.example.roomdb.R

class TripAdapter(private val context: Context) :
    RecyclerView.Adapter<TripAdapter.TripViewHolder>() {

    private val tripList = ArrayList<TripDetails>()
    var onCheckBoxClick: ((TripDetails) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        return TripViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_trip, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val item = tripList[position]
        holder.customerName.text = item.customerName
        holder.cbMultipleOptions.isSelected = item.selected
        holder.pickUpAddress.text = item.pickUpAddress
        holder.dropOffAddress.text = item.dropOffAddress
        holder.price.text = item.price
        holder.pickUpTime.text = item.pickUpTime.toString()
        holder.cbMultipleOptions.setOnClickListener {
            holder.cbMultipleOptions.isSelected = !holder.cbMultipleOptions.isSelected
            item.selected = holder.cbMultipleOptions.isSelected
            onCheckBoxClick?.invoke(item)
        }

    }

    override fun getItemCount(): Int {
        return tripList.size
    }

    fun updateList(newList: List<TripDetails>) {
        tripList.clear()
        tripList.addAll(newList)
        notifyDataSetChanged()
    }

    inner class TripViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val customerName: TextView = itemView.findViewById(R.id.tv_customer_name)
        val pickUpAddress: TextView = itemView.findViewById(R.id.tv_pickup_address)
        val dropOffAddress: TextView = itemView.findViewById(R.id.tv_drop_off_address)
        val price: TextView = itemView.findViewById(R.id.tv_price)
        val pickUpTime: TextView = itemView.findViewById(R.id.tv_pickup_time)
        val cbMultipleOptions: CheckBox = itemView.findViewById(R.id.cbMultipleOptions)
    }
}