package com.example.twinkle94.dealwithit.fragments;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twinkle94.dealwithit.R;
import com.example.twinkle94.dealwithit.adding_task_page.sub_items.ScheduleTask;
import com.example.twinkle94.dealwithit.adding_task_page.sub_items.SubTask;
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

    private String[] week_dates;
    private String date;

    private TextView start_end_time_tv;

    //TODO: make something better
    private ViewGroup.OnHierarchyChangeListener onSubItemRemoveListener;
    private List<AddingTaskFragment.OnTaskRemovedListener> removed_tasks;

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
            saveInput();
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
            SubTask subTask = new ScheduleTask(activity, task_container_ly, R.layout.schedule_task_item, date);
            ((ScheduleTask) subTask).setOnTimeSetListener(new ScheduleTask.OnTimeSetListener() {
                @Override
                public void onTimeSet(TextView time_tv, View view) {
                    start_end_time_tv = time_tv;
                    activity.setTimePicker(view);
                }
            });
            subTask.addView();
            removed_tasks.add(subTask);
        }
        else Toast.makeText(activity, "You have to chose a day first!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void saveInput()
    {
        if(!isInputErrors())
        {
            activity.finish();
        }
        else Toast.makeText(activity, "Check your input!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCancel()
    {
       clearScheduleContainer();
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
    public void setInterest(View view)
    {

    }

    @Override
    public void onInterestPicked(int interest_id)
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
        return false;
    }

    @Override
    protected void removeValidators()
    {

    }

    @Override
    protected void initializeListeners()
    {
        //TODO: is it the best place for this?
        removed_tasks = new ArrayList<>();

        //TODO: make something better
        onSubItemRemoveListener = new ViewGroup.OnHierarchyChangeListener()
        {
            @Override
            public void onChildViewAdded(View parent, View child)
            {

            }

            @Override
            public void onChildViewRemoved(View parent, View child)
            {
                removed_tasks.get(removed_tasks.size() - 1).onTaskRemovedCall();
                removed_tasks.remove(removed_tasks.size() - 1);
            }
        };
    }

    @Override
    protected void setListeners()
    {
        task_container_ly.setOnHierarchyChangeListener(onSubItemRemoveListener);
    }

    @Override
    protected void removeListeners()
    {
        //TODO: is it the best place for this?
        removed_tasks = null;
        //TODO: make something better
        task_container_ly.setOnHierarchyChangeListener(null);
    }

    //TODO: when task is created earlier than day, day is - " ".
    private void initializeDayPicker(View viewHierarchy)
    {
        final LinearLayout daysContainer = (LinearLayout) viewHierarchy.findViewById(R.id.day_container);
        initWeek();

        for (int i = 0; i < daysContainer.getChildCount(); i++)
        {
            final TextView day_tv = (TextView) daysContainer.getChildAt(i);
            day_tv.setTag(i);
            day_tv.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    clearScheduleContainer();
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

                    setDate((int)view.getTag());
                    ((TextView) view).setText(setFullDay((int)view.getTag()));
                }
            });
        }
    }

    private void clearDays(LinearLayout daysContainer)
    {
        String[] cut_day_names = getResources().getStringArray(R.array.days_cut_array);

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
        Calendar calendar = Calendar.getInstance();
        week_dates = new String[7];

        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 1);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        for (int i = 0; i < 7; i++)
        {
            week_dates[i] = sdf.format(calendar.getTime());
            calendar.add(Calendar.DAY_OF_WEEK, 1);
        }
    }

    private void setDate(int day_position)
    {
        date = week_dates[day_position];
        Log.i("DATE", date);
    }

    private void clearScheduleContainer()
    {
        if(task_container_ly.getChildCount() != 0) task_container_ly.removeAllViews();
    }
}
