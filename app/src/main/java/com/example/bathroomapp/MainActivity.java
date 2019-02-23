package com.example.bathroomapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //new getJSON().execute("http://api.openweathermap.org/data/2.5/forecast?zip=08852&appid=904eaa61902dba144dc85f29013ec210&units=imperial");

    }
}
