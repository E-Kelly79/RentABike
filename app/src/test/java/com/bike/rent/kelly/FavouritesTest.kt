package com.bike.rent.kelly

import com.bike.rent.kelly.model.favs.Favourites
import org.junit.*

class FavouritesTest{
    val favourite: Favourites = Favourites("1","stationName", "City name", "address", 1.2092F, -6.2343F )

    @Test
    fun textCreate(){
        Assert.assertEquals("1", favourite.favId)
        Assert.assertEquals("stationName", favourite.stationName)
        Assert.assertEquals("City name", favourite.cityName)
        Assert.assertEquals("address", favourite.address)
        Assert.assertEquals(1.2092F, favourite.longitude)
        Assert.assertEquals(-6.2343F, favourite.latitude)
    }

    @Test
    fun testEquals() {
        val myFavs = Favourites("1", "this", "that", "helloo street", 54.10243F, -6.43454F)
        val yourFavs = Favourites("2", "york", "football", "Hell avenue", 54.10243F, -6.43454F)
        Assert.assertEquals(myFavs, myFavs)
        Assert.assertEquals(yourFavs, yourFavs)
        Assert.assertNotEquals(myFavs, yourFavs)
    }


}