package android.school_work.com.work.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.school_work.com.work.R;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


public class SoundService extends Service {

    public static final String SoundStage = "SoundStage";
    public static final String StopSound = "StopSound";
    private final IBinder mBinder = new LocalBinder();
    private String TAG = SoundService.class.getName();
    private MediaPlayer mp;


    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, final Intent intent) {
            if (intent.getAction().equals(SoundStage)) {

                boolean play = intent.getBooleanExtra("play", false);
                Log.i(TAG, "SoundStage " + String.valueOf(play));
                if (play)
                    mp.start();
                else
                    if(mp.isPlaying()) mp.pause();
            }
        }
    };

    public SoundService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(SoundStage);
        mIntentFilter.addAction(StopSound);
        Log.i(TAG, "onCreate ");
        mp = MediaPlayer.create(this, R.raw.original_graduation_song);
        mp.setLooping(false);

        registerReceiver(mReceiver, mIntentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        notificationChannel();
        Log.i(TAG, "onStartCommand ");
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind ");
        return mBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy ");
        unregisterReceiver(mReceiver);
    }

    public void notificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            NotificationManager nm = (NotificationManager) this
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel("SchoolWork",
                    "SchoolWork Channel",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("The user-visible description of the channel.");
            channel.enableVibration(false);
            channel.enableLights(false);
            channel.setVibrationPattern(null);
            channel.setSound(null, null);

            channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            nm.createNotificationChannel(channel);

            Notification.Builder builder = new Notification.Builder(this, "SchoolWork")
                    .setPriority(Notification.PRIORITY_DEFAULT)
                    .setAutoCancel(true);
            Notification notification = builder.build();
            startForeground(50, notification);
            stopForeground(true);

        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);
            Notification notification = builder.build();
            startForeground(1, notification);
        }
    }

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {

        public SoundService getService() {
            // Return this instance of SoundService so clients can call public methods
            return SoundService.this;
        }
    }
}
