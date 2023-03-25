package com.blooddonationapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DonorListAdapter extends BaseAdapter {
    Context context;
    ArrayList<DonorLangModel> donorLangModelArrayList;

    public DonorListAdapter(Context context, ArrayList<DonorLangModel> donorLangModelArrayList){
        this.context=context;
        this.donorLangModelArrayList=donorLangModelArrayList;
    }

    @Override
    public int getCount() {
        return donorLangModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return donorLangModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=layoutInflater.inflate(R.layout.donor_table,null);

        TextView tv_Uname,tv_cntNo,tvBtype,tvEmail,tvPassword;
        tv_Uname=view.findViewById(R.id.uname);
        tv_cntNo=view.findViewById(R.id.tv_cntNo);
        tvBtype=view.findViewById(R.id.tv_btype);
        tvEmail=view.findViewById(R.id.tv_email);
        tvPassword=view.findViewById(R.id.tv_pwd);


        tv_Uname.setText(donorLangModelArrayList.get(position).getUserName());
        tv_cntNo.setText(donorLangModelArrayList.get(position).getContactNo());
        tvBtype.setText(donorLangModelArrayList.get(position).getBloodType());
        tvEmail.setText(donorLangModelArrayList.get(position).getEmail());
        tvPassword.setText(donorLangModelArrayList.get(position).getPassword());

        ImageView
                imgEdit = view.findViewById(R.id.img_edit);
        ImageView imgDelete = view.findViewById(R.id.img_delete);

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = donorLangModelArrayList.get(position).get_id();
                Log.e("id in edit-adapter: ",id);

                Intent intent=new Intent(context,DonorUpdateActivity.class);
                intent.putExtra("DONOR_ID",id);
                intent.putExtra("USERNAME",donorLangModelArrayList.get(position).getUserName());
                intent.putExtra("CONTACT_NO",donorLangModelArrayList.get(position).getContactNo());
                intent.putExtra("BLOOD_TYPE",donorLangModelArrayList.get(position).getBloodType());
                intent.putExtra("EMAIL",donorLangModelArrayList.get(position).getEmail());
                intent.putExtra("PASSWORD",donorLangModelArrayList.get(position).getPassword());

                context.startActivity(intent);
            }
        });

        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = donorLangModelArrayList.get(position).get_id();
                Log.e("id in delete: ",id);

                Intent intent=new Intent(context,DonorUpdateActivity.class);
                intent.putExtra("DONOR_ID",id);
                intent.putExtra("USERNAME",donorLangModelArrayList.get(position).getUserName());
                intent.putExtra("CONTACT_NO",donorLangModelArrayList.get(position).getContactNo());
                intent.putExtra("BLOOD_TYPE",donorLangModelArrayList.get(position).getBloodType());
                intent.putExtra("EMAIL",donorLangModelArrayList.get(position).getEmail());
                intent.putExtra("PASSWORD",donorLangModelArrayList.get(position).getPassword());

                context.startActivity(intent);
            }
        });

        return view;
    }
}
