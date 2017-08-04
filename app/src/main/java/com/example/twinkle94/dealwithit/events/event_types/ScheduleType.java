package com.example.twinkle94.dealwithit.events.event_types;

//For choosing the type of schedule (Move to Schedule class)
public enum ScheduleType
{
    LESSON("Lesson"),
    EXAM("Exam"),
    LABORATORY_WORK("Lab.Work"),

    NO_TYPE("No Type");

    private final String type;

    ScheduleType(String s)
    {
        type = s;
    }

    public String toString()
    {
        return this.type;
    }

    public static ScheduleType getName(String schedule_event_type_string)
    {
        for(ScheduleType type : values())
        {
            if( type.type.equals(schedule_event_type_string))
            {
                return type;
            }
        }
        return null;
    }
}
