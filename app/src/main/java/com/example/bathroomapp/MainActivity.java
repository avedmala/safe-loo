package com.example.bathroomapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationListener{

    double lat;
    double lng;
    double latM;
    double lngM;
    String ada = "false";
    String unisex = "false";
    String a;
    String b;
    JSONObject jsonObject;
    boolean current;
    int settings;

    Button findBathroom;
    EditText editText;
    RadioButton currentLoc;
    RadioButton manualLoc;
    ImageView setting;

    Intent intent;
    Intent intentSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findBathroom = findViewById(R.id.id_findBathrooms);
        editText = findViewById(R.id.id_editText);
        currentLoc=findViewById(R.id.id_currentLoc);
        manualLoc=findViewById(R.id.id_enterLoc);
        setting = findViewById(R.id.id_settings);

        editText.setVisibility(View.INVISIBLE);
        findBathroom.setVisibility(View.INVISIBLE);

        intent = new Intent(this, LocationActivity.class);
        intentSetting = new Intent(this, SettingActivity.class);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("settings", settings);
                startActivityForResult(intentSetting, 101);
            }
        });

        currentLoc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    editText.setVisibility(View.INVISIBLE);
                    findBathroom.setVisibility(View.VISIBLE);
                }
            }
        });

        manualLoc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    editText.setVisibility(View.VISIBLE);
                    findBathroom.setVisibility(View.VISIBLE);
                }
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
                if(currentLoc.isChecked()) {
                    new getCurrentJSON().execute("https://www.refugerestrooms.org:443/api/v1/restrooms/by_location.json?per_page=100&ada=" + ada + "&unisex=" + unisex + "&lat=" + lat + "&lng=" + lng);
                    current = true;
                }
                else if(manualLoc.isChecked()) {
                    new getManualJSON().execute("https://maps.googleapis.com/maps/api/geocode/json?address="+editText.getText().toString().replace(" ","+")+"&key=%20AIzaSyCld3yqglDpsfkE3ezJVSPbqj9YSuNZJGE");
                    current = false;
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int setting = data.getIntExtra("setting", 3);
        if(setting == 0) {
            ada = "true";
            unisex = "true";
        }
        else if(setting == 1) {
            ada = "true";
            unisex = "false";
        }
        else if(setting == 2) {
            ada = "false";
            unisex = "true";
        }
        else if(setting == 3) {
            ada = "false";
            unisex = "false";
        }
        settings = setting;
        Log.d("TAG", settings+" "+setting);
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


    private class getCurrentJSON extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                URLConnection urlConnection = url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                a = bufferedReader.readLine();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            intent.putExtra("JSON", a);
            if(current) {
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);
            }
            else {
                intent.putExtra("lat", latM);
                intent.putExtra("lng", lngM);
            }
            startActivity(intent);
            return a;
        }
    }

    private class getManualJSON extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                URLConnection urlConnection = url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String st;
                while((st = bufferedReader.readLine()) != null){
                    b += st.replace(" ", "");
                }
                b = b.replace("null", "");
                try {
                    jsonObject = new JSONObject(b);
                    latM = jsonObject.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                    lngM = jsonObject.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            new getCurrentJSON().execute("https://www.refugerestrooms.org:443/api/v1/restrooms/by_location.json?per_page=100&ada="+ada+"&unisex="+unisex+"&lat="+latM+"&lng="+lngM);
            return b;
        }
    }



}
