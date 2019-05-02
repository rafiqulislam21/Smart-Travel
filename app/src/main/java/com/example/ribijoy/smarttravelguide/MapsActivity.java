package com.example.ribijoy.smarttravelguide;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,GoogleMap.OnMarkerClickListener,LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    //ALT + INSERT to implement all method
    Double myLatitude = null;
    Double myLongitude = null;
    protected static final String TAG = "MapsActivity";

    Button markBtn;
    Button geoLocationBtn;
    Button satelliteBtn;
    Button clearBtn;
    Button btnSearch;

    EditText mSearchText;


    @Override
    public void onLocationChanged(Location location) {


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        setUpMap();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG,"Conncetion Suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i(TAG,"Conncetion Failed: ConnectionResult.getErrorCodr() = "+connectionResult.getErrorMessage());
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);                                                     //-----------------------------
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if(mGoogleApiClient == null){
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API).build();
        }

        // for mark location
        mSearchText = (EditText) findViewById(R.id.etLocationInput);
        //btnSearch = (Button) findViewById(R.id.btnSearch);

        markBtn = (Button) findViewById(R.id.btnMark);
        markBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LatLng myLocation = new LatLng(myLatitude, myLongitude);
                mMap.addMarker(new MarkerOptions().position(myLocation).title("My Current Location"));


                //Toast.makeText(MapsActivity.this,myLatitude.toString(),Toast.LENGTH_SHORT).show();

            }
        });

        //for Search Location

        geoLocationBtn = (Button) findViewById(R.id.btnSearch);
        geoLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText searchTxt = (EditText) findViewById(R.id.etLocationInput);
                String search = searchTxt.getText().toString();

                if(search != null && !search.equals("")){
                    List<Address> addressList = null;

                    Geocoder geocoder = new Geocoder(MapsActivity.this);
                    try {
                        addressList = geocoder.getFromLocationName(search,2);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(TAG, "geoLocate: IOException: " + e.getMessage() );
                        Toast.makeText(getBaseContext(),"Location no found!", Toast.LENGTH_SHORT).show();

                    }
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

                    myLatitude = address.getLatitude();
                    myLongitude = address.getLongitude();

                    //if(address.equals("")){
                    //Toast.makeText(MapsActivity.this,address.toString(),Toast.LENGTH_SHORT).show();
                    //}else{
                    mMap.addMarker(new MarkerOptions().position(latLng).title("Search Result"));
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                    //}


                }
                else{
                    Toast.makeText(getBaseContext(),"Enter Place name!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //for satellite

        satelliteBtn = (Button) findViewById(R.id.btnSatellite);
        satelliteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL){
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    satelliteBtn.setText("Hybrid");
                }
                else if (mMap.getMapType() == GoogleMap.MAP_TYPE_SATELLITE){
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    satelliteBtn.setText("Normal");
                }
                else {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    satelliteBtn.setText("Satellite");
                }
            }
        });

        //for clear

        clearBtn = (Button) findViewById(R.id.btnRemove);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
            }
        });


    }


    //backpress--------------------------------------------------------------
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(MapsActivity.this, activity_home.class);
        startActivity(intent);

    }


    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();

      //  if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
          //  LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (com.google.android.gms.location.LocationListener) this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            setUpMap();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if ( mGoogleApiClient != null && mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }
    }

    private void setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        LocationAvailability locationAvailability =
                LocationServices.FusedLocationApi.getLocationAvailability(mGoogleApiClient);
        if (null != locationAvailability && locationAvailability.isLocationAvailable()) {
            // 3
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            // 4
            if (mLastLocation != null) {
                LatLng currentLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation
                        .getLongitude());

                myLatitude = mLastLocation.getLatitude();
                myLongitude = mLastLocation.getLongitude();

                placeMarkerOnMap(currentLocation);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12));
            }
        }
    }

    protected void placeMarkerOnMap(LatLng location) {
        // 1
        MarkerOptions markerOptions = new MarkerOptions().position(location);
        // 2
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource
                (getResources(), R.mipmap.ic_user_location)));
        mMap.addMarker(markerOptions).setTitle("My Location");

    }




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng dhaka = new LatLng(23.777176, 90.399452);
        mMap.addMarker(new MarkerOptions().position(dhaka).title("Marker in Dhaka"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(dhaka));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dhaka, 12));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMarkerClickListener(this);

        //available trevelling palces add marker

        LatLng coxs_bazer = new LatLng(21.4272, 92.0058);
        mMap.addMarker(new MarkerOptions().position(coxs_bazer).title("Marker in coxs_bazer"));

        LatLng Sundarbans  = new LatLng(21.9497, 89.1833);
        mMap.addMarker(new MarkerOptions().position(Sundarbans).title("Marker in Sundarbans "));

        LatLng Mainimati   = new LatLng(23.5094, 91.1065);
        mMap.addMarker(new MarkerOptions().position(Mainimati).title("Marker in Mainimati "));

        LatLng Lalakhal = new LatLng(25.1072, 92.1778);
        mMap.addMarker(new MarkerOptions().position(Lalakhal).title("Marker in Lalakhal"));

        LatLng Jaflong = new LatLng(25.1634, 92.0175);
        mMap.addMarker(new MarkerOptions().position(Jaflong).title("Marker in Jaflong"));

        LatLng Sonargaon = new LatLng(23.6906, 90.6204);
        mMap.addMarker(new MarkerOptions().position(Sonargaon).title("Marker in Sonargaon"));

        //click mark in map
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.addMarker(new MarkerOptions().position(latLng).title("Clicked Location"));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        });
    }
}
