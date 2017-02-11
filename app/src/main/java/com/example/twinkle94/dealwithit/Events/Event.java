package com.example.twinkle94.dealwithit.events;

import com.example.twinkle94.dealwithit.adapter.today_page_adapter.Item;
import com.example.twinkle94.dealwithit.events.type_enums.EventType;

//TODO: Something wrong with git. Check what.!
public abstract class Event implements Item
{
    private int id;
    private String title;
    private String time_start;
    private String time_end;
    private String date;
    private EventType type;
    private String state;
    private int importance;

    //TODO: The average value of all interests of task. Not sure if I have to leave it here?
    private int interests;

    //TODO: Duration of task. Not sure if I have to leave it here(because Birthday have no duration)?
    private int duration;

    public Event(int id, String title, String time_start, String time_end, String date, EventType type, String state, int importance)
    {
        this.id = id;
        this.title = title;
        this.time_start = time_start;
        this.time_end = time_end;
        this.date = date;
        this.type = type;
        this.state = state;
        this.importance = importance;
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

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public EventType getType()
    {
        return type;
    }

    public void setType(EventType type)
    {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }



    //TODO: setter interests of task. Not sure if I have to leave it here(maybe just add to straight field)?
    protected void setInterests(int interests)
    {
        this.interests = interests;
    }

    public int getInterests()
    {
        return interests;
    }
}
