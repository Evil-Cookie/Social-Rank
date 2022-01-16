package com.nikita.mozhaev.socialrank.system;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.nikita.mozhaev.socialrank.R;

public class NotificationService extends FirebaseMessagingService {

    public static final String TAG = "TestFbseMsgngSvc";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            String val1 = remoteMessage.getData().get("val1");
            String val2 = remoteMessage.getData().get("val2");
            String val3 = remoteMessage.getData().get("val3");
            int color = (1 << 16) | (1 << 8) | (0);
            ShowNotification(val1, val2, color);
        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }


    @Override
    public void onDeletedMessages() {


    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    void ShowNotification(String title, String text, int color) {
        NotificationCompat.Builder mNotify = new NotificationCompat.Builder(getApplicationContext(), "");
        mNotify.setLights(color, 100, 200);
        mNotify.setSmallIcon(
                R.drawable.ic_noti);
        mNotify.setContentTitle(title);
        mNotify.setContentText(text);
        mNotify.setDefaults(android.app.Notification.DEFAULT_SOUND);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        int mId = 1001;
        try {
            mNotificationManager.notify(mId, mNotify.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
