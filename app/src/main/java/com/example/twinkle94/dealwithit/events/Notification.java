package com.example.twinkle94.dealwithit.events;

public class Notification
{
    private int id;

    private int event_id;
    private int image;
    private String content;
    private String time;
    private String date;

    public Notification(int id, int event_id, int image, String content, String time, String date)
    {
        this.id = id;
        this.event_id = event_id;
        this.image = image;
        this.content = content;
        this.time = time;
        this.date = date;
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
