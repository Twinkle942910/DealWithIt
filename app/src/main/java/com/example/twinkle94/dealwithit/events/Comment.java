package com.example.twinkle94.dealwithit.events;


public class Comment
{
    //TODO: check if we need this field
    private int id;
    //TODO: check if we need this field
    private  int id_event;
    private String content;

    public Comment(int id, int id_event, String content)
    {
        this.id = id;
        this.id_event = id_event;
        this.content = content;
    }

    public Comment()
    {
        this.id = -1;
        this.id_event = -1;
        this.content = "No_content";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_event() {
        return id_event;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
