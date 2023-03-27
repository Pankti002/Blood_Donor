package com.blooddonationapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

import utils.VolleySingleton;
import utils.util;

public class SearchActivity extends AppCompatActivity {

    String strBloodGroups[] = {"Select your Blood Group", "A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
    Spinner spinnerBloodGrp;
    String strBloodGrpSelected;


    Button btnSearch;
    Queue list=new Queue();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        btnSearch=findViewById(R.id.btn_Search);
        spinnerBloodGrp = findViewById(R.id.spinner);


        ArrayAdapter<String> arrayAdapter = new
                ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strBloodGroups) {
                    @Override
                    public View getDropDownView(int position, @Nullable View convertView,
                                                @NonNull ViewGroup parent) {

                        TextView tvData = (TextView) super.getDropDownView(position, convertView, parent);
                        tvData.setTextColor(Color.BLACK);
                        tvData.setTextSize(20);
                        return tvData;
                    }

                };
        spinnerBloodGrp.setAdapter(arrayAdapter);

        spinnerBloodGrp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strBloodGrpSelected = strBloodGroups[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetDonorsApi();
                Intent intent=new Intent(SearchActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });




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
                        Log.e("Blood Group: ",strBtype);
                        Log.e(strBloodGrpSelected,"");

                        if(strBtype.equals(strBloodGrpSelected))
                        {
                            Log.e("BLOOD GROUP in if ",strBtype);
                            list.enqueue(strUserName,strContactNo,strBtype,strEmail,strPassword);
                        }

                    }

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

        VolleySingleton.getInstance(SearchActivity.this).addToRequestQueue(stringRequest);


    }
}