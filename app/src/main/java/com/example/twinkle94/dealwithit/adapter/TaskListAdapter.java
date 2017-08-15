package com.example.twinkle94.dealwithit.adapter;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.twinkle94.dealwithit.R;
import com.example.twinkle94.dealwithit.events.ComplexEvent;
import com.example.twinkle94.dealwithit.events.event_types.Birthday;
import com.example.twinkle94.dealwithit.events.event_types.ToDo;
import com.example.twinkle94.dealwithit.task_list_page.TaskActivity;

import java.util.ArrayList;
import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {
    private static final String TAG = TaskListAdapter.class.getSimpleName();
    private static final int BIRTHDAY = 1;

    private Context mContext;
    private List<ComplexEvent> taskList;

    public TaskListAdapter(Context mContext) {
        this.mContext = mContext;
        taskList = new ArrayList<>();
    }

    @Override
    public TaskListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater =
                (LayoutInflater) mContext.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View taskCard = layoutInflater.inflate(R.layout.card_task, parent, false);

        Log.i(TAG, "Created holder");

        return new ViewHolder(taskCard, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ComplexEvent event = taskList.get(position);

        holder.mTaskTitle.setText(event.getTitle());
        holder.mTaskDate.setText(event.getStartDate());
        holder.mTaskFromTime.setText(event.getStartTime());
        holder.mTaskToTime.setText(event.getEndTime());
        holder.mTaskDuration.setText(event.getFormattedDuration());
        holder.mTaskSubtasks.setText(event.doneSubtasksCount() + "/" + event.subtasksCount());
        holder.mTaskComments.setText(String.valueOf(event.commentsCount()));
        holder.mTaskImportance.setText(String.valueOf(event.getImportance()));
        holder.mTaskInterests.setText(String.valueOf(event.getInterests()));
        holder.mTaskState.setText(event.getStateName());

        if(holder.getItemViewType() == BIRTHDAY){
           holder.mBirthdayLocation.setText(((Birthday)event).getLocation().getStreet());
        }

        Log.i(TAG, "Rendered position: " + position);
    }

    @Override
    public int getItemViewType(int position) {
        return taskList.get(position).getType().ordinal();
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTaskTitle;
        private TextView mTaskDate;
        private TextView mTaskFromTime;
        private TextView mTaskToTime;
        private TextView mTaskDuration;
        private TextView mTaskSubtasks;
        private TextView mTaskComments;
        private TextView mTaskImportance;
        private TextView mTaskInterests;
        private RelativeLayout mTaskStateLayout;
        private TextView mTaskState;

        private TextView mBirthdayLocation;
        private RelativeLayout mBirthdayLocationLayout;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);

            mTaskTitle = (TextView) itemView.findViewById(R.id.today_task_title);
            mTaskDate = (TextView) itemView.findViewById(R.id.task_date);
            mTaskFromTime = (TextView) itemView.findViewById(R.id.task_from_time);
            mTaskToTime = (TextView) itemView.findViewById(R.id.task_to_time);
            mTaskDuration = (TextView) itemView.findViewById(R.id.task_duration);
            mTaskSubtasks = (TextView) itemView.findViewById(R.id.task_subtasks);
            mTaskComments = (TextView) itemView.findViewById(R.id.task_comments);
            mTaskImportance = (TextView) itemView.findViewById(R.id.task_importance);
            mTaskInterests = (TextView) itemView.findViewById(R.id.task_interests);
            mTaskStateLayout = (RelativeLayout) itemView.findViewById(R.id.task_state_layout);
            mTaskState = (TextView) itemView.findViewById(R.id.task_state);

            if(viewType == BIRTHDAY){
                mBirthdayLocationLayout = (RelativeLayout) itemView.findViewById(R.id.task_location_layout);
                mBirthdayLocationLayout.setVisibility(View.VISIBLE);
                mBirthdayLocation = (TextView) itemView.findViewById(R.id.task_location);
            }
        }
    }

    public void add(ComplexEvent event) {
        taskList.add(event);
    }

    public void updateAll() {
        notifyDataSetChanged();
    }

    //TODO: make this work.
    private void showPopup(View v, final ToDo task) {
        PopupMenu popup = new PopupMenu(mContext, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_interests_item, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.edit:
                        ((TaskActivity) mContext).editTaskDialog(task);
                        return true;
                    case R.id.delete:
                        ((TaskActivity) mContext).deleteTaskDialog(task);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();
    }
}
