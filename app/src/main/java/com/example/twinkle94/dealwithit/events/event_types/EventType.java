package com.example.twinkle94.dealwithit.events.event_types;

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
                              //TODO: better give EventType as an argument?
    public static int getImage(String type)
    {
        int[] images = {Constants.TODO_TYPE_IMAGE,
                Constants.BIRTHDAY_TYPE_IMAGE,
                Constants.SCHEDULE_TYPE_IMAGE,
                Constants.WORK_TASK_TYPE_IMAGE,
                Constants.NO_TYPE_IMAGE};

        for(EventType event_type : EventType.values())
        {
            if(type.equals(event_type.toString()))
            {
                return images[event_type.ordinal()];
            }
        }
        return images[4];
    }

    public static EventType getName(String stringType)
    {
        for(EventType type : values())
        {
            if( type.title.equals(stringType))
            {
                return type;
            }
        }
        return null;
    }

    public static boolean compare(EventType type1, EventType type2)
    {
        return type1.equals(type2);
    }

}
