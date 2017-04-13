package com.example.twinkle94.dealwithit.events;

import com.example.twinkle94.dealwithit.adapter.interests_page_adapter.InterestItem;
import com.example.twinkle94.dealwithit.adapter.interests_page_adapter.InterestItemType;

public class Interest implements InterestItem
{
    private int id;

    private int event_id;
    private String title;
    private int value;

    public Interest(int id, int event_id, String title, int value)
    {
        this.id = id;
        this.event_id = event_id;
        this.title = title;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public int getType()
    {
        return InterestItemType.INTEREST.ordinal();
    }
}
