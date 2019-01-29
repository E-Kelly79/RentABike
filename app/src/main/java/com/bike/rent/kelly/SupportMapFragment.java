package com.bike.rent.kelly;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.bike.rent.kelly.ui.base.BaseActivity;
import com.bike.rent.kelly.ui.main.MainActivity;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import timber.log.Timber;

public class SupportMapFragment extends BaseActivity {

    private MapboxMap mMapboxMap;
    private MapView mapView;
    private float lat;
    private float lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, "pk.eyJ1IjoiYW5kcm9pZGtlbGx5IiwiYSI6ImNqcmZmN29udjIydDQ0M24wZHR3eTg3MDAifQ.L-taXW6VInPZrFwuIhYnOQ");
        setContentView(R.layout.google_maps_fragment);
        Intent intent = getIntent();
        lat = intent.getFloatExtra("LAT", 0.0f);
        lng = intent.getFloatExtra("LNG", 0.0f);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull final MapboxMap mapboxMap) {


                mapboxMap.setStyle(Style.LIGHT, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
// Add the marker image to map
                        style.addImage("marker-icon-id",
                                BitmapFactory.decodeResource(
                                        getResources(), R.drawable.mapbox_marker_icon_default));

                        GeoJsonSource geoJsonSource = new GeoJsonSource("source-id", Feature.fromGeometry(
                                Point.fromLngLat(lng, lat)));
                        style.addSource(geoJsonSource);

                        SymbolLayer symbolLayer = new SymbolLayer("layer-id", "source-id");
                        symbolLayer.withProperties(
                                PropertyFactory.iconImage("marker-icon-id")
                        );
                        style.addLayer(symbolLayer);

                    }
                });
                CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(lat, lng))
                        .zoom(11)
                        .tilt(20)
                        .build();
                mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 2000);

                mapboxMap.setStyle(Style.TRAFFIC_DAY, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {

                        // Custom map style has been loaded and map is now ready


                    }
                });
            }
        });

    }

    @Override
    public void onBackPressed() {
        Timber.i("onBracked Pressed");
        Bundle bundle = new Bundle();
        bundle.putString("City", "Dublin");
        getBaseActivity().loadCitySelectFragment(bundle, false);
    }

    // Add the mapView's own lifecycle methods to the activity's lifecycle methods
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
