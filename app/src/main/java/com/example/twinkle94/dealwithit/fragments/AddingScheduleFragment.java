package com.example.twinkle94.dealwithit.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twinkle94.dealwithit.R;
import com.example.twinkle94.dealwithit.adding_task_page.NewTaskActivity;
import com.example.twinkle94.dealwithit.adding_task_page.sub_items.ScheduleTask;
import com.example.twinkle94.dealwithit.adding_task_page.sub_items.SubTask;
import com.example.twinkle94.dealwithit.background.FetchEventsTask;
import com.example.twinkle94.dealwithit.events.Interest;
import com.example.twinkle94.dealwithit.events.task_types.Schedule;
import com.example.twinkle94.dealwithit.events.type_enums.EventType;
import com.example.twinkle94.dealwithit.util.TextValidator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddingScheduleFragment extends Fragment
{
    private static final String NAME = AddingScheduleFragment.class.getSimpleName();
    private static final int LAYOUT = R.layout.fragment_new_schedule_type;

    public static final String TASK_TYPE = "schedule_type";
    private static final String TYPE_NOT_SET = EventType.NO_TYPE.toString();

    private NewTaskActivity activity;

    private TextView task_type_output_tv;
    private TextInputEditText type_iet;

    private LinearLayout task_container_ly;

    //Validators
    private TextValidator outputTypeValidator;
    private TextValidator inputTypeValidator;

    private EventType task_type = EventType.NO_TYPE;

    private String date;

    private List<Schedule> scheduleList;

    private TextView start_end_time_tv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        Log.i(NAME, "onCreate");
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.activity = (NewTaskActivity) context;

        Log.i(NAME, "onAttach");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        final View viewHierarchy = inflater.inflate(LAYOUT, container, false);

        task_type_output_tv = (TextView) viewHierarchy.findViewById(R.id.schedule_task_type);
        type_iet = (TextInputEditText) viewHierarchy.findViewById(R.id.schedule_task_type_input);
        initializeDayPicker(viewHierarchy);

        task_container_ly = (LinearLayout) viewHierarchy.findViewById(R.id.task_container);

        Bundle bundle = getArguments();
        String type = bundle != null ? bundle.getString(TASK_TYPE, TYPE_NOT_SET) : TYPE_NOT_SET;

        if (!type.equals(TYPE_NOT_SET))
        {
            setInputType(type);
            setOutputType(type);
        }

        Log.i(NAME, "onCreateView");

        return viewHierarchy;
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

                    date = setFullDay((int)view.getTag());
                    ((TextView) view).setText(date);
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        Log.i(NAME, "onActivityCreated");
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Log.i(NAME, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeValidators();
        Log.i(NAME, "onResume");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        Log.i(NAME, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();

        type_iet.removeTextChangedListener(inputTypeValidator);
        type_iet.setOnFocusChangeListener(null);

        Log.i(NAME, "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(NAME, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(NAME, "onDetach");
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        Log.i(NAME, "onDestroyView");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu_adding_task_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.save)
        {
            addScheduleToDB();
            activity.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void addScheduleToDB()
    {
        FetchEventsTask addingToDB = new FetchEventsTask(activity);
        List<Interest> interestList = new ArrayList<>();

        interestList.add(new Interest(1, 1, "Study", 73));
        interestList.add(new Interest(2, 1, "Book", 83));
        interestList.add(new Interest(3, 1, "School", 12));

        for (Schedule schedule : scheduleList)
        {
            schedule.setListInterests(interestList);
            addingToDB.execute("add_data", schedule);
        }
    }

    public void pickTypeDialog()
    {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.dialog_type_choice, null);
        dialogBuilder.setView(dialogView);

        dialogBuilder.setTitle(R.string.pick_type_dialog_title);

        final RadioGroup radioGroup = (RadioGroup) dialogView.findViewById(R.id.type_group);

        dialogBuilder.setPositiveButton(getString(R.string.dialog_ok), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
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
        });

        dialogBuilder.setNegativeButton(getString(R.string.dialog_cancel), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                dialog.dismiss();
            }
        });

        final AlertDialog b = dialogBuilder.create();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId)
                {
                    case R.id.birthday_type_button:
                        task_type = EventType.BIRTHDAY;
                        callTaskType();
                        b.dismiss();
                        break;

                    case R.id.work_tasks_type_button:
                        task_type = EventType.WORKTASK;
                        callTaskType();
                        b.dismiss();
                        break;

                    case R.id.todo_type_button:
                        task_type = EventType.TODO;
                        callTaskType();
                        b.dismiss();
                        break;

                    case R.id.schedule_type_button:
                        task_type = EventType.SCHEDULE;
                        break;
                }
            }
        });

        b.show();
    }

    private void initScheduleTaskList()
    {
        if(task_container_ly.getChildCount() == 0)
        {
            scheduleList = new ArrayList<>();
        }
    }


    public void addScheduleTask()
    {
        initScheduleTaskList();

        //TODO: Better to save in list?
        SubTask subTask = new ScheduleTask(activity, task_container_ly, R.layout.schedule_task_item, date, scheduleList);
        ((ScheduleTask)subTask).setOnTimeSetListener(new ScheduleTask.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TextView time_tv, View view)
            {
                start_end_time_tv = time_tv;
                activity.setTimePicker(view);
            }
        });
        subTask.addView();
    }

    //TODO: make class for time operations.
    public void setTime(int hour, int minute, boolean am_pm, int type)
    {
        Calendar calendar = Calendar.getInstance();
        String myFormatTime;

        //TODO: use callback for other purposes (error check - validation).
       // SubTaskCallback subTaskCallback = (SubTaskCallback) subTask;

        if (!am_pm)
        {
            myFormatTime = "hh:mm a";
        }
        else
        {
            myFormatTime = "kk:mm";
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormatTime, Locale.US);

        //TODO: when it's 00:12 or something, picker returns 24 hours.
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        start_end_time_tv.setText(simpleDateFormat.format(calendar.getTime()));
    }

    private void callTaskType()
    {
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try
        {
            OnScheduleTypePickListener typePickListener = (OnScheduleTypePickListener) activity;
            typePickListener.onTaskTypePick(task_type);
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString()
                    + " must implement OnScheduleTypePickListener");
        }
    }

    //TODO: compare with task method. Do we need type parameter when we have task_type field?
    private void callAddingScheduleFragment(EventType type)
    {
        try
        {
            OnScheduleTypePickListener typePickListener = (OnScheduleTypePickListener) activity;
            typePickListener.onTaskTypePick(type);
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString()
                    + " must implement OnScheduleTypePickListener");
        }
    }

    private void setOutputType(String type)
    {
        task_type_output_tv.setText(type);
        task_type_output_tv.setTextColor(ContextCompat.getColor(activity, EventType.getColor(type)));
    }

    private void setInputType(String type)
    {
        type_iet.setText(type);
    }

    private void initializeValidators()
    {
        final View fragmentView = getView();

        if(fragmentView != null)
        {
            final TextInputLayout task_type_layout = (TextInputLayout) fragmentView.findViewById(R.id.schedule_task_type_input_layout);

            outputTypeValidator = new TextValidator(task_type_output_tv)
            {
                @Override
                public void validate(TextView textView, String text)
                {
                    checkTypeOutput(text);
                }
            };

            inputTypeValidator = new TextValidator(type_iet)
            {
                @Override
                public void validate(TextView textView, String text)
                {
                    checkTypeInput(text, task_type_layout);
                }
            };

            task_type_output_tv.addTextChangedListener(outputTypeValidator);
            type_iet.addTextChangedListener(inputTypeValidator);

            type_iet.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (!b) {
                        if (checkIfTextEmpty(type_iet))
                            task_type_layout.setError(getString(R.string.empty_error));
                        else checkTypeInput(type_iet.getText().toString(), task_type_layout);
                    }
                }
            });
        }
    }

    private boolean checkIfTextEmpty(TextInputEditText textInput)
    {
        return TextUtils.isEmpty(textInput.getText());
    }

    //TODO: Make it simpler(refactor).
    private void checkTypeInput(String text, TextInputLayout task_type_layout)
    {
        boolean fullWord = false;

        for (EventType type : EventType.values())
        {
            if (text.toLowerCase().equals(type.toString().toLowerCase()))
            {
                task_type_layout.setErrorEnabled(false);
                task_type_layout.setError(null);
                task_type = type;

                fullWord = true;
                break;
            }
            else
            {
                task_type_layout.setError(getString(R.string.type_error));
                task_type = EventType.NO_TYPE;
            }
        }
        if(fullWord) {
            Log.e(NAME, task_type.toString());
            setOutputType(task_type.toString());
        }
    }

    private void checkTypeOutput(String outputType)
    {
        EventType type = EventType.SCHEDULE;

        boolean isSchedule = outputType.toLowerCase().equals(type.toString().toLowerCase());

        if(!isSchedule)
        {
            //TODO: refactor this shitty thing
            switch (outputType)
            {
                case "ToDo":
                    callAddingScheduleFragment(EventType.TODO);
                    break;

                case "Birthday":
                    callAddingScheduleFragment(EventType.BIRTHDAY);
                    break;

                case "Work Task":
                    callAddingScheduleFragment(EventType.WORKTASK);
                    break;
            }

            task_type_output_tv.removeTextChangedListener(outputTypeValidator);
        }

        Log.e(NAME, isSchedule + " checkTypeOutput");
    }
}
