package com.example.dailyassist;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// Import statements

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Set up TextView for the settings title
        TextView textViewSettingsTitle = findViewById(R.id.textViewSettingsTitle);
        textViewSettingsTitle.setText("Settings");

        // Add code to handle settings options
    }
}
