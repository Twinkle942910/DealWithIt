package com.example.twinkle94.dealwithit.events.type_enums;

public enum EventType
{
    TODO ("ToDo"),
    BIRTHDAY ("Birthday"),
    SCHEDULE ("Schedule"),
    WORKTASK ("Work Task"),

    NO_TYPE("No Type");

    private final String title;

    EventType(String s)
    {
        title = s;
    }

    public String toString()
    {
        return this.title;
    }
}
