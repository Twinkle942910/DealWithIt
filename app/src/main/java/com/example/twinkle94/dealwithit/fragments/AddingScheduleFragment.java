package com.example.twinkle94.dealwithit.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twinkle94.dealwithit.R;
import com.example.twinkle94.dealwithit.adding_task_page.sub_items.ScheduleTask;
import com.example.twinkle94.dealwithit.adding_task_page.sub_items.SubTask;
import com.example.twinkle94.dealwithit.events.event_types.EventType;

import java.text.DateFormatSymbols;
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
    private List<AddingTaskFragment.OnTaskRemovedListener> removed_tasks = new ArrayList<>();
    //TODO: move to abstract, possibly
    private OnInterestCallListener interestCallListener;


    private OnInterestPickedListener interestPickedListener;

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

        //TODO: move to abstract, possibly
        if (context instanceof OnInterestCallListener)
        {
            interestCallListener = (OnInterestCallListener) context;
        }
        else
        {
            throw new ClassCastException(context.toString()
                    + " must implement AddingScheduleFragment.OnInterestCallListener");
        }
    }

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
            final SubTask subTask = new ScheduleTask(activity, task_container_ly, R.layout.schedule_task_item, date);

            ((ScheduleTask) subTask).setOnTimeSetListener(new ScheduleTask.OnTimeSetListener() {
                @Override
                public void onTimeSet(TextView time_tv, View view) {
                    start_end_time_tv = time_tv;
                    activity.setTimePicker(view);
                }
            });
            //TODO: awful callback, think of something better!
            ((ScheduleTask) subTask).setInterestPickedListener(new ScheduleTask.OnInterestPickedListener()
            {
                @Override
                public void onInterestPicked(View view)
                {
                    activity.setInterest(view);
                    interestPickedListener = (OnInterestPickedListener) subTask;
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
        interestCallListener.onInterestsCall();
    }

    @Override
    public void onInterestPicked(int interest_id)
    {
        interestPickedListener.onInterestsPick(interest_id);
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
       /* //TODO: is it the best place for this?
        removed_tasks = new ArrayList<>();*/

        //TODO: make something better
        onSubItemRemoveListener = new ViewGroup.OnHierarchyChangeListener()
        {
            @Override
            public void onChildViewAdded(View parent, View child)
            {
                //TODO: find better solution.
                child.setTag(task_container_ly.getChildCount() - 1);
            }

            //TODO: refactor. This is disgusting!
            @Override
            public void onChildViewRemoved(View parent, View child)
            {
                if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP)
                {
                    removed_tasks.get(((ViewGroup)parent).indexOfChild(child)).onTaskRemovedCall();
                    removed_tasks.remove(((ViewGroup)parent).indexOfChild(child));
                }
                else
                {
                    int childPosition = (Integer) child.getTag();
                    int childCount = ((ViewGroup)parent).getChildCount();
                    removed_tasks.get(childPosition).onTaskRemovedCall();
                    removed_tasks.remove(childPosition);

                    //TODO: find better solution.
                    for(int i = 0; i < childCount; i++)
                    {
                        ((ViewGroup)parent).getChildAt(i).setTag(i);
                    }
                }
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
        /*//TODO: is it the best place for this?
        removed_tasks = null;*/
        //TODO: make something better
        task_container_ly.setOnHierarchyChangeListener(null);
    }

    //TODO: Refactor day picking methods.
    private void initializeDayPicker(View viewHierarchy)
    {
        final LinearLayout daysContainer = (LinearLayout) viewHierarchy.findViewById(R.id.day_container);
        final int DAYS_IN_WEEK = 7;

        final String[] shortNamesOfDays = new String[DAYS_IN_WEEK];
        final String[] fullNamesOfDays = new String[DAYS_IN_WEEK];

        initWeek(shortNamesOfDays, fullNamesOfDays);

        for (int i = 0; i < daysContainer.getChildCount(); i++)
        {
            final TextView day_tv = (TextView) daysContainer.getChildAt(i);
            day_tv.setText(shortNamesOfDays[i]);
            day_tv.setTag(i);
            day_tv.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    clearScheduleContainer();
                    clearDays(daysContainer, shortNamesOfDays);

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
                    ((TextView) view).setText(fullNamesOfDays[((int)view.getTag())]);
                }
            });
        }
    }

    private void clearDays(LinearLayout daysContainer, String[] shortNamesOfDays)
    {
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

            day.setText(shortNamesOfDays[i]);
        }
    }

    private void initWeek(String[] shortNamesOfDays, String[] fullNamesOfDays)
    {
        final int DAYS_IN_WEEK = 7;
        final Locale currentLocale = getLocale();

        Calendar calendar = Calendar.getInstance(currentLocale);
        week_dates = new String[DAYS_IN_WEEK];

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        int firstDayIndex = calendar.getFirstDayOfWeek();
        calendar.set(Calendar.DAY_OF_WEEK, firstDayIndex);

        for (int i = 0; i < DAYS_IN_WEEK; i++)
        {
            shortNamesOfDays[i] = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, currentLocale);
            fullNamesOfDays[i] = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, currentLocale);

            week_dates[i] = dateFormat.format(calendar.getTime());
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

    private Locale getLocale() {
        //TODO: Move this to somewhere more general.
        Locale currentLocale;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            currentLocale = getResources().getConfiguration().getLocales().get(0);
        } else{
            currentLocale = getResources().getConfiguration().locale;
        }
        return currentLocale;
    }

    public interface OnInterestCallListener
    {
        void onInterestsCall();
    }

    public interface OnInterestPickedListener
    {
        void onInterestsPick(int interest_id);
    }
}
