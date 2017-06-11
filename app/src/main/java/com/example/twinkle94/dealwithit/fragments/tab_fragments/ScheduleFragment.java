package com.example.twinkle94.dealwithit.fragments.tab_fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.twinkle94.dealwithit.R;
import com.example.twinkle94.dealwithit.adapter.ScheduleDayEventAdapter;
import com.example.twinkle94.dealwithit.adapter.ScheduleDaysAdapter;
import com.example.twinkle94.dealwithit.database.EventDAO;
import com.example.twinkle94.dealwithit.fragments.tab_fragments.schedule_page.ScheduleDay;
import com.example.twinkle94.dealwithit.fragments.tab_fragments.schedule_page.ScheduleList;

public class ScheduleFragment extends AbstractTabFragment implements AdapterView.OnItemClickListener
{
    private static final String TAG = ScheduleFragment.class.getSimpleName();
    public static final String SCHEDULE_PAGE = "SCHEDULE_PAGE";
    private static final int LAYOUT = R.layout.fragment_schedule;

    private TextView tv_year;
    private TextView tv_month;
    private TextView tv_currentDay;

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
        tv_currentDay = (TextView) view.findViewById(R.id.picked_day);

        initList();

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        ScheduleDay day = (ScheduleDay) parent.getItemAtPosition(position);

        //TODO: show tasks for day here. (maybe move initialization for optimization)
        //TODO:set current date be default!
        tv_currentDay.setText(day.getDayName() + ", " + tv_month.getText() + " " + day.getDayNumber());

        //TODO:set current date be default!
        ScheduleDayEventAdapter scheduleDayEventAdapter  = new ScheduleDayEventAdapter(getActivity());
        scheduleDayEventAdapter.setDate(day.getCurrentDate());

        Log.i(TAG, "Day date: " + day.getCurrentDate());

        ListView dayEventList = (ListView) this.view.findViewById(R.id.schedule_day_event_list);
        new EventDAO(getActivity()).getEventListByDateOnBG(scheduleDayEventAdapter);

        dayEventList.setAdapter(scheduleDayEventAdapter);
    }

    private void initList()
    {
        ScheduleList scheduleList = new ScheduleList();

        tv_year = (TextView)view.findViewById(R.id.schedule_list_year);
        tv_month = (TextView)view.findViewById(R.id.schedule_list_month);

        tv_year.setText(Integer.toString(scheduleList.getYear()));
        tv_month.setText(scheduleList.getMonth());

        ScheduleDaysAdapter scheduleDaysAdapter = new ScheduleDaysAdapter(getActivity());
        ListView dayList = (ListView) view.findViewById(R.id.schedule_list);
        dayList.setOnItemClickListener(this);

        scheduleDaysAdapter.addAll(scheduleList.getScheduleDayList());

        dayList.setAdapter(scheduleDaysAdapter);
    }


}
