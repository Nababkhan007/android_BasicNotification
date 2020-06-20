package com.example.basicnotification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "BasicNotification";
    private EditText messageEt;
    private Button sendNotificationBtn;
    private String message;
    private static int notificationId = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialization();

        createNotificationChannel();

        onClick();
    }

    private void onClick() {
        sendNotificationBtn.setOnClickListener(view -> {
            message = messageEt.getText().toString();

            if (!message.isEmpty()) {
                messageEt.setText("");
                sendNotification(message);

            } else {
                Toast.makeText(this, "Please write a message!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name_txt);
            String description = getString(R.string.channel_description_txt);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    private void sendNotification(String message) {
        Intent intent = new Intent(this, NotificationActivity.class);
        intent.putExtra("message", message);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap bitmapImage = BitmapFactory.decodeResource(getResources(), R.drawable.avoid_failure_image);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_baseline_message_24)
                .setContentTitle("Notification")
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message))
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(bitmapImage))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationId, builder.build());

    }

    private void initialization() {
        messageEt = findViewById(R.id.messageEtId);
        sendNotificationBtn = findViewById(R.id.sendNotificationBtnId);
    }
}