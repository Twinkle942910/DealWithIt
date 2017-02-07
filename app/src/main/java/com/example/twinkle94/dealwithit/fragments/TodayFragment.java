package com.example.twinkle94.dealwithit.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.twinkle94.dealwithit.R;
import com.example.twinkle94.dealwithit.adapter.TodayTaskAdapter;
import com.example.twinkle94.dealwithit.events.Event;

public class TodayFragment extends AbstractTabFragment
{
    public static final String TODAY_PAGE = "TODAY_PAGE";
    public static final int LAYOUT = R.layout.fragment_today;

    private ListView schedule_list;

    private Event event;
    private Event event1;
    private Event event3;

    public static TodayFragment newInstance(int page, Context context)
    {
        Bundle args = new Bundle();
        args.putInt(TODAY_PAGE, page);

        TodayFragment fragment = new TodayFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_today_name));

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(LAYOUT, container, false);

        //final Bundle args = getArguments();

        schedule_list = (ListView) view.findViewById(R.id.schedule_list);

        event = new Event(1,"Algorythm Theory", "10:20 AM", "11:55 AM", "07.01.2017", "Schedule", "Waiting", 79);
        event.setScheduleType("Lesson");

        event1 = new Event(2,"Object-oriented programming", "12:00 AM", "01:30 PM", "07.01.2017", "Schedule", "Waiting", 63);
        event1.setScheduleType("Lab.Work");

        event3 = new Event(2,"Aplied Math", "01:50 PM", "03:35 PM", "07.01.2017", "Schedule", "Waiting", 17);
        event3.setScheduleType("Exam");

        TodayTaskAdapter adapter = new TodayTaskAdapter(getActivity(), R.layout.today_list_item);
        adapter.add(event);
        adapter.add(event1);
        adapter.add(event3);

        schedule_list.setAdapter(adapter);

        return view;
    }
}
