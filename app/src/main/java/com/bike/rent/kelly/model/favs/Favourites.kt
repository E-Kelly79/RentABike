package com.bike.rent.kelly.model.favs

data class Favourites(var favId: String? = null,
                      var stationName: String? = null,
                      var cityName: String? = null,
                      var address: String? = null,
                      var longitude: Float? = null,
                      var latitude: Float? = null){

    override fun toString(): String {
        return super.toString()
    }
}
