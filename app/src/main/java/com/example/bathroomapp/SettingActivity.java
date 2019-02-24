package com.example.bathroomapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;

public class SettingActivity extends AppCompatActivity {

    Button button;
    CheckBox disable;
    CheckBox unisex;
    RadioButton mile;
    RadioButton km;
    int settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        button = findViewById(R.id.id_button);
        disable = findViewById(R.id.id_disables);
        unisex = findViewById(R.id.id_unisex);
        mile = findViewById(R.id.id_mile);
        km = findViewById(R.id.id_km);

        settings = getIntent().getIntExtra("settings", 3);
        Log.d("TAG", ""+settings);

//        if(getIntent().getStringExtra("unit").equals("km"))
//            mile.toggle();
//        else if(getIntent().getStringExtra("unit").equals("mi"))
//            km.toggle();

        if(settings == 0) {
            disable.setChecked(true);
            unisex.setChecked(true);
        }
        else if(settings == 1) {
            disable.setChecked(true);
            unisex.setChecked(false);
        }
        else if(settings == 2) {
            disable.setChecked(false);
            unisex.setChecked(true);
        }
        else if(settings == 3) {
            disable.setChecked(false);
            unisex.setChecked(false);
        }
        Log.d("TAG", settings+"");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendInfoBack = new Intent();

                if(disable.isChecked() && unisex.isChecked())
                    sendInfoBack.putExtra("setting", 0);
                else if(disable.isChecked() && !unisex.isChecked())
                    sendInfoBack.putExtra("setting", 1);
                else if(!disable.isChecked() && unisex.isChecked())
                    sendInfoBack.putExtra("setting", 2);
                else if(!disable.isChecked() && !unisex.isChecked())
                    sendInfoBack.putExtra("setting", 3);

                if(mile.isChecked())
                    sendInfoBack.putExtra("unit", "mi");
                else if(km.isChecked())
                    sendInfoBack.putExtra("unit", "km");

                setResult(RESULT_OK, sendInfoBack);
                finish();

            }
        });

    }
}
