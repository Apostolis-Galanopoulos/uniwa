package android.school_work.com.work.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.school_work.com.work.Model.SchoolDepartmentsModel;
import android.school_work.com.work.R;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.ArrayList;

public class DepartmentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<SchoolDepartmentsModel> list = new ArrayList<>();
    Context context;

    public DepartmentsAdapter(ArrayList<SchoolDepartmentsModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View view;
        switch (viewType) {
            case SchoolDepartmentsModel.NORMAL_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.school_department_row, parent, false);
                return new NormalTypeViewHolder(view);
        }
        return null;
    }

    public void clearAll() {
        this.list.clear();
        notifyDataSetChanged();
    }

    public void refresh() {
        notifyDataSetChanged();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final SchoolDepartmentsModel _model = list.get(position);

        switch (_model.getType()) {
            case SchoolDepartmentsModel.NORMAL_TYPE:
                ((NormalTypeViewHolder) holder).name.setText(_model.getName());
                break;
        }

    }

    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, SchoolDepartmentsModel data) {
        list.add(position, data);
        notifyItemInserted(position);
    }

    public void animate(RecyclerView.ViewHolder viewHolder) {
        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.bounce_interpolator);
        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(SchoolDepartmentsModel data) {
        int position = list.indexOf(data);
        list.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemViewType(int position) {

        switch (list.get(position).getType()) {
            case 0:
                return SchoolDepartmentsModel.NORMAL_TYPE;
            default:
                return -1;
        }
    }

    public interface RecyclerViewItemClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    public class NormalTypeViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        NormalTypeViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);

        }
    }

    public class LoaderTypeViewHolder extends RecyclerView.ViewHolder {

        LoaderTypeViewHolder(View itemView) {
            super(itemView);

        }
    }

}
