package com.example.twinkle94.dealwithit.adapter.today_page_adapters;

import com.example.twinkle94.dealwithit.events.type_enums.EventType;

public class EventTypeSection implements Item
{
    private String title;

    public EventTypeSection(String title)
    {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public EventType getType()
    {
        return EventType.NO_TYPE;
    }
}
