package com.example.dailyassist;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class SecondAddReminderActivity extends AppCompatActivity {

    private CheckBox exerciseCheckBox, sleepCheckBox, readCheckBox, socializeCheckBox, journalingCheckBox;
    private Button refreshButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_add_reminder);

        // Initialize views
        exerciseCheckBox = findViewById(R.id.exerciseCheckBox);
        sleepCheckBox = findViewById(R.id.sleepCheckBox);
        readCheckBox = findViewById(R.id.readCheckBox);
        socializeCheckBox = findViewById(R.id.socializeCheckBox);
        journalingCheckBox = findViewById(R.id.journalingCheckBox);
        refreshButton = findViewById(R.id.refreshButton);

        // Set refresh button click listener
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Uncheck all checkboxes when refresh button is clicked
                exerciseCheckBox.setChecked(false);
                sleepCheckBox.setChecked(false);
                readCheckBox.setChecked(false);
                socializeCheckBox.setChecked(false);
                journalingCheckBox.setChecked(false);
            }
        });

        // Create notification channel
        createNotificationChannel();
    }

    // Method to handle checkbox click
    public void onCheckboxClicked(View view) {
        // Check if all checkboxes are checked
        if (exerciseCheckBox.isChecked() && sleepCheckBox.isChecked() && readCheckBox.isChecked()
                && socializeCheckBox.isChecked() && journalingCheckBox.isChecked()) {
            // If all checkboxes are checked, show notification
            showNotification("Daily Challenge Successful", "Congratulations! Daily challenge completed.");
        }
    }

    // Method to create notification channel
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Default Channel";
            String description = "Default Notification Channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("default", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    // Method to show notification
    private void showNotification(String title, String message) {
        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(123, builder.build());
    }
}
