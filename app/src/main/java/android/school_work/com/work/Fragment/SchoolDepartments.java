package android.school_work.com.work.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.school_work.com.work.Adapter.DepartmentsAdapter;
import android.school_work.com.work.Helper.CustomRVItemTouchListener;
import android.school_work.com.work.Model.SchoolDepartmentsModel;
import android.school_work.com.work.R;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class SchoolDepartments extends Fragment {

    private static final String TAG = SchoolDepartments.class.getSimpleName();

    private Activity mContext;
    private RecyclerView schoolDepartmentsList;
    private DepartmentsAdapter adapter;
    private SchoolDepartmentsListener mListener;
    private SwipeRefreshLayout swipe_refresh;
    private View something_going_wrong;
    private RelativeLayout retry;
    private long mLastClickTime = 0;
    private ArrayList<SchoolDepartmentsModel> departmentsList = new ArrayList<>();

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == retry) getsSchoolDepartments();
        }
    };


    public static SchoolDepartments newInstance() {
        SchoolDepartments f = new SchoolDepartments();
        // Supply num input as an argument.
        Bundle args = new Bundle();
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
        mListener = (SchoolDepartments.SchoolDepartmentsListener) c;
        super.onAttach(c);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.school_departments, container, false);

        retry = view.findViewById(R.id.retry);
        something_going_wrong = view.findViewById(R.id.something_going_wrong);
        schoolDepartmentsList = view.findViewById(R.id.schoolDepartmentsList);
        swipe_refresh = view.findViewById(R.id.swipe_refresh);


        schoolDepartmentsList.addOnItemTouchListener(new CustomRVItemTouchListener(getContext(), schoolDepartmentsList, new DepartmentsAdapter.RecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.i(TAG, " onClick " + position);
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                SchoolDepartmentsModel item = departmentsList.get(position);
                if (mListener != null) mListener.onSelection(item);
            }

            @Override
            public void onLongClick(View view, int position) {
                Log.i(TAG, " onLongClick " + position);

            }
        }));
        swipe_refresh.setRefreshing(true);
        getsSchoolDepartments();
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (departmentsList.size() > 0) {
                    departmentsList.clear();
                    adapter.clearAll();
                }
                getsSchoolDepartments();
            }
        });

        retry.setOnClickListener(clickListener);
        return view;
    }

    private void getsSchoolDepartments() {

        SchoolDepartmentsModel.getSchoolDepartments(new SchoolDepartmentsModel.getProviderData() {
            @Override
            public void onSuccess(ArrayList<SchoolDepartmentsModel> result) {
                something_going_wrong.setVisibility(View.GONE);
                swipe_refresh.setRefreshing(false);
                departmentsList = result;
                adapter = new DepartmentsAdapter(result, mContext.getApplication());
                schoolDepartmentsList.setAdapter(adapter);
                schoolDepartmentsList.setLayoutManager(new LinearLayoutManager(mContext));

            }

            @Override
            public void onFailure(String message, boolean state, int code) {
                swipe_refresh.setRefreshing(false);
                something_going_wrong.setVisibility(View.VISIBLE);
            }
        });
    }


    public interface SchoolDepartmentsListener {
        void onSelection(SchoolDepartmentsModel row);
    }

}
