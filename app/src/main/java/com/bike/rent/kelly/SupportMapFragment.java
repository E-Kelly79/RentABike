package com.bike.rent.kelly;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.bike.rent.kelly.data.local.PreferencesHelper;
import com.bike.rent.kelly.model.favs.Favourites;
import com.bike.rent.kelly.ui.base.BaseActivity;
import com.bike.rent.kelly.ui.base.BaseFragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
public class SupportMapFragment extends BaseFragment {

    private View mView;
    private MapboxMap mMapboxMap;
    private MapView mapView;
    private PreferencesHelper mPreferencesHelper;
    private float lat;
    private float lng;
    private String name;
    private String city;
    private String address;
    private FloatingActionButton fab;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mFavRef;
    private Favourites mFavourites;

    @Override
    public void onCreate(@org.jetbrains.annotations.Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(getBaseActivity(),
                getString(R.string.map_box_key));
        mDatabase = FirebaseDatabase.getInstance();
        mPreferencesHelper = new PreferencesHelper(getContext());
        mFavRef = mDatabase.getReference("Favourites");
        getPreferenceData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.google_maps_fragment, container, false);
        fab = mView.findViewById(R.id.floatingActionButton);
        checkDatabaseForMatch();
        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                saveFavouritesToDatabase();
                fab.setImageDrawable(getResources().getDrawable(android.R.drawable.btn_star_big_on));
                getBaseActivity().loadFavouriteFragment(getBaseArguments(), false);
            }
        });
        mapView = mView.findViewById(R.id.mapView);
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
        return mView;
    }

    public void saveFavouritesToDatabase(){
        //Push object to new node in firebase database with a unique ID
        mFavRef = mDatabase.getReference("Favourites").push();
        mFavourites = new Favourites(name, city, address, lng, lat);
        mFavRef.setValue(mFavourites);
    }

    public void checkDatabaseForMatch() {
        mDatabase.getReference().child("Favourites").orderByChild("stationName").equalTo(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    fab.setImageDrawable(getResources().getDrawable(android.R.drawable.btn_star_big_on));
                    fab.setClickable(false);
                }else{
                    fab.setImageDrawable(getResources().getDrawable(android.R.drawable.btn_star_big_off));
                    fab.setClickable(true);
                }
            }
            @Override
            public void onCancelled(@NonNull final DatabaseError databaseError) {

            }
        });

    }



    public void getPreferenceData(){
        lat = mPreferencesHelper.getPrefFloat(BaseActivity.LAT);
        lng = mPreferencesHelper.getPrefFloat(BaseActivity.LNG);
        name = mPreferencesHelper.getPrefString(BaseActivity.TITLE);
        city = mPreferencesHelper.getPrefString(BaseActivity.CITY);
        address = mPreferencesHelper.getPrefString(BaseActivity.ADDRESS);
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
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
