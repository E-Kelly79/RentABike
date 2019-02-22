package com.bike.rent.kelly;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;

import android.Manifest;
import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;
import com.bike.rent.kelly.data.local.PreferencesHelper;
import com.bike.rent.kelly.model.favs.Favourites;
import com.bike.rent.kelly.ui.base.BaseActivity;
import com.bike.rent.kelly.ui.base.BaseFragment;

import com.bike.rent.kelly.utils.SnackBars;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.MapboxDirections.Builder;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.api.directions.v5.models.DirectionsWaypoint;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.maps.Style.OnStyleLoaded;
import com.mapbox.mapboxsdk.style.layers.PropertyFactory;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupportMapFragment extends AppCompatActivity implements OnMapReadyCallback,MapboxMap.OnMapClickListener, PermissionsListener {

    private static final String TAG = SupportMapFragment.class.getSimpleName();
    private MapView mapView;
    private MapboxMap mMapboxMap;
    private PermissionsManager mPermissionsManager;
    private LocationComponent locationComponent;
    private DirectionsRoute currentRoute;
    private NavigationMapRoute mNavigationMapRoute;
    private FloatingActionButton fab;
    private FloatingActionButton startNavigationBtn;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mFavRef;
    private Favourites mFavourites;
    private PreferencesHelper mPreferencesHelper;
    private float lat;
    private float lng;
    private String name;
    private String city;
    private String address;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this,
                getString(R.string.map_box_key));
        setContentView(R.layout.google_maps_fragment);
        mapView = findViewById(R.id.mMapView);
        mapView.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance();
        mPreferencesHelper = new PreferencesHelper(this);
        mFavRef = mDatabase.getReference("Favourites");

        getPreferenceData();
        fab = findViewById(R.id.mFloatingActionButton);
        startNavigationBtn = findViewById(R.id.start_nav_btn);
        checkDatabaseForMatch();
        fab.setOnClickListener(view -> {
            saveFavouritesToDatabase();
            fab.setImageDrawable(getResources().getDrawable(android.R.drawable.btn_star_big_on));
            SnackBars.Companion.centerSnackbar(view, name + " was added to favourites");
        });

        mapView.getMapAsync(this);
    }

    //TODO ... DELETE this at when you know it is not needed
//    @SuppressLint("MissingPermission")
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
//            @Nullable final Bundle savedInstanceState) {
//        mView = inflater.inflate(R.layout.google_maps_fragment, container, false);
//        mapView = mView.findViewById(R.id.mMapView);
//        fab = mView.findViewById(R.id.mFloatingActionButton);
//        checkDatabaseForMatch();
//        fab.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(final View view) {
//                saveFavouritesToDatabase();
//                fab.setImageDrawable(getResources().getDrawable(android.R.drawable.btn_star_big_on));
//                SnackBars.Companion.centerSnackbar(mView, name + " was added to favourites");
//            }
//        });
//        getPreferenceData();
////        originPos = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(), locationComponent.getLastKnownLocation().getLatitude());
////        destPos = Point.fromLngLat(lng, lng);
//        mapView.onCreate(savedInstanceState);
//
//        mapView.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(@NonNull final MapboxMap mapboxMap) {
//                SupportMapFragment.this.mMapboxMap = mapboxMap;
//                mapboxMap.setStyle(Style.OUTDOORS, new OnStyleLoaded() {
//
//                    @Override
//                    public void onStyleLoaded(@NonNull final Style style) {
//                        enableLocationComponent(style);
//                        //getRoute(originPos, destPos);
//                        // Add the marker image to map
//                        style.addImage("marker-icon-id",
//                                BitmapFactory.decodeResource(
//                                        getResources(), R.drawable.mapbox_marker_icon_default));
//
//                        GeoJsonSource geoJsonSource = new GeoJsonSource("source-id", Feature.fromGeometry(
//                                Point.fromLngLat(lng, lat)));
//                        style.addSource(geoJsonSource);
//
//                        SymbolLayer symbolLayer = new SymbolLayer("layer-id", "source-id");
//                        symbolLayer.withProperties(
//                                PropertyFactory.iconImage("marker-icon-id")
//                        );
//                        style.addLayer(symbolLayer);
//
//                    }
//                });
//                CameraPosition position = new CameraPosition.Builder()
//                        .target(new LatLng(lat, lng))
//                        .zoom(11)
//                        .tilt(20)
//                        .build();
//                mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 2000);
//
//                mapboxMap.setStyle(Style.TRAFFIC_DAY, new Style.OnStyleLoaded() {
//                    @Override
//                    public void onStyleLoaded(@NonNull Style style) {
//                        // Custom map style has been loaded and map is now ready
//
//                    }
//                });
//            }
//        });
//
//        return mView;
//    }

