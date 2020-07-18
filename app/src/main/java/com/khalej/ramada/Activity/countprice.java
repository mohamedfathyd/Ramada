package com.khalej.ramada.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import me.anwarshahriar.calligrapher.Calligrapher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.khalej.ramada.R;
import com.khalej.ramada.model.Apiclient_home;
import com.khalej.ramada.model.apiinterface_home;
import com.khalej.ramada.model.calculate_price;
import com.khalej.ramada.model.contact_country;
import com.khalej.ramada.model.calculate_price;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Integer.parseInt;

public class countprice extends AppCompatActivity {
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;
    AppCompatButton regeister;
    private apiinterface_home apiinterface;
    contact_country contact_country;
    contact_country contact_city;
    List<contact_country.country> country;
    List<contact_country.country> cities;
    List<contact_country.country> cities2;
    String countryId,countryId2,cityId,cityId2;
    EditText weight;
    TextView quantity;
    int count=1;
    ImageView commerce,edit;
    ProgressDialog progressDialog;
    int commerceValue=0;
    int editValue=0;
    Button confirm;
    Spinner spin,spincity,spin2,spin2city;
    calculate_price calculateprice;
    ImageView plus,minus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countprice);
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
        quantity=findViewById(R.id.quantity);
        weight=findViewById(R.id.weight);
        confirm=findViewById(R.id.confirm);
        commerce=findViewById(R.id.commerce);
        edit=findViewById(R.id.edit);
        spin=findViewById(R.id.spinCountry);
        spincity=findViewById(R.id.spinCity);
        spin2=findViewById(R.id.spinCountry2);
        spin2city=findViewById(R.id.spinCity2);
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
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchInfo_calculate();
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
        fetchInfo_countrys();
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                countryId=country.get(i).getId();
                fetchInfo_cites();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                countryId2=country.get(i).getId();
                fetchInfo_cites2();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spincity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cityId=cities.get(i).getId();
             }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spin2city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cityId2=cities2.get(i).getId();
           }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void fetchInfo_countrys() {
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<contact_country> call = apiinterface.getCountrys();
        call.enqueue(new Callback<contact_country>() {
            @Override
            public void onResponse(Call<contact_country> call, Response<contact_country> response) {
                contact_country = response.body();

                // Toast.makeText(getContext(),contact_category+"",Toast.LENGTH_LONG).show();
                try {
                    country=contact_country.getPayload();
                    if (country.size() != 0 || !(country.isEmpty())) {
                        ArrayList<String> arrayList = new ArrayList<>();
                        for (int i = 0; i < country.size(); i++) {

                            arrayList.add(country.get(i).getAr_name());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                countprice.this,
                                android.R.layout.simple_spinner_item,
                                arrayList
                        );
                        spin.setAdapter(adapter);
                        spin2.setAdapter(adapter);
                    }

                } catch (Exception e) {
                   
                }
            }

            @Override
            public void onFailure(Call<contact_country> call, Throwable t) {

            }
        });
    }
    public void fetchInfo_cites() {
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<contact_country> call = apiinterface.getCities(countryId);
        call.enqueue(new Callback<contact_country>() {
            @Override
            public void onResponse(Call<contact_country> call, Response<contact_country> response) {
                contact_country = response.body();

                // Toast.makeText(getContext(),contact_category+"",Toast.LENGTH_LONG).show();
                try {
                    cities=contact_country.getPayload();
                    if (cities.size() != 0 || !(cities.isEmpty())) {
                        ArrayList<String> arrayList = new ArrayList<>();
                        for (int i = 0; i < cities.size(); i++) {

                            arrayList.add(cities.get(i).getAr_name());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                countprice.this,
                                android.R.layout.simple_spinner_item,
                                arrayList
                        );
                        spincity.setAdapter(adapter);

                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<contact_country> call, Throwable t) {

            }
        });
    }

    public void fetchInfo_cites2() {
        apiinterface = Apiclient_home.getapiClient().create(apiinterface_home.class);
        Call<contact_country> call = apiinterface.getCities(countryId2);
        call.enqueue(new Callback<contact_country>() {
            @Override
            public void onResponse(Call<contact_country> call, Response<contact_country> response) {
                contact_country = response.body();

                // Toast.makeText(getContext(),contact_category+"",Toast.LENGTH_LONG).show();
                try {
                    cities2=contact_country.getPayload();
                    if (cities2.size() != 0 || !(cities2.isEmpty())) {
                        ArrayList<String> arrayList = new ArrayList<>();
                        for (int i = 0; i < cities2.size(); i++) {

                            arrayList.add(cities2.get(i).getAr_name());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                countprice.this,
                                android.R.layout.simple_spinner_item,
                                arrayList
                        );
                        spin2city.setAdapter(adapter);

                    }

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<contact_country> call, Throwable t) {

            }
        });
    }

    public void fetchInfo_calculate() {
        if(quantity.getText().toString().isEmpty()||weight.getText().toString().isEmpty()){
            Toast.makeText(countprice.this,"من فضلك املئ الحقول الفارغة أولاً" ,Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog = ProgressDialog.show(countprice.this, "جاري حساب السعر", "Please wait...", false, false);
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
        Call<calculate_price> call = apiinterface.content_calculate(headers,countryId,cityId,countryId2,cityId2,type,
                Integer.parseInt(quantity.getText().toString()),Double.parseDouble(weight.getText().toString()));
        call.enqueue(new Callback<calculate_price>() {
            @Override
            public void onResponse(Call<calculate_price> call, Response<calculate_price> response) {
                progressDialog.dismiss();
                calculateprice = response.body();
                try {
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(countprice.this);
                    dlgAlert.setMessage("التكلفة ستكون :" + calculateprice.getPayload().getFinal_price());
                    dlgAlert.setTitle("Ramada");
                    dlgAlert.setIcon(R.drawable.logo);
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<calculate_price> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}
