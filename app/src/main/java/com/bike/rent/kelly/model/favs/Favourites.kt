package com.bike.rent.kelly.model.favs

class Favourites{
    var userId: Int? = null
    var stationName: String? = null
    var cityName: String? = null
    var address: String? = null
    var longitude: Float? = null
    var latitude: Float? = null

    constructor(userId: Int? = null, stationName: String, cityName: String, longitude:Float, latitude: Float,
        address: String){
        this.userId = userId
        this.stationName = stationName
        this.cityName = cityName
        this.longitude = longitude
        this.latitude = latitude
        this.address = address
    }




}