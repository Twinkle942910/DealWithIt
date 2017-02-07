package com.example.twinkle94.dealwithit.events;

public enum EventType
{
    TODO ("ToDo"),
    BIRTHDAY ("Birthday"),
    SCHEDULE ("Schedule"),
    WORKTASK ("Work Task"),

    NO_TYPE("No Type");

    private final String title;

    private EventType(String s)
    {
        title = s;
    }

    public EventType getName(String otherName)
    {
        if(title.equals(otherName))
            return this;

        return NO_TYPE;
    }

    public String toString()
    {
        return this.title;
    }
}
