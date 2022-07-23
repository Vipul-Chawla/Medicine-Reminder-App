package com.example.project7;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecentTreatmentEdit extends AppCompatActivity {
    private Button add, done;
    private LinearLayout layout;

    private FirebaseAuth firebaseAuth;
    private String uid;
    private DatabaseReference root, db;

    private HashMap<String, HashMap<String, List<Integer>>> map = new HashMap<String, HashMap<String, List<Integer>>>();
    private int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_treatment_edit);
        getSupportActionBar().hide();

        firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getCurrentUser().getUid();
        root = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Recent Medicines");

        add = (Button) findViewById(R.id.add);
        done = (Button) findViewById(R.id.done);
        layout = (LinearLayout) findViewById(R.id.layout);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addView();
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeInput();
                DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Total Medicine in Recent");
                db.setValue(size);
                startActivity(new Intent(RecentTreatmentEdit.this, RecentTreatment.class));
                finish();
            }
        });
    }

    private void takeInput() {
        int i;
        for (i = 0;i < layout.getChildCount(); i++){
            View medicineView = layout.getChildAt(i);
            EditText illness, med;
            CheckBox breakfast_before, breakfast_after, lunch_before, lunch_after, dinner_before, dinner_after;

            illness = (EditText) medicineView.findViewById(R.id.illness);
            med = (EditText) medicineView.findViewById(R.id.med);
            breakfast_before = (CheckBox) medicineView.findViewById(R.id.breakfast_before);
            breakfast_after = (CheckBox) medicineView.findViewById(R.id.breakfast_after);
            lunch_before = (CheckBox) medicineView.findViewById(R.id.lunch_before);
            lunch_after = (CheckBox) medicineView.findViewById(R.id.lunch_after);
            dinner_before = (CheckBox) medicineView.findViewById(R.id.dinner_before);
            dinner_after = (CheckBox) medicineView.findViewById(R.id.dinner_after);

            int a, b, c;
            if (breakfast_before.isChecked() && breakfast_after.isChecked()){
                a = 2;
            }else if (breakfast_before.isChecked() && !breakfast_after.isChecked()){
                a = 0;
            }else{
                a = 1;
            }

            if (lunch_before.isChecked() && lunch_after.isChecked()){
                b = 2;
            }else if (lunch_before.isChecked() && !lunch_after.isChecked()){
                b = 0;
            }else{
                b = 1;
            }

            if (dinner_before.isChecked() && dinner_after.isChecked()){
                c = 2;
            }else if (dinner_before.isChecked() && !dinner_after.isChecked()){
                c = 0;
            }else{
                c = 1;
            }

            List<Integer> array = new ArrayList<Integer>();
            array.add(a);   array.add(b);   array.add(c);

            String medicine = med.getText().toString();
            String ill = illness.getText().toString();

            DatabaseReference databaseReference = root.child(String.valueOf(i));
            databaseReference.child("Illness").setValue(ill);
            databaseReference = root.child(String.valueOf(i)).child("Medicines");
            databaseReference.setValue(medicine);
            databaseReference = root.child(String.valueOf(i)).child("Timings");
            databaseReference.child("Breakfast").setValue(a);
            databaseReference.child("Lunch").setValue(b);
            databaseReference.child("Dinner").setValue(c);
        }
        size = i;
    }

    private void addView() {
        View medicineView = getLayoutInflater().inflate(R.layout.medicines, null, false);

        ImageView close;
        EditText illness, med;
        CheckBox breakfast_before, breakfast_after, lunch_before, lunch_after, dinner_before, dinner_after;

        illness = (EditText) medicineView.findViewById(R.id.illness);
        med = (EditText) medicineView.findViewById(R.id.med);
        breakfast_before = (CheckBox) medicineView.findViewById(R.id.breakfast_before);
        breakfast_after = (CheckBox) medicineView.findViewById(R.id.breakfast_after);
        lunch_before = (CheckBox) medicineView.findViewById(R.id.lunch_before);
        lunch_after = (CheckBox) medicineView.findViewById(R.id.lunch_after);
        dinner_before = (CheckBox) medicineView.findViewById(R.id.dinner_before);
        dinner_after = (CheckBox) medicineView.findViewById(R.id.dinner_after);
        close = (ImageView) medicineView.findViewById(R.id.close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = root;
                databaseReference.child(illness.getText().toString()).removeValue();
                layout.removeView(medicineView);
            }
        });

        layout.addView(medicineView);
    }
}