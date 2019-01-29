package com.bike.rent.kelly.ui.maps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bike.rent.kelly.ui.base.BaseFragment
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import kotlinx.android.synthetic.main.google_maps_fragment.mapView
import com.mapbox.mapboxsdk.maps.MapboxMap

import com.mapbox.mapboxsdk.maps.Style

class GoogleMapsFragment : BaseFragment() {
    var mView: View? = null
    private var mapView: MapView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(context!!, "pk.eyJ1IjoiYW5kcm9pZGtlbGx5IiwiYSI6ImNqcmZmN29udjIydDQ0M24wZHR3eTg3MDAifQ.L-taXW6VInPZrFwuIhYnOQ")

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(com.bike.rent.kelly.R.layout.google_maps_fragment, container, false)
        mapView = mView!!.findViewById(com.bike.rent.kelly.R.id.mapView) as MapView
        //mapView!!.getMapAsync { }
        mapView!!.onCreate(savedInstanceState)
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {



    }

    // Add the mapView lifecycle to the activity's lifecycle methods
    override fun onResume() {
        super.onResume()
        mapView!!.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView!!.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView!!.onStop()
    }

    override fun onPause() {
        super.onPause()
        mapView!!.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView!!.onLowMemory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView!!.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView!!.onSaveInstanceState(outState)
    }
}




