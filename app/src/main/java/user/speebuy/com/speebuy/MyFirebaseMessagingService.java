package user.speebuy.com.speebuy;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by User on 15-Jul-18.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
        private static final String TAG = "FirebaseMessagingServce";

        @Override
        public void onMessageReceived(RemoteMessage remoteMessage) {

            startService(new Intent(this,MyFirebaseMessagingService.class));
            String notificationTitle = null, notificationBody = null;
            Log.d(TAG, "Message Notification Body: inside the service");
            // Check if message contains a notification payload.
            if (remoteMessage.getNotification() != null) {
                Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
                notificationTitle = remoteMessage.getNotification().getTitle();
                notificationBody = remoteMessage.getNotification().getBody();
            }

            // Also if you intend on generating your own notifications as a result of a received FCM
            // message, here is where that should be initiated. See sendNotification method below.
            sendNotification(notificationTitle, notificationBody);
        }

    @Override
    public void onTaskRemoved(Intent rootIntent)
    {
        startService(new Intent(this,MyFirebaseMessagingService.class));
        super.onTaskRemoved(rootIntent);
    }

    private void sendNotification(String notificationTitle, String notificationBody) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);
            int icn;
          //  if(notificationTitle.equals("START"))
            //    icn=R.drawable.parkstart;
           // else
           //     icn=R.mipmap.ic_launcher;
        //    Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        /*    NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                    .setAutoCancel(true)   //Automatically delete the notification
                    .setSmallIcon(icn) //Notification icon
                    .setContentIntent(pendingIntent)
                    .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),icn))
                    .setContentTitle(notificationTitle)
                    .setContentText(notificationBody)
                    .setSound(defaultSoundUri);


            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notificationBuilder.build());*/
        }

    @Override
    public void onCreate() {
        Log.d(TAG, "Entered inside the service");
            super.onCreate();
    }
}
