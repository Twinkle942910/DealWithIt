package com.example.twinkle94.dealwithit.events.notes;

public class EventInterest
{
    private int id;
    private int id_event;
    private int id_interest;

    public EventInterest(int id, int id_event, int id_interest)
    {
        this.id = id;
        this.id_event = id_event;
        this.id_interest = id_interest;
    }

    public EventInterest()
    {
        this.id = -1;
        this.id_event = -1;
        this.id_interest = -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_event()
    {
        return id_event;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }

    public int getId_interest() {
        return id_interest;
    }

    public void setId_interest(int id_interest) {
        this.id_interest = id_interest;
    }
}
