package com.example.project7;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class zoom extends AppCompatActivity {

    ImageView imageView;
    TextView engName;
    TextView sanName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom);
        imageView = findViewById(R.id.zoomimage);
        engName = findViewById(R.id.engname);
        sanName = findViewById(R.id.sanskritname);
        Intent intent = getIntent();
        engName.setText("English Name : "+intent.getStringExtra("engName"));
        sanName.setText("Sanskrit Name : "+intent.getStringExtra("sanName"));
        String link = intent.getStringExtra("link");
        if(link != null)
            Glide.with(this).load(link).into(imageView);
        else
            imageView.setImageResource(R.drawable.noimage);

    }
}