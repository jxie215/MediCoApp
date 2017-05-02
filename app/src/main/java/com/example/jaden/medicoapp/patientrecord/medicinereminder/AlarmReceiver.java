package com.example.jaden.medicoapp.patientrecord.medicinereminder;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.example.jaden.medicoapp.Constants;
import com.example.jaden.medicoapp.LoginActivity;
import com.example.jaden.medicoapp.R;

public class AlarmReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        //this will update the UI with message
        Bundle bundle = intent.getExtras().getBundle(Constants.ALARM_BUNDLE);

        Intent myIntent = new Intent(context,AlarmNotification.class);
        myIntent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        myIntent.putExtra(Constants.ALARM_BUNDLE, bundle);
        context.startActivity(myIntent);
    }
    class AlarmService extends IntentService {
        private NotificationManager alarmNotificationManager;

        public AlarmService() {
            super("AlarmService");
        }

        @Override
        public void onHandleIntent(Intent intent) {
            sendNotification("Wake Up! Wake Up!");
        }

        private void sendNotification(String msg) {
            Log.d("AlarmService", "Preparing to send notification...: " + msg);
            alarmNotificationManager = (NotificationManager) this
                    .getSystemService(Context.NOTIFICATION_SERVICE);

            PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                    new Intent(this, LoginActivity.class), 0);

            NotificationCompat.Builder alamNotificationBuilder = new NotificationCompat.Builder(
                    this).setContentTitle("Alarm").setSmallIcon(R.drawable.pill_baw)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                    .setContentText(msg);


            alamNotificationBuilder.setContentIntent(contentIntent);
            alarmNotificationManager.notify(1, alamNotificationBuilder.build());
            Log.d("AlarmService", "Notification sent.");
        }
    }
}