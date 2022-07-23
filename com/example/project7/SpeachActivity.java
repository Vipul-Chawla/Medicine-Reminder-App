package com.example.project7;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class SpeachActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{

    TextToSpeech textToSpeech;
    ImageView imageView;
    String input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speach);
        getSupportActionBar().hide();

        imageView = findViewById(R.id.mainSpeaker);
        Intent intent = getIntent();
        input = intent.getStringExtra("Speach");
        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();

        textToSpeech = new TextToSpeech(this, this);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }

        });
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS){
            int result = textToSpeech.setLanguage(Locale.ENGLISH);
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS", "Language not supported");
            }else{
                imageView.setEnabled(true);
                speak();
            }
        }else{
            Log.e("TextToSpeech", "Initialization Failed");
        }
    }
    private void speak(){
        textToSpeech.speak(input, TextToSpeech.QUEUE_FLUSH, null);
    }
}