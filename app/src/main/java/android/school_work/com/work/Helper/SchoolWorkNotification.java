package android.school_work.com.work.Helper;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.school_work.com.work.MainActivity;
import android.school_work.com.work.R;
import android.support.v4.app.NotificationCompat;

import static android.media.RingtoneManager.*;


public class SchoolWorkNotification {

    private static final String NOTIFICATION_TAG = "SchoolWork";


    public static void notify(final Context context,
                              final String exampleString, final String Title, String channelId) {
        Intent MessageIntent = new Intent();
        MessageIntent.setClass(context, MainActivity.class);

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(Title)
                .setContentText(exampleString)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setTicker(Title)
                .setSound(getDefaultUri(TYPE_NOTIFICATION))
                .setContentIntent(
                        PendingIntent.getActivity(
                                context,
                                0,
                                MessageIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT))
                .setAutoCancel(true);

        notify(context, builder.build(), channelId);
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    private static void notify(final Context context, final Notification notification, String channelId) {
        NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("SchoolWork",
                    "SchoolWork Channel",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("The user-visible description of the channel.");
            channel.enableVibration(true);
            channel.enableLights(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            nm.createNotificationChannel(channel);

        }

        assert nm != null;
        nm.notify(NOTIFICATION_TAG, 0, notification);
    }

    /**
     * Cancels any notifications of this type previously shown using
     * .
     */
    public static void cancel(final Context context) {
        final NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancelAll();
    }
}
