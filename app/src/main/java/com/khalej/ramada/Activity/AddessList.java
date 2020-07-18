package com.khalej.ramada.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.khalej.ramada.Adapter.RecyclerAdapter_first_annonce;
import com.khalej.ramada.R;
import com.khalej.ramada.model.Apiclient_home;
import com.khalej.ramada.model.apiinterface_home;
import com.khalej.ramada.model.contact_address;

import java.util.HashMap;
import java.util.List;

public class AddessList extends AppCompatActivity {
Button add;
    RecyclerAdapter_first_annonce recyclerAdapter_first_annonce;
    contact_address contact_address;
    List<contact_address.Address> addresses;
    private apiinterface_home apiinterface;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    private RecyclerView recyclerView3;
    Intent intent;
    ProgressBar progressBar;
    String type,typeR;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addess_list);
        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        progressBar=(ProgressBar)findViewById(R.id.progressBar_subject);
        progressBar.setVisibility(View.VISIBLE);
        intent=getIntent();
        type=intent.getStringExtra("selectType");
        recyclerView3=findViewById(R.id.recyclerview2);
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

        add=findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddessList.this,AddNewAddress.class));
            }
        });
        layoutManager = new GridLayoutManager(this, 1);
        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(
                        1, //The number of Columns in the grid
                        LinearLayoutManager.VERTICAL);
        recyclerView3.setLayoutManager(staggeredGridLayoutManager);
        recyclerView3.setHasFixedSize(true);
        fetchInfo_annonce();
    }
    public void fetchInfo_annonce() {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));

        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<contact_address> call = apiinterface.user_address(headers);
        call.enqueue(new Callback<contact_address>() {
            @Override
            public void onResponse(Call<contact_address> call, Response<contact_address> response) {
                progressBar.setVisibility(View.GONE);
                contact_address=response.body();
                try {
                    addresses=contact_address.getPayload();
                    if (addresses.size() != 0 || !(addresses.isEmpty())) {
                        recyclerAdapter_first_annonce= new RecyclerAdapter_first_annonce(AddessList.this, addresses,type);
                        recyclerView3.setAdapter(recyclerAdapter_first_annonce);
                    }

                } catch (Exception e) {
                    progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<contact_address> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
