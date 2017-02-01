package com.example.twinkle94.dealwithit.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.twinkle94.dealwithit.R;

public class ScheduleFragment extends AbstractTabFragment
{
    public static final String SCHEDULE_PAGE = "SCHEDULE_PAGE";
    public static final int LAYOUT = R.layout.fragment_schedule;

    
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
        view = inflater.inflate(LAYOUT, container, false);

        //final Bundle args = getArguments();

        return view;
    }
}
