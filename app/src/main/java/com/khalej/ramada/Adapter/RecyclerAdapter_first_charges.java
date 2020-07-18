package com.khalej.ramada.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.khalej.ramada.Activity.MainActivity;
import com.khalej.ramada.Activity.Trackshipment;
import com.khalej.ramada.R;
import com.khalej.ramada.model.contact_address;
import com.khalej.ramada.model.contact_charges;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerAdapter_first_charges extends RecyclerView.Adapter<RecyclerAdapter_first_charges.MyViewHolder> {
    Typeface myTypeface;
    private Context context;
    List<contact_charges.shipments_requests.requests> contactslist;
    RecyclerView recyclerView;
    private SharedPreferences sharedpref;
    private SharedPreferences.Editor edt;

    public RecyclerAdapter_first_charges(Context context, List<contact_charges.shipments_requests.requests> contactslist ){
        this.contactslist=contactslist;
        this.context=context;


    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.chargeslist,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        sharedpref = context.getSharedPreferences("Education", Context.MODE_PRIVATE);
            edt = sharedpref.edit();
            if(sharedpref.getString("language","").trim().equals("ar")){

                holder.name.setText("الكمية :"+contactslist.get(position).getQuantity());
               holder.code.setText("السعر :"+contactslist.get(position).getPrice()+contactslist.get(position).getCurrency());
            }else{

                holder.name.setText("Quantity :"+contactslist.get(position).getQuantity());
                holder.code.setText("price :"+contactslist.get(position).getPrice()+contactslist.get(position).getCurrency());

            }
            holder.address.setText(contactslist.get(position).getDescription());
        holder.date.setText(contactslist.get(position).getDay());



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



             edt.putString("track",contactslist.get(position).getTrack_code());
             edt.apply();
               Intent intent=new Intent(context,MainActivity.class);
               intent.putExtra("track","track");
               context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return contactslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,address,date,code;

        public MyViewHolder(View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.title);
            date=itemView.findViewById(R.id.date);
            address=itemView.findViewById(R.id.text);
            code=itemView.findViewById(R.id.code);

        }
    }
    private void loadFragment(Fragment fragment) {
        // load fragment

    }
}