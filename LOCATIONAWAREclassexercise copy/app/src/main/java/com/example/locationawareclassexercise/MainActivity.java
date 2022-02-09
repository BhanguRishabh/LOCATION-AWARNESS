package com.example.locationawareclassexercise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity implements LocationListener {
protected  LocationListener locationListener;
protected LocationManager locationManager;
protected  Double latitude , longitude , altitude ;
  protected String  Address;
    public static final int REQUEST_CODE = 112;

    TextView longt , latit , alt , add , acc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        longt = findViewById(R.id.tvlong);
        latit = findViewById(R.id.tvLat);
        alt = findViewById(R.id.tvAlt);
        add = findViewById(R.id.tvAdd);
        acc = findViewById(R.id.tvAcc);



        requestLocationPermission();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        try {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            longitude = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude();
            latitude = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude();
            longt.setText(String.valueOf(longitude));
            latit.setText(String.valueOf(latitude));

            alt.setText(String.valueOf(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getAltitude()) +  " METRES");
            acc.setText(String.valueOf(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getAccuracy())+  " METRES");


            reverseGeocoding(latitude,longitude);

        }




    }

    @Override
    public void onLocationChanged(@NonNull Location location) {





    }



    private boolean isLocationPermissionGranted() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            return true;
        } else {
            return false;
        }

    }
    private void requestLocationPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);

    }
// USING FUNCTION FOR GETTING ADDRESS
    private  void reverseGeocoding(Double latitude,Double longitude){
        List<Address> geocodeMatches = null;
        String Address1;

        try {
            geocodeMatches =
                    new Geocoder(this).getFromLocation(latitude, longitude, 1);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (!geocodeMatches.isEmpty())
        {
            Address1 = geocodeMatches.get(0).getAddressLine(0);

            add.setText(Address1);
        }

    }
}