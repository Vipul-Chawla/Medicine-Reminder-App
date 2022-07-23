package com.example.project7;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class BookAppoinment extends AppCompatActivity {
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appoinment);

        List<String> departmentList = new ArrayList<String>();
        departmentList.add("Ear, Nose and Throat");
        departmentList.add("Gynaecology");
        departmentList.add("Pulmonology");
        departmentList.add("Dermatology");
        departmentList.add("Digestion");
        departmentList.add("General Physician");
        departmentList.add("Pediatrics");
        departmentList.add("Orthopedic");
        departmentList.add("Neurology");
        departmentList.add("Cardiology");
        departmentList.add("Sexology");
        departmentList.add("Kidney and Urine");
        departmentList.add("Eye and Vision");
        departmentList.add("Diabetes and Endocrinology");
        departmentList.add("Dental");
        departmentList.add("Cancer");
        departmentList.add("Physiotherapy");
        departmentList.add("Psychological Counselling");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(BookAppoinment.this, R.layout.dept_list, R.id.dept_btn, departmentList);
        gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setAdapter(arrayAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent , View view , int position , long id) {
                String dept = departmentList.get(position);
                Intent intent = new Intent(BookAppoinment.this, DoctorList.class);
                intent.putExtra("Dept",dept);
                startActivity(intent);
            }
        });
    }
}