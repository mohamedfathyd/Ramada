package com.khalej.ramada.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import me.anwarshahriar.calligrapher.Calligrapher;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.khalej.ramada.R;
import com.khalej.ramada.model.Apiclient_home;
import com.khalej.ramada.model.apiinterface_home;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

public class hand_address extends AppCompatActivity {
    private apiinterface_home apiinterface;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    Intent intent;
    EditText address,buildname,ApartmentNumber,role,info,groundNumber;
    Spinner type;
    Button confirm;
    ProgressDialog progressDialog;
    double lat=0.0,lng=0.0;
    boolean s=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hand_address);
        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
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
        buildname=findViewById(R.id.name);
        ApartmentNumber=findViewById(R.id.ApartmentNumber);
        type=findViewById(R.id.type);
        info=findViewById(R.id.info);
        groundNumber=findViewById(R.id.groundNumber);
        role=findViewById(R.id.role);
        address=findViewById(R.id.address);
        intent=getIntent();
        confirm=findViewById(R.id.confirm);
        lat=intent.getDoubleExtra("latyou",0.0);
        lng=intent.getDoubleExtra("lngyou",0.0);
        address.setText(intent.getStringExtra("address"));
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchInfo();
            }
        });

    }
    public void fetchInfo() {
         String h="home";
           if(type.getSelectedItemPosition()==0){
               h="home";
           }
           else{
               h="work";
           }
        progressDialog = ProgressDialog.show(hand_address.this, "جاري أضافة العنوان الجديد", "Please wait...", false, false);
        progressDialog.show();
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));

        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = apiinterface.add_newAddress(headers,address.getText().toString(),
                Integer.parseInt(buildname.getText().toString())
                ,Integer.parseInt(ApartmentNumber.getText().toString()), Integer.parseInt(role.getText().toString())
                ,info.getText().toString(),groundNumber.getText().toString(),
                h,1 ,lat,lng ,"8ba0f0d9-50ba-4d34-b6c2-485652770300");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();

                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(hand_address.this);
                dlgAlert.setMessage("تم أضافة العنوان بنجاح");
                dlgAlert.setTitle("Ramada");
                dlgAlert.setIcon(R.drawable.logo);
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
                startActivity(new Intent(hand_address.this,AddessList.class));
                finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //  Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
