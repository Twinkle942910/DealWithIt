package com.example.twinkle94.dealwithit.adding_task_page;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.twinkle94.dealwithit.R;
import com.example.twinkle94.dealwithit.events.event_types.EventType;
import com.example.twinkle94.dealwithit.fragments.AbstractAddingFragment;
import com.example.twinkle94.dealwithit.fragments.AddingScheduleFragment;
import com.example.twinkle94.dealwithit.fragments.AddingTaskFragment;
import com.example.twinkle94.dealwithit.fragments.OnTypePickListener;
import com.example.twinkle94.dealwithit.interests_page.InterestsActivity;

import java.util.Calendar;

public class NewTaskActivity extends AppCompatActivity implements OnTypePickListener, AddingTaskFragment.OnInterestCallListener, AddingScheduleFragment.OnInterestCallListener
{
    private static final String NAME = NewTaskActivity.class.getSimpleName();
    static final String TASK_FRAGMENT_TAG = "addTaskFragment";
    static final String SCHEDULE_FRAGMENT_TAG = "addScheduleFragment";
    static final int PICK_INTEREST_REQUEST = 9;  // The request code

    private Toolbar toolbar;
    private AbstractAddingFragment addingFragment;
    private AddingTaskFragment addingTaskFragment;
    private AddingScheduleFragment addingScheduleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        initToolbar();
        setHomeButton();

        if (savedInstanceState == null)
        {
           initTaskTypeFragment();
        }
    }

    @Override
    public void onScheduleTypePick(EventType type)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if(fm.findFragmentByTag(SCHEDULE_FRAGMENT_TAG) == null)
        {
            addingScheduleFragment = new AddingScheduleFragment();
        }

        addingFragment = addingScheduleFragment;
        setFragmentTaskType(type);

        ft.replace(R.id.fragment_task_type_container, addingFragment, SCHEDULE_FRAGMENT_TAG);
        ft.commit();
    }

    @Override
    public void onTaskTypePick(EventType type)
    {
        AbstractAddingFragment atf = (AddingTaskFragment) getSupportFragmentManager().findFragmentByTag(TASK_FRAGMENT_TAG);

        if(atf == null)
        {
            addingFragment = addingTaskFragment;
            setFragmentTaskType(type);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_task_type_container, addingFragment, TASK_FRAGMENT_TAG)
                    .commit();
        }
    }

    @Override
    public void onInterestsCall()
    {
        Intent pickContactIntent = new Intent(NewTaskActivity.this, InterestsActivity.class);
        startActivityForResult(pickContactIntent, PICK_INTEREST_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_INTEREST_REQUEST)
        {
            if (resultCode == RESULT_OK)
            {
                int interest_id = data.getIntExtra(InterestsActivity.INTEREST_ID_EXTRA, -1);
                addingFragment.onInterestPicked(interest_id);
            }
        }
    }

    private void initTaskTypeFragment()
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        addingTaskFragment = new AddingTaskFragment();
        addingFragment = addingTaskFragment;
        ft.add(R.id.fragment_task_type_container, addingFragment, TASK_FRAGMENT_TAG);
        ft.commit();
    }

    private void setFragmentTaskType(EventType type)
    {
        Bundle type_bundle = new Bundle();
        type_bundle.putString(AddingTaskFragment.TASK_TYPE, type.toString());
        type_bundle.putInt(AddingTaskFragment.TASK_TYPE_IMAGE, EventType.getImage(type.toString()));
        addingFragment.setArguments(type_bundle);
    }

    //init Toolbar
    private void initToolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setHomeButton()
    {
        if(toolbar != null)
        {
            toolbar.setNavigationIcon(AppCompatResources.getDrawable(this, R.drawable.ic_cancel_button));
            toolbar.setNavigationOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    addingFragment.onCancel();
                   finish();
                }
            });
        }
    }

    public void setInterest(View view)
    {
        addingFragment.setInterest(view);
    }

    public void setTimePicker(View view)
    {
        DialogFragment timePickerFragment = TimePickerFragment.newInstance(view.getId());
        timePickerFragment.show(getSupportFragmentManager(), "timePicker");
    }

    public void setDatePicker(View view)
    {
        DialogFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void onAddTaskItem(View view)
    {
        addingFragment.addSubItem(view);
    }

    public void onPickType(View view)
    {
        addingFragment.pickTypeDialog();
    }

    private void setTaskTime(int hour, int minute, boolean am_pm, int type)
    {
        addingFragment.setTime(hour, minute, am_pm, type);
    }

    private void setTaskDate(int year, int month, int day)
    {
        addingFragment.setDate(year, month, day);
    }

    //Time picker fragment
    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener
    {
        protected static final String TIME_TYPE = "type";
        protected static final int TIME_TYPE_NOT_SET = -1;

        public static TimePickerFragment newInstance(int time_type)
        {
            final Bundle args = new Bundle();

            args.putInt(TIME_TYPE, time_type);

            final TimePickerFragment fragment = new TimePickerFragment();
            fragment.setArguments(args);
            return fragment;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), R.style.MyDialogTheme, this, hour, minute,
                    DateFormat.is24HourFormat(getActivity())); //takes user preference of format from settings.
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute)
        {
            //Get time type
            Bundle args = getArguments();
            int type = args != null ? args.getInt(TIME_TYPE) : TIME_TYPE_NOT_SET;

            // Do something with the time chosen by the user
            ((NewTaskActivity)getActivity()).setTaskTime(hourOfDay, minute, DateFormat.is24HourFormat(getActivity()), type);
        }
    }

    //Date picker fragment
    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
    {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), R.style.MyDialogTheme, this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day)
        {
            // Do something with the date chosen by the user
            ((NewTaskActivity)getActivity()).setTaskDate(year, month, day);
        }
    }
}
