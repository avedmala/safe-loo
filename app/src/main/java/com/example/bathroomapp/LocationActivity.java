package com.example.bathroomapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LocationActivity extends AppCompatActivity {

    JSONArray json;
    int pageNum = 0;

    TextView name;
    TextView address;
    TextView unisex;
    TextView accessible;
    TextView distance;
    TextView page;
    ImageView forward;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        name = findViewById(R.id.id_locName);
        address = findViewById(R.id.id_addy);
        unisex = findViewById(R.id.id_unisex);
        accessible = findViewById(R.id.id_accessible);
        distance = findViewById(R.id.id_distance);
        page = findViewById(R.id.id_pageNum);
        forward = findViewById(R.id.id_imageViewRight);
        back = findViewById(R.id.id_imageViewLeft);

        //forward.setImageDra;

        try {
            json = new JSONArray(getIntent().getStringExtra("EXTRA"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        page.setText(pageNum+"");
        try {
            name.setText(json.getJSONObject(pageNum).getString("name"));
            address.setText(json.getJSONObject(pageNum).getString("street"));
            unisex.setText(json.getJSONObject(pageNum).getString("unisex"));
            accessible.setText(json.getJSONObject(pageNum).getString("accessible"));
            distance.setText(json.getJSONObject(pageNum).getDouble("distance")+"");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageNum++;
                page.setText(pageNum+"");

                try {
                    name.setText(json.getJSONObject(pageNum).getString("name"));
                    address.setText(json.getJSONObject(pageNum).getString("street"));
                    unisex.setText(json.getJSONObject(pageNum).getString("unisex"));
                    accessible.setText(json.getJSONObject(pageNum).getString("accessible"));
                    distance.setText(json.getJSONObject(pageNum).getDouble("distance")+"");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageNum--;
                page.setText(pageNum+"");

                try {
                    name.setText(json.getJSONObject(pageNum).getString("name"));
                    address.setText(json.getJSONObject(pageNum).getString("street"));
                    unisex.setText(json.getJSONObject(pageNum).getString("unisex"));
                    accessible.setText(json.getJSONObject(pageNum).getString("accessible"));
                    distance.setText(json.getJSONObject(pageNum).getDouble("distance")+"");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }
}
