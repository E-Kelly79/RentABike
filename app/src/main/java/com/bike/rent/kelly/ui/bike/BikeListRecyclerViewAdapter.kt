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
import com.bike.rent.kelly.ui.base.BaseActivity
import com.bike.rent.kelly.utils.BikeArray
import com.bumptech.glide.Glide

/**
 * @author Eoin Kelly
 * Created 21/01/2019
 *
 * Class to set up the recycler view for the bike list
 * @constructor must take a ArrayList of type Bike and a context listener is optional
 *
 */
class BikeListRecyclerViewAdapter(private var bikeList:ArrayList<Bike>, private val context: Context,
        val listener: (Int) -> Unit): RecyclerView.Adapter<BikeListRecyclerViewAdapter.ViewHolder>() {
    var base = BaseActivity

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
        if (position > BikeArray.bikeimages.size){
            BikeArray.bikeimages[position] = 0
        }

        //Loop trough the drawables for name that contains  and add it to array list
        var imageListId = ArrayList<Int>()
        var drawables = R.drawable::class.java!!.fields
        for (drawable in drawables) {
            //if the drawable name contains "bicycle" the get resource id and add to list
            if (drawable.name.contains("bicycle_")){
                imageListId.add(context.resources.getIdentifier(drawable.name, "drawable", context.packageName))
            }
        }

        //check to see if the position is less the the size of the array
        //if it is then add images to the placeholder other wise add the 4th image to placeholder
        if (position < imageListId.size) {
            Glide.with(context)
                .load(imageListId[position])
                .into(holder.image)
        }else{
            Glide.with(context)
                .load(imageListId[3])
                .into(holder.image)
        }

    }

    fun filterList(filteredBikeList: ArrayList<Bike>){
        this.bikeList = filteredBikeList
        notifyDataSetChanged()
    }


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var streetName = itemView.findViewById<TextView>(R.id.card_list_name)
        var streetAddress = itemView.findViewById<TextView>(R.id.card_list_address)
        var banking = itemView.findViewById<TextView>(R.id.card_list_banking)
        var bikeAvailable = itemView.findViewById<TextView>(R.id.card_list_bikes_available)
        var status = itemView.findViewById<TextView>(R.id.card_list_status)
        var parentLayout = itemView.findViewById<ConstraintLayout>(R.id.constraintLayout)
        var lat = itemView.findViewById<TextView>(R.id.text_lat)
        var lng = itemView.findViewById<TextView>(R.id.text_lng)
        var image= itemView.findViewById<ImageView>(R.id.unsplash)

        /**
         * function to bind bike object information to the given variables
         * @param bike Bike object
         * @param pos for the postition of the recycler view
         * @param listener for the click event of the recycler view
         */
        fun bindView(bike: Bike, pos:Int, listener:(Int)-> Unit) = with(itemView){
            streetName.text = "Name: ${bike.name}"
            streetAddress.text = "Address: ${bike.address}"
            banking.text = "Card Use: ${bike.banking.toString()}"
            bikeAvailable.text = "Bikes Availabile ${bike.availableBikes.toString()}"
            status.text = "Status: ${bike.status}"
            lat.text = bike.lat.toString()
            lng.text = bike.lng.toString()
            parentLayout.setOnClickListener { listener(pos) }
        }

    }
}