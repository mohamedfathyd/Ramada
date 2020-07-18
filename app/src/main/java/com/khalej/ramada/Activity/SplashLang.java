package com.khalej.ramada.Activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.khalej.ramada.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import me.anwarshahriar.calligrapher.Calligrapher;

public class SplashLang extends AppCompatActivity {
ImageView arrowRight,arrowLeft;
    AppCompatButton appCompatButton;
TextView lang;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashlang);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Droid.ttf", true);

        arrowLeft=findViewById(R.id.arrowleft);
        arrowRight=findViewById(R.id.arrowright);
        lang=findViewById(R.id.lang);
        appCompatButton=findViewById(R.id.appCompatButtonRegisterservcies);
        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        lang.setText("عربي");
        arrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lang.getText().toString().equals("عربي")){
                    lang.setText("English");
                }
                else {
                    lang.setText("عربي");
                }

            }
        });
        arrowLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lang.getText().toString().equals("عربي")){
                    lang.setText("English");
                }
                else {
                    lang.setText("عربي");
                }

            }
        });
        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lang.getText().toString().equals("عربي")){
                    edt.putString("language","ar");
                    edt.apply();
                    startActivity(new Intent(SplashLang.this,Login.class));
                    finish();

                }
                else
                {
                    edt.putString("language","en");
                    edt.apply();
                    startActivity(new Intent(SplashLang.this,Login.class));
                    finish();
                }
            }
        });
    }
}
