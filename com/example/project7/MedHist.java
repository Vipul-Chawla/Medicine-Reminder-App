package com.example.project7;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MedHist extends AppCompatActivity {
    private TextView name, age, gender, bloodType, majorIllness, majorSurgery, majorInjuries, allergy, geneticDisease;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference root, ref;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_hist);

        firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getCurrentUser().getUid();
        root = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        //Toast.makeText(MedHist.this, uid, Toast.LENGTH_LONG).show();

        name = (TextView) findViewById(R.id.name);
        age = (TextView) findViewById(R.id.age);
        gender = (TextView) findViewById(R.id.gender);
        bloodType = (TextView) findViewById(R.id.blood_type);
        majorIllness = (TextView) findViewById(R.id.major_illness);
        majorSurgery = (TextView) findViewById(R.id.major_surgery);
        majorInjuries = (TextView) findViewById(R.id.major_injuries);
        allergy = (TextView) findViewById(R.id.allergy);
        geneticDisease = (TextView) findViewById(R.id.genetic_illness);

        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("name").exists()){
                    String temp = snapshot.child("name").getValue().toString();
                    name.setText(temp);
                    //Toast.makeText(MedHist.this, temp, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        root.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("age").exists()){
                    String temp = snapshot.child("age").getValue().toString();
                    age.setText(temp);
                    //Toast.makeText(MedHist.this, temp, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child("gender").exists()){
                    String temp = snapshot.child("gender").getValue().toString();
                    gender.setText(temp);
                    //Toast.makeText(MedHist.this, temp, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        root.child("Medical Card").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("bloodType")){
                    String temp = snapshot.child("bloodType").getValue().toString();
                    bloodType.setText(temp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        root.child("Medical Card").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("majorIllness")){
                    String temp = snapshot.child("majorIllness").getValue().toString();
                    majorIllness.setText(temp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        root.child("Medical Card").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("surgery")){
                    String temp = snapshot.child("surgery").getValue().toString();
                    majorSurgery.setText(temp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        root.child("Medical Card").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("injuries")){
                    String temp = snapshot.child("injuries").getValue().toString();
                    majorInjuries.setText(temp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        root.child("Medical Card").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("allergy")){
                    String temp = snapshot.child("allergy").getValue().toString();
                    allergy.setText(temp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        root.child("Medical Card").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("geneticDisease")){
                    String temp = snapshot.child("geneticDisease").getValue().toString();
                    geneticDisease.setText(temp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.edit:
                Intent intent = new Intent(MedHist.this, MedHistEdit.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}