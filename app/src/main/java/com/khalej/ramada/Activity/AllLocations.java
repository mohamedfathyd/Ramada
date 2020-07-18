package com.khalej.ramada.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.khalej.ramada.R;
import com.khalej.ramada.model.Apiclient_home;
import com.khalej.ramada.model.apiinterface_home;
import com.khalej.ramada.model.branches;
import com.khalej.ramada.model.branches;

import java.util.ArrayList;
import java.util.List;

public class AllLocations extends Fragment implements OnMapReadyCallback {
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    private GoogleMap mMap;
    private MapView mapView;
    private apiinterface_home apiinterface;
    branches branchess;
    List<branches.pranches>branchessl=new ArrayList<>();
    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.activity_all_locations, container, false);

        sharedpref = getActivity().getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        return view;

    }
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        try{
            boolean success =googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(getContext(),R.raw.map));
            if(success){}
        }catch (Exception e){}
        // Add a marker in Sydney and move the camera
        LatLng sydney;
        if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
            Toast.makeText(getContext(),"من فضلك تأكد ان الخريطة تحديد الموقع يعمل بشكل جيد عن طريق فتح جوجل ماب وغلقه لكي يمكننا تحديد موقعك الحالي", Toast.LENGTH_LONG).show();

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

        fetchInfo_branches();
    }

    public void fetchInfo_branches() {
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<branches> call = apiinterface.getbranches();
        call.enqueue(new Callback<branches>() {
            @Override
            public void onResponse(Call<branches> call, Response<branches> response) {
                branchess = response.body();
                try {
                    branchessl=branchess.getPayload();


                    if (branchessl.size() != 0 || !(branchessl.isEmpty())) {
                        for (int i = 0; i < branchessl.size(); i++) {
                            int height = 100;
                            int width = 100;
                            BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.bluelocation);
                            Bitmap b = bitmapdraw.getBitmap();
                            Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
                             mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(branchessl.get(i).getLatitude())
                                    ,Double.parseDouble(branchessl.get(i).getLongitude())))
                                    .title(branchessl.get(i).getAr_name()).icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));

                        }
                        CameraUpdate location= CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(branchessl.get(0).getLatitude())
                                ,Double.parseDouble(branchessl.get(0).getLongitude())),15);
                        mMap.animateCamera(location);

                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<branches> call, Throwable t) {

            }
        });
    }
}
