package com.example.ribijoy.smarttravelguide;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class activity_home extends AppCompatActivity implements LocationListener {



    private Button btnSearch;
    private EditText etSearch;
    private CardView crdTopPlace, crdMap, crdMakeTrip, crdCalculator, crdLogout; // home card view
    private TextView tvTemp, tvHumidity, tvWind, tvCurrentLocation, tvStatus;

    private LocationManager locationManager;
    private Double logitude;
    private Double latitude;

    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //for home cards
        crdLogout = (CardView) findViewById(R.id.crdLogout);
        crdTopPlace = (CardView) findViewById(R.id.crdTopPlace);
        crdCalculator = (CardView) findViewById(R.id.crdCalculator);
        crdMap = (CardView) findViewById(R.id.crdMap);
        crdMakeTrip = (CardView) findViewById(R.id.crdMakeTrip);

        btnSearch = (Button) findViewById(R.id.btnSearchPlace);

        //for home weather report
        tvTemp = (TextView) findViewById(R.id.tvTemp);
        tvHumidity = (TextView) findViewById(R.id.tvHumidity);
        tvWind = (TextView) findViewById(R.id.tvWind);
        tvCurrentLocation = (TextView) findViewById(R.id.tvCurrentLocation);
        tvStatus = (TextView) findViewById(R.id.tvStatus);

        find_weather();

        //get location
/*/ unhide later --------------------------------------------------------------------------------------------------
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);

        //funciton for longitude and latitude
        onLocationChanged(location);
        //function for location name
        //loc_func(location);


/*-----------------------------------------------------------------------------------------------------------------*/

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(),"Feature Comming soon!", Toast.LENGTH_SHORT).show();
            }
        });

        //for home cards
        crdLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_home.this, Login.class);
                startActivity(intent);
                FirebaseAuth.getInstance().signOut();// signout
                Toast.makeText(getBaseContext(),"Signout", Toast.LENGTH_SHORT).show();
            }
        });


        crdTopPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity_home.this, activity_topPlaces.class);
                startActivity(intent);

            }
        });



        crdCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_home.this, activity_calculator.class);
                startActivity(intent);
            }
        });


        crdMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_home.this, MapsActivity.class);
                startActivity(intent);
            }
        });



        crdMakeTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_home.this, activity_maketrip.class);
                startActivity(intent);
            }
        });
    }



    //backpress--------------------------------------------------------------
    /*@Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(activity_home.this, Login.class);
        startActivity(intent);

    }*/
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {


        if(backPressedTime+2000>System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
            //
            FirebaseAuth.getInstance().signOut();// signout
            Intent intent = new Intent(activity_home.this, Login.class);
            startActivity(intent);

            //getActivity().finish();
            //System.exit(0);
        } else {
            backToast = Toast.makeText(getBaseContext(),"Press back again to Signout", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }





    private void find_weather() {


    }


    @Override
    public void onLocationChanged(Location location) {
     logitude = location.getLongitude();
     latitude = location.getLatitude();

    //tvTemp.setText(Double.toString(logitude));
    //tvStatus.setText(Double.toString(latitude));


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

    // function for location

   /* private void loc_func(Location location){

        try {
            Geocoder geocoder = new Geocoder(this);
            List<Address> addresses=null;
            addresses=geocoder.getFromLocation(latitude,logitude,1);

            String country=addresses.get(0).getCountryName();
           String city=addresses.get(0).getLocality();
           String state=addresses.get(0).getLocality();

           tvCurrentLocation.setText(country);

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        }
    }
    */
}
