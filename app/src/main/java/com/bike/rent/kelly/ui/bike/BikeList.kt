package com.bike.rent.kelly.ui.bike

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request.Method
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.bike.rent.kelly.R
import com.bike.rent.kelly.data.local.PreferencesHelper
import com.bike.rent.kelly.data.local.PrefsHelper
import com.bike.rent.kelly.model.Bike
import com.bike.rent.kelly.ui.base.BaseFragment
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import kotlinx.android.synthetic.main.bike_list_fragment.bike_recycler_view

class BikeList: BaseFragment() {
    var volleyRequest: RequestQueue? = null
    var preferences: PreferencesHelper? = null

    var mView: View? = null
    lateinit var bikeList: ArrayList<Bike>
    var bikeAdapter: BikeListAdapter? = null
    var layoutManager: RecyclerView.LayoutManager? = null

    lateinit var url: String


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.bike_list_fragment, container, false)
        baseActivity.showToolbar()
        baseActivity.setTitle("Bike List")
        var test = arguments
        var myString = test?.getString("contractName")
        url = "https://api.jcdecaux.com/vls/v1/stations?contract=$myString&apiKey=567c5a18aec43057727314c80b218d65bced9c61"

        preferences = PreferencesHelper(context!!)

        baseActivity.mivToolbarPrimary?.setOnClickListener {
            baseActivity.showNavDrawer()
        }
        bikeList = ArrayList<Bike>()
        volleyRequest = Volley.newRequestQueue(context)
        getBikes(url)
        return mView
    }

    fun getBikes(url: String) {
        val bikeRequest = JsonArrayRequest(Method.GET, url,
            Response.Listener { response: JSONArray ->
                try {
                    var position: JSONObject
                    for (i in 0 until response.length()) {
                        var bikeObj = response.getJSONObject(i)
                        var bike = Bike()
                        position = bikeObj.getJSONObject("position")
                        bike.lat = position.getDouble("lat")
                        bike.lng = position.getDouble("lng")
                        bike.name = bikeObj.getString("name")
                        bike.address = bikeObj.getString("address")
                        bike.banking = bikeObj.getBoolean("banking")
                        bike.availableBikes = bikeObj.getInt("available_bikes")
                        bike.status = bikeObj.getString("status")
                        bike.number = bikeObj.getInt("number")
                        bike.contractName = bikeObj.getString("contract_name")
                        bike.bonus = bikeObj.optBoolean("bonus")
                        bike.bikeStands = bikeObj.getInt("bike_stands")
                        bike.lastUpdated = bikeObj.getLong("last_update")

                        bikeList.add(bike)

                        bikeAdapter = BikeListAdapter(bikeList, context!!){row ->
                            val latitude = bikeList[row].lat
                            val longitude = bikeList[row].lng
                            savePrefs(latitude!!.toDouble(), longitude!!.toDouble())
                            baseActivity.loadGoogleMapsFragment(arguments!!, false)
                        }
                        layoutManager = LinearLayoutManager(context)
                        bike_recycler_view.layoutManager = layoutManager
                        bike_recycler_view.adapter = bikeAdapter
                    }
                    bikeAdapter!!.notifyDataSetChanged()

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {
                Log.d("ERROR====>", "sometimes is wrong")
            })
        volleyRequest!!.add(bikeRequest)
    }

    fun savePrefs(lat: Double, lng: Double){
        val prefs =  context!!.getSharedPreferences("LatLng", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putFloat("LAT", lat.toFloat())
        editor.putFloat("LNG", lng.toFloat())
        editor.commit()
    }
}