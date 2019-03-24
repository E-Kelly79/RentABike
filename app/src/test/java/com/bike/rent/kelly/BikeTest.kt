package com.bike.rent.kelly

import com.bike.rent.kelly.model.bike.Bike
import org.junit.*
import org.junit.Assert.*

class BikeTest{
    var bike: Bike = Bike(2, "Seo", "GBS", "101 wit street", true,
        true, 18, 6, "Open", 1000001L, 54.10243, -6.43454)

    @Test
    fun textCreate(){
        assertEquals(2, bike.number)
        assertEquals("Seo", bike.contractName)
        assertEquals("GBS", bike.name)
        assertEquals("101 wit street", bike.address)
        assertEquals(true, bike.banking)
        assertEquals(true, bike.bonus)
        assertEquals(18, bike.bikeStands)
        assertEquals(6, bike.availableBikes)
        assertEquals("Open", bike.status)
        assertEquals(1000001L, bike.lastUpdated)
        assertEquals(54.10243, bike.lat)
        assertEquals(-6.43454, bike.lng)
    }

    @Test
    fun testEquals() {
        val myBike = Bike(4, "Yes", "BES", "101 johns street", true,
            true, 18, 6, "Closed", 1000001L, 54.10243, -6.43454)
        val yourBike = Bike(4, "No", "SEB", "102 foxwood street", false,
            true, 28, 16, "Open", 1000001L, 54.10243, -6.43454)
        assertEquals(myBike, myBike)
        assertEquals(yourBike, yourBike)
        assertNotEquals(myBike, yourBike)
    }

}