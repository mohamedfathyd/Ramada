package com.khalej.ramada.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;


import com.khalej.ramada.R;
import com.khalej.ramada.model.Apiclient_home;
import com.khalej.ramada.model.apiinterface_home;
import com.khalej.ramada.model.contact_general_userData;

import java.util.HashMap;
import java.util.List;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main_fragment extends Fragment {
    private RecyclerView recyclerView, recyclerView2, recyclerView3;
    private RecyclerView.LayoutManager layoutManager;
    CountDownTimer countDownTimer;
   LinearLayout a,b,c;
    int x = 0;
    int y = 0;
    Switch swtch;
    TextView name;
    int id;
    ProgressBar progressBar;
    ImageView notification;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    AppCompatButton regeister;
    private apiinterface_home apiinterface;
    contact_general_userData contactList;
    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_main_fragment, container, false);
        sharedpref = getActivity().getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        name=view.findViewById(R.id.name);
        fetchInfo_card();
       // id = getArguments().getInt("id");
          a=view.findViewById(R.id.a);
          b=view.findViewById(R.id.b);
          c=view.findViewById(R.id.c);
          a.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  startActivity(new Intent(getContext(),AddessList.class));
              }
          });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),mycharges.class));
            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),Profile.class));
            }
        });
        return view;
    }
    public void fetchInfo_card() {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));

        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<contact_general_userData> call = apiinterface.user_profile(headers);
        call.enqueue(new Callback<contact_general_userData>() {
            @Override
            public void onResponse(Call<contact_general_userData> call, Response<contact_general_userData> response) {
                contactList = response.body();
                try {
                    edt.putString("id",contactList.getPayload().getId());
                    edt.putString("name",contactList.getPayload().getName());
                    edt.putString("phone",contactList.getPayload().getPhone());
                    edt.putString("address",contactList.getPayload().getEmail());
                    edt.putString("type",contactList.getPayload().getType());
                    edt.apply();
                    name.setText("مرحبا " + contactList.getPayload().getName() );

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<contact_general_userData> call, Throwable t) {

            }
        });
    }
}
