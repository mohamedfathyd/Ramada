package com.khalej.ramada.Activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import com.khalej.ramada.R;
import com.khalej.ramada.model.Apiclient_home;
import com.khalej.ramada.model.Reset;
import com.khalej.ramada.model.apiinterface_home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPassword extends AppCompatActivity {
    TextInputEditText textInputEditTextphone,textInputEditTextpass,textInputEditTextcode;
    AppCompatButton appCompatButtonRegisterservcies;
    LinearLayout lock,code;

    ProgressDialog progressDialog;
    private apiinterface_home apiinterface;
    Reset reset;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.colorPrimaryDark));

        setContentView(R.layout.activity_forget_password);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "Droid.ttf", true);
        lock=findViewById(R.id.lock);
        code=findViewById(R.id.code);
        lock.setVisibility(View.GONE);
        code.setVisibility(View.GONE);
        textInputEditTextphone=findViewById(R.id.textInputEditTextphone);
        textInputEditTextcode=findViewById(R.id.textInputEditTextcode);
        textInputEditTextpass=findViewById(R.id.textInputEditTextpass);
        appCompatButtonRegisterservcies=findViewById(R.id.appCompatButtonRegisterservcies);
        appCompatButtonRegisterservcies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(code.getVisibility()== View.VISIBLE &&lock.getVisibility() == View.GONE){
                    fetchInfo__();
                }

                if(lock.getVisibility() == View.VISIBLE){
                    fetchInfo_();
                }

                if(code.getVisibility()== View.GONE &&lock.getVisibility() == View.GONE){
                    fetchInfo();}
            }
        });

    }

    public void fetchInfo(){
        progressDialog = ProgressDialog.show(ForgetPassword.this,"جاري الإرسال","Please wait...",false,false);
        progressDialog.show();

        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<Reset> call= apiinterface.getcontacts_ResetPassword(
                textInputEditTextphone.getText().toString());
        call.enqueue(new Callback<Reset>() {
            @Override
            public void onResponse(Call<Reset> call, Response<Reset> response) {
                reset=response.body();
                progressDialog.dismiss();
                if(reset.getCode()==200&&reset.isStatus()){
                    Toast.makeText(ForgetPassword.this,"وصلك بريد ألكترونى يمكنك الأن تحديد كلمة مرور جديدة",Toast.LENGTH_LONG).show();
                    code.setVisibility(View.VISIBLE);
                }
                else{
                    Toast.makeText(ForgetPassword.this,"هذه البيانات غير مسجلة",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Reset> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
    public void fetchInfo_(){
        progressDialog = ProgressDialog.show(ForgetPassword.this,"جاري تحديث كلمة المرور","Please wait...",false,false);
        progressDialog.show();

        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<Reset> call= apiinterface.getcontacts_updatePassword(
                textInputEditTextphone.getText().toString(),textInputEditTextpass.getText().toString());
        call.enqueue(new Callback<Reset>() {
            @Override
            public void onResponse(Call<Reset> call, Response<Reset> response) {
                reset=response.body();
                progressDialog.dismiss();
                if(reset.getCode()==200&&reset.isStatus()){
                    Toast.makeText(ForgetPassword.this,"تم تغيير الرقم السري بنجاح",Toast.LENGTH_LONG).show();

                }
                else{
                    Toast.makeText(ForgetPassword.this,"هناك  خطأ بالبيانات ",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Reset> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
    public void fetchInfo__(){
        progressDialog = ProgressDialog.show(ForgetPassword.this,"جاري تحديث كلمة المرور","Please wait...",false,false);
        progressDialog.show();

        apiinterface= Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<Reset> call= apiinterface.getcontacts_tokenPassword(
                textInputEditTextphone.getText().toString(),textInputEditTextcode.getText().toString());
        call.enqueue(new Callback<Reset>() {
            @Override
            public void onResponse(Call<Reset> call, Response<Reset> response) {
                reset=response.body();
                progressDialog.dismiss();
                if(reset.getCode()==200&&reset.isStatus()){
                    Toast.makeText(ForgetPassword.this,"كود تأكيد صحيح",Toast.LENGTH_LONG).show();
                    lock.setVisibility(View.VISIBLE);
                }
                else{
                    Toast.makeText(ForgetPassword.this,"كود تأكيد غير صحيح",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<Reset> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}
