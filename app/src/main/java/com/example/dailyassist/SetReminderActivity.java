package com.example.dailyassist;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SetReminderActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextDate, editTextTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_reminder);

        // Initialize EditText fields
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDate = findViewById(R.id.editTextDate);
        editTextTime = findViewById(R.id.editTextTime);

        // Set up button click listener for setting reminder
        Button buttonSetReminder = findViewById(R.id.buttonSave);
        buttonSetReminder.setOnClickListener(view -> setReminder());
    }

    private void setReminder() {
        String title = editTextTitle.getText().toString().trim();
        String date = editTextDate.getText().toString().trim();
        String time = editTextTime.getText().toString().trim();

        if (title.isEmpty() || date.isEmpty() || time.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Parse date and time
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            Date reminderDateTime = dateFormat.parse(date + " " + time);
            long reminderMillis = reminderDateTime.getTime();

            // Set up the AlarmManager to trigger a notification
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, ReminderBroadcastReceiver.class);
            intent.putExtra("title", title); // Pass the title to the broadcast receiver
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP, reminderMillis, pendingIntent);

            // Show confirmation message
            Toast.makeText(this, "Reminder set successfully", Toast.LENGTH_SHORT).show();

            // Optionally, you can finish() the activity here to return to the previous screen
            // finish();
        } catch (ParseException e) {
            Toast.makeText(this, "Error setting reminder", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
