package com.bike.rent.kelly.data.local

import android.content.Context

class PreferencesHelper(context: Context) {
    val PREFERENCE_NAME = "GBSBIKE"
    val PREFERENCE_LAT = "LAT"
    val PREFERENCE_LNG = "LNG"

    val preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun getLat(): Double{
        var default = preference.getFloat(PREFERENCE_LAT, 0.0f)
        var latVaule = default.toDouble()
        return latVaule
    }

    fun setLat(lat: Double){
        val editor = preference.edit()
        editor.putFloat(PREFERENCE_LAT, lat.toFloat())
        editor.apply()
    }

    fun getLng(): Double{
        var default = preference.getFloat(PREFERENCE_LNG, 0.0f)
        var lngVaule = default.toDouble()
        return lngVaule
    }

    fun setLng(lng: Double){
        val editor = preference.edit()
        editor.putFloat(PREFERENCE_LAT, lng.toFloat())
        editor.apply()
    }


}