package com.example.project7;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RecentTreatment extends AppCompatActivity {
    private LinearLayout layout;

    private FirebaseAuth firebaseAuth;
    private String uid;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_treatment);

        firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

        layout = (LinearLayout) findViewById(R.id.layout);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("Total Medicine in Recent")){
                    String temp = snapshot.child("Total Medicine in Recent").getValue().toString();
                    addView(temp);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void addView(String temp) {
        Integer size = Integer.valueOf(temp);

        //Toast.makeText(RecentTreatment.this, String.valueOf(size), Toast.LENGTH_SHORT).show();
        DatabaseReference db = databaseReference.child("Recent Medicines");

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot children : snapshot.getChildren()){
                    String ill = children.child("Illness").getValue().toString();
                    String med = children.child("Medicines").getValue().toString();
                    String a, b, c;
                    a = children.child("Timings").child("Breakfast").getValue().toString();
                    b = children.child("Timings").child("Lunch").getValue().toString();
                    c = children.child("Timings").child("Dinner").getValue().toString();

                    View medicineView = getLayoutInflater().inflate(R.layout.medicines, null, false);
                    EditText illness, medi;
                    CheckBox breakfast_before, breakfast_after, lunch_before, lunch_after, dinner_before, dinner_after;

                    illness = (EditText) medicineView.findViewById(R.id.illness);
                    medi = (EditText) medicineView.findViewById(R.id.med);
                    breakfast_before = (CheckBox) medicineView.findViewById(R.id.breakfast_before);
                    breakfast_after = (CheckBox) medicineView.findViewById(R.id.breakfast_after);
                    lunch_before = (CheckBox) medicineView.findViewById(R.id.lunch_before);
                    lunch_after = (CheckBox) medicineView.findViewById(R.id.lunch_after);
                    dinner_before = (CheckBox) medicineView.findViewById(R.id.dinner_before);
                    dinner_after = (CheckBox) medicineView.findViewById(R.id.dinner_after);

                    illness.setText(ill);
                    medi.setText(med);
                    if (Integer.valueOf(a) == 2){
                        breakfast_before.setChecked(true);
                        breakfast_after.setChecked(true);
                    }else if (Integer.valueOf(a) == 1){
                        breakfast_after.setChecked(true);
                    }else{
                        breakfast_before.setChecked(true);
                    }
                    if (Integer.valueOf(b) == 2){
                        lunch_before.setChecked(true);
                        lunch_after.setChecked(true);
                    }else if (Integer.valueOf(b) == 1){
                        lunch_after.setChecked(true);
                    }else{
                        lunch_before.setChecked(true);
                    }
                    if (Integer.valueOf(c) == 2){
                        dinner_before.setChecked(true);
                        dinner_after.setChecked(true);
                    }else if (Integer.valueOf(c) == 1){
                        dinner_after.setChecked(true);
                    }else{
                        dinner_before.setChecked(true);
                    }

                    layout.addView(medicineView);
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
                Intent intent = new Intent(RecentTreatment.this, RecentTreatmentEdit.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}