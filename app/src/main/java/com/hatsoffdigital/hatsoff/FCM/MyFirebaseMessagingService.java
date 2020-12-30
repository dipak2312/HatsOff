package com.hatsoffdigital.hatsoff.FCM;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.hatsoffdigital.hatsoff.Activity.Notification.NotificationActivity;
import com.hatsoffdigital.hatsoff.R;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.core.app.NotificationCompat;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    NotificationChannel mChannel;
    Bitmap bitmap;
    Intent intent;
    String img_url;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);


        if (remoteMessage.getData().size() > 0) {
            img_url = remoteMessage.getData().get("icon");

            if (!img_url.equals("")) {
                getBitmapfromUrl(img_url);
            }

            createNotification(remoteMessage, bitmap);
        }

   }


    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }


    private void createNotification(RemoteMessage remoteMessage, Bitmap bitmap) {



        if (bitmap != null) {
            int notifyID = 1;
            String CHANNEL_ID = "my_channel_01";// The id of the channel.
            String name = "HatsOff";// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            }

            intent = new Intent(this, NotificationActivity.class);
            intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("imageString", img_url);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            PendingIntent resultIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.not)
                    .setContentTitle(remoteMessage.getData().get("title"))
                    .setContentText(remoteMessage.getData().get("body"))
                    //.setContentTitle(remoteMessage.getNotification().getTitle())
                    //.setContentText(remoteMessage.getNotification().getBody())
                    .setChannelId(CHANNEL_ID)
                    .setStyle(new NotificationCompat.BigPictureStyle()
                    .bigPicture(bitmap))/*Notification with Image*/
                    .setColor(getResources().getColor(R.color.HOBlue))
                    .setSound(defaultSoundUri)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage.getData().get("body")))
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setAutoCancel(true)
                    .setContentIntent(resultIntent);


            NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(mChannel);
            }

            notificationManager.notify(0, mNotificationBuilder.build());


        } else {
            int notifyID = 1;
            String CHANNEL_ID = "my_channel_01";// The id of the channel.
            String name = "HatsOff";// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            }

            intent = new Intent(this, NotificationActivity.class);
            intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
            //intent.putExtra("imageString", img_url);

            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            PendingIntent resultIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.not)
                    .setContentTitle(remoteMessage.getData().get("title"))
                    .setContentText(remoteMessage.getData().get("body"))
                    //.setContentTitle(remoteMessage.getNotification().getTitle())
                    //.setContentText(remoteMessage.getNotification().getBody())
                    .setChannelId(CHANNEL_ID)
                    .setSound(defaultSoundUri)
                    .setColor(getResources().getColor(R.color.HOBlue))
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setAutoCancel(true)
                    .setContentIntent(resultIntent);


            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(mChannel);
            }

            notificationManager.notify(0, mNotificationBuilder.build());

        }
    }




}
