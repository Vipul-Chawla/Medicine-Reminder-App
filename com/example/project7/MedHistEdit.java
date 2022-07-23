package com.example.project7;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MedHistEdit extends AppCompatActivity {
    private EditText bloodType, majorIllness, majorSurgery, illness, injuries, allergy, geneticIllness;
    private Button doneBtn, majorIllnessBtn, majorSurgeryBtn, illnessBtn, injuriesBtn, allergyBtn, geneticIllnessBtn;
    private String bloodTypeText, majorIllnessText, majorSurgeryText, illnessText, injuriesText, allergyText, geneticIllnessText;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_hist_edit);
        getSupportActionBar().hide();

        bloodTypeText = "";
        majorIllnessText = "";
        majorSurgeryText = "";
        illnessText = "";
        injuriesText = "";
        allergyText = "";
        geneticIllnessText = "";

        firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Medical Card");

        bloodType = (EditText) findViewById(R.id.blood_type);
        majorIllness = (EditText) findViewById(R.id.major_illness);
        majorSurgery = (EditText) findViewById(R.id.major_surgery);
        illness = (EditText) findViewById(R.id.illness);
        injuries = (EditText) findViewById(R.id.injuries);
        allergy = (EditText) findViewById(R.id.allergy);
        geneticIllness = (EditText) findViewById(R.id.gen_med);

        Boolean flag = false;
        majorIllnessBtn = (Button) findViewById(R.id.major_illness_btn);
        majorIllnessBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                majorIllness.setVisibility(View.VISIBLE);
            }
        });

        majorSurgeryBtn = (Button) findViewById(R.id.major_surgery_btn);
        majorSurgeryBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                majorSurgery.setVisibility(View.VISIBLE);
            }
        });

        illnessBtn = (Button) findViewById(R.id.illness_btn);
        illnessBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                illness.setVisibility(View.VISIBLE);
            }
        });

        injuriesBtn = (Button) findViewById(R.id.injuries_btn);
        injuriesBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                injuries.setVisibility(View.VISIBLE);
            }
        });

        allergyBtn = (Button) findViewById(R.id.allergy_btn);
        allergyBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                allergy.setVisibility(View.VISIBLE);
            }
        });

        geneticIllnessBtn = (Button) findViewById(R.id.gen_ill_btn);
        geneticIllnessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                geneticIllness.setVisibility(View.VISIBLE);
            }
        });

        doneBtn = (Button) findViewById(R.id.done);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bloodTypeText = bloodType.getText().toString();
                majorIllnessText = majorIllness.getText().toString();
                majorSurgeryText = majorSurgery.getText().toString();
                illnessText = illness.getText().toString();
                injuriesText = injuries.getText().toString();
                allergyText = allergy.getText().toString();
                geneticIllnessText = geneticIllness.getText().toString();
                UserDetails userDetails = new UserDetails(bloodTypeText, majorIllnessText, majorSurgeryText, illnessText, injuriesText, allergyText, geneticIllnessText);
                databaseReference.setValue(userDetails)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                startActivity(new Intent(MedHistEdit.this, MedHist.class));
                                finish();
                            }
                        });
            }
        });
    }
}