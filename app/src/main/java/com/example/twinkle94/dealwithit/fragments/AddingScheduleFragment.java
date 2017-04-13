package com.example.twinkle94.dealwithit.fragments;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twinkle94.dealwithit.R;
import com.example.twinkle94.dealwithit.adding_task_page.sub_items.ScheduleTask;
import com.example.twinkle94.dealwithit.adding_task_page.sub_items.SubTask;
import com.example.twinkle94.dealwithit.background.FetchEventsTask;
import com.example.twinkle94.dealwithit.events.Interest;
import com.example.twinkle94.dealwithit.events.task_types.Schedule;
import com.example.twinkle94.dealwithit.events.type_enums.EventType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddingScheduleFragment extends AbstractAddingFragment
{
    private static final String NAME = AddingScheduleFragment.class.getSimpleName();
    private LinearLayout task_container_ly;

    private String date;

    private List<Schedule> scheduleList;

    private TextView start_end_time_tv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    protected void onMenuItemClick(int menu_item_id)
    {
        if (menu_item_id == R.id.save)
        {
            addSubTaskToDB();
        }
    }

    @Override
    public int getFragmentMenu()
    {
        return R.menu.menu_adding_task_fragment;
    }

    @Override
    public int getFragmentLayout()
    {
        return R.layout.fragment_new_schedule_type;
    }

    @Override
    protected void initializeViews(View fragmentView)
    {
        if (fragmentView != null)
        {
            task_container_ly = (LinearLayout) fragmentView.findViewById(R.id.task_container);
            initializeDayPicker(fragmentView);
        }
    }

    @Override
    protected void checkTypeOutputAction(EventType type, boolean isSchedule)
    {
        if(!isSchedule)
        {
            //TODO: compare with task method. Do we need type parameter when we have task_type field?
            taskTypeReplace(type);
            removeOutputTypeValidator();
        }
    }

    @Override
    protected void replaceScheduleOrTask(EventType type)
    {
        //TODO: compare with task method. Do we need type parameter when we have task_type field?
        typePickListener.onTaskTypePick(type);
    }

    @Override
    protected void onTypePickPositive(RadioGroup radioGroup)
    {
        if(radioGroup.getCheckedRadioButtonId() >= 0 && radioGroup.getCheckedRadioButtonId() == R.id.schedule_type_button)
        {
            setInputType(task_type.toString());
            setOutputType(task_type.toString());
        }
        else
        {
            //TODO: check this out!
            Toast.makeText(activity, "You must chose something!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onTypePickNegative(DialogInterface dialog)
    {
        dialog.dismiss();
    }

    @Override
    protected void onTypePickChanged(int checkedId, AlertDialog b)
    {
        switch (checkedId)
        {
            case R.id.birthday_type_button:
                task_type = EventType.BIRTHDAY;
                task_type_image = EventType.getImage(EventType.BIRTHDAY.toString());
                taskTypeReplace(task_type);
                b.dismiss();
                break;

            case R.id.work_tasks_type_button:
                task_type = EventType.WORKTASK;
                task_type_image = EventType.getImage(EventType.WORKTASK.toString());
                taskTypeReplace(task_type);
                b.dismiss();
                break;

            case R.id.todo_type_button:
                task_type = EventType.TODO;
                task_type_image = EventType.getImage(EventType.TODO.toString());
                taskTypeReplace(task_type);
                b.dismiss();
                break;

            case R.id.schedule_type_button:
                task_type = EventType.SCHEDULE;
                task_type_image = EventType.getImage(EventType.SCHEDULE.toString());
                break;
        }
    }

    @Override
    public void addSubItem(View view)
    {
        if(date != null)
        {
            initScheduleTaskList();

            SubTask subTask = new ScheduleTask(activity, task_container_ly, R.layout.schedule_task_item, date, scheduleList);
            ((ScheduleTask) subTask).setOnTimeSetListener(new ScheduleTask.OnTimeSetListener() {
                @Override
                public void onTimeSet(TextView time_tv, View view) {
                    start_end_time_tv = time_tv;
                    activity.setTimePicker(view);
                }
            });
            subTask.addView();
        }
        else Toast.makeText(activity, "You have to chose a day first!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void addSubTaskToDB()
    {
        if(!isInputErrors())
        {
            List<Interest> interestList = new ArrayList<>();

            interestList.add(new Interest(1, 1, "Study", 73));
            interestList.add(new Interest(2, 1, "Book", 83));
            interestList.add(new Interest(3, 1, "School", 12));

            for (Schedule schedule : scheduleList)
            {
                schedule.setListInterests(interestList);
                new FetchEventsTask(activity).execute("add_data", schedule);
            }

            activity.finish();
        }
        else Toast.makeText(activity, "Check your input!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void setTimeOutput(int type, SimpleDateFormat output_format, Calendar calendar)
    {
        start_end_time_tv.setText(output_format.format(calendar.getTime()));
    }

    @Override
    protected void setDateOutput(int year, int month, int day)
    {

    }

    @Override
    protected void initializeValidators()
    {

    }

    @Override
    protected void addValidators() {

    }

    @Override
    protected void validate(TextView textView, String text)
    {

    }

    @Override
    protected boolean isInputErrors()
    {
        if(scheduleList == null) return true;

        if(scheduleList.size() != task_container_ly.getChildCount())
            return true;

        return false;
    }

    @Override
    protected void removeValidators()
    {

    }

    @Override
    protected void initializeListeners()
    {

    }

    @Override
    protected void setListeners()
    {

    }

    @Override
    protected void removeListeners()
    {

    }

    private void initScheduleTaskList()
    {
        if(task_container_ly.getChildCount() == 0)
        {
            scheduleList = new ArrayList<>();
        }
    }

    //TODO: when task is created earlier than day, day is - " ".
    private void initializeDayPicker(View viewHierarchy)
    {
        final LinearLayout daysContainer = (LinearLayout) viewHierarchy.findViewById(R.id.day_container);

        for (int i = 0; i < daysContainer.getChildCount(); i++)
        {
            final TextView day = (TextView) daysContainer.getChildAt(i);
            day.setTag(i);
            day.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    clearDays(daysContainer);

                    ((TextView) view).setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
                    {
                        ((TextView) view).setTextAppearance(activity, R.style.TextRobotoRegular);
                    }
                    else
                    {
                        ((TextView) view).setTextAppearance(R.style.TextRobotoRegular);
                    }

                    initWeek();
                    ((TextView) view).setText(setFullDay((int)view.getTag()));
                }
            });
        }
    }

    private void clearDays(LinearLayout daysContainer)
    {
        String[] cut_day_names = new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};

        for (int i = 0; i < daysContainer.getChildCount(); i++)
        {
            final TextView day = (TextView) daysContainer.getChildAt(i);

            day.setTextColor(ContextCompat.getColor(getContext(), R.color.colorNotificationText));

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            {
                day.setTextAppearance(activity, R.style.TextRobotoLight);
            }
            else
            {
                day.setTextAppearance(R.style.TextRobotoLight);
            }

            day.setText(cut_day_names[i]);
        }
    }

    private String setFullDay(int day_index)
    {
        String[] days_array = getResources().getStringArray(R.array.days_array);
        return days_array[day_index];
    }

    private void initWeek()
    {
        // Get Current Date
        final Calendar calendar = Calendar.getInstance();

        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int  mMonth = calendar.get(Calendar.MONTH);
        int mYear = calendar.get(Calendar.YEAR);

        String myFormatDate = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormatDate, Locale.US);

        int dayMonday = 0;

        switch (day)
        {
            // Current day is Monday
            case Calendar.MONDAY:
                dayMonday = 0;
                break;

            // Current day is Tuesday
            case Calendar.TUESDAY:
                dayMonday =  - 1;
                break;

            // Current day is Wednesday
            case Calendar.WEDNESDAY:
                dayMonday =  - 2;
                break;

            // Current day is Thursday
            case Calendar.THURSDAY:
                dayMonday =  - 3;
                break;

            // Current day is Friday
            case Calendar.FRIDAY:
                dayMonday =  - 4;
                break;

            // Current day is Saturday
            case Calendar.SATURDAY:
                dayMonday =  - 5;
                break;

            // Current day is Sunday
            case Calendar.SUNDAY:
                dayMonday =  - 6;
                break;
        }

        calendar.set(Calendar.MONTH, mMonth);
        calendar.set(Calendar.YEAR, mYear);

        setDays(calendar, dateFormat, dayMonday);
    }

    private void setDays(Calendar c, SimpleDateFormat sdf, int dayMonday)
    {
        for(int i = 0; i < 6; i++)
        {
            if(i == 0)
            {
                c.add(Calendar.DAY_OF_MONTH, dayMonday);
            }
            else
            {
                c.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
        date = sdf.format(c.getTime());
    }
}