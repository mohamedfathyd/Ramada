package com.khalej.ramada.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.khalej.ramada.R;
import com.khalej.ramada.model.Apiclient_home;
import com.khalej.ramada.model.apiinterface_home;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimeSendd extends Fragment {
    ImageView lar,sm,mid;
    LinearLayout add_address;
    String size="small";
    Button confirm;
    DatePickerDialog picker;
    EditText wegiht,description;
    TextView  dateSelect,timeSelect,quantaty;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    private apiinterface_home apiinterface;
    ProgressDialog progressDialog;
    int count=1;
    ImageView plus,minus;
    Intent intent;
    TextView addresstext;
    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.activity_time_send, container, false);
        // id = getArguments().getInt("id");
        sharedpref = getActivity().getSharedPreferences("Education", Context.MODE_PRIVATE);
        edt = sharedpref.edit();
        lar=view.findViewById(R.id.lar);
        mid=view.findViewById(R.id.mid);
        sm=view.findViewById(R.id.sm);
        quantaty=view.findViewById(R.id.quantity);
        description=view.findViewById(R.id.description);
        wegiht=view.findViewById(R.id.weight);
        confirm=view.findViewById(R.id.confirm);
        plus=view.findViewById(R.id.plus);
        minus=view.findViewById(R.id.minus);
        addresstext=view.findViewById(R.id.addresstext);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++ ;
                quantaty.setText(count+"");

            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count>1){
                    count-- ;
                    quantaty.setText(count+"");
                }
            }
        });
        add_address=view.findViewById(R.id.add_address);
        dateSelect=view.findViewById(R.id.dateSelect);
        timeSelect=view.findViewById(R.id.timeSelect);
        add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(),AddessList.class);
                intent.putExtra("selectType","timeSend");
                startActivity(intent);
            }
        });
        timeSelect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String hour="";
                        if(selectedHour<10){
                            hour="0"+(selectedHour);
                        }
                        else{
                            hour= String.valueOf((selectedHour));
                        }
                        String minute="";
                        if(selectedMinute<10){
                            minute="0"+(selectedMinute);
                        }
                        else{
                            minute= String.valueOf((selectedMinute));
                        }
                        timeSelect.setText( hour + ":" + minute);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        dateSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String month="";
                                if((monthOfYear+1)<10){
                                    month="0"+(monthOfYear+1);
                                }
                                else{
                                    month= String.valueOf((monthOfYear+1));
                                }
                                String day="";
                                if((dayOfMonth+1)<10){
                                    day="0"+(dayOfMonth);
                                }
                                else{
                                    day= String.valueOf((dayOfMonth));
                                }
                                dateSelect.setText(year + "-" + month+ "-" + day);
                            }
                        }, day, month, year);
                picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                picker.show();

            }
        });
        lar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                size="large";
                lar.setBackgroundResource(R.drawable.analyticsyellow);
                mid.setBackgroundResource(R.drawable.shippinganddelivery);
                sm.setBackgroundResource(R.drawable.artanddesign);
            }
        });
        sm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                size="small";
                lar.setBackgroundResource(R.drawable.analytics);
                mid.setBackgroundResource(R.drawable.shippinganddelivery);
                sm.setBackgroundResource(R.drawable.artanddesignyellow);
            }
        });
        mid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                size="medium";
                lar.setBackgroundResource(R.drawable.analytics);
                mid.setBackgroundResource(R.drawable.shippinganddeliveryyellow);
                sm.setBackgroundResource(R.drawable.artanddesign);
            }
        });
 confirm.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
   fetchInfo();
     }
 });

        return view;
    }
    public void fetchInfo() {
 if(sharedpref.getString("addressId","").equals("")){
     Toast.makeText(getContext(),"من فضلك قم بتحديد عنوان الأرسال أولا",Toast.LENGTH_LONG).show();
     return;
 }
        if(dateSelect.getText().toString().isEmpty()||timeSelect.getText().toString().isEmpty()||
                quantaty.getText().toString().isEmpty()||wegiht.getText().toString().isEmpty()||
                description.getText().toString().isEmpty() ){
            Toast.makeText(getContext(),"من فضلك املئ الحقول الفارغة أولاً" ,Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog = ProgressDialog.show(getContext(), "جاري تقديم الطلب", "Please wait...", false, false);
        progressDialog.show();
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");
        headers.put("Authorization","Bearer "+ sharedpref.getString("token",""));

        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<ResponseBody> call = apiinterface.add_pickup(headers,dateSelect.getText().toString(),timeSelect.getText().toString(),
                size,Integer.parseInt(quantaty.getText().toString()), Double.parseDouble(wegiht.getText().toString()),description.getText().toString(),
                sharedpref.getString("addressId",""));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();

                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(getContext());
                dlgAlert.setMessage("تم تقديم الطلب بنجاح");
                dlgAlert.setTitle("Ramada");
                dlgAlert.setIcon(R.drawable.logo);
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
                edt.putString("addressId","");
                edt.putString("addressR","");
                edt.putString("addressIdR","");
                edt.apply();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
              //  Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
