package com.example.twinkle94.dealwithit.events.type_enums;

import com.example.twinkle94.dealwithit.util.Constants;

public enum EventType
{
    TODO ("ToDo"),
    BIRTHDAY ("Birthday"),
    SCHEDULE ("Schedule"),
    WORKTASK ("Work Task"),

    NO_TYPE("No Type Selected");

    private final String title;

    EventType(String s)
    {
        title = s;
    }

    public String toString()
    {
        return this.title;
    }

    //TODO: Bit hardcoded, do it better.
    public static int getColor(String type)
    {
        //TODO: move array to Constants(or new class or enum) and get values from there.
        int[] colors = {Constants.TODO_COLOR,
                Constants.BIRTHDAY_COLOR,
                Constants.SCHEDULE_COLOR,
                Constants.WORK_TASK_COLOR,
                Constants.NO_TYPE_COLOR};

       for(EventType event_type : EventType.values())
       {
           if(type.equals(event_type.toString()))
           {
                return colors[event_type.ordinal()];
           }
       }
        return colors[4];
    }
}
