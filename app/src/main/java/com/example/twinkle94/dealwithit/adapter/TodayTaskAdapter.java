package com.example.twinkle94.dealwithit.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.twinkle94.dealwithit.R;
import com.example.twinkle94.dealwithit.events.Event;

import java.util.ArrayList;
import java.util.List;

public class TodayTaskAdapter extends ArrayAdapter
{
    List list_of_tasks = new ArrayList();

    public TodayTaskAdapter(Context context, int resource)
    {
        super(context, resource);
    }

    public void add(Event event)
    {
        list_of_tasks.add(event);
    }

    @Override
    public int getCount()
    {
        return list_of_tasks.size();
    }

    @Nullable
    @Override
    public Object getItem(int position)
    {
        return list_of_tasks.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        EventHolder productHolder;

        if(row == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.today_list_item, parent, false);
            productHolder = new EventHolder();

            switch (((Event)getItem(position)).getType())
            {
                //Change to enum
                case "Schedule":

                    productHolder.task_title = (TextView) row.findViewById(R.id.schedule_task_title);

                    productHolder.from_title = (TextView) row.findViewById(R.id.schedule_task_type);
                    productHolder.from_image = (ImageView) row.findViewById(R.id.lesson_type);

                    productHolder.from_time = (TextView) row.findViewById(R.id.from_time);
                    productHolder.to_time = (TextView) row.findViewById(R.id.to_time);
                    productHolder.to_image = (ImageView) row.findViewById(R.id.time_icon);

                    productHolder.importance = (TextView) row.findViewById(R.id.importance_percent);
                    productHolder.importance_image = (ImageView) row.findViewById(R.id.importance_icon);

                    break;
            }



            row.setTag(productHolder);
        }

        else
        {
            productHolder = (EventHolder) row.getTag();
        }

        Event event = (Event) getItem(position);

        productHolder.task_title.setText(event.getTitle());

        productHolder.from_title.setText(event.getSchedule_type());
        productHolder.from_image.setImageResource(R.drawable.ic_lesson_type);

        productHolder.from_time.setText(event.getTime_start());
        productHolder.to_time.setText(event.getTime_end());
        productHolder.to_image.setImageResource(R.drawable.ic_time);

        productHolder.importance.setText(Integer.toString(event.getImportance()) + "%");
        productHolder.importance_image.setImageResource(R.drawable.ic_importance_icon);

        return row;
    }

    private static class EventHolder
    {
        TextView task_title;

        TextView from_title;
        TextView from_time;

        ImageView from_image;

        TextView to_title;
        TextView to_time;

        ImageView to_image;

        TextView importance;
        ImageView importance_image;
    }
}
