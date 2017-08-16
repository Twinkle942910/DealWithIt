package com.example.twinkle94.dealwithit.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.twinkle94.dealwithit.R;
import com.example.twinkle94.dealwithit.task_list_page.TaskActivity;

public class TaskDetailFragment extends Fragment {
    public static final String TAG  = TaskDetailFragment.class.getSimpleName();

    private int taskId;

    //State
    private LinearLayout mStateLayout;
    private TextView mState;
    private ImageView mStateIcon;

    //Date
    private TextView mDate;

    //Time
    private TextView mStartTime;
    private TextView mEndTime;

    //Duration
    private TextView mDuration;

    //Importance
    private TextView mImportance;

    //Interests
    private TextView mInterests;

    //Comments (Not sure if it has to be a field, and if we need to initialize this in onCreateView)
    private LinearLayout mCommentsLayout;
    private TextView mCommentsCount;
    private LinearLayout mCommentsContainer;

    //Subtasks (Not sure if it has to be a field, and if we need to initialize this in onCreateView)
    private LinearLayout mSubtasksLayout;
    private TextView mSubtasksCount;
    private CheckBox mSubtasksDone;
    private LinearLayout mSubtasksContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_detail, container, false);
        taskId = getArguments() == null ? -1 : getArguments().getInt(TaskActivity.TASK_ID);

        mStateLayout = (LinearLayout) view.findViewById(R.id.task_state_layout);
        mState = (TextView) view.findViewById(R.id.task_state);
        mStateIcon = (ImageView) view.findViewById(R.id.task_state_icon);

        mDate = (TextView) view.findViewById(R.id.task_date);

        mStartTime = (TextView) view.findViewById(R.id.task_from_time);
        mEndTime = (TextView) view.findViewById(R.id.task_to_time);

        mDuration = (TextView) view.findViewById(R.id.task_duration);

        mImportance = (TextView) view.findViewById(R.id.task_importance);

        mInterests = (TextView) view.findViewById(R.id.task_interests);

        mInterests = (TextView) view.findViewById(R.id.task_interests);

        mCommentsLayout = (LinearLayout) view.findViewById(R.id.task_comments_layout);
        mCommentsCount = (TextView) view.findViewById(R.id.task_comments);
        mCommentsContainer = (LinearLayout) view.findViewById(R.id.task_comments_container);

        mSubtasksLayout = (LinearLayout) view.findViewById(R.id.task_subtasks_layout);
        mSubtasksCount = (TextView) view.findViewById(R.id.task_subtasks);
        mSubtasksDone = (CheckBox) view.findViewById(R.id.task_subtasks_done);
        mSubtasksContainer = (LinearLayout) view.findViewById(R.id.task_subtasks_container);

        return view;
    }

    public static TaskDetailFragment newInstance(int taskId) {
        Bundle args = new Bundle();
        args.putInt(TaskActivity.TASK_ID, taskId);

        TaskDetailFragment taskDetailFragment =  new TaskDetailFragment();
        taskDetailFragment.setArguments(args);

        return taskDetailFragment;
    }
}
