package com.example.twinkle94.dealwithit.fragments.tab_fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.twinkle94.dealwithit.R;

public class ScheduleFragment extends AbstractTabFragment
{
    public static final String SCHEDULE_PAGE = "SCHEDULE_PAGE";
    private static final int LAYOUT = R.layout.fragment_schedule;

    
    public static ScheduleFragment newInstance(int page, Context context)
    {
        Bundle args = new Bundle();
        args.putInt(SCHEDULE_PAGE, page);

        ScheduleFragment fragment = new ScheduleFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_schedule_name));

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.i(SCHEDULE_PAGE, "onCreateView");

        view = inflater.inflate(LAYOUT, container, false);

        //final Bundle args = getArguments();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.i(SCHEDULE_PAGE, "onCreate");
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        Log.i(SCHEDULE_PAGE, "onAttach");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        Log.i(SCHEDULE_PAGE, "onActivityCreated");
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Log.i(SCHEDULE_PAGE, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(SCHEDULE_PAGE, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(SCHEDULE_PAGE, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(SCHEDULE_PAGE, "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(SCHEDULE_PAGE, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(SCHEDULE_PAGE, "onDetach");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(SCHEDULE_PAGE, "onDestroyView");
    }
}
