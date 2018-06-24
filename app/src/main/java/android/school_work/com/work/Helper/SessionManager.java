package android.school_work.com.work.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class SessionManager {
    // Shared preferences file name
    private static final String PREF_NAME = "SchoolWorkSession";
    private static final String KEY_IS_VOLUME = "Volume";
    private static final String KEY_IS_BACKGROUND = "IsBackground";
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();
    // Shared Preferences
    SharedPreferences pref;
    Editor editor;
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;


    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public boolean getVolume() {
        return pref.getBoolean(KEY_IS_VOLUME, true);
    }
    public boolean getIsBackground() {
        return pref.getBoolean(KEY_IS_BACKGROUND, false);
    }

    public void setVolume(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_VOLUME, isLoggedIn);
        // commit changes
        editor.commit();
    }
    public void setIsBackground(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_BACKGROUND, isLoggedIn);
        // commit changes
        editor.commit();
    }
}
