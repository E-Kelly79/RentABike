package com.bike.rent.kelly.ui.bike

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bike.rent.kelly.R
import com.bike.rent.kelly.model.bike.Bike

/**
 * @author Eoin Kelly
 * Created 21/01/2019
 *
 * Class to set up the recycler view for the bike list
 * @constructor must take a ArrayList of type Bike and a context listener is optional
 *
 */
class BikeListRecyclerViewAdapter(private val bikeList:ArrayList<Bike>, private val context: Context, val listener: (Int) -> Unit):
        RecyclerView.Adapter<BikeListRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.bike_list_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return bikeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.bindView(bikeList[position], position, listener)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var bikeImage = itemView.findViewById<ImageView>(R.id.card_list_bike_image)
        var streetName = itemView.findViewById<TextView>(R.id.card_list_name)
        var streetAddress = itemView.findViewById<TextView>(R.id.card_list_address)
        var banking = itemView.findViewById<TextView>(R.id.card_list_banking)
        var bikeAvailable = itemView.findViewById<TextView>(R.id.card_list_bikes_available)
        var status = itemView.findViewById<TextView>(R.id.card_list_status)
        var parentLayout = itemView.findViewById<ConstraintLayout>(R.id.constraintLayout)
        var lat = itemView.findViewById<TextView>(R.id.text_lat)
        var lng = itemView.findViewById<TextView>(R.id.text_lng)

        /**
         *
         */
        fun bindView(bike: Bike, pos:Int, listener:(Int)-> Unit) = with(itemView){
            streetName.text = "Name: ${bike.name}"
            streetAddress.text = "Address: ${bike.address}"
            banking.text = "Card Use: ${bike.banking.toString()}"
            bikeAvailable.text = "Bikes Availabile ${bike.availableBikes.toString()}"
            status.text = "Status: ${bike.status}"
            bikeImage.setImageResource(R.drawable.ic_bike)
            lat.text = bike.lat.toString()
            lng.text = bike.lng.toString()
            parentLayout.setOnClickListener { listener(pos) }
        }

    }
}