package com.example.project7;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAcc extends AppCompatActivity {

    private EditText username, email, password, phone, age;
    private Spinner genderSpinner;
    private Button register;
    private String gender = "";

    private FirebaseAuth firebaseAuth;
    private DatabaseReference root, databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acc);
        getSupportActionBar().hide();

        firebaseAuth = FirebaseAuth.getInstance();
        root = FirebaseDatabase.getInstance().getReference().child("Users");

        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        phone = (EditText) findViewById(R.id.phone);
        age = (EditText) findViewById(R.id.age);
        genderSpinner = (Spinner) findViewById(R.id.gender);
        register = (Button) findViewById(R.id.reg_btn);
        setSpinner();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(CreateAcc.this, username.getText().toString() + " " + emailText + " " + passwordText + " " + phoneText + " " + ageText + " " + gender, Toast.LENGTH_LONG).show();
                if (TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(password.getText().toString()) || TextUtils.isEmpty(phone.getText().toString()) || TextUtils.isEmpty(age.getText().toString()) || gender.equals("Unknown")){
                    Toast.makeText(CreateAcc.this, "Enter credentials", Toast.LENGTH_SHORT).show();
                }else{
                    UserDetails userDetails = new UserDetails(email.getText().toString(), username.getText().toString(), phone.getText().toString(), gender, age.getText().toString());
                    firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(CreateAcc.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                        FirebaseUser user = firebaseAuth.getCurrentUser();
                                        String uid = user.getUid();
                                        databaseReference = root.child(uid);
                                        databaseReference.setValue(userDetails)
                                                .addOnCompleteListener(new OnCompleteListener<Void>(){
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Intent intent = new Intent(CreateAcc.this, HomePage.class);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                });
                                    }
                                }
                            });
                }
            }
        });
    }

    private void setSpinner(){
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(CreateAcc.this, R.array.gender, android.R.layout.simple_spinner_item);
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        genderSpinner.setAdapter(genderSpinnerAdapter);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent , View view , int position , long id) {
                String selection = (String) parent.getItemAtPosition(position);

                switch (selection){
                    case "Female":
                        gender = "Female";
                        break;
                    case "Male":
                        gender = "Male";
                        break;
                    case "Transgender":
                        gender = "Transgender";
                        break;
                    default:
                        gender = "Unknown";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                gender = "Unknown";
            }
        });
    }
}