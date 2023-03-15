package riswan.fkti.pipa.firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.Objects;

import riswan.fkti.pipa.R;

public class FirebaseMessage extends FirebaseMessagingService {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        NotificationManager notificationManager = (NotificationManager) getSystemService ( Context.NOTIFICATION_SERVICE );
        if (!Objects.equals ( null, message.getNotification () )) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel = new NotificationChannel ( "default", "riswan.fkti.pipa", NotificationManager.IMPORTANCE_HIGH );
                notificationManager.createNotificationChannel ( notificationChannel );
            }
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder ( this, "default" );
            notificationBuilder.setAutoCancel ( true )
                    .setStyle ( new NotificationCompat.BigTextStyle ().bigText ( message.getNotification ().getBody () ) )
                    .setDefaults ( Notification.DEFAULT_ALL )
                    .setWhen ( System.currentTimeMillis () )
                    .setSmallIcon ( R.drawable.logo )
                    .setTicker ( message.getNotification ().getTitle () )
                    .setPriority ( Notification.PRIORITY_MAX )
                    .setContentTitle ( message.getNotification ().getTitle () )
                    .setContentText ( message.getNotification ().getBody () );
            notificationManager.notify ( 1, notificationBuilder.build () );
        }
    }
}