//    private void getRoute(Point origin, Point dest){
//        NavigationRoute.builder()
//                .accessToken(getString(R.string.map_box_key))
//                .origin(origin)
//                .destination(dest)
//                .build()
//                .getRoute(new retrofit2.Callback<DirectionsResponse>() {
//                    @Override
//                    public void onResponse(final Call<DirectionsResponse> call,
//                            final Response<DirectionsResponse> response) {
//                        if (response.body() == null){
//                            Log.e(TAG, "No Routes found checktoken");
//                            return;
//                        }else if (response.body().routes().size() == 0){
//                            Log.e(TAG, "No Routes found");
//                            return;
//                        }
//                        DirectionsRoute current = response.body().routes().get(0);
//                        if (mNavigationMapRoute != null){
//                            mNavigationMapRoute.removeRoute();
//                        }else {
//                            mNavigationMapRoute = new NavigationMapRoute(null, mapView, mMapboxMap);
//
//                        mNavigationMapRoute.addRoute(current);
//                    }
//
//                    @Override
//                    public void onFailure(final Call<DirectionsResponse> call, final Throwable t) {
//                        Log.e(TAG, "Error" + t.getMessage());
//                    }
//                });
//    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mPermissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onExplanationNeeded(final List<String> permissionsToExplain) {
        Toast.makeText(this, "onExplanation", Toast.LENGTH_LONG).show();
    }

    @SuppressWarnings( {"MissingPermission"})
    @Override
    public boolean onMapClick(@NonNull LatLng point) {

        Point destinationPoint = Point.fromLngLat(lng, lat);
        Point originPoint = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(),
                locationComponent.getLastKnownLocation().getLatitude());

        GeoJsonSource source = mMapboxMap.getStyle().getSourceAs("destination-source-id");
        if (source != null) {
            source.setGeoJson(Feature.fromGeometry(destinationPoint));
        }

        getRoute(originPoint, destinationPoint);
        startNavigationBtn.setEnabled(true);
        startNavigationBtn.setBackgroundResource(android.R.color.holo_green_light);
        return true;
    }

    private void getRoute(Point origin, Point destination) {
        NavigationRoute.builder(this)
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .destination(destination)
                .build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
// You can get the generic HTTP info about the response
                        Log.d(TAG, "Response code: " + response.code());
                        if (response.body() == null) {
                            Log.e(TAG, "No routes found, make sure you set the right user and access token.");
                            return;
                        } else if (response.body().routes().size() < 1) {
                            Log.e(TAG, "No routes found");
                            return;
                        }

                        currentRoute = response.body().routes().get(0);

// Draw the route on the map
                        if (mNavigationMapRoute != null) {
                            mNavigationMapRoute.removeRoute();
                        } else {
                            mNavigationMapRoute = new NavigationMapRoute(null, mapView, mMapboxMap, R.style.NavigationMapRoute);
                        }
                        mNavigationMapRoute.addRoute(currentRoute);
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                        Log.e(TAG, "Error: " + throwable.getMessage());
                    }
                });
    }

    @SuppressWarnings( {"MissingPermission"})
    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        this.mMapboxMap = mapboxMap;
        mapboxMap.setStyle(getString(R.string.navigation_guidance_day), new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                enableLocationComponent(style);

                addDestinationIconSymbolLayer(style);
                Point destinationPoint = Point.fromLngLat(lng, lat);
                Point originPoint = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(),
                        locationComponent.getLastKnownLocation().getLatitude());

                GeoJsonSource source = mMapboxMap.getStyle().getSourceAs("destination-source-id");
                if (source != null) {
                    source.setGeoJson(Feature.fromGeometry(destinationPoint));
                }

                getRoute(originPoint, destinationPoint);

                mapboxMap.addOnMapClickListener(SupportMapFragment.this);
                startNavigationBtn.setOnClickListener(v -> {

                    NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                            .directionsRoute(currentRoute)
                            .shouldSimulateRoute(true)
                            .build();
                // Call this method with Context from within an Activity
                    NavigationLauncher.startNavigation(SupportMapFragment.this, options);
                });
            }
        });
    }

    private void addDestinationIconSymbolLayer(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addImage("destination-icon-id",
                BitmapFactory.decodeResource(this.getResources(), R.drawable.mapbox_marker_icon_default));
        GeoJsonSource geoJsonSource = new GeoJsonSource("destination-source-id");
        loadedMapStyle.addSource(geoJsonSource);
        SymbolLayer destinationSymbolLayer = new SymbolLayer("destination-symbol-layer-id", "destination-source-id");
        destinationSymbolLayer.withProperties(
                iconImage("destination-icon-id"),
                iconAllowOverlap(true),
                iconIgnorePlacement(true)
        );
        loadedMapStyle.addLayer(destinationSymbolLayer);
    }

    @Override
    public void onPermissionResult(final boolean granted) {
        if (granted){
            enableLocationComponent(mMapboxMap.getStyle());
        }else{
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
            finish();
        }
    }



    @SuppressLint("MissingPermission")
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            locationComponent = mMapboxMap.getLocationComponent();
            locationComponent.activateLocationComponent(this, loadedMapStyle);
            locationComponent.setLocationComponentEnabled(true);
            locationComponent.setCameraMode(CameraMode.TRACKING);
            locationComponent.setRenderMode(RenderMode.COMPASS);
        }else{
            mPermissionsManager = new PermissionsManager(this);
            mPermissionsManager.requestLocationPermissions(this);
        }
    }

    @Override
    public void onPointerCaptureChanged(final boolean hasCapture) {

    }


    public void saveFavouritesToDatabase(){
        //Push object to new node in firebase database with a unique ID
        String mFavId = mDatabase.getReference("Favourites").push().getKey();
        mFavourites = new Favourites(mFavId, name, city, address, lng, lat);
        mFavRef.child(mFavId).setValue(mFavourites);
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

    public void deleteFavourite(String favId){
        DatabaseReference drFavourite = FirebaseDatabase.getInstance().getReference("Favourites").child(mFavourites.getFavId());
        drFavourite.removeValue();
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
