package com.example.bathroomapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    String lat = "40.371680";
    String lng = "-74.555990";
    Boolean ada = false;
    Boolean unisex = false;
    JSONObject jsonObject;
    String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(ada && unisex)
            new getJSON().execute("https://www.refugerestrooms.org:443/api/v1/restrooms/by_location.json?per_page=100&ada=true&unisex=true&lat="+lat+"&lng="+lng);
        else if(!ada && !unisex)
            new getJSON().execute("https://www.refugerestrooms.org:443/api/v1/restrooms/by_location.json?per_page=100&ada=false&unisex=false&lat="+lat+"&lng="+lng);
        else if(ada && !unisex)
            new getJSON().execute("https://www.refugerestrooms.org:443/api/v1/restrooms/by_location.json?per_page=100&ada=true&unisex=false&lat="+lat+"&lng="+lng);
        else if(!ada && unisex)
            new getJSON().execute("https://www.refugerestrooms.org:443/api/v1/restrooms/by_location.json?per_page=100&ada=false&unisex=true&lat="+lat+"&lng="+lng);

    }

    public class getJSON extends AsyncTask<String, Void, String> {
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
            Log.v("TAG", s);
            return s;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                jsonObject = new JSONObject(s);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
