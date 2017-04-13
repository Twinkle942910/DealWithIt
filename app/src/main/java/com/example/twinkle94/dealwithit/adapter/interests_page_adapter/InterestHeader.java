package com.example.twinkle94.dealwithit.adapter.interests_page_adapter;

public class InterestHeader implements InterestItem
{
    private final String title;

    public InterestHeader(String title)
    {
        this.title = title;
    }

    @Override
    public String toString()
    {
        return title;
    }

    @Override
    public int getType()
    {
        return InterestItemType.INTEREST_HEADER.ordinal();
    }
}
