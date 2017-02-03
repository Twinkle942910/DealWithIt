package com.example.twinkle94.dealwithit.database;

public abstract class Event
{
    private int id;
    private String title;
    private String time_start;
    private String time_end;
    private String date;
    private String type;
    private String state;
    private boolean comment;
    private boolean sub_tasks;
    private boolean interests;
    private boolean location;
    private int importance;

    public Event(int id, String title, String time_start, String time_end, String date, String type, String state,
                 boolean comment, boolean sub_tasks, boolean interests, boolean location, int importance)
    {
        this.id = id;
        this.title = title;
        this.time_start = time_start;
        this.time_end = time_end;
        this.date = date;
        this.type = type;
        this.state = state;
        this.comment = comment;
        this.sub_tasks = sub_tasks;
        this.interests = interests;
        this.location = location;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isComment() {
        return comment;
    }

    public void setComment(boolean comment) {
        this.comment = comment;
    }

    public boolean isSub_tasks() {
        return sub_tasks;
    }

    public void setSub_tasks(boolean sub_tasks) {
        this.sub_tasks = sub_tasks;
    }

    public boolean isInterests() {
        return interests;
    }

    public void setInterests(boolean interests) {
        this.interests = interests;
    }

    public boolean isLocation() {
        return location;
    }

    public void setLocation(boolean location) {
        this.location = location;
    }

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }
}
