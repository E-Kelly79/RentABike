package com.bike.rent.kelly.model

class Bike(){
    var number: Int? = null
    var contractName: String? = null
    var name: String? = null
    var address: String? = null
    var banking: Boolean? = null
    var bonus: Boolean? = null
    var bikeStands: Int? = null
    var availableBikes: Int? = null
    var status: String? = null
    var lastUpdated: Long? = null
    var lat: Double? = null
    var lng: Double? = null

    constructor(number: Int, contractName: String, name: String, address: String,
                banking: Boolean, bonus: Boolean, bikeStands: Int, availableBikes: Int,
                status: String, lastUpdated: Long, lat: Double, lng: Double): this(){
        this.number = number
        this.contractName = contractName
        this.name = name
        this.address = address
        this.banking = banking
        this.bonus = bonus
        this.bikeStands = bikeStands
        this.availableBikes = availableBikes
        this.status = status
        this.lastUpdated = lastUpdated
        this.lat = lat
        this.lng = lng
    }


}