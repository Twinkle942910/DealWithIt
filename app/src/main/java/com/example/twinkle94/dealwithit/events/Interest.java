package com.example.twinkle94.dealwithit.events;

import com.example.twinkle94.dealwithit.adapter.interests_page_adapter.InterestItem;
import com.example.twinkle94.dealwithit.adapter.interests_page_adapter.InterestItemType;

public class Interest implements InterestItem
{
    private int id;

    private String title;
    private int value;

    public Interest(int id, String title, int value)
    {
        this.id = id;
        this.title = title;
        this.value = value;
    }

    public Interest()
    {
        this.id = -1;
        this.title = "no_title";
        this.value = -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
