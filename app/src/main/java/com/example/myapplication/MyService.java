package com.example.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyService extends FirebaseMessagingService {
    AppDb db;

    public MyService() {
        db = AppDb.getDb(this);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification() != null) {

            int messageId = Integer.parseInt(remoteMessage.getData().get("id"));
            String content = remoteMessage.getData().get("content");
            String created = remoteMessage.getData().get("created");
           // Boolean sent = Boolean.parseBoolean(remoteMessage.getData().get("sent"));
            String contactId = remoteMessage.getData().get("contactId");
            Message message = new Message(content, contactId);
            message.setCreated(created);
            message.setSent(false);
            db.messagesDao().insert(message);
            

            Message createdMessage = db.messagesDao().get(messageId);

            createNotificationChannel();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            notificationManagerCompat.notify(1, builder.build());
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", "My Channel", importance);
            channel.setDescription("Demo channel");
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}