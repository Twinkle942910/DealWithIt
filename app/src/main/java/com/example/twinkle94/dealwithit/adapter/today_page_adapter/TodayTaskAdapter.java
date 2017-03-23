package com.example.twinkle94.dealwithit.adapter.today_page_adapter;

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
import com.example.twinkle94.dealwithit.events.task_types.Schedule;
import com.example.twinkle94.dealwithit.events.type_enums.EventType;

import java.util.ArrayList;
import java.util.List;

public class TodayTaskAdapter extends ArrayAdapter
{
    private final String NAME = TodayTaskAdapter.class.getSimpleName();

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_LIST = 1;

    private List<Item> list_of_tasks = new ArrayList<>();

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
        return getItem(position).getType() == EventType.NO_TYPE ?
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
        EventViewHolder eventViewHolder;

        // Get the data item type for this position
        int type = getItemViewType(position);

        if(convertView == null)
        {
            eventViewHolder = new EventViewHolder();

            // Inflate XML layout based on the type
            convertView = getInflatedLayoutForType(type, parent);

            switch (type)
            {
                case TYPE_HEADER:
                    initHeader(convertView, eventViewHolder);
                    break;

                case TYPE_LIST:
                    initViews(convertView, eventViewHolder);
                    break;
            }

            convertView.setTag(eventViewHolder);
        }

        else
        {
            eventViewHolder = (EventViewHolder) convertView.getTag();
        }

        //set all views with values from list
        setViewValues(position, eventViewHolder);

