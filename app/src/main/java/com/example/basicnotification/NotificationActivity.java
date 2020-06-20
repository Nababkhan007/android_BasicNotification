package com.example.basicnotification;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class NotificationActivity extends AppCompatActivity {
    private TextView notificationTv;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        initialization();

        setNotificationMessage();
    }

    private void setNotificationMessage() {
        message = getIntent().getStringExtra("message");
        notificationTv.setText(message);
    }

    private void initialization() {
        notificationTv = findViewById(R.id.notificationTvId);
    }
}