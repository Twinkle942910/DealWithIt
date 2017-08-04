package com.example.twinkle94.dealwithit.adapter.today_page_adapter;

import com.example.twinkle94.dealwithit.events.event_types.EventType;

public class EventTypeSection implements Item
{
    private String title;

    public EventTypeSection(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }

    @Override
    public EventType getType()
    {
        return EventType.NO_TYPE;
    }

    @Override
    public String toString() {
        return title;
    }
}
