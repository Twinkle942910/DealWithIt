package com.example.twinkle94.dealwithit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.twinkle94.dealwithit.R;
import com.example.twinkle94.dealwithit.fragments.tab_fragments.schedule_page.ScheduleDay;

import java.util.ArrayList;
import java.util.List;

public class ScheduleDaysAdapter extends BaseAdapter
{
    private static final int ITEM_LAYOUT = R.layout.schedule_list_day_item;

    private List<ScheduleDay> scheduleDays;
    private Context context;

    public ScheduleDaysAdapter(Context context)
    {
        this.context = context;
        scheduleDays = new ArrayList<>();
    }

    @Override
    public int getCount()
    {
        return scheduleDays.size();
    }

    @Override
    public Object getItem(int position)
    {
        return scheduleDays.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ScheduleDay scheduleDay = (ScheduleDay) getItem(position);
        ScheduleDaysViewHolder scheduleDaysViewHolder;

        if(convertView == null)
        {
            LayoutInflater layoutInflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(ITEM_LAYOUT, parent, false);

            scheduleDaysViewHolder = new ScheduleDaysViewHolder();
            scheduleDaysViewHolder.tv_dayName =
                    (TextView) convertView.findViewById(R.id.schedule_list_item_day_name);
            scheduleDaysViewHolder.tv_dayNumber =
                    (TextView) convertView.findViewById(R.id.schedule_list_item_day_number);

            convertView.setTag(scheduleDaysViewHolder);
        }
        else
        {
            scheduleDaysViewHolder = (ScheduleDaysViewHolder) convertView.getTag();
        }

        scheduleDaysViewHolder.tv_dayName.setText(scheduleDay.getDayName());
        scheduleDaysViewHolder.tv_dayNumber.setText(Integer.toString(scheduleDay.getDayNumber()));

        return convertView;
    }

    public void add(ScheduleDay scheduleDay)
    {
        scheduleDays.add(scheduleDay);
    }

    public void addAll(List<ScheduleDay> scheduleDayList)
    {
        scheduleDays.addAll(scheduleDayList);
    }

    private static class ScheduleDaysViewHolder
    {
        TextView tv_dayName;
        TextView tv_dayNumber;
    }
}
