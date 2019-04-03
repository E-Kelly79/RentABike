package com.bike.rent.kelly.ui.bike

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.Request.Method
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.bike.rent.kelly.R
import com.bike.rent.kelly.SupportMapFragment
import com.bike.rent.kelly.data.local.PreferencesHelper
import com.bike.rent.kelly.model.bike.Bike
import com.bike.rent.kelly.ui.base.BaseActivity
import com.bike.rent.kelly.ui.base.BaseFragment
import kotlinx.android.synthetic.main.bike_list_fragment.mBikeRecyclerView
import kotlinx.android.synthetic.main.bike_list_fragment.mSearch
import kotlinx.android.synthetic.main.toolbar.mToolbarHome
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber

/**
 * @author Eoin Kelly
 * created 21/01/19
 *
 * Class to add bike objects to the recycler view
 */
class BikeList: BaseFragment() {
    var volleyRequest: RequestQueue? = null
    var preferences: PreferencesHelper? = null
    var mView: View? = null
    lateinit var bikeList: ArrayList<Bike>
    var mBikeRecyclerViewAdapter: BikeListRecyclerViewAdapter? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    lateinit var url: String

    /**
     * Inflate a layoutfragment to show
     * @param inflater inflate the give view to show layout on screen
     * @param container the view in which the layout will sit in
     * @param savedInstanceState used to save any data and pass to another activity
     * @return View
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.bike_list_fragment, container, false)
        baseActivity.showToolbar()
        baseActivity.setTitle("Bike List")
        baseActivity.unlockNavDrawer()
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        baseActivity.setStatusBarColor(
            R.color.color_primary, R.color.color_black, View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        var test = arguments
        var myString = test?.getString("contractName")
        Timber.i(myString, String)
        url = "https://api.jcdecaux.com/vls/v1/stations?contract=$myString&apiKey=567c5a18aec43057727314c80b218d65bced9c61"
        preferences = PreferencesHelper(context!!)
        baseActivity.mToolbarHome.setOnClickListener {
            baseActivity.showNavDrawer()
        }
        bikeList = ArrayList<Bike>()
        volleyRequest = Volley.newRequestQueue(context)
        getBikes(url)
        mSearch.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(search: Editable?) {
                filterList(search.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                null
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                null
            }
        })

    }

    /**
     * Used to make a http request to given url to pull back some json data
     * @param url a string with a given url
     */
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

                        // Get the Lat Lng form json and use it in the onclick event of recyclerview
                        // to add that mark on the map
                        mBikeRecyclerViewAdapter = BikeListRecyclerViewAdapter(bikeList, context!!){row ->
                            val latitude = bikeList[row].lat
                            val longitude = bikeList[row].lng
                            val title = bikeList[row].name
                            val city = bikeList[row].contractName
                            val address = bikeList[row].address
                            setPreferences(latitude!!, longitude!!, title!!, city!!, address!!)
                            var intent: Intent = Intent(baseActivity, SupportMapFragment::class.java)
                            startActivity(intent)
                            //baseActivity.loadGoogleMapsFragment(arguments!!, false)
                        }
                        layoutManager = LinearLayoutManager(context)
                        mBikeRecyclerView.layoutManager = layoutManager
                        mBikeRecyclerView.adapter = mBikeRecyclerViewAdapter
                    }
                    mBikeRecyclerViewAdapter!!.notifyDataSetChanged()

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {
                Log.d("ERROR====>", "somethis went wrong")
            })
        volleyRequest!!.add(bikeRequest)
    }

    /**
     * Set preferences for favourites
     */
    fun setPreferences(lat: Double, long: Double, title: String, city: String, address:String) {
        preferences!!.setPrefFloat(BaseActivity.LAT, lat.toFloat())
        preferences!!.setPrefFloat(BaseActivity.LNG, long.toFloat())
        preferences!!.setPrefString(BaseActivity.TITLE, title)
        preferences!!.setPrefString(BaseActivity.CITY, city)
        preferences!!.setPrefString(BaseActivity.ADDRESS, address)
    }

    fun filterList(text: String){
        var fliterBikeArray: ArrayList<Bike> = ArrayList()
        for (bike in bikeList){
            if (bike.address!!.toLowerCase().contains(text.toLowerCase())){
                fliterBikeArray.add(bike)
                mBikeRecyclerViewAdapter!!.filterList(fliterBikeArray)
            }
        }
    }
}