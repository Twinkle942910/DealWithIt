package com.example.twinkle94.dealwithit.events.task_types;

import com.example.twinkle94.dealwithit.events.Event;
import com.example.twinkle94.dealwithit.events.type_enums.EventType;

public class Schedule extends Event
{
    public Schedule(int id, String title, String time_start, String time_end, String date,
                    EventType type, String state, int importance)
    {
        super(id, title, time_start, time_end, date, type, state, importance);
    }
}
