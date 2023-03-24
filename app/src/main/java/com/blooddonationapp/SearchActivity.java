package com.blooddonationapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {

    String strBloodGroups[] = {"Select your Blood Group", "A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
    Spinner spinnerBloodGrp;
    String strBloodGrpSelected;

    Button btnSearch;

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

        //search in text file and display result



    }
}