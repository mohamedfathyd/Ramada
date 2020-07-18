package com.khalej.ramada.Activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.khalej.ramada.R;
import com.khalej.ramada.model.apiinterface_home;

import java.util.Locale;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import me.anwarshahriar.calligrapher.Calligrapher;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private ActionBar toolbar;
    ImageView card;
    TextView toolbar_title;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    BottomNavigationView navigation;
    String lang;
    ImageView chat;
    Intent intent;
    LinearLayout main,more,myorders,notification,servcies;
    int x=0;
    Fragment fragment;
    private apiinterface_home apiinterface;
    ImageView menu,technology,profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        lang=sharedpref.getString("language","").trim();
        if(lang.equals(null)){
            edt.putString("language","ar");
            lang="en";
            edt.apply();
        }
        intent=getIntent();
        edt.putString("addressId","");
        edt.putString("addressIdR","");
        edt.putString("addressR","");
        edt.apply();
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        setContentView(R.layout.activity_main);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Droid.ttf", true);

        this.setTitle("");
        main=findViewById(R.id.main);
        myorders=findViewById(R.id.myorders);
        technology=findViewById(R.id.technology);
        notification=findViewById(R.id.notification);
        more=findViewById(R.id.more);
        servcies=findViewById(R.id.service);
        intent=getIntent();
        menu=findViewById(R.id.menu);
        profile=findViewById(R.id.profile);
        Fragment fragment = new Main_fragment();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("id",intent.getIntExtra("id",0));
        fragment.setArguments(bundle2);
        loadFragment(fragment);
        if(sharedpref.getString("remember","").equals("yes")){
        }
        try{
        if(intent.getStringExtra("track").equals("track")){
            Fragment fragmentt;
            fragmentt = new Trackshipment();
            fragmentt.setArguments(bundle2);
            loadFragment(fragmentt);
        }}
        catch (Exception e){}
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment;
                fragment = new Main_fragment();
                Bundle bundle2 = new Bundle();
                bundle2.putInt("id",intent.getIntExtra("id",0));
                fragment.setArguments(bundle2);
                loadFragment(fragment);

                x=0;
            }
        });
        myorders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment;
                fragment = new Trackshipment();

                loadFragment(fragment);

                x=3;


            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment;
                fragment = new TimeSendd();
                loadFragment(fragment);

                x=2;
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                startActivity(new Intent(MainActivity.this,ReadyCharges.class));
            }
        });
        servcies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment;
                fragment = new AllLocations();
                loadFragment(fragment);
                x=4;
            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(MainActivity.this , view);
                popup.setOnMenuItemClickListener(MainActivity.this);
                popup.inflate(R.menu.menu_bar);
                popup.show();
            }
        });
        technology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,countprice.class));
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Profile.class));
            }
        });
    }


    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    @Override
    public void onBackPressed() {
        if(x==0){
            finish();}
        else{
            Fragment fragment;
            fragment = new Main_fragment();
            Bundle bundle = new Bundle();
            bundle.putInt("id",intent.getIntExtra("id",0));
            fragment.setArguments(bundle);
            loadFragment(fragment);

            x=0;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                startActivity(new Intent(MainActivity.this,mycharges.class));
                return true;
            case R.id.item2:
                startActivity(new Intent(MainActivity.this,AddessList.class));
                return true;
            case R.id.item3:
                startActivity(new Intent(MainActivity.this,Media_activity.class));
                return true;
            case R.id.item4:
                startActivity(new Intent(MainActivity.this,CallUs.class));
                return true;
            case R.id.item5:
                startActivity(new Intent(MainActivity.this,WhoUs_activity.class));
                return true;
            case R.id.item7:
                startActivity(new Intent(MainActivity.this,feedback.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
