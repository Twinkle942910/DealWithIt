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
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.twinkle94.dealwithit.R;
import com.example.twinkle94.dealwithit.events.type_enums.EventType;
import com.example.twinkle94.dealwithit.fragments.AddingScheduleFragment;
import com.example.twinkle94.dealwithit.fragments.AddingTaskFragment;
import com.example.twinkle94.dealwithit.fragments.OnScheduleTypePickListener;

import java.util.Calendar;

public class NewTaskActivity extends AppCompatActivity implements OnScheduleTypePickListener
{
    private static final String NAME = NewTaskActivity.class.getSimpleName();
    static final String TASK_FRAGMENT_TAG = "addTaskFragment";
    static final String SCHEDULE_FRAGMENT_TAG = "addScheduleFragment";

    private Toolbar toolbar;
    private AddingTaskFragment addingTaskFragment;
    private AddingScheduleFragment addingScheduleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        initToolbar();
        setHomeButton();

        //TODO: change it later(method name and fragment adding)
        if (savedInstanceState == null)
        {
            // only create fragment if activity is started for the first time
           initTaskTypeFragment();

        }
        else
        {
            // do nothing - fragment is recreated automatically
        }


        Log.i(NAME, "onCreate");
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        //TODO: remove fragment here, or onStop().

        Log.i(NAME, "onPause");
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.i(NAME, "onStart");
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        Log.i(NAME, "onStop");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.i(NAME, "onDestroy");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.i(NAME, "onResume");
    }

    @Override
    protected void onRestart()
    {
        super.onRestart();
        Log.i(NAME, "onRestart");
    }

    @Override
    public void onScheduleTypePick(EventType type)
    {
        FragmentManager fm = getSupportFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();

        //TODO: check this, it can recreate instance of fragment a lot of times.
        if(fm.findFragmentByTag(SCHEDULE_FRAGMENT_TAG) == null)
        {
            addingScheduleFragment = new AddingScheduleFragment();
        }

        Bundle schedule_bundle = new Bundle();
        schedule_bundle.putString(AddingScheduleFragment.TASK_TYPE, type.toString());
        addingScheduleFragment.setArguments(schedule_bundle);

        ft.replace(R.id.fragment_task_type_container, addingScheduleFragment, SCHEDULE_FRAGMENT_TAG);
        ft.commit();
    }

    @Override
    public void onTaskTypePick(EventType type)
    {
        AddingTaskFragment atf = (AddingTaskFragment) getSupportFragmentManager().findFragmentByTag(TASK_FRAGMENT_TAG);

        if(atf == null)
        {
            Bundle task_bundle = new Bundle();
            task_bundle.putString(AddingTaskFragment.TASK_TYPE, type.toString());
            addingTaskFragment.setArguments(task_bundle);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_task_type_container, addingTaskFragment, TASK_FRAGMENT_TAG)
                    .commit();
        }
    }

    private void initTaskTypeFragment()
    {
        // Get fragment manager
        FragmentManager fm = getSupportFragmentManager();

        // Begin transaction
        FragmentTransaction ft = fm.beginTransaction();

        // Create the Fragment and add
        addingTaskFragment = new AddingTaskFragment();
        ft.add(R.id.fragment_task_type_container, addingTaskFragment, TASK_FRAGMENT_TAG);
       // ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

        // Commit the changes
        ft.commit();
    }

    //init Toolbar
    private void initToolbar()
    {
        float density = Resources.getSystem().getDisplayMetrics().density;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO: Move this, because it wastes a lot of time when activity loads.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            toolbar.setElevation((4 * density));
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

        if(view.getId() == R.id.button_add_task) addingScheduleFragment.addScheduleTask();
        else  addingTaskFragment.addItem(view);
    }

    public void onPickType(View view)
    {
        if(view.getId() == R.id.task_type) addingTaskFragment.pickTypeDialog();
        else addingScheduleFragment.pickTypeDialog();
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
        if(type == R.id.time_start_layout || type == R.id.time_end_layout) addingTaskFragment.setTime(hour, minute, am_pm, type);
        else addingScheduleFragment.setTime(hour, minute, am_pm, type);
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
