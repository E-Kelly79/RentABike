package com.bike.rent.kelly.model

import java.util.Date

data class Bike(val number: Int,
                val contractName: String,
                val name: String,
                val address: String,
                val banking: Boolean,
                val bonus: Boolean,
                val bikeStands: Int,
                val available_bikes: Int,
                val status: String,
                val lastUpdated: Date)