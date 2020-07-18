package com.khalej.ramada.Activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.khalej.ramada.R;
import com.khalej.ramada.model.apiinterface_home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import me.anwarshahriar.calligrapher.Calligrapher;

public class ReadyCharges extends AppCompatActivity {
LinearLayout add_address,add_address_receiver;
TextView addresstext,addresstextreceiver;
    private SharedPreferences sharedpref;
    Button confirm;
    private SharedPreferences.Editor edt;
    private apiinterface_home apiinterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready_charges);
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
        add_address=findViewById(R.id.add_address);
        add_address_receiver=findViewById(R.id.add_address_receiver);
        addresstext=findViewById(R.id.addresstext);
        addresstextreceiver=findViewById(R.id.addresstextreceiver);
        confirm=findViewById(R.id.confirm);
        add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ReadyCharges.this,AddessList.class);
                intent.putExtra("selectType","timeSend");
                startActivity(intent);
            }
        });
        add_address_receiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ReadyCharges.this,AddessList.class);
                intent.putExtra("selectType","timeSendR");
                startActivity(intent);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sharedpref.getString("addressId","").equals("")){
                    Toast.makeText(ReadyCharges.this,"من فضلك قم بتحديد عنوان المرسل منه أولا",Toast.LENGTH_LONG).show();
                    return;
                }
                if(sharedpref.getString("addressIdR","").equals("")){
                    Toast.makeText(ReadyCharges.this,"من فضلك قم بتحديد عنوان المرسل أليه أولا",Toast.LENGTH_LONG).show();
                    return;
                }

   Intent intent=new Intent(ReadyCharges.this,ChargeDetails.class);
                intent.putExtra("senderId",sharedpref.getString("addressId",""));
                intent.putExtra("receiverId",sharedpref.getString("addressIdR",""));
                startActivity(intent);


            }
        });
    }
}
