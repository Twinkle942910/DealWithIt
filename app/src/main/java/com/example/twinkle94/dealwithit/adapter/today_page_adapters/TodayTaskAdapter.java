package com.example.twinkle94.dealwithit.adapter.today_page_adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.twinkle94.dealwithit.R;
import com.example.twinkle94.dealwithit.events.Event;
import com.example.twinkle94.dealwithit.events.type_enums.EventType;

import java.util.ArrayList;
import java.util.List;

public class TodayTaskAdapter extends ArrayAdapter
{
    private final String NAME = this.getClass().getName();

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_LIST = 1;

    private List<Item> list_of_tasks = new ArrayList<Item>();

    public TodayTaskAdapter(Context context, int resource)
    {
        super(context, resource);
    }

    public void add(Item event)
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
    public Item getItem(int position)
    {
        return list_of_tasks.get(position);
    }

    @Override
    public int getItemViewType(int position)
    {
        return (getItem(position).getType() == EventType.NO_TYPE) ?
                RowType.HEADER_ITEM.ordinal()
                :
                RowType.LIST_ITEM.ordinal();
    }

    @Override
    public int getViewTypeCount()
    {
        return RowType.values().length;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent)
    {
        View row = convertView;
        EventHolder eventHolder;

        // Get the data item type for this position
        int type = getItemViewType(position);

        if(row == null)
        {
            eventHolder = new EventHolder();

            // Inflate XML layout based on the type
            row = getInflatedLayoutForType(type, parent);

            switch (type)
            {
                case TYPE_HEADER:
                    initHeader(row, eventHolder);
                    break;

                case TYPE_LIST:
                    initViews(row, eventHolder);
                    break;
            }

            row.setTag(eventHolder);
        }

        else
        {
            eventHolder = (EventHolder) row.getTag();
        }

        //set all views with values from list
        setViewValues(position, eventHolder);

        return row;
    }

    private void setViewValues(int position, EventHolder eventHolder)
    {
        Item event = getItem(position);

        int type_color;
        String task_title;
        String from_time;
        String to_time;
        String importance;
        String interest;

        if (event != null)
        {
            switch (event.getType())
            {
                case SCHEDULE:
                    type_color = ContextCompat.getColor(getContext(), R.color.colorTypeSchedule);
                    task_title = ((Event)(event)).getTitle();
                    String schedule_type = ((Event)(event)).getSchedule_type().toString();
                    from_time = ((Event)(event)).getTime_start();
                    to_time = ((Event)(event)).getTime_end();
                    importance = Integer.toString(((Event)(event)).getImportance()) + "%";
                    interest = Integer.toString(((Event)(event)).getImportance() + 9) + "%";

                    eventViewValues(eventHolder, type_color, task_title, schedule_type, from_time, to_time, importance, interest, View.VISIBLE, View.VISIBLE);
                    break;

                case TODO:
                    type_color = ContextCompat.getColor(getContext(), R.color.colorTypeToDo);
                    task_title = ((Event)(event)).getTitle();
                    from_time = ((Event)(event)).getTime_start();
                    to_time = ((Event)(event)).getTime_end();
                    importance = Integer.toString(((Event)(event)).getImportance()) + "%";
                    interest = Integer.toString(((Event)(event)).getImportance() + 9) + "%";

                    eventViewValues(eventHolder, type_color, task_title, "", from_time, to_time, importance, interest, View.GONE, View.GONE);
                    break;

                case WORKTASK:
                    type_color = ContextCompat.getColor(getContext(), R.color.colorTypeWorkTasks);
                    task_title = ((Event)(event)).getTitle();
                    from_time = ((Event)(event)).getTime_start();
                    to_time = ((Event)(event)).getTime_end();
                    importance = Integer.toString(((Event)(event)).getImportance()) + "%";
                    interest = Integer.toString(((Event)(event)).getImportance() + 9) + "%";

                    eventViewValues(eventHolder, type_color, task_title, "", from_time, to_time, importance, interest, View.GONE, View.GONE);
                    break;

                case BIRTHDAY:
                    type_color = ContextCompat.getColor(getContext(), R.color.colorTypeBirthday);
                    task_title = ((Event)(event)).getTitle();
                    from_time = ((Event)(event)).getTime_start();
                    to_time = ((Event)(event)).getTime_end();
                    importance = Integer.toString(((Event)(event)).getImportance()) + "%";
                    interest = Integer.toString(((Event)(event)).getImportance() + 9) + "%";

                    eventViewValues(eventHolder, type_color, task_title, "", from_time, to_time, importance, interest, View.GONE, View.GONE);
                    break;

                case NO_TYPE:
                    String type = ((EventTypeSection) getItem(position)).getTitle();

                    eventHolder.headerText.setText(type);

                    switch(type)
                    {
                        case "Schedule":
                            eventHolder.headerText.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTypeSchedule));
                            break;

                        case "ToDo":
                            eventHolder.headerText.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTypeToDo));
                            break;

                        case "Work Task":
                            eventHolder.headerText.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTypeWorkTasks));
                            break;

                        case "Birthday":
                            eventHolder.headerText.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTypeBirthday));
                            break;

                    }

                    break;
            }
        }
    }

    private void eventViewValues(EventHolder eventHolder, int type_color, String task_title, String schedule_type,
                                 String from_time, String to_time, String importance, String interest,
                                 int task_type_visibility, int task_type_image_visibility)
    {
        eventHolder.type_color.setBackgroundColor(type_color);

        eventHolder.task_title.setText(task_title);
        eventHolder.task_type.setText(schedule_type);
        eventHolder.task_type_image.setImageResource(R.drawable.ic_lesson_type);

        eventHolder.task_type.setVisibility(task_type_visibility);
        eventHolder.task_type_image.setVisibility(task_type_image_visibility);

        eventHolder.from_title.setText("From");
        eventHolder.from_time.setText(from_time);
        eventHolder.from_image.setImageResource(R.drawable.ic_from_time);

        eventHolder.to_title.setText("To");
        eventHolder.to_time.setText(to_time);
        eventHolder.to_image.setImageResource(R.drawable.ic_to_time);

        eventHolder.importance.setText(importance);
        eventHolder.importance_image.setImageResource(R.drawable.ic_importance_icon);

        eventHolder.interest.setText(interest);
        eventHolder.interest_image.setImageResource(R.drawable.ic_interest_today_page);
    }

    private View getInflatedLayoutForType(int type, ViewGroup parent)
    {
        LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View inflated_view = null;

        switch(type)
        {
            case TYPE_HEADER:
                inflated_view = layoutInflater.inflate(R.layout.today_list_header, parent, false);
                break;

            case TYPE_LIST:
                inflated_view = layoutInflater.inflate(R.layout.today_list_item, parent, false);
                break;
        }

        return inflated_view;
    }

    private void initHeader(View row, EventHolder eventHolder)
    {
        eventHolder.headerText = (TextView) row.findViewById(R.id.tasks_title);
    }

    private void initViews(View row, EventHolder eventHolder)
    {
        eventHolder.type_color = row.findViewById(R.id.type_color);

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
    }

    private static class EventHolder
    {
        View type_color;

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

        //Header text
        TextView headerText;
    }

    private enum RowType
    {
        HEADER_ITEM, LIST_ITEM
    }
}
