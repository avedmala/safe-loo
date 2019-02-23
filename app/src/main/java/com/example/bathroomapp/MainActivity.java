package com.example.bathroomapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity implements LocationListener{

    double lat;
    double lng;
    String ada = "false";
    String unisex = "false";
    String s;

    CheckBox isDisabled;
    CheckBox isNeutral;
    Button findBathroom;
    EditText editText;
    RadioButton currentLoc;
    RadioButton manualLoc;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isDisabled = findViewById(R.id.id_isDisabled);
        isNeutral = findViewById(R.id.id_isNeutral);
        findBathroom = findViewById(R.id.id_findBathrooms);
        editText = findViewById(R.id.id_editText);
        currentLoc=findViewById(R.id.id_currentLoc);
        manualLoc=findViewById(R.id.id_enterLoc);

        editText.setVisibility(View.INVISIBLE);

        currentLoc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    editText.setVisibility(View.INVISIBLE);
                }
            }
        });

        manualLoc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    editText.setVisibility(View.VISIBLE);
            }
        });

        intent = new Intent(this, LocationActivity.class);



        isDisabled.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ada = "true";
                }
                else
                    ada = "false";
            }
        });

        isNeutral.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    unisex = "true";
                else
                    unisex = "false";

            }
        });

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 200, 1, this);

        findBathroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new getJSON().execute("https://www.refugerestrooms.org:443/api/v1/restrooms/by_location.json?per_page=100&ada="+ada+"&unisex="+unisex+"&lat="+lat+"&lng="+lng);
            }
        });

    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();
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


    private class getJSON extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                URLConnection urlConnection = url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                s = bufferedReader.readLine();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            intent.putExtra("EXTRA", s);
            startActivity(intent);
            return s;
        }
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            try {
//                jsonObject = new JSONObject(s);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
