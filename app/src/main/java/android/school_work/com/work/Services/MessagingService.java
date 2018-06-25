package android.school_work.com.work.Services;

import android.school_work.com.work.Helper.AppConfig;
import android.school_work.com.work.Helper.SchoolWorkNotification;
import android.school_work.com.work.Helper.SessionManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessagingService extends FirebaseMessagingService {
    private static final String TAG = MessagingService.class.getSimpleName();

    public MessagingService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String body = remoteMessage.getNotification().getBody();
        if (!body.equals("")) {
            SessionManager session = new SessionManager(getApplicationContext());
            if (!session.getIsBackground())
                SchoolWorkNotification.notify(MessagingService.this, body, "School Work", AppConfig.CHANNELID);

        }

    }
}
