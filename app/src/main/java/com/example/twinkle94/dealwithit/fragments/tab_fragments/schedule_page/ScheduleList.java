package com.example.twinkle94.dealwithit.fragments.tab_fragments.schedule_page;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ScheduleList
{
    private int year;
    private String month;
    private List<ScheduleDay> scheduleDayList;

    private int daysNumber;

    public ScheduleList()
    {
        scheduleDayList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        //Set current year by default
        year = calendar.get(Calendar.YEAR);
        //Set current month by default
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        month = month_date.format(calendar.getTime());

        daysNumber = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        initDayList();
    }

    private void initDayList()
    {

        for (int i = 1; i <= daysNumber; i++)
        {
            //TODO: Maybe make a field?
            //TODO: HARDCODEEEE!
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);

            Date date = null;
            try
            {
                date = new SimpleDateFormat("MMMM").parse(month);
            } catch (ParseException e)
            {
                e.printStackTrace();
            }
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            calendar.set(Calendar.MONTH, cal.get(Calendar.MONTH));
            calendar.set(Calendar.DAY_OF_MONTH, i);

            SimpleDateFormat day_name = new SimpleDateFormat("EEEE");
            String dayName = day_name.format(calendar.getTime());

            ScheduleDay scheduleDay = new ScheduleDay(dayName, i);
            scheduleDay.setYear(year);
            scheduleDay.setMonth(cal.get(Calendar.MONTH));
            scheduleDayList.add(scheduleDay);
        }
    }

    public int getYear()
    {
        return year;
    }

    public String getMonth()
    {
        return month;
    }

    public List<ScheduleDay> getScheduleDayList()
    {
        return scheduleDayList;
    }
}
