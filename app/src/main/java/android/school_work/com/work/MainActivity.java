package android.school_work.com.work;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.school_work.com.work.Fragment.DepartmentView;
import android.school_work.com.work.Fragment.SchoolDepartments;
import android.school_work.com.work.Helper.ServiceInfo;
import android.school_work.com.work.Helper.SessionManager;
import android.school_work.com.work.Model.SchoolDepartmentsModel;
import android.school_work.com.work.Services.SoundService;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements SchoolDepartments.SchoolDepartmentsListener, DepartmentView.DepartmentViewListener {

    private final static String TAG = MainActivity.class.getSimpleName();
    private SessionManager session;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new SessionManager(getApplicationContext());
        session.setIsBackground(false);
        replaceFragment(511,"List", new SchoolDepartmentsModel());
    }

    @SuppressLint("ResourceType")
    private void replaceFragment(int requestCode,String FRAGMENT_TAG, SchoolDepartmentsModel department) {
        Fragment f = new Fragment();

        switch (requestCode) {
            case 511:
                f = new SchoolDepartments();
                break;
            case 512:
                f = new DepartmentView();
                break;
        }

        f.setArguments(getIntent().getExtras());
        Bundle args = new Bundle();
        args.putParcelable("department", department);
        f.setArguments(args);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (requestCode == 511)
            ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        else
            ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);

        ft.addToBackStack(FRAGMENT_TAG);
        ft.replace(R.id.FragmentPlace, f,FRAGMENT_TAG);
        ft.commit();
    }

    @Override
    public void onSelection(SchoolDepartmentsModel row) {
        replaceFragment(512,"View", row);
    }

    @Override
    public void onBack() {
        replaceFragment(511,"List", new SchoolDepartmentsModel());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        session.setIsBackground(true);
    }
    @Override
    public void onPause() {
        super.onPause();
        session.setIsBackground(true);
    }
    @Override
    public void onResume() {
        super.onResume();
        session.setIsBackground(false);
        new Thread(new Runnable() {
            public void run() {
                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction(SoundService.SoundStage);
                broadcastIntent.putExtra("play", false);
                sendBroadcast(broadcastIntent);
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(MainActivity.this, SoundService.class);
        if (!ServiceInfo.isMyServiceRunning(SoundService.class, MainActivity.this)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent);
            } else {
                startService(intent);
            }
        }
    }


}
