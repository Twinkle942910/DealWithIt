package com.example.twinkle94.dealwithit.events.type_enums;

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
}
