package com.blooddonationapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

import utils.VolleySingleton;
import utils.util;

public class DonorUpdateActivity extends AppCompatActivity {

    TextInputEditText edt_name, edt_number, edt_email, edt_password;
    Button btnUpdate,btnDelete;

    String strBloodGroups[] = {"Select your Blood Group", "A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
    Spinner spinnerBloodGrp;
    String strBloodGrpSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_update);

        Intent i = getIntent();
        edt_name = findViewById(R.id.inputName);
        edt_number = findViewById(R.id.inputNumber);
        edt_email = findViewById(R.id.inputEmail);
        edt_password = findViewById(R.id.inputPassword);

        btnUpdate = findViewById(R.id.updateButton);
        btnDelete=findViewById(R.id.deleteButton);

        spinnerBloodGrp = findViewById(R.id.spinnerBloodGrp);

        String strDonorId=i.getStringExtra("DONOR_ID");
        String strUserName=i.getStringExtra("USERNAME");
        String strContactNo=i.getStringExtra("CONTACT_NO");
        String strBloodType=i.getStringExtra("BLOOD_TYPE");
        String strEmail=i.getStringExtra("EMAIL");
        String strPassword=i.getStringExtra("PASSWORD");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strBloodGroups);
        int position = adapter.getPosition(strBloodType);
        spinnerBloodGrp.post(new Runnable() {
            @Override
            public void run() {
                spinnerBloodGrp.setSelection(position);
            }
        });


        DonorLangModel donorLangModel=new DonorLangModel();
        edt_name.setText(strUserName);
        edt_number.setText(strContactNo);
        edt_email.setText(strEmail);
        edt_password.setText(strPassword);



        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAPI(strDonorId);
            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strUserName=edt_name.getText().toString();
                String strContactNo=edt_number.getText().toString();
                String strEmail=edt_email.getText().toString();
                String strPassword=edt_password.getText().toString();

                if(strUserName.length()==0)
                {
                    edt_name.requestFocus();
                    edt_name.setError("Enter Alphabetical Characters Only");
                }
                else if(!strUserName.matches("[a-zA-Z ]+"))
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
                else {
                    Toast.makeText(DonorUpdateActivity.this, "Validation Successful", Toast.LENGTH_LONG).show();
                    UpdateApiCall(strDonorId, strUserName, strContactNo, strBloodType, strEmail, strPassword);
                }
            }
        });
    }

    private void UpdateApiCall(String strDonorId, String strUserName, String strContactNo, String strBloodType, String strEmail, String strPassword) {
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, util.DONOR_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("api calling done", response);
                Log.e("id in api: ",strDonorId);
                Intent intent = new Intent(DonorUpdateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("donorId", strDonorId);
                hashMap.put("userName",strUserName);
                hashMap.put("contactNo", strContactNo);
                hashMap.put("bloodType", strBloodType);
                hashMap.put("email", strEmail);
                hashMap.put("password",strPassword);
                return hashMap;


            }
        };
        VolleySingleton.getInstance(DonorUpdateActivity.this).addToRequestQueue(stringRequest);




    }

    private void deleteAPI(String strDonorId) {

        Log.e("TAG****", "deleteAPI Update "+strDonorId);
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, util.DONOR_URL, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                Log.e("api calling done", response);
                Intent intent = new Intent(DonorUpdateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> hashMap = new HashMap<>();
                hashMap.put("donorId", strDonorId);
                return hashMap;


            }
        };
        VolleySingleton.getInstance(DonorUpdateActivity.this).addToRequestQueue(stringRequest);



    }

}
