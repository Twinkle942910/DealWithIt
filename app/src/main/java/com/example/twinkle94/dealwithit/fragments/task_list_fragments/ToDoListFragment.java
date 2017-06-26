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
import com.example.twinkle94.dealwithit.adapter.ToDoListAdapter;
import com.example.twinkle94.dealwithit.database.EventDAO;

public class ToDoListFragment extends Fragment {
    public static final String TAG  = ToDoListFragment.class.getSimpleName();
    private ToDoListAdapter mToDoListAdapter;
    private RecyclerView mTaskList;
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        mTaskList = (RecyclerView) view.findViewById(R.id.task_list);
        mToDoListAdapter = new ToDoListAdapter(mContext);

        new EventDAO(mContext).getToDoListOnBG(mToDoListAdapter);
        mTaskList.setAdapter(mToDoListAdapter);

        mTaskList.setLayoutManager(new LinearLayoutManager(mContext));

        return view;
    }
}
