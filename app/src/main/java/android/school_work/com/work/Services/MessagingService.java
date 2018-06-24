package android.school_work.com.work.Services;

import android.school_work.com.work.Helper.SchoolWorkNotification;
import android.school_work.com.work.Helper.SessionManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MessagingService extends FirebaseMessagingService {
    private static final String TAG = MessagingService.class.getSimpleName();

    public MessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "getData().size(): " + remoteMessage.getData().size()); // for the data size
        Log.d(TAG, "getData: " + remoteMessage.getData());
        Log.d(TAG, "getBody: " + remoteMessage.getNotification().getBody());
        Log.d(TAG, "getTitle: " + remoteMessage.getNotification().getTitle());


        String body = remoteMessage.getNotification().getBody();
        if (!body.equals("")) {
            SessionManager session = new SessionManager(getApplicationContext());
            Log.d(TAG, "session.getIsBackground: " + !session.getIsBackground());

            Log.d(TAG, "body: " + body);
            if (!session.getIsBackground())
                SchoolWorkNotification.notify(MessagingService.this, body, body, body);

        }

    }
}
