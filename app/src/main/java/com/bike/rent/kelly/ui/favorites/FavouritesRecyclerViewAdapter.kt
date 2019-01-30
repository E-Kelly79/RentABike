package com.bike.rent.kelly.ui.favorites

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bike.rent.kelly.R
import com.bike.rent.kelly.model.favs.Favourites


class FavouritesRecyclerViewAdapter(private val favList:ArrayList<Favourites>, private val context: Context,
        val listener: (Int) -> Unit): RecyclerView.Adapter<FavouritesRecyclerViewAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesRecyclerViewAdapter.ViewHolder {
        val view: View = LayoutInflater.from(context)
            .inflate(R.layout.favs_list, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return favList.size
    }

    override fun onBindViewHolder(holder: FavouritesRecyclerViewAdapter.ViewHolder, position: Int) {
        holder?.bindView(favList[position], position, listener)
    }


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var favImage = itemView.findViewById<ImageView>(R.id.img_fav)
        var stationName = itemView.findViewById<TextView>(R.id.text_station_name)
        var cityName = itemView.findViewById<TextView>(R.id.text_city_name)
        var address = itemView.findViewById<TextView>(R.id.text_fav_address)
        var lat = itemView.findViewById<TextView>(R.id.fav_lat)
        var lng = itemView.findViewById<TextView>(R.id.fav_lng)
        var cardLayout = itemView.findViewById<CardView>(R.id.cardView)

        fun bindView(fav: Favourites, pos:Int, listener:(Int)-> Unit) = with(itemView){
            stationName.text =fav.stationName
            address.text = fav.address
            cityName.text = fav.cityName
            favImage.setImageResource(R.drawable.ic_bike)
            lat.text = fav.latitude.toString()
            lng.text = fav.longitude.toString()
            cardLayout.setOnClickListener { listener(pos) }
        }


    }

}