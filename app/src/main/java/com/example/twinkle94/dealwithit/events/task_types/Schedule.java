package com.example.twinkle94.dealwithit.events.task_types;

import com.example.twinkle94.dealwithit.events.Event;
import com.example.twinkle94.dealwithit.events.Interest;
import com.example.twinkle94.dealwithit.events.type_enums.EventType;
import com.example.twinkle94.dealwithit.events.type_enums.ScheduleType;

import java.util.List;

public class Schedule extends Event
{
    private ScheduleType scheduleType;
    private List<Interest> interests;

    public Schedule(int id, String title, ScheduleType scheduleType, String time_start, String time_end, String date, String state, int importance)
    {
        super(id, title, time_start, time_end, date, EventType.SCHEDULE, state, importance);
        this.scheduleType = scheduleType;
    }

    public Schedule()
    {
        super();
        this.scheduleType = ScheduleType.NO_TYPE;
    }

    public void setScheduleType(ScheduleType scheduleType)
    {
        this.scheduleType = scheduleType;
    }

    public ScheduleType getScheduleType()
    {
        return scheduleType;
    }
}
