package com.example.project7;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ChatBot extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);
        getSupportActionBar().hide();
    }
}