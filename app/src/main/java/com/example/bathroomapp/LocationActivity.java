package com.example.bathroomapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;

public class LocationActivity extends AppCompatActivity {

    JSONArray json;
    JSONObject jsonDistance;
    JSONObject jsonLatLng;
    int pageNum = 0;
    String matrixDistance;
    String a;
    String b;
    String streetD;
    String cityD;
    String origin;
    Double lat;
    Double lng;
    Double dist;

    String units;

    TextView name;
    TextView street;
    TextView city;
    ImageView unisex;
    ImageView accessible;
    TextView distance;
    TextView page;
    TextView directions;
    TextView comments;
    ImageView forward;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        name = findViewById(R.id.id_locName);
        street = findViewById(R.id.id_street);
        city = findViewById(R.id.id_city);
        distance = findViewById(R.id.id_distance);
        page = findViewById(R.id.id_pageNum);
        forward = findViewById(R.id.id_imageViewRight);
        back = findViewById(R.id.id_imageViewLeft);
        unisex = findViewById(R.id.id_imageViewUnisex);
        accessible = findViewById(R.id.id_imageViewDisabled);
        directions = findViewById(R.id.id_directions);
        comments = findViewById(R.id.id_comments);

        unisex.setVisibility(View.INVISIBLE);
        accessible.setVisibility(View.INVISIBLE);
        back.setVisibility(View.INVISIBLE);


        lat = getIntent().getDoubleExtra("lat", 0.0);
        lng = getIntent().getDoubleExtra("lng", 0.0);
        //units = getIntent().getStringExtra("unit");
        units = "mi";

        new getLatLng().execute("https://maps.googleapis.com/maps/api/geocode/json?latlng="+lat+","+lng+"&key=AIzaSyCld3yqglDpsfkE3ezJVSPbqj9YSuNZJGE");

