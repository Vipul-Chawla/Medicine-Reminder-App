package com.example.project7;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class HomePage extends AppCompatActivity {
    private TextView brkfst, lun, dinn;
    private Button brkBtn, lunBtn, dinBtn, scan_btn, recent_btn, music_btn, yoga_btn, book_btn;

    private TimePicker myTimePicker;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private TimePickerDialog timePickerDialog;

    private FirebaseAuth firebaseAuth;
    private String uid;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        //getSupportActionBar().hide();

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        firebaseAuth = FirebaseAuth.getInstance();
        uid = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Time");
        
        createNotificationChannel();

        brkfst = (TextView) findViewById(R.id.brkfast);
        lun = (TextView) findViewById(R.id.lunch);
        dinn = (TextView) findViewById(R.id.dinner);
        brkBtn = (Button) findViewById(R.id.brk_btn);
        lunBtn = (Button) findViewById(R.id.lunch_btn);
        dinBtn = (Button) findViewById(R.id.din_btn);
        scan_btn = (Button) findViewById(R.id.scan_btn);
        recent_btn = (Button) findViewById(R.id.recent_med_btn);
        music_btn = (Button) findViewById(R.id.music_btn);
        yoga_btn = (Button) findViewById(R.id.yoga_btn);
        book_btn = (Button) findViewById(R.id.book);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.chat);

        fab.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this, ChatBot.class);
                intent.setData(null);
                startActivity(intent);
            }
        });

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("Breakfast")){
                    brkfst.setText(snapshot.child("Breakfast").getValue().toString());
                }
                if (snapshot.hasChild("Lunch")){
                    lun.setText(snapshot.child("Lunch").getValue().toString());
                }
                if (snapshot.hasChild("Dinner")){
                    dinn.setText(snapshot.child("Dinner").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        brkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePickerDialog(false, 1);
            }
        });

        lunBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePickerDialog(false, 2);
            }
        });

        dinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTimePickerDialog(false, 3);
            }
        });

        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, ScanActivity.class));
            }
        });

        recent_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, RecentTreatment.class));
            }
        });

        music_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, MusicPlayer.class));
            }
        });

        yoga_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, YogaActivity.class));
            }
        });

        book_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this, BookAppoinment.class));
            }
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "MedicineChannel";
            String desc = "Channel for Alarm for medicine";
            int imp = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("Medicine", name, imp);
            notificationChannel.setDescription(desc);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void openTimePickerDialog(boolean is24r, int index) {
        Calendar calendar = Calendar.getInstance();

        if (index == 1){
            timePickerDialog = new TimePickerDialog(HomePage.this,
                                                    onTimeSetListenerBreakFast, calendar.get(Calendar.HOUR_OF_DAY),
                                                    calendar.get(Calendar.MINUTE), is24r);
        }
        if (index == 2){
            timePickerDialog = new TimePickerDialog(HomePage.this,
                                                    onTimeSetListenerLunch, calendar.get(Calendar.HOUR_OF_DAY),
                                                    calendar.get(Calendar.MINUTE), is24r);
        }
        if (index == 3){
            timePickerDialog = new TimePickerDialog(HomePage.this,
                                                    onTimeSetListenerDinner, calendar.get(Calendar.HOUR_OF_DAY),
                                                    calendar.get(Calendar.MINUTE), is24r);
        }
        timePickerDialog.setTitle("Set Alarm Time");
        timePickerDialog.show();
    }

    TimePickerDialog.OnTimeSetListener onTimeSetListenerBreakFast = new TimePickerDialog.OnTimeSetListener() {

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();

            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);

            if (calSet.compareTo(calNow) <= 0) {
                // Today Set time passed, count to tomorrow
                calSet.add(Calendar.DATE, 1);
            }

            setAlarm(calSet, 1);
        }
    };

    TimePickerDialog.OnTimeSetListener onTimeSetListenerLunch = new TimePickerDialog.OnTimeSetListener() {

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();

            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);

            if (calSet.compareTo(calNow) <= 0) {
                // Today Set time passed, count to tomorrow
                calSet.add(Calendar.DATE, 1);
            }

            setAlarm(calSet, 2);
        }
    };

    TimePickerDialog.OnTimeSetListener onTimeSetListenerDinner = new TimePickerDialog.OnTimeSetListener() {

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();

            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);

            if (calSet.compareTo(calNow) <= 0) {
                // Today Set time passed, count to tomorrow
                calSet.add(Calendar.DATE, 1);
            }

            setAlarm(calSet, 3);
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setAlarm(Calendar targetCal, int index) {

        int hour = targetCal.get(Calendar.HOUR_OF_DAY);
        if (hour > 12)
            hour = hour - 12;
        int min = targetCal.get(Calendar.MINUTE);
        int aa = targetCal.get(Calendar.AM_PM);
        String time = String.valueOf(hour);
        if (min < 10){
            time = time + ":0" + String.valueOf(min);
        }else{
            time = time + ":" + String.valueOf(min);
        }
        if (aa == 0){
            time = time + "am";
        }else{
            time = time + "pm";
        }

        if (index == 1){
            brkfst.setText(time);
            databaseReference.child("Breakfast").setValue(time);
        }
        if (index == 2){
            lun.setText(time);
            databaseReference.child("Lunch").setValue(time);
        }
        if (index == 3){
            dinn.setText(time);
            databaseReference.child("Dinner").setValue(time);
        }

        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(HomePage.this, index, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home_page , menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.med_record:
                startActivity(new Intent(HomePage.this, MedHist.class));
                return true;
            case R.id.sign_out_menu:
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Time");
                ref.removeValue();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomePage.this, MainActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}