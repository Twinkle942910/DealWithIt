package com.example.twinkle94.dealwithit.events;

import com.example.twinkle94.dealwithit.adapter.today_page_adapter.Item;
import com.example.twinkle94.dealwithit.events.type_enums.EventType;

import java.util.List;

//TODO: Something wrong with git. Check what.!
public abstract class Event implements Item
{
    //TODO: check if we need this field
    private int id;
    private String title;
    //TODO: is it right for time to be String?
    private String time_start;
    private String time_end;
    //TODO: Should I use DateTime class?
    private String date;
    private EventType type;
    private String state;
    private int importance;
    private List<Interest> list_interests;

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

    public Event()
    {
        this.id = -1;
        this.title = "No_title";
        this.time_start = "No_time_start";
        this.time_end = "No_time_end";
        this.date = "No_date";
        this.type = EventType.NO_TYPE;
        this.state = "No_state";
        this.importance = -1;
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

    public void addInterest(Interest interest)
    {
        list_interests.add(interest);
    }

    private void countInterest()
    {
        int sum = 0;

        for (Interest interest: list_interests)
        {
            sum += interest.getValue();

            setInterests(sum / list_interests.size());
        }
    }

    public int getInterestsAverage()
    {
        return interests;
    }

    public List<Interest> getListInterests()
    {
        return list_interests;
    }

    public void setListInterests(List<Interest> list_interests) {
        this.list_interests = list_interests;
    }

    public int getDuration()
    {
        int startTime = convertTime(time_start);
        int endTime = convertTime(time_end);

        this.duration = endTime - startTime;

        /*int minutes = duration % 60;
        int hours = duration - minutes;*/

        //TODO: what if end time is greater than start (when tasks starts before midnight)?
        return duration > 0 ? duration : 0  /*(hours * 60) + minutes*/;
    }

    //TODO: think about AM - PM
    private int convertTime(String timeString)
    {
        char[] timeCharacters = timeString.toCharArray();
        int result = 0;
        int numbPosition = 1;
        for(int charPosition = timeCharacters.length - 1; charPosition >= 0; charPosition--)
        {
            if(timeCharacters[charPosition] >= '0' && timeCharacters[charPosition] <= '9')
            {
                int number = Character.getNumericValue(timeCharacters[charPosition]);
                result += number * numbPosition;
                numbPosition *= 10;
            }
        }

        return result;
    }
}