        try {
            json = new JSONArray(getIntent().getStringExtra("JSON"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //distance.setText(matrixDistance);
        page.setText(pageNum+1+"");
        try {
            name.setText(json.getJSONObject(pageNum).getString("name"));
            street.setText(json.getJSONObject(pageNum).getString("street"));
            city.setText(json.getJSONObject(pageNum).getString("city")+", "+json.getJSONObject(pageNum).getString("state"));
            dist = json.getJSONObject(pageNum).getDouble("distance");
            distance.setText(String.format("%.2f", dist) + " "+units);
            if(json.getJSONObject(pageNum).getString("unisex").equals("true"))
                unisex.setVisibility(View.VISIBLE);
            else
                unisex.setVisibility(View.INVISIBLE);
            if(json.getJSONObject(pageNum).getString("accessible").equals("true"))
                accessible.setVisibility(View.VISIBLE);
            else
                accessible.setVisibility(View.INVISIBLE);

            if(json.getJSONObject(pageNum).getString("directions").equals("") || json.getJSONObject(pageNum).getString("directions").equals("null")) {
                directions.setText("No Directions Available");
            }
            else {
                directions.setText(json.getJSONObject(pageNum).getString("directions"));
            }

            if(json.getJSONObject(pageNum).getString("comment").equals("") || json.getJSONObject(pageNum).getString("comment").equals("null")) {
                comments.setText("No Comments Available");
            }
            else {
                comments.setText(json.getJSONObject(pageNum).getString("comment"));
            }

            streetD = json.getJSONObject(pageNum).getString("street").replace(" ", "+");
            cityD = json.getJSONObject(pageNum).getString("city").replace(" ", "+");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(Intent.ACTION_VIEW);
                try {
                    mapIntent.setData(Uri.parse("geo:"+json.getJSONObject(pageNum).getString("latitude")+","+json.getJSONObject(pageNum).getString("longitude")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                try {
//                    Log.d("TAG","http://maps.google.com/maps?saddr="+lat+","+lng+"&daddr="+json.getJSONObject(pageNum).getString("latitude")+","+json.getJSONObject(pageNum).getString("longitude"));
//                    mapIntent.setData(Uri.parse("http://maps.google.com/maps?saddr="+lat+","+lng+"&daddr="+json.getJSONObject(pageNum).getString("latitude")+","+json.getJSONObject(pageNum).getString("longitude")));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                startActivity(mapIntent);
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(pageNum+1 == json.length())
                        forward.setVisibility(View.INVISIBLE);
                    else {
                        pageNum++;
                        page.setText(pageNum + 1 + "");
                        name.setText(json.getJSONObject(pageNum).getString("name"));
                        street.setText(json.getJSONObject(pageNum).getString("street"));
                        city.setText(json.getJSONObject(pageNum).getString("city") + ", " + json.getJSONObject(pageNum).getString("state"));
                        dist = json.getJSONObject(pageNum).getDouble("distance");
                        distance.setText(String.format("%.2f", dist) + " "+units);
                        if (json.getJSONObject(pageNum).getString("unisex").equals("true"))
                            unisex.setVisibility(View.VISIBLE);
                        else
                            unisex.setVisibility(View.INVISIBLE);
                        if (json.getJSONObject(pageNum).getString("accessible").equals("true"))
                            accessible.setVisibility(View.VISIBLE);
                        else
                            accessible.setVisibility(View.INVISIBLE);

                        if(json.getJSONObject(pageNum).getString("directions").equals("") || json.getJSONObject(pageNum).getString("directions").equals("null")) {
                            directions.setText("No Directions Available");
                        } else {
                            directions.setText(json.getJSONObject(pageNum).getString("directions"));
                        }

                        if(json.getJSONObject(pageNum).getString("comment").equals("") || json.getJSONObject(pageNum).getString("comment").equals("null")) {
                            comments.setText("No Comments Available");
                        } else {
                            comments.setText(json.getJSONObject(pageNum).getString("comment"));
                        }

                        streetD = json.getJSONObject(pageNum).getString("street").replace(" ", "+");
                        cityD = json.getJSONObject(pageNum).getString("city").replace(" ", "+");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(pageNum == 0)
                    back.setVisibility(View.INVISIBLE);
                else
                    back.setVisibility(View.VISIBLE);

                //new getDistance().execute("https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins="+origin+"&destinations="+streetD+"+"+cityD+"&key=AIzaSyCld3yqglDpsfkE3ezJVSPbqj9YSuNZJGE");
                //distance.setText(matrixDistance);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageNum--;
                page.setText(pageNum+1+"");
                forward.setVisibility(View.VISIBLE);

                if(pageNum == 0)
                    back.setVisibility(View.INVISIBLE);
                else
                    back.setVisibility(View.VISIBLE);

                try {
                    name.setText(json.getJSONObject(pageNum).getString("name"));
                    street.setText(json.getJSONObject(pageNum).getString("street"));
                    city.setText(json.getJSONObject(pageNum).getString("city")+", "+json.getJSONObject(pageNum).getString("state"));
                    dist = json.getJSONObject(pageNum).getDouble("distance");
                    distance.setText(String.format("%.2f", dist) + " "+units);
                    if(json.getJSONObject(pageNum).getString("unisex").equals("true"))
                        unisex.setVisibility(View.VISIBLE);
                    else
                        unisex.setVisibility(View.INVISIBLE);
                    if(json.getJSONObject(pageNum).getString("accessible").equals("true"))
                        accessible.setVisibility(View.VISIBLE);
                    else
                        accessible.setVisibility(View.INVISIBLE);

                    if(json.getJSONObject(pageNum).getString("directions").equals("") || json.getJSONObject(pageNum).getString("directions").equals("null")) {
                        directions.setText("No Directions Available");
                    }
                    else {
                        directions.setText(json.getJSONObject(pageNum).getString("directions"));
                    }

                    if(json.getJSONObject(pageNum).getString("comment").equals("") || json.getJSONObject(pageNum).getString("comment").equals("null")) {
                        comments.setText("No Comments Available");
                    }
                    else {
                        comments.setText(json.getJSONObject(pageNum).getString("comment"));
                    }

                    streetD = json.getJSONObject(pageNum).getString("street").replace(" ", "+");
                    cityD = json.getJSONObject(pageNum).getString("city").replace(" ", "+");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //new getDistance().execute("https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins="+origin+"&destinations="+streetD+"+"+cityD+"&key=AIzaSyCld3yqglDpsfkE3ezJVSPbqj9YSuNZJGE");
                //distance.setText(matrixDistance);
            }
        });

    }

    private class getDistance extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                URLConnection urlConnection = url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String st;
                while((st = bufferedReader.readLine()) != null){
                    a += st.replace(" ", "");
                }
                a = a.replace("null", "");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                jsonDistance = new JSONObject(a);
                matrixDistance = jsonDistance.getJSONArray("rows").getJSONObject(0).getJSONArray("elements").getJSONObject(0).getJSONObject("distance").getString("text");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return a;
        }
    }

    private class getLatLng extends AsyncTask<String, Void, String> {
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
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                jsonLatLng = new JSONObject(b);
                origin = jsonLatLng.getJSONArray("results").getJSONObject(0).getString("formatted_address").replace(" ","+");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //new getDistance().execute("https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins="+origin+"&destinations="+streetD+"+"+cityD+"&key=AIzaSyCld3yqglDpsfkE3ezJVSPbqj9YSuNZJGE");
            return b;
        }
    }


}
