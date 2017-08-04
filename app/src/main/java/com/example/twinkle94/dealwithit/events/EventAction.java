package com.example.twinkle94.dealwithit.events;

public enum EventAction {
    DO,
    FINISH,
    CANCEL,
    TIME,
    RENEW;

    public static boolean compare(EventAction type1, EventAction type2)
    {
        return type1.equals(type2);
    }
}
