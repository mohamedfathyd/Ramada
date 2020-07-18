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
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.khalej.ramada.R;
import com.khalej.ramada.model.Apiclient_home;
import com.khalej.ramada.model.apiinterface_home;

public class feedback extends AppCompatActivity {
  ImageView aa,bb,cc,dd,ee;
  String type="ok";
    Button Confirm;
    ProgressDialog progressDialog;
    private apiinterface_home apiinterface;
  EditText comment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
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
        comment=findViewById(R.id.comment);
      aa=findViewById(R.id.aa);
      bb=findViewById(R.id.bb);
      cc=findViewById(R.id.cc);
      dd=findViewById(R.id.dd);
      ee=findViewById(R.id.ee);
      Confirm=findViewById(R.id.confirm);
      Confirm.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              fetchInfo();
          }
      });
      aa.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              aa.setBackgroundResource(R.drawable.aayellow);
              bb.setBackgroundResource(R.drawable.bb);
              cc.setBackgroundResource(R.drawable.cc);
              dd.setBackgroundResource(R.drawable.dd);
              ee.setBackgroundResource(R.drawable.ee);
              type="terrible";
          }
      });
        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aa.setBackgroundResource(R.drawable.aa);
                bb.setBackgroundResource(R.drawable.bbyellow);
                cc.setBackgroundResource(R.drawable.cc);
                dd.setBackgroundResource(R.drawable.dd);
                ee.setBackgroundResource(R.drawable.ee);
                type="ok";
            }
        });
        cc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aa.setBackgroundResource(R.drawable.aa);
                bb.setBackgroundResource(R.drawable.bb);
                cc.setBackgroundResource(R.drawable.ccyellow);
                dd.setBackgroundResource(R.drawable.dd);
                ee.setBackgroundResource(R.drawable.ee);
                type="awesome";
            }
        });
        dd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aa.setBackgroundResource(R.drawable.aa);
                bb.setBackgroundResource(R.drawable.bb);
                cc.setBackgroundResource(R.drawable.cc);
                dd.setBackgroundResource(R.drawable.ddyellow);
                ee.setBackgroundResource(R.drawable.ee);
                type="great";
            }
        });
        ee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aa.setBackgroundResource(R.drawable.aa);
                bb.setBackgroundResource(R.drawable.bb);
                cc.setBackgroundResource(R.drawable.cc);
                dd.setBackgroundResource(R.drawable.dd);
                ee.setBackgroundResource(R.drawable.eeyellow);
                type="bad";
            }
        });
    }
    public void fetchInfo() {
        if(comment.getText().toString().isEmpty()){
            Toast.makeText(feedback.this,"من فضلك املئ الحقول الفارغة أولاً" ,Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog = ProgressDialog.show(feedback.this, "جاري ارسال الملاحظة", "Please wait...", false, false);
        progressDialog.show();

        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = apiinterface.feedback(type,comment.getText().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(feedback.this);
                dlgAlert.setMessage("تم ارسال ملاحظتك بنجاح");
                dlgAlert.setTitle("Ramada");
                dlgAlert.setIcon(R.drawable.logo);
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(feedback.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
