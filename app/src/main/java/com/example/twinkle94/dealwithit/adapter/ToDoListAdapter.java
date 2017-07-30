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
import com.example.twinkle94.dealwithit.events.task_types.ToDo;
import com.example.twinkle94.dealwithit.task_list_page.TaskListActivity;

import java.util.ArrayList;
import java.util.List;

public class ToDoListAdapter extends RecyclerView.Adapter<ToDoListAdapter.ViewHolder> {
    private static final String TAG = ToDoListAdapter.class.getSimpleName();
    private Context mContext;
    private List<ToDo> mToDoList;

    public ToDoListAdapter(Context mContext) {
        this.mContext = mContext;
        mToDoList = new ArrayList<>();
    }

    @Override
    public ToDoListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater =
                (LayoutInflater) mContext.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        View taskCard = layoutInflater.inflate(R.layout.card_task, parent, false);

        Log.i(TAG, "Created holder");

        return new ViewHolder(taskCard);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ToDo toDo = mToDoList.get(position);

        holder.mTaskTitle.setText(toDo.getTitle());
        holder.mTaskDate.setText(toDo.getStartDate());
        holder.mTaskFromTime.setText(toDo.getStartTime());
        holder.mTaskToTime.setText(toDo.getEndTime());
       // holder.mTaskDuration.setText(String.valueOf(toDo.getDuration()));
        //TODO: improve getting list size!
        holder.mTaskSubtasks.setText(String.valueOf(toDo.getListSubTasks().size()));
        holder.mTaskComments.setText(String.valueOf(toDo.getListComments().size()));
        holder.mTaskImportance.setText(String.valueOf(toDo.getImportance()));
        //TODO: improve hardcode!
        holder.mTaskInterests.setText(String.valueOf(79));
        holder.mTaskState.setText("Now");

        Log.i(TAG, "Rendered position: " + position);
    }

    @Override
    public int getItemCount() {
        return mToDoList.size();
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

        public ViewHolder(View itemView) {
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
        }
    }

    public void add(ToDo toDo){
        mToDoList.add(toDo);
    }

    public void updateAll(){
        notifyDataSetChanged();
    }

    private void showPopup(View v, final ToDo task)
    {
        PopupMenu popup = new PopupMenu(mContext, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_interests_item, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.edit:
                        ((TaskListActivity)mContext).editTaskDialog(task);
                        return true;
                    case R.id.delete:
                        ((TaskListActivity)mContext).deleteTaskDialog(task);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();
    }
}
