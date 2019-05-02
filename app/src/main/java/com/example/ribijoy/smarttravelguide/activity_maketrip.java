package com.example.ribijoy.smarttravelguide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class activity_maketrip extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maketrip);
    }

    //backpress--------------------------------------------------------------
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(activity_maketrip.this, activity_home.class);
        startActivity(intent);

    }
}
