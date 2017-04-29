package com.example.twinkle94.dealwithit.events;

public class Sub_task
{
    private int id;

    private int event_id;
    private String content;
    private boolean checked;

    public Sub_task(int id, int event_id, String content, boolean checked)
    {
        this.id = id;
        this.event_id = event_id;
        this.content = content;
        this.checked = checked;
    }

    public Sub_task()
    {
        this.id = -1;
        this.event_id = -1;
        this.content = "No_content";
        this.checked = false;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
