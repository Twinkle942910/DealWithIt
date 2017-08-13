package com.example.twinkle94.dealwithit.fragments.task_list_fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.twinkle94.dealwithit.R;
import com.example.twinkle94.dealwithit.adapter.TaskListAdapter;
import com.example.twinkle94.dealwithit.database.EventDAO;
import com.example.twinkle94.dealwithit.events.event_types.EventType;

public class TaskListFragment extends Fragment {
    public static final String TAG  = TaskListFragment.class.getSimpleName();
    private static final String TASK_TYPE  = "task_type";

    private TaskListAdapter mTaskListAdapter;
    private RecyclerView mTaskList;
    private Context mContext;

    private EventType taskType;

    public static TaskListFragment newInstance(EventType taskType){
        Bundle args = new Bundle();
        args.putString(TASK_TYPE, taskType.toString());

        final TaskListFragment thisFragment = new TaskListFragment();
        thisFragment.setArguments(args);

        return thisFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        taskType = getArguments() != null ? EventType.getName(getArguments().getString(TASK_TYPE)) : EventType.NO_TYPE;

        mTaskList = (RecyclerView) view.findViewById(R.id.task_list);
        mTaskListAdapter = new TaskListAdapter(mContext);

        new EventDAO(mContext).getComplexEventListByTypeOnBG(mTaskListAdapter, taskType);
        mTaskList.setAdapter(mTaskListAdapter);

        mTaskList.setLayoutManager(new LinearLayoutManager(mContext));

        return view;
    }
}
