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
    private List<Event> list_of_tasks = new ArrayList<Event>();

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
    public Event getItem(int position)
    {
        return list_of_tasks.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent)
    {
        View row = convertView;
        EventHolder eventHolder;

        if(row == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.today_list_item, parent, false);
            eventHolder = new EventHolder();

                switch (getItem(position).getType())
                {
                    case SCHEDULE:

                        eventHolder.task_title = (TextView) row.findViewById(R.id.task_title);
                        eventHolder.task_type = (TextView) row.findViewById(R.id.task_type);
                        eventHolder.task_type_image = (ImageView) row.findViewById(R.id.lesson_type);

                        eventHolder.from_title = (TextView) row.findViewById(R.id.from_title);
                        eventHolder.from_time = (TextView) row.findViewById(R.id.from_time);
                        eventHolder.from_image = (ImageView) row.findViewById(R.id.from_time_icon);

                        eventHolder.to_title = (TextView) row.findViewById(R.id.to_title);
                        eventHolder.to_time = (TextView) row.findViewById(R.id.to_time);
                        eventHolder.to_image = (ImageView) row.findViewById(R.id.to_time_icon);

                        eventHolder.importance = (TextView) row.findViewById(R.id.importance_percent);
                        eventHolder.importance_image = (ImageView) row.findViewById(R.id.importance_icon);

                        eventHolder.interest = (TextView) row.findViewById(R.id.interest_percent);
                        eventHolder.interest_image = (ImageView) row.findViewById(R.id.interest_icon);

                        if(position == list_of_tasks.size() - 1)
                        {
                            //Announcing bottom divider
                            eventHolder.bottom_divider  = row.findViewById(R.id.bottom_divider);
                            eventHolder.bottom_divider.setVisibility(View.VISIBLE);
                        }

                        break;

                    case TODO:
                        break;

                    case WORKTASK:
                        break;

                    case BIRTHDAY:
                        break;

                    case NO_TYPE:
                        //ToDo: add something
                        break;
                }

            row.setTag(eventHolder);
        }

        else
        {
            eventHolder = (EventHolder) row.getTag();
        }


        Event event = getItem(position);

        if (event != null)
        {
            eventHolder.task_title.setText(event.getTitle());
            eventHolder.task_type.setText(event.getSchedule_type().toString());
            eventHolder.task_type_image.setImageResource(R.drawable.ic_lesson_type);

            eventHolder.from_title.setText(new String("From"));
            eventHolder.from_time.setText(event.getTime_start());
            eventHolder.from_image.setImageResource(R.drawable.ic_from_time);

            eventHolder.to_title.setText(new String("To"));
            eventHolder.to_time.setText(event.getTime_end());
            eventHolder.to_image.setImageResource(R.drawable.ic_to_time);

            eventHolder.importance.setText(Integer.toString(event.getImportance()) + "%");
            eventHolder.importance_image.setImageResource(R.drawable.ic_importance_icon);

            eventHolder.interest.setText(Integer.toString(event.getImportance() + 9) + "%");
            eventHolder.interest_image.setImageResource(R.drawable.ic_interest_today_page);
        }

        return row;
    }

    private static class EventHolder
    {
        TextView task_title;
        TextView task_type;
        ImageView task_type_image;

        TextView from_title;
        TextView from_time;
        ImageView from_image;

        TextView to_title;
        TextView to_time;
        ImageView to_image;

        TextView importance;
        ImageView importance_image;

        TextView interest;
        ImageView interest_image;

        View bottom_divider;
    }
}
