package com.example.twinkle94.dealwithit.fragments.tab_fragments.schedule_page;

import com.example.twinkle94.dealwithit.events.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ScheduleDay
{
    private String dayName;
    private int dayNumber;
    private List<Event> dayEventsList;

    //TODO: maybe we don't need this here?(definitely).
    private int year;
    private int month;

    public ScheduleDay(String dayName, int dayNumber)
    {
        this.dayName = dayName;
        this.dayNumber = dayNumber;
        dayEventsList = new ArrayList<>();
    }

    public String getDayName()
    {
        return dayName;
    }

    public void setDayName(String dayName)
    {
        this.dayName = dayName;
    }

    public int getDayNumber()
    {
        return dayNumber;
    }

    public void setDayNumber(int dayNumber)
    {
        this.dayNumber = dayNumber;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public void setMonth(int month)
    {
        this.month = month;
    }

    public List<Event> getDayEventsList()
    {
        return dayEventsList;
    }

    public String getCurrentDate()
    {
        SimpleDateFormat dateFormat =  new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayNumber);

        return dateFormat.format(calendar.getTime());
    }
}
