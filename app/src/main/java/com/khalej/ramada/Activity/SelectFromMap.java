package com.khalej.ramada.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import me.anwarshahriar.calligrapher.Calligrapher;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.khalej.ramada.R;
import com.khalej.ramada.model.apiinterface_home;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SelectFromMap extends AppCompatActivity implements OnMapReadyCallback {
    private apiinterface_home apiinterface;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    private GoogleMap mMap;
    Intent intent;
     Bitmap bitmap;
    Button cunti;
    LatLng latLngValue = null;
    TextView address;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_from_map);
        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        address=findViewById(R.id.address);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
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
        searchView=findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String Location=searchView.getQuery().toString();
                List<Address> addressList=null;
                try {


                if(Location !=null||!Location.equals("")){
                    Geocoder geocoder=new Geocoder(SelectFromMap.this);
                    try{
                        addressList=geocoder.getFromLocationName(Location,1);
                    }catch (Exception e){}
                }
                Address addresss =addressList.get(0);
                LatLng latLng= new LatLng(addresss.getLatitude(),addresss.getLongitude());
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng).title("Direct").icon(BitmapDescriptorFactory.fromResource(R.drawable.location)));
                CameraUpdate location= CameraUpdateFactory.newLatLngZoom(latLng,17);
                mMap.animateCamera(location); Geocoder geocoder;
                List<Address> addresses = null;
                geocoder = new Geocoder(SelectFromMap.this, Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                } catch (IOException e) {
                    e.printStackTrace();
                }

                address.setText(addresses.get(0).getAddressLine(0));
                latLngValue=latLng;
                }catch (Exception e){
                    Toast.makeText(SelectFromMap.this,"قم بتحريك الخريطة لتحديد الموقع" ,Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mMap.clear();
                return false;
            }
        });
        cunti = findViewById(R.id.cunti);
    }
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        try{
            boolean success =googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this,R.raw.map));
            if(success){}
        }catch (Exception e){}
        googleMap.setIndoorEnabled(false);
        LatLng sydney;
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        if(Double.valueOf(sharedpref.getString("lat",""))==0){
            sydney = new LatLng(23.6587778, 43.0343392);
            Toast.makeText(SelectFromMap.this,"من فضلك تأكد ان الخريطة تحديد الموقع يعمل بشكل جيد عن طريق فتح جوجل ماب وغلقه لكي يمكننا تحديد موقعك الحالي",Toast.LENGTH_LONG).show();

        }
        else{
            sydney = new LatLng(Double.valueOf(sharedpref.getString("lat","")), Double.valueOf(sharedpref.getString("lng","")));
        }
        // mMap.addMarker(new MarkerOptions().position(sydney).title("HandMade"));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        CameraUpdate location= CameraUpdateFactory.newLatLngZoom(sydney,18);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(latLng).title("Direct").icon(BitmapDescriptorFactory.fromResource(R.drawable.location)));
                Geocoder geocoder;
                List<Address> addresses = null;
                geocoder = new Geocoder(SelectFromMap.this, Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                } catch (IOException e) {
                    e.printStackTrace();
                }
                address.setText(addresses.get(0).getAddressLine(0));
                latLngValue=latLng;
            }
        });
        mMap.animateCamera(location);
        cunti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(latLngValue!=null){
                    mylocation(latLngValue);}
            }
        });


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    public void mylocation(final LatLng latLng){

        new AlertDialog.Builder(SelectFromMap.this)
                .setTitle("Ramada")
                .setMessage("هل هذا هو موقعك ؟")
                .setIcon(R.drawable.logo)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Geocoder geocoder;
                        List<Address> addresses = null;
                        geocoder = new Geocoder(SelectFromMap.this, Locale.getDefault());

                        try {
                            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Intent intent=new Intent(SelectFromMap.this,hand_address.class);
                        intent.putExtra("latyou",latLng.latitude);
                        intent.putExtra("lngyou",latLng.longitude);
                        intent.putExtra("address",addresses.get(0).getAddressLine(0));
                        startActivity(intent);
                        finish();


                    }})
                .setNegativeButton(android.R.string.no,  new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        mMap.clear();
                    }}).show();

    }
}
