package com.bike.rent.kelly.ui.maps

import android.annotation.SuppressLint
import com.bike.rent.kelly.ui.base.BaseFragment
import com.mapbox.mapboxsdk.maps.MapView
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bike.rent.kelly.R

import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.mapboxsdk.location.LocationComponent

import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.location.LocationComponentOptions
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode

class GoogleMapsFragment : BaseFragment(), OnMapReadyCallback, PermissionsListener {
    private lateinit var  permissionsManager: PermissionsManager
    private lateinit var  mMap: MapboxMap
    private lateinit var mapView: MapView
    lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Mapbox access token is configured here. This needs to be called either in your application
        // object or in the same activity which contains the mapview.
        Mapbox.getInstance(context!!, "pk.eyJ1IjoiYW5kcm9pZGtlbGx5IiwiYSI6ImNqcmZmN29udjIydDQ0M24wZHR3eTg3MDAifQ.L-taXW6VInPZrFwuIhYnOQ")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.google_maps_fragment, container, false)
        mapView = mView.findViewById(R.id.mapView) as MapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        return mView
    }

    override fun onMapReady(mapboxMap: MapboxMap ) {
        mMap = mapboxMap
        enableLocationComponent()
    }

    @SuppressLint("MissingPermission")
    fun enableLocationComponent() {
        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(context)) {
            var options: LocationComponentOptions = LocationComponentOptions.builder(baseActivity)
                .trackingGesturesManagement(true)
                .accuracyColor(ContextCompat.getColor(context!!, R.color.mapbox_blue))
                .build()

            // Get an instance of the component
             var locationComponent: LocationComponent = mMap.locationComponent

            // Activate with options
            //locationComponent.activateLocationComponent(baseActivity, options)

// Enable to make component visible
            locationComponent.isLocationComponentEnabled = true

// Set the component's camera mode
           // locationComponent.cameraMode = CameraMode.TRACKING
            //locationComponent.renderMode = RenderMode.COMPASS
        } else {
            permissionsManager = PermissionsManager(this)
            permissionsManager.requestLocationPermissions(baseActivity)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    override fun onExplanationNeeded(permissionsToExplain: List<String>) {
        Toast.makeText(context, "Grant Permissions", Toast.LENGTH_LONG).show()
    }


   override fun onPermissionResult(granted: Boolean) {
        if (granted) {
            enableLocationComponent();
        } else {
            Toast.makeText(context, "YEs", Toast.LENGTH_LONG).show();
        }
    }


    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }


    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

}




//    private lateinit var mapView: MapView
//    private lateinit var mMap: MapboxMap
//    private lateinit var mPermissionManager: PermissionsManager
//    private lateinit var mLocationComponent: LocationComponent
//    lateinit var mView: View
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        Mapbox.getInstance(context!!, "pk.eyJ1IjoiYW5kcm9pZGtlbGx5IiwiYSI6ImNqcmZmN29udjIydDQ0M24wZHR3eTg3MDAifQ.L-taXW6VInPZrFwuIhYnOQ")
//        mapView.
//    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        var office: LatLng = LatLng(38.899895, -77.03401)
//        var options: MapboxMapOptions = MapboxMapOptions()
//        options.camera(CameraPosition.Builder()
//        .target(office)
//        .zoom(9.0)
//        .build())
//        mView = inflater.inflate(com.bike.rent.kelly.R.layout.google_maps_fragment, container, false)
//        mapView = mView.findViewById(com.bike.rent.kelly.R.id.mapView)
//        mapView.onCreate(savedInstanceState)
//        mapView.getMapAsync(this)
//        return mView
//    }
//
//    override fun onMapReady(mapboxMap: MapboxMap) {
//        mMap = mapboxMap
//        enableLocationComponent()
//    }
//
//    @SuppressLint("MissingPermission")
//    private fun enableLocationComponent(){
//        //Check if permissions are enabled and if not request them
//        if (PermissionsManager.areLocationPermissionsGranted(context)){
////            var options: LocationComponentOptions = LocationComponentOptions.builder(context!!)
////                 .trackingGesturesManagement(true)
////                 .accuracyColor(ContextCompat.getColor(context!!, R.color.mapbox_blue))
////                 .build()
//
//            //Get an instance of the compent
//            mLocationComponent = mMap.locationComponent
//
//            // Enable to make component visible
//            mLocationComponent.isLocationComponentEnabled = true
//
//            // Set the component's camera mode
//            //mLocationComponent.cameraMode = CameraMode.TRACKING
//            //mLocationComponent.renderMode = RenderMode.COMPASS
//        }else{
//            mPermissionManager = PermissionsManager(this)
//            mPermissionManager.requestLocationPermissions(baseActivity)
//        }
//    }
//
//    private fun enableLocation(){
//        if(PermissionsManager.areLocationPermissionsGranted(context)){
//            initLocationEngine()
//            initLocationLayer()
//        }else{
//            mPermissionManager = PermissionsManager(this)
//            mPermissionManager.requestLocationPermissions(baseActivity)
//        }
//    }
//
//    private fun initLocationEngine(){
//    }
//
//    private fun initLocationLayer(){
//
//    }
//
//    override fun onExplanationNeeded(permissionsToExplain: MutableList<String>?) {
//        Toast.makeText(context, "Please turn on you location ", Toast.LENGTH_LONG).show()
//    }
//
//    override fun onPermissionResult(granted: Boolean){
//        if (granted){
//            enableLocationComponent()
//        }else{
//            Toast.makeText(context, "No Location found", Toast.LENGTH_LONG).show()
//        }
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
//        grantResults: IntArray) {
//        mPermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
//    }
//
//
//    override fun onStart() {
//        super.onStart()
//        mapView.onStart()
//    }
//
//    override fun onResume() {
//        super.onResume()
//        mapView.onResume()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        mapView.onPause()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        mapView.onStop()
//    }
//
//    override fun onLowMemory() {
//        super.onLowMemory()
//        mapView.onLowMemory()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        mapView.onDestroy()
//    }
//
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        mapView.onSaveInstanceState(outState)
//    }
//}
