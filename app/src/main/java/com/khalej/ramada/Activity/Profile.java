package com.khalej.ramada.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import me.anwarshahriar.calligrapher.Calligrapher;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.khalej.ramada.R;
import com.khalej.ramada.model.Apiclient_home;
import com.khalej.ramada.model.apiinterface_home;

import java.util.HashMap;

public class Profile extends AppCompatActivity {
LinearLayout a,b,c,d,e,f;
TextView usename,phone,email;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    Dialog dialog;
    TextView name;
    ProgressDialog progressDialog;
    EditText comment;
    Button confirm;
    private apiinterface_home apiinterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sharedpref = getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        a=findViewById(R.id.a);
        b=findViewById(R.id.b);
        c=findViewById(R.id.c);
        d=findViewById(R.id.d);
        e=findViewById(R.id.e);
        f=findViewById(R.id.f);
        usename=findViewById(R.id.username);
        phone=findViewById(R.id.phone);
        email=findViewById(R.id.email);
        usename.setText(sharedpref.getString("name",""));
        phone.setText(sharedpref.getString("phone",""));
        email.setText(sharedpref.getString("address",""));
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Profile.this,AddessList.class));
            }
        });
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt.putInt("id",0);
                edt.putString("name","");
                edt.putString("image","");
                edt.putString("phone","");
                edt.putString("address","");
                edt.putString("password","");
                edt.putString("createdAt","");
                edt.putInt("type",0);
                edt.putFloat("wallet",0);
                edt.putString("token","");
                edt.putString("remember","no");
                edt.apply();
                startActivity(new Intent(Profile.this, Login.class));
                finish();
            }
        });
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sharedpref.getString("language","").trim().equals("ar")){
                    edt.putString("language","en");
                    edt.apply();
                    startActivity(new Intent(Profile.this,MainActivity.class));
                    finish();
                }
                else
                {
                    edt.putString("language","ar");
                    edt.apply();
                    startActivity(new Intent(Profile.this,MainActivity.class));
                    finish();
                }
            }
        });
      a.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              dialog = new Dialog(Profile.this);
              dialog.setContentView(R.layout.dialog_change);
              dialog.getWindow().setLayout( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
              dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

              comment=dialog.findViewById(R.id.comment);
              confirm=dialog.findViewById(R.id.confirm);
              name=dialog.findViewById(R.id.name);
              name.setText("الأسم الجديد :");
              confirm.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      if(comment.getText()==null||comment.getText().toString().equals("")){
                          Toast.makeText(Profile.this,"قم بكتاية الأسم أولا" ,Toast.LENGTH_LONG).show();
                      }
                      else{
                          fetchAddnewName();
                      }
                  }
              });
              dialog.show();
          }
      });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(Profile.this);
                dialog.setContentView(R.layout.dialog_change);
                dialog.getWindow().setLayout( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                comment=dialog.findViewById(R.id.comment);
                confirm=dialog.findViewById(R.id.confirm);
                name=dialog.findViewById(R.id.name);
                name.setText("رقم الهاتف الجديد :");
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(comment.getText()==null||comment.getText().toString().equals("")){
                            Toast.makeText(Profile.this,"قم بكتاية رقم الهاتف الجديد أولا" ,Toast.LENGTH_LONG).show();
                        }
                        else{
                            fetchAddnewPhone();
                        }
                    }
                });
                dialog.show();
            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(Profile.this);
                dialog.setContentView(R.layout.dialog_change);
                dialog.getWindow().setLayout( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                comment=dialog.findViewById(R.id.comment);
                confirm=dialog.findViewById(R.id.confirm);
                name=dialog.findViewById(R.id.name);
                name.setText("البريد الألكتروني الجديد :");
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(comment.getText()==null||comment.getText().toString().equals("")){
                            Toast.makeText(Profile.this,"قم بكتاية البريد الألكترونى الجديد أولا" ,Toast.LENGTH_LONG).show();
                        }
                        else{
                            fetchAddnewEmail();
                        }
                    }
                });
                dialog.show();
            }
        });
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
    }
    public void fetchAddnewName(){
        progressDialog = ProgressDialog.show(Profile.this, "جاري تغيير الأسم", "Please wait...", false, false);
        progressDialog.show();
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = apiinterface.changeName(headers,comment.getText().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(Profile.this);
                dlgAlert.setMessage("تم تغيير الأسم بنجاح");
                dlgAlert.setTitle("Ramada");
                dlgAlert.setIcon(R.drawable.logo);
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Profile.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void fetchAddnewPhone(){
        progressDialog = ProgressDialog.show(Profile.this, "جاري تغيير رقم الهاتف", "Please wait...", false, false);
        progressDialog.show();
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = apiinterface.changePhone(headers,comment.getText().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(Profile.this);
                dlgAlert.setMessage("تم تغيير رقم الهاتف بنجاح");
                dlgAlert.setTitle("Ramada");
                dlgAlert.setIcon(R.drawable.logo);
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Profile.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void fetchAddnewEmail(){
        progressDialog = ProgressDialog.show(Profile.this, "جاري تغيير البريد الألكترونى", "Please wait...", false, false);
        progressDialog.show();
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = apiinterface.changeEmail(headers,comment.getText().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(Profile.this);
                dlgAlert.setMessage("تم تغيير البريد الألكتروني بنجاح");
                dlgAlert.setTitle("Ramada");
                dlgAlert.setIcon(R.drawable.logo);
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
                email.setText(comment.getText().toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Profile.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
