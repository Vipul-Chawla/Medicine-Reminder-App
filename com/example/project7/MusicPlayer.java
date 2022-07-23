package com.example.project7;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MusicPlayer extends AppCompatActivity {
    RecyclerView recyclerView;
    showadapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        recyclerView = findViewById(R.id.recyclerview);
        myadapter = new showadapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(MusicPlayer.this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(myadapter);
    }
}