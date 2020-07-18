package com.khalej.ramada.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.khalej.ramada.R;
import com.khalej.ramada.model.Apiclient_home;
import com.khalej.ramada.model.Track;
import com.khalej.ramada.model.apiinterface_home;

import java.util.HashMap;

public class Trackshipment  extends Fragment {
    EditText add_number;
    ProgressDialog progressDialog;
    private apiinterface_home apiinterface;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    Button confirm;
    Track track;
    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.activity_trackshipment, container, false);
        sharedpref = getActivity().getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        add_number=view.findViewById(R.id.add_number);
        confirm=view.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(add_number.getText().toString().equals("")||add_number.getText()==null){}
                else{
                getposition();}
            }
        });
        add_number.setText(sharedpref.getString("track",""));
        edt.putString("track","");
        edt.apply();
        return view;
    }
  public void getposition(){
    progressDialog = ProgressDialog.show(getContext(), "جاري تتبع الشحنة", "Please wait...", false, false);
        progressDialog.show();
    HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));
    apiinterface = Apiclient_home.getapiClient().create(apiinterface_home .class);
    Call<Track> call = apiinterface.track(headers,add_number.getText().toString());
        call.enqueue(new Callback<Track>() {
        @Override
        public void onResponse(Call<Track> call, Response<Track> response) {
            progressDialog.dismiss();
            track=response.body();
            try {
                Intent intent = new Intent(getContext(), OrderCurrentStatus.class);
                intent.putExtra("type", track.getPayload().getCurrent().getAr_name());
                startActivity(intent);
            }
            catch (Exception e){
                Intent intent = new Intent(getContext(), OrderCurrentStatus.class);
                intent.putExtra("type","لم يتم الموافقة على هذا الطلب حتى الآن...انتظر حتى تتم الموافقة ");
                startActivity(intent);
            }

        }

        @Override
        public void onFailure(Call<Track> call, Throwable t) {
            progressDialog.dismiss();
        }
    });
}
}
