package com.example.twinkle94.dealwithit.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.twinkle94.dealwithit.R;
import com.example.twinkle94.dealwithit.adapter.today_page_adapters.EventTypeSection;
import com.example.twinkle94.dealwithit.adapter.today_page_adapters.TodayTaskAdapter;
import com.example.twinkle94.dealwithit.events.Event;
import com.example.twinkle94.dealwithit.events.type_enums.EventType;
import com.example.twinkle94.dealwithit.events.type_enums.ScheduleType;

public class TodayFragment extends AbstractTabFragment
{
    public static final String TODAY_PAGE = "TODAY_PAGE";
    public static final int LAYOUT = R.layout.fragment_today;

    private ListView task_list;

    private TodayTaskAdapter today_task_adapter;

    private Event event;
    private Event event1;
    private Event event3;

    private Event event4;
    private Event event5;

    private Event event6;
    private Event event7;

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

        task_list = (ListView) view.findViewById(R.id.tasks_list);

        today_task_adapter = new TodayTaskAdapter(getActivity(), R.layout.today_list_item);

        eventCreation();

        today_task_adapter.add(new EventTypeSection("Schedule"));
        today_task_adapter.add(event);
        today_task_adapter.add(event1);
        today_task_adapter.add(event3);

        today_task_adapter.add(new EventTypeSection("ToDo"));
        today_task_adapter.add(event4);
        today_task_adapter.add(event5);

        today_task_adapter.add(new EventTypeSection("Work Task"));
        today_task_adapter.add(event6);
        today_task_adapter.add(event7);

        task_list.setAdapter(today_task_adapter);

        return view;
    }

    private void eventCreation()
    {
        event = new Event(1,"Algorythm Theory", "10:20 AM", "11:55 AM", "07.01.2017", EventType.SCHEDULE, "Waiting", 79);
        event.setScheduleType(ScheduleType.LESSON);

        event1 = new Event(2,"Object-oriented programming", "12:00 AM", "01:30 PM", "07.01.2017", EventType.SCHEDULE, "Waiting", 63);
        event1.setScheduleType(ScheduleType.LABORATORY_WORK);

        event3 = new Event(2,"Aplied Math", "01:50 PM", "03:35 PM", "07.01.2017", EventType.SCHEDULE, "Waiting", 17);
        event3.setScheduleType(ScheduleType.EXAM);


        event4 = new Event(5,"Clean house", "09:30 AM", "03:35 PM", "07.01.2017", EventType.TODO, "Waiting", 91);
       // event4.setScheduleType(ScheduleType.EXAM);


        event5 = new Event(7,"Buy grocery and other things", "01:30 AM", "02:25 PM", "07.01.2017", EventType.TODO, "Waiting", 23);
       // event5.setScheduleType(ScheduleType.EXAM);

        event6 = new Event(5,"Refactoring", "09:30 AM", "01:35 PM", "07.01.2017", EventType.WORKTASK, "Waiting", 81);
        // event4.setScheduleType(ScheduleType.EXAM);


        event7 = new Event(7,"Build database", "04:30 PM", "05:25 PM", "07.01.2017", EventType.WORKTASK, "Waiting", 33);
        // event5.setScheduleType(ScheduleType.EXAM);
    }
}
