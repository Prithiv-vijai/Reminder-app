package com.example.dailyassist;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class FirstAddReminderActivity extends AppCompatActivity {

    private ImageView waterGlassImageView;
    private Button plusButton;
    private TextView countTextView;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_add_reminder);

        waterGlassImageView = findViewById(R.id.waterGlassImageView);
        plusButton = findViewById(R.id.plusButton);
        countTextView = findViewById(R.id.countTextView);

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                countTextView.setText(String.valueOf(count));
                // Update water glass image or perform other actions here
                updateWaterGlassImage();
            }
        });
    }

    private void updateWaterGlassImage() {
        // Update water glass image or perform other actions here
        if (count == 0) {
            // If no glasses consumed, display the empty glass image
            waterGlassImageView.setImageResource(R.drawable.a1);
        } else if (count <= 5) {
            // If count is less than or equal to maximum count, display the glass with water level proportional to count
            int drawableId;
            switch (count) {
                case 1:
                    drawableId = R.drawable.a1;
                    break;
                case 2:
                    drawableId = R.drawable.a2;
                    break;
                case 3:
                    drawableId = R.drawable.a3;
                    break;
                case 4:
                    drawableId = R.drawable.a4;
                    break;
                case 5:
                    drawableId = R.drawable.a5;
                    sendNotification();
                    break;
                default:
                    drawableId = R.drawable.a5; // Handle cases where count exceeds 5
                    break;
            }
            waterGlassImageView.setImageResource(drawableId);
        } else {
            // If count exceeds maximum count, display the full glass image
            waterGlassImageView.setImageResource(R.drawable.a5);
        }
    }

    private void sendNotification() {
        // Create a notification channel for devices running Android Oreo and higher
        createNotificationChannel();

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "default")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("Congratulations!")
                .setContentText("Drinking water challenge completed!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(0, builder.build());
    }

    // Create a notification channel for devices running Android Oreo and higher
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Daily Assist";
            String description = "Daily Assist Notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("default", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
