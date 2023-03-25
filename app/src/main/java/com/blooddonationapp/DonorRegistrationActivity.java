package com.blooddonationapp;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.VolleySingleton;
import utils.util;

public class DonorRegistrationActivity extends AppCompatActivity {

    TextInputEditText edt_name, edt_number, edt_email, edt_password;
    Button btnRegister;

    String strBloodGroups[] = {"Select your Blood Group", "A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
    Spinner spinnerBloodGrp;
    String strBloodGrpSelected;

    CustomLinkedList list=new CustomLinkedList();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_registration);

        edt_name = findViewById(R.id.inputName);
        edt_number = findViewById(R.id.inputNumber);
        edt_email = findViewById(R.id.inputEmail);
        edt_password = findViewById(R.id.inputPassword);

        btnRegister = findViewById(R.id.registerButton);

        spinnerBloodGrp = findViewById(R.id.spinnerBloodGrp);

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

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strName = edt_name.getText().toString();
                String strContactNo = edt_number.getText().toString();
                String strEmail = edt_email.getText().toString();
                String strPassword = edt_password.getText().toString();


                if(strName.length()==0)
                {
                    edt_name.requestFocus();
                    edt_name.setError("Enter Alphabetical Characters Only");
                }
                else if(!strName.matches("[a-zA-Z ]+"))
                {
                    edt_name.requestFocus();
                    edt_name.setError("ENTER ONLY ALPHABETICAL CHARACTER");
                }
                else if(strContactNo.length()==0)
                {
                    edt_number.requestFocus();
                    edt_number.setError("FIELD CANNOT BE EMPTY");
                }
                else if(!strContactNo.matches("^[0-9]{10}$"))
                {
                    edt_number.requestFocus();
                    edt_number.setError("ENTER 10 DIGITS ONLY");
                }
                else if(strEmail.length()==0)
                {
                    edt_email.requestFocus();
                    edt_email.setError("FIELD CANNOT BE EMPTY");
                }
                else if(!strEmail.matches("^[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]{0,4}$"))
                {
                    edt_email.requestFocus();
                    edt_email.setError("ENTER A VALID EMAIL ADDRESS");
                }
                else if(strPassword.length()==0)
                {
                    edt_password.requestFocus();
                    edt_password.setError("FIELD CANNOT BE EMPTY");
                }
                else if(!strPassword.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"))
                {
                    edt_password.requestFocus();
                    edt_password.setError("PASSWORD MUST CONTAIN AT LEAST :\n ONE DIGIT, ONE LOWERCASE LETTER, ONE UPPERCASE LETTER,AND A SPECIAL CHARATER\nNO SPACE ALLOWED\nMINIMUM 8 CHARACTERS ALLOWED");
                }
                else{
                    //linkedList
                    Log.e("api calling","now");
                    Intent intent1=new Intent(DonorRegistrationActivity.this,DonorUpdateActivity.class);

                    list.insert(strName,strContactNo,strBloodGrpSelected,strEmail,strPassword);
                    list.show();
                    Log.e(String.valueOf(list),"list");

                    intent1.putExtra("linked_list", (Serializable) list);
                    startActivity(intent1);



                    addDonor(strName, strContactNo,strBloodGrpSelected, strEmail, strPassword);
                }
            }
        });





    }

    private void addDonor(String strName, String strContactNo,String strBloodGrpSelected, String strEmail, String strPassword) {
        Log.e("api calling","done"+strName+strContactNo+strBloodGrpSelected+strEmail+strPassword);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, util.DONOR_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Intent intent = new Intent(DonorRegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", String.valueOf(error));
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("userName", strName);
                hashMap.put("contactNo", strContactNo);
                hashMap.put("bloodType", strBloodGrpSelected);
                hashMap.put("email", strEmail);
                hashMap.put("password", strPassword);
                return hashMap;
            }
        };
        VolleySingleton.getInstance(DonorRegistrationActivity.this).addToRequestQueue(stringRequest);
    }

    private void commitToFile(String userName, String contactNo, String bloodType,String email,
                              String password) throws IOException {
        final String entryString = new String("userName=" + userName
                + ";contactNo=" + contactNo + ";bloodType="
                + bloodType + ";Email"+ email+ ";password"+password);
        FileOutputStream fOut = openFileOutput("savedData.txt",
                MODE_APPEND);
        OutputStreamWriter osw = new OutputStreamWriter(fOut);
        osw.write(entryString);
        osw.flush();
        osw.close();
    }
}
