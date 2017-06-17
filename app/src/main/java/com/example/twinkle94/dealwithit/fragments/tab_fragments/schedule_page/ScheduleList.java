package com.example.twinkle94.dealwithit.fragments.tab_fragments.schedule_page;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ScheduleList {
    private int mYear;
    private int mMonth;
    private List<ScheduleDay> mScheduleList;

    private int mDaysNumber;

    public ScheduleList() {
        mScheduleList = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();

        //Set current mYear by default
        mYear = calendar.get(Calendar.YEAR);

        //Set current mMonth by default
        mMonth = calendar.get(Calendar.MONTH);

        mDaysNumber = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        initDayList();
    }

    private void initDayList() {
        for (int i = 1; i <= mDaysNumber; i++) {
            //TODO: Maybe make a field?
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, mYear);
            calendar.set(Calendar.MONTH, mMonth);
            calendar.set(Calendar.DAY_OF_MONTH, i);

            SimpleDateFormat day_name = new SimpleDateFormat("EEEE");
            String dayName = day_name.format(calendar.getTime());

            ScheduleDay scheduleDay = new ScheduleDay(dayName, i);
            scheduleDay.setYear(mYear);
            scheduleDay.setMonth(mMonth);
            mScheduleList.add(scheduleDay);
        }
    }

    public int getYear() {
        return mYear;
    }

    public String getMonth() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, mMonth);
        return simpleDateFormat.format(calendar.getTime());
    }

    public void update(int year, int month) {
        mYear = year;
        mMonth = month;

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, mYear);
        calendar.set(Calendar.MONTH, mMonth);
        mDaysNumber = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        if (!mScheduleList.isEmpty()) {
            mScheduleList.clear();
            initDayList();
        }
    }

    public List<ScheduleDay> getScheduleDayList() {
        return mScheduleList;
    }
}
