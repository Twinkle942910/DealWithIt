package com.example.twinkle94.dealwithit.adding_task_page;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.res.Resources;
import android.os.Build;
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
import com.example.twinkle94.dealwithit.fragments.AddingTaskFragment;

import java.util.Calendar;

public class NewTaskActivity extends AppCompatActivity
{
    private Toolbar toolbar;
    private AddingTaskFragment addingTaskFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        initToolbar();
        setHomeButton();

        //TODO: change it later(method name and fragment adding)
        initFragment();
    }

    private void initFragment()
    {
        // Get fragment manager
        FragmentManager fm = getSupportFragmentManager();

        // Begin transaction
        FragmentTransaction ft = fm.beginTransaction();

        // Create the Fragment and add
        addingTaskFragment = new AddingTaskFragment();
        ft.add(R.id.fragment_task_type, addingTaskFragment, "addTaskFragment");

        // Commit the changes
        ft.commit();
    }

    //init Toolbar
    private void initToolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO: Move this, because it wastes a lot of time when activity loads.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            toolbar.setElevation((4 * Resources.getSystem().getDisplayMetrics().density));
        }
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
                   finish();
                }
            });
        }
    }

    //TODO: Figure out more about communication between fragment and activity(and 2 fragments and activity(of activity))
    public void onAddTaskItem(View view)
    {
        addingTaskFragment.addItem(view);
    }

    public void onPickType(View view)
    {
        addingTaskFragment.pickTypeDialog();
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

    private void setTaskTime(int hour, int minute, boolean am_pm, int type)
    {
        addingTaskFragment.setTime(hour, minute, am_pm, type);
    }

    private void setTaskDate(int year, int month, int day)
    {
        addingTaskFragment.setDate(year, month, day);
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
