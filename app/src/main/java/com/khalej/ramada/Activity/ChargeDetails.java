package com.khalej.ramada.Activity;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.khalej.ramada.R;
import com.khalej.ramada.model.Apiclient_home;
import com.khalej.ramada.model.apiinterface_home;
import com.khalej.ramada.model.contact_charge;
import com.khalej.ramada.model.contact_charge;

import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChargeDetails extends AppCompatActivity {
    private SharedPreferences sharedpref;
    Button confirm;
    private SharedPreferences.Editor edt;
    contact_charge contact_charge;
    private apiinterface_home apiinterface;
    ProgressDialog progressDialog;
    EditText weight,decription,amount,nationalId;
    TextView quantity;
    int count=1;
    ImageView commerce,edit;
    int commerceValue=0;
    int editValue=0;
    ImageView plus,minus;
    Intent intent;
    Spinner typee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge_details);
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
        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        intent=getIntent();
        decription=findViewById(R.id.description);
        nationalId=findViewById(R.id.nationalId);
        amount=findViewById(R.id.amount);
        quantity=findViewById(R.id.quantity);
        typee=findViewById(R.id.type);
        weight=findViewById(R.id.weight);
        confirm=findViewById(R.id.confirm);
        commerce=findViewById(R.id.commerce);
        edit=findViewById(R.id.edit);
        plus=findViewById(R.id.plus);
        minus=findViewById(R.id.minus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++ ;
                quantity.setText(count+"");

            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count>1){
                    count-- ;
                    quantity.setText(count+"");
                }
            }
        });
        commerce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit.setBackgroundResource(R.drawable.editt);
                editValue=0;
                if(commerceValue==0){
                    commerce.setBackgroundResource(R.drawable.commerceyellow);
                    commerceValue=1;
                }
                else{
                    commerce.setBackgroundResource(R.drawable.commercee);
                    commerceValue=0;
                }
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commerce.setBackgroundResource(R.drawable.commercee);
                commerceValue=0;
                if(editValue==0){
                    edit.setBackgroundResource(R.drawable.edittyellow);
                    editValue=1;
                }
                else{
                    edit.setBackgroundResource(R.drawable.editt);
                    editValue=0;
                }
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchInfo_calculate();
            }
        });
    }
    public void fetchInfo_calculate() {
        if(amount.getText().toString().isEmpty()||nationalId.getText().toString().isEmpty()||
                quantity.getText().toString().isEmpty()||weight.getText().toString().isEmpty()||
                decription.getText().toString().isEmpty() ){
            Toast.makeText(ChargeDetails.this,"من فضلك املئ الحقول الفارغة أولاً" ,Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog = ProgressDialog.show(ChargeDetails.this, "جاري طلب الشحنه", "Please wait...", false, false);
        progressDialog.show();
        String type="document";
        HashMap<String, String> headers = new HashMap<String, String>();
        if(commerceValue==1){
            type="document";
        }
        if(editValue==1){
            type="parcel";
        }
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));

        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<contact_charge> call = apiinterface.make_charge(headers,type,typee.getSelectedItem().toString().toLowerCase(),
                Double.parseDouble(amount.getText().toString()),nationalId.getText().toString(),
                sharedpref.getString("address",""),Integer.parseInt(quantity.getText().toString()),
                Double.parseDouble(weight.getText().toString()),decription.getText().toString(),intent.getStringExtra("senderId"),
                intent.getStringExtra("receiverId"));
        call.enqueue(new Callback<contact_charge>() {
            @Override
            public void onResponse(Call<contact_charge> call, Response<contact_charge> response) {
                progressDialog.dismiss();
                contact_charge = response.body();
                try {
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(ChargeDetails.this);
                    dlgAlert.setMessage("تم الطلب بنجاح");
                    dlgAlert.setTitle("Ramada");
                    dlgAlert.setIcon(R.drawable.logo);
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                    Intent intent=new Intent(ChargeDetails.this,ChargeSuccess.class);
                    intent.putExtra("price",contact_charge.getPayload().getPrice());
                    intent.putExtra("currency",contact_charge.getPayload().getCurrency());
                    startActivity(intent);
                    finish();

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<contact_charge> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}
