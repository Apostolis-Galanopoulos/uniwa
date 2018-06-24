package android.school_work.com.work.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.school_work.com.work.Helper.SessionManager;
import android.school_work.com.work.Model.SchoolDepartmentsModel;
import android.school_work.com.work.R;
import android.school_work.com.work.Services.SoundService;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class DepartmentView extends Fragment {

    private static final String TAG = DepartmentView.class.getSimpleName();

    private Activity mContext;
    private SchoolDepartmentsModel department;
    private DepartmentViewListener mListener;


    public static DepartmentView newInstance(SchoolDepartmentsModel department) {
        DepartmentView f = new DepartmentView();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putParcelable("department", department);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Activity c) {
        mContext = c;
        mListener = (DepartmentView.DepartmentViewListener) c;
        super.onAttach(c);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.department_view, container, false);
        department = getArguments().getParcelable("department");
        final SessionManager session = new SessionManager(getActivity().getApplicationContext());

        final FloatingActionButton myFab = view.findViewById(R.id.sound);
        TextView description = view.findViewById(R.id.description);
        TextView purpose = view.findViewById(R.id.purpose);
        TextView location = view.findViewById(R.id.location);
        TextView phone = view.findViewById(R.id.phone);
        ImageView photoUrl = view.findViewById(R.id.photoUrl);
        CollapsingToolbarLayout collapsingToolbarLayout = view.findViewById(R.id.toolbar_layout);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        description.setText(department.getDescription());
        purpose.setText(department.getPurpose());
        location.setText(department.getLocation());
        phone.setText(department.getPhoneSec());

        load(department.getPhotoUrl(), photoUrl);


        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        collapsingToolbarLayout.setTitle(department.getName());


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    sound(false);
                    mListener.onBack();
                }
            }
        });


        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (session.getVolume()) {
                    sound(false);
                    myFab.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_action_volume_off));
                    session.setVolume(false);
                } else {
                    sound(true);
                    myFab.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_action_volume_up));
                    session.setVolume(true);
                }
            }
        });

        if (session.getVolume()) {
            sound(true);
            myFab.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_action_volume_up));
        } else
            myFab.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_action_volume_off));

        return view;
    }

    public void load(String url, ImageView image_View) {

        Glide.with(mContext).load(url)
                .thumbnail(1f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(image_View);

    }

    private void sound(final boolean state) {
        new Thread(new Runnable() {
            public void run() {
                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction(SoundService.SoundStage);
                broadcastIntent.putExtra("play", state);
                if (getActivity() != null) getActivity().sendBroadcast(broadcastIntent);
            }
        }).start();
    }

    public interface DepartmentViewListener {
        void onBack();
    }
}
