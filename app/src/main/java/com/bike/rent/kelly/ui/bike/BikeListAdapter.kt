package com.bike.rent.kelly.ui.bike

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bike.rent.kelly.R
import com.bike.rent.kelly.model.Bike
import com.squareup.picasso.Picasso

class BikeListAdapter(private val bikeList:ArrayList<Bike>,
                      private val context: Context): RecyclerView.Adapter<BikeListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.bike_list_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return bikeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(holder != null) {
            holder.bindView(bikeList[position])
        }

    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var bikeImage = itemView.findViewById<ImageView>(R.id.card_list_bike_image)
        var streetName = itemView.findViewById<TextView>(R.id.card_list_name)
        var streetAddress = itemView.findViewById<TextView>(R.id.card_list_address)
        var banking = itemView.findViewById<TextView>(R.id.card_list_banking)
        var bikeAvailable = itemView.findViewById<TextView>(R.id.card_list_bikes_available)
        var status = itemView.findViewById<TextView>(R.id.card_list_status)

        fun bindView(bike: Bike){
            streetName.text = "Name: ${bike.name}"
            streetAddress.text = "Address: ${bike.address}"
            banking.text = "Card Use: ${bike.banking.toString()}"
            bikeAvailable.text = "Bikes Availabile ${bike.availableBikes.toString()}"
            status.text = "Status: ${bike.status}"
            bikeImage.setImageResource(R.drawable.ic_bike)
        }

    }
}