package com.blooddonationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import utils.VolleySingleton;
import utils.util;


public class MainActivity extends AppCompatActivity {
    ListView listview;
//    FloatingActionButton btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = findViewById(R.id.ls_User_listview);
        GetDonorsApi();
    }

    private void GetDonorsApi() {
        ArrayList<DonorLangModel> arrayList = new ArrayList<DonorLangModel>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, util.DONOR_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "Display--onResponse:"+response );

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String strDonorId = jsonObject1.getString("_id");
                        String strUserName = jsonObject1.getString("userName");
                        String strContactNo = jsonObject1.getString("contactNo");
                        String strEmail = jsonObject1.getString("email");
                        String strPassword = jsonObject1.getString("password");
                        String strBtype = jsonObject1.getString("bloodType");


                        DonorLangModel donorLangModel = new DonorLangModel();
                        donorLangModel.set_id(strDonorId);
                        donorLangModel.setUserName(strUserName);
                        donorLangModel.setContactNo(strContactNo);
                        donorLangModel.setBloodType(strBtype);
                        donorLangModel.setEmail(strEmail);
                        donorLangModel.setPassword(strPassword);
                        arrayList.add(donorLangModel);

                    }
                    DonorListAdapter donorListAdapter = new DonorListAdapter(MainActivity.this, arrayList);
                    listview.setAdapter(donorListAdapter);
                    donorListAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error: ", String.valueOf(error));
            }
        });

        VolleySingleton.getInstance(MainActivity.this).addToRequestQueue(stringRequest);


    }
}