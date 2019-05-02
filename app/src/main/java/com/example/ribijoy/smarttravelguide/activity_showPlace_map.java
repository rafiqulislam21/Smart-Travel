package com.example.ribijoy.smarttravelguide;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.Manifest;
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
import android.view.View;
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
import com.google.firebase.database.DatabaseReference;

import java.io.IOException;
import java.util.List;

public class activity_showPlace_map extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
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

    //data related------------------------------------------------------------

    public String placeName = "", placeAddress = "", placeType = "", placeLat = "", placeLon = "";
    public static String cityname = "";

    private DatabaseReference mRef;
    public static TextView txtName,txtPlaceType, txtPlaceAddress,txtPlaceWeatherStatus,txtPlaceWeatherTemperature,txtPlaceWeatherHumidity,txtPlaceWeatherWindSpeed;

//----------------------------------------------------------------------------------------------

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

        //setUpMap();
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
        setContentView(R.layout.activity_show_place_map);

        //data related form firebase-----------------------------------------------------------------------------------------------------

        txtName = (TextView)findViewById(R.id.tvName);
        txtPlaceType = (TextView)findViewById(R.id.txtPlaceType);
        txtPlaceAddress = (TextView)findViewById(R.id.txtPlaceAddress);
        txtPlaceWeatherStatus = (TextView)findViewById(R.id.txtPlaceWeatherStatus);
        txtPlaceWeatherTemperature = (TextView)findViewById(R.id.txtPlaceWeatherTemperature);
        txtPlaceWeatherHumidity = (TextView)findViewById(R.id.txtPlaceWeatherHumidity);
        txtPlaceWeatherWindSpeed = (TextView)findViewById(R.id.txtPlaceWeatherWindSpeed);


        Intent intent= getIntent();
        Bundle b = intent.getExtras();

        if(b!=null)
        {
            placeName =(String) b.get("name");
            placeType =(String) b.get("type");
            placeLat =(String) b.get("lat");
            placeLon =(String) b.get("lon");
            placeAddress =(String) b.get("address");
            cityname =(String) b.get("city");
            //tvName.setText(j);
           /* Log.i("activity change ", placeName);
            Log.i("activity change ", placeType);
            Log.i("activity change ", placeLat);
            Log.i("activity change ", placeLon);
            Log.i("activity change ", placeAddress);
            */
        }

        //set details from firebase recyle view
        txtName.setText(placeName);
        txtPlaceType.setText(placeType);
        txtPlaceAddress.setText(placeAddress);




        Weather getData = new Weather();
        getData.execute("http://api.openweathermap.org/data/2.5/"+"weather?q="+cityname+"&appid=8a7e9b5174e799941ef0045766af5a9c");



        //-----------------------------data related from fire base-------------------------------------------------



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

       /* clearBtn = (Button) findViewById(R.id.btnRemove);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
            }
        });
*/

    }


    //backpress--------------------------------------------------------------
    @Override
    public void onBackPressed() {
            super.onBackPressed();

        Intent intent = new Intent(activity_showPlace_map.this, activity_topPlaces.class);
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

        //if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
          //  LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (com.google.android.gms.location.LocationListener) this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            //setUpMap();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if ( mGoogleApiClient != null && mGoogleApiClient.isConnected()){
            mGoogleApiClient.disconnect();
        }
    }

   /* private void setUpMap() {
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
        mMap.addMarker(markerOptions);

    }

*/

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
        LatLng dhaka = new LatLng(Double.parseDouble(placeLat), Double.parseDouble(placeLon));
        mMap.addMarker(new MarkerOptions().position(dhaka).title("Marker in "+cityname));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(dhaka));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dhaka, 12));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMarkerClickListener(this);

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
