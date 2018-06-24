package android.school_work.com.work.Helper;


import android.app.ActivityManager;
import android.content.Context;

public class ServiceInfo {
    public static boolean isMyServiceRunning(Class<?> serviceClass,Context _context) {
        ActivityManager manager = (ActivityManager) _context.getSystemService(Context.ACTIVITY_SERVICE);
        assert manager != null;
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
