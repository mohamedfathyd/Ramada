package com.khalej.ramada.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import me.anwarshahriar.calligrapher.Calligrapher;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.khalej.ramada.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddNewAddress extends AppCompatActivity  implements OnMapReadyCallback {
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    private GoogleMap mMap;
    LinearLayout a,b,c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_address);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        a=findViewById(R.id.a);
        b=findViewById(R.id.b);
        c=findViewById(R.id.c);
        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Geocoder geocoder;
                List<Address> addresses = null;
                geocoder = new Geocoder(AddNewAddress.this, Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(Double.valueOf(sharedpref.getString("lat","")),
                            Double.valueOf(sharedpref.getString("lng","")), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent intent=new Intent(AddNewAddress.this,hand_address.class);
                intent.putExtra("latyou",Double.valueOf(sharedpref.getString("lat","")));
                intent.putExtra("lngyou",Double.valueOf(sharedpref.getString("lng","")));
                intent.putExtra("address",addresses.get(0).getAddressLine(0));
                startActivity(intent);
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddNewAddress.this,SelectFromMap.class));
            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddNewAddress.this,hand_address.class));
            }
        });
        setSupportActionBar(toolbar);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Droid.ttf", true);
        this.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        try{
            boolean success =googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(AddNewAddress.this,R.raw.map));
            if(success){}
        }catch (Exception e){}
        // Add a marker in Sydney and move the camera
        LatLng sydney;
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        // Add a marker in Sydney and move the camera
        try{
            if(Double.valueOf(sharedpref.getString("lat",""))==0){
                sydney = new LatLng(23.6587778, 43.0343392);
                Toast.makeText(AddNewAddress.this,"من فضلك تأكد ان الخريطة تحديد الموقع يعمل بشكل جيد عن طريق فتح جوجل ماب وغلقه لكي يمكننا تحديد موقعك الحالي", Toast.LENGTH_LONG).show();

            }
            else{
                sydney = new LatLng(Double.valueOf(sharedpref.getString("lat","")), Double.valueOf(sharedpref.getString("lng","")));
            }}
        catch (Exception e){
            sydney = new LatLng(23.6587778, 43.0343392);

        }
        // mMap.addMarker(new MarkerOptions().position(sydney).title("HandMade"));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        CameraUpdate location= CameraUpdateFactory.newLatLngZoom(sydney,15);
        mMap.animateCamera(location);
    }
}
