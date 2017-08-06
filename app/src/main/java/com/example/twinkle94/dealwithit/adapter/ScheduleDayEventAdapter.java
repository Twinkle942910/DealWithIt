package com.example.twinkle94.dealwithit.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.twinkle94.dealwithit.R;
import com.example.twinkle94.dealwithit.events.Event;

import java.util.ArrayList;
import java.util.List;

public class ScheduleDayEventAdapter extends BaseAdapter
{
    private int DEFAULT_DURATION_HEIGHT;

    private List<Event> eventList;
    private Context context;
    private String date;

    public ScheduleDayEventAdapter(Context context)
    {
        this.context = context;
        eventList = new ArrayList<>();
    }

    @Override
    public int getCount()
    {
        return eventList.size();
    }

    @Override
    public Event getItem(int position)
    {
        return eventList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Event event = getItem(position);
        ScheduleDayEventViewHolder scheduleDayEventViewHolder;

        if(convertView == null)
        {
            scheduleDayEventViewHolder = new ScheduleDayEventViewHolder();

            LayoutInflater layoutInflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.schedule_day_event_item, parent, false);

            scheduleDayEventViewHolder.tv_startTime =
                    (TextView) convertView.findViewById(R.id.schedule_day_task_start_time);

            scheduleDayEventViewHolder.tv_endTime =
                    (TextView) convertView.findViewById(R.id.schedule_day_task_end_time);

            scheduleDayEventViewHolder.rl_background =
                    (RelativeLayout) convertView.findViewById(R.id.schedule_day_task_background);

            scheduleDayEventViewHolder.v_duration =
                    convertView.findViewById(R.id.task_duration_view);

            scheduleDayEventViewHolder.tv_type =
                    (TextView) convertView.findViewById(R.id.schedule_day_task_type);

            scheduleDayEventViewHolder.tv_title =
                    (TextView) convertView.findViewById(R.id.schedule_day_task_title);

            scheduleDayEventViewHolder.tv_state =
                    (TextView) convertView.findViewById(R.id.schedule_day_task_state);

            scheduleDayEventViewHolder.tv_state_image =
                    (ImageView) convertView.findViewById(R.id.schedule_day_task_state_image);

            //TODO: Possibly this is not right place for this.
            DEFAULT_DURATION_HEIGHT = scheduleDayEventViewHolder.v_duration.getLayoutParams().height;

            convertView.setTag(scheduleDayEventViewHolder);
        }
        else scheduleDayEventViewHolder = (ScheduleDayEventViewHolder) convertView.getTag();

        scheduleDayEventViewHolder.tv_startTime.setText(event.getStartTime());
        scheduleDayEventViewHolder.tv_endTime.setText(event.getEndTime());

        //TODO: set color depending on time.
        switch (event.getType())
        {
            case TODO:
                scheduleDayEventViewHolder.rl_background.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTypeToDo));
                break;

            case SCHEDULE:
                scheduleDayEventViewHolder.rl_background.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTypeSchedule));
                break;

            case WORKTASK:
                scheduleDayEventViewHolder.rl_background.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTypeWorkTasks));
                break;

            case BIRTHDAY:
                scheduleDayEventViewHolder.rl_background.setBackgroundColor(ContextCompat.getColor(context, R.color.colorTypeBirthday));
                break;
        }

        int height = DEFAULT_DURATION_HEIGHT + dpToPx(event.getDuration());
        scheduleDayEventViewHolder.v_duration.getLayoutParams().height = height;

        scheduleDayEventViewHolder.tv_type.setText(event.getType().toString());
        scheduleDayEventViewHolder.tv_title.setText(event.getTitle());
        scheduleDayEventViewHolder.tv_state.setText(event.getStateName());

        scheduleDayEventViewHolder.tv_state_image.setImageResource(event.getStateImage());

        return convertView;
    }

    public void add(Event event)
    {
        this.eventList.add(event);
    }

    public void addAll(List<Event> eventList)
    {
        this.eventList.addAll(eventList);
    }

    public  void clear()
    {
        if(!eventList.isEmpty())
        eventList.clear();
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public void updateAll()
    {
        notifyDataSetChanged();
    }

    private static class ScheduleDayEventViewHolder
    {
        TextView tv_startTime;
        TextView tv_endTime;
        RelativeLayout rl_background;
        View v_duration;
        TextView tv_type;
        TextView tv_title;
        TextView tv_state;
        ImageView tv_state_image;
    }

    private int pxToDp(int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    private int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