        return convertView;
    }

    private void setViewValues(int position, EventViewHolder eventViewHolder)
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
                    String schedule_type = ((Schedule)(event)).getScheduleType().toString();
                    int schedule_type_image = getSchedule_type_image(event);
                    from_time = ((Event)(event)).getTime_start();
                    to_time = ((Event)(event)).getTime_end();
                    importance = Integer.toString(((Event)(event)).getImportance()) + "%";
                    interest = Integer.toString(((Event)(event)).getImportance() + 9) + "%";

                    eventViewValues(eventViewHolder, type_color, task_title, schedule_type_image, schedule_type, from_time, to_time, importance, interest, View.VISIBLE, View.VISIBLE);
                    break;

                case TODO:
                    type_color = ContextCompat.getColor(getContext(), R.color.colorTypeToDo);
                    task_title = ((Event)(event)).getTitle();
                    from_time = ((Event)(event)).getTime_start();
                    to_time = ((Event)(event)).getTime_end();
                    importance = Integer.toString(((Event)(event)).getImportance()) + "%";
                    interest = Integer.toString(((Event)(event)).getImportance() + 9) + "%";

                    eventViewValues(eventViewHolder, type_color, task_title, 0, "", from_time, to_time, importance, interest, View.GONE, View.GONE);
                    break;

                case WORKTASK:
                    type_color = ContextCompat.getColor(getContext(), R.color.colorTypeWorkTasks);
                    task_title = ((Event)(event)).getTitle();
                    from_time = ((Event)(event)).getTime_start();
                    to_time = ((Event)(event)).getTime_end();
                    importance = Integer.toString(((Event)(event)).getImportance()) + "%";
                    interest = Integer.toString(((Event)(event)).getImportance() + 9) + "%";

                    eventViewValues(eventViewHolder, type_color, task_title, 0, "", from_time, to_time, importance, interest, View.GONE, View.GONE);
                    break;

                case BIRTHDAY:
                    type_color = ContextCompat.getColor(getContext(), R.color.colorTypeBirthday);
                    task_title = ((Event)(event)).getTitle();
                    from_time = ((Event)(event)).getTime_start();
                    to_time = ((Event)(event)).getTime_end();
                    importance = Integer.toString(((Event)(event)).getImportance()) + "%";
                    interest = Integer.toString(((Event)(event)).getImportance() + 9) + "%";

                    eventViewValues(eventViewHolder, type_color, task_title, 0, "", from_time, to_time, importance, interest, View.GONE, View.GONE);
                    break;

                case NO_TYPE:
                    String type = ((EventTypeSection) getItem(position)).getTitle();

                    eventViewHolder.headerText.setText(type);

                    switch(type)
                    {
                        case "Schedule":
                            eventViewHolder.headerText.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTypeSchedule));
                            break;

                        case "ToDo":
                            eventViewHolder.headerText.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTypeToDo));
                            break;

                        case "Work Task":
                            eventViewHolder.headerText.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTypeWorkTasks));
                            break;

                        case "Birthday":
                            eventViewHolder.headerText.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTypeBirthday));
                            break;

                    }

                    break;

                //TODO: add default, when task have no type.
            }
        }
    }

    private int getSchedule_type_image(Item event)
    {
        int schedule_type_image = R.drawable.ic_lesson_type;

        switch(((Schedule)(event)).getScheduleType())
        {
            case LESSON:
                schedule_type_image = R.drawable.ic_lesson_type;
                break;

            case EXAM:
                schedule_type_image = R.drawable.ic_exam_schedule_type;
                break;

            case LABORATORY_WORK:
                schedule_type_image = R.drawable.ic_lab_work_schedule_type;
                break;
        }
        return schedule_type_image;
    }

    //TODO: overload method with less parameters for different types of tasks.
    private void eventViewValues(EventViewHolder eventViewHolder, int type_color, String task_title, int scheduleTypeImage, String schedule_type,
                                 String from_time, String to_time, String importance, String interest,
                                 int task_type_visibility, int task_type_image_visibility)
    {
        eventViewHolder.type_color.setBackgroundColor(type_color);

        eventViewHolder.task_title.setText(task_title);
        eventViewHolder.task_type.setText(schedule_type);
        eventViewHolder.task_type_image.setImageResource(scheduleTypeImage);

        eventViewHolder.task_type.setVisibility(task_type_visibility);
        eventViewHolder.task_type_image.setVisibility(task_type_image_visibility);

        eventViewHolder.from_title.setText("From");
        eventViewHolder.from_time.setText(from_time);
        eventViewHolder.from_image.setImageResource(R.drawable.ic_from_time);

        eventViewHolder.to_title.setText("To");
        eventViewHolder.to_time.setText(to_time);
        eventViewHolder.to_image.setImageResource(R.drawable.ic_to_time);

        eventViewHolder.importance.setText(importance);
        eventViewHolder.importance_image.setImageResource(R.drawable.ic_importance_icon);

        eventViewHolder.interest.setText(interest);
        eventViewHolder.interest_image.setImageResource(R.drawable.ic_interest_today_page);
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

    private void initHeader(View row, EventViewHolder eventViewHolder)
    {
        eventViewHolder.headerText = (TextView) row.findViewById(R.id.tasks_title);
    }

    private void initViews(View row, EventViewHolder eventViewHolder)
    {
        eventViewHolder.type_color = row.findViewById(R.id.type_color);

        eventViewHolder.task_title = (TextView) row.findViewById(R.id.task_title);
        eventViewHolder.task_type = (TextView) row.findViewById(R.id.schedule_type);
        eventViewHolder.task_type_image = (ImageView) row.findViewById(R.id.lesson_type);

        eventViewHolder.from_title = (TextView) row.findViewById(R.id.from_title);
        eventViewHolder.from_time = (TextView) row.findViewById(R.id.schedule_start_time);
        eventViewHolder.from_image = (ImageView) row.findViewById(R.id.schedule_start_time_icon);

        eventViewHolder.to_title = (TextView) row.findViewById(R.id.to_title);
        eventViewHolder.to_time = (TextView) row.findViewById(R.id.schedule_end_time);
        eventViewHolder.to_image = (ImageView) row.findViewById(R.id.schedule_end_time_icon);

        eventViewHolder.importance = (TextView) row.findViewById(R.id.importance_percent);
        eventViewHolder.importance_image = (ImageView) row.findViewById(R.id.importance_icon);

        eventViewHolder.interest = (TextView) row.findViewById(R.id.interest_percent);
        eventViewHolder.interest_image = (ImageView) row.findViewById(R.id.interest_icon);
    }

    private static class EventViewHolder
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
