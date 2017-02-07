package com.example.twinkle94.dealwithit.events;
       //TODO: change back later
public /*abstract*/ class Event
{
    private int id;
    private String title;
    private String time_start;
    private String time_end;
    private String date;
  //  private EventType type;
   private String type;
    private String state;
    private int importance;

    private String schedule_type;

    public Event(int id, String title, String time_start, String time_end, String date, String type, String state, int importance)
    {
        this.id = id;
        this.title = title;
        this.time_start = time_start;
        this.time_end = time_end;
        this.date = date;
      //  this.type = this.type.getName(type);
       this.type = type;
        this.state = state;
        this.importance = importance;
    }

    //Move later to class Schedule
    public void setScheduleType(String schedule_type)
    {
      this.schedule_type = schedule_type;
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

  /*  public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }*/

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

    public int getImportance() {
        return importance;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }

    public String getSchedule_type() {
        return schedule_type;
    }

    //For choosing the type of schedule (Move to Schedule class)
    private enum ScheduleType
    {
        LESSON,
        EXAM,
        LABORATORY_WORK;
    }
}
