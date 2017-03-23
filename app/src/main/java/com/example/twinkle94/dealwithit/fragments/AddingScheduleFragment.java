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
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twinkle94.dealwithit.R;
import com.example.twinkle94.dealwithit.adding_task_page.NewTaskActivity;
import com.example.twinkle94.dealwithit.events.task_types.Schedule;
import com.example.twinkle94.dealwithit.events.type_enums.EventType;
import com.example.twinkle94.dealwithit.events.type_enums.ScheduleType;
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

    //TODO: maybe it should be in DateTime format?
    private TextView start_time_tv;
    private TextView end_time_tv;

    private ScheduleType scheduleType  = ScheduleType.NO_TYPE;
    private int importance_value;
    private String date;

    private List<Schedule> schedule_tasks_list;

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
            //addTask();
            activity.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    public void pickScheduleTypeDialog(final TextView task_type)
    {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.dialog_schedule_type_choice, null);
        dialogBuilder.setView(dialogView);

        dialogBuilder.setTitle(R.string.pick_schedule_type_dialog_title);

        final RadioGroup radioGroup = (RadioGroup) dialogView.findViewById(R.id.schedule_type_group);

        dialogBuilder.setPositiveButton(getString(R.string.dialog_ok), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                if(radioGroup.getCheckedRadioButtonId() >= 0)
                {
                   task_type.setText(scheduleType.toString());
                   // scheduleType = ScheduleType.NO_TYPE;
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
                    case R.id.lesson_type_button:
                        scheduleType = ScheduleType.LESSON;
                        break;

                    case R.id.lab_work_type_button:
                        scheduleType = ScheduleType.LABORATORY_WORK;
                        break;

                    case R.id.exam_type_button:
                        scheduleType = ScheduleType.EXAM;
                        break;
                }
            }
        });

        b.show();
    }

    //TODO: Make new class for creating sub view
    public void addScheduleTask()
    {
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View scheduleTaskView = layoutInflater.inflate(R.layout.schedule_task_item, task_container_ly, false);

        //Up panel views
        final TextView task_number_tv = (TextView) scheduleTaskView.findViewById(R.id.schedule_task_number);
        final ImageView task_expand_collapse_iv = (ImageView) scheduleTaskView.findViewById(R.id.schedule_expand_collapse_task_icon);

        //=======================================Input views==============================================
        //Title view
        final TextInputEditText task_content_tv = (TextInputEditText) scheduleTaskView.findViewById(R.id.schedule_task_content);

        //Type views
        final RelativeLayout task_type_rl = (RelativeLayout)scheduleTaskView.findViewById(R.id.schedule_type_title_section);
        final TextView task_type_tv = (TextView) scheduleTaskView.findViewById(R.id.schedule_type_of_task);

        //Time views
        final RelativeLayout task_start_time_rl = (RelativeLayout)scheduleTaskView.findViewById(R.id.schedule_start_time_title_section);
        final RelativeLayout task_end_time_rl = (RelativeLayout)scheduleTaskView.findViewById(R.id.schedule_end_time_title_section);

        final TextView task_start_time_tv = (TextView) scheduleTaskView.findViewById(R.id.schedule_start_time);
        final TextView task_end_time_tv = (TextView) scheduleTaskView.findViewById(R.id.schedule_end_time);

        //Interest views
        final RelativeLayout task_interests_rl = (RelativeLayout) scheduleTaskView.findViewById(R.id.schedule_interests_title_section);
        final TextView task_interests_tv = (TextView) scheduleTaskView.findViewById(R.id.schedule_interests);

        //Importance views
        final SeekBar importance_setting_sb = (SeekBar) scheduleTaskView.findViewById(R.id.importance);
        final TextView importance_percent_tv = (TextView) scheduleTaskView.findViewById(R.id.importance_percent_value);
        final TextView importance_type_tv = (TextView) scheduleTaskView.findViewById(R.id.importance_type);
        //===============================================================================================

        //Down panel buttons
        final TextView task_done_tv = (TextView) scheduleTaskView.findViewById(R.id.schedule_input_done);
        final TextView task_edit_tv = (TextView) scheduleTaskView.findViewById(R.id.schedule_input_edit);
        final TextView task_delete_tv = (TextView) scheduleTaskView.findViewById(R.id.schedule_input_delete);

        if(task_container_ly.getChildCount() == 0)
        {
            schedule_tasks_list = new ArrayList<>();
        }

        task_type_tv.setText(scheduleType.toString());
        task_number_tv.setText(String.format(Locale.US, "%d", task_container_ly.getChildCount() + 1));

        importance_value = 0;

        final TextValidator taskTitleValidator = new TextValidator(task_content_tv)
        {
            @Override
            public void validate(TextView textView, String text)
            {
                checkIfTaskContentEmpty(task_content_tv, scheduleTaskView);
            }
        };

        final TextValidator taskTypeValidator = new TextValidator(task_type_tv)
        {
            @Override
            public void validate(TextView textView, String text)
            {
                checkIfTypeEmpty(task_type_tv, scheduleTaskView);
            }
        };

        final TextValidator taskStartTimeValidator = new TextValidator(task_start_time_tv)
        {
            @Override
            public void validate(TextView textView, String text)
            {
                checkIfStartTimeEmpty(task_start_time_tv, scheduleTaskView);
            }
        };

        final TextValidator taskEndTimeValidator = new TextValidator(task_end_time_tv)
        {
            @Override
            public void validate(TextView textView, String text)
            {
                checkIfEndTimeEmpty(task_end_time_tv, scheduleTaskView);
            }
        };

        final View.OnClickListener taskExpandCollapse = new View.OnClickListener()
        {
            boolean isExpanded = false;

            @Override
            public void onClick(View v)
            {
                if(isExpanded)
                {
                    task_expand_collapse_iv.setImageResource(R.drawable.ic_expand_less);
                    isExpanded = false;
                }
                else
                {
                    task_expand_collapse_iv.setImageResource(R.drawable.ic_expand_more);
                    isExpanded = true;
                }

                expandCollapseView(scheduleTaskView, isExpanded);
            }
        };

        final ProgressChecker taskImportanceListener = new ProgressChecker()
        {
            @Override
            public void progress(int i)
            {
                importance_value = i;
                importance_percent_tv.setText(importance_value + "%");

                if (importance_value >= 60)
                    importance_type_tv.setText(R.string.important_text);
                else importance_type_tv.setText(R.string.not_important_text);
            }
        };

        final View.OnClickListener taskClickListener = new View.OnClickListener()
        {
            boolean isDone = false;

            @Override
            public void onClick(View v)
            {
                int item_index = task_container_ly.indexOfChild(scheduleTaskView);

                switch (v.getId())
                {
                    case R.id.schedule_type_title_section:
                        pickScheduleTypeDialog(task_type_tv);
                        break;

                    case R.id.schedule_start_time_title_section:
                        activity.setTimePicker(v);
                        start_time_tv = task_start_time_tv;
                        break;


                    case R.id.schedule_end_time_title_section:
                        activity.setTimePicker(v);
                        end_time_tv = task_end_time_tv;
                        break;

                    case R.id.schedule_interests_title_section:
                        //fake method
                        task_interests_tv.setText("3 interests");
                        break;

                    case R.id.schedule_input_delete:
                        ((LinearLayout) scheduleTaskView.getParent()).removeView(scheduleTaskView);
                        subItemNumberReorder();
                        v.setOnClickListener(null);

                        //disable expand_collapse listener
                        task_expand_collapse_iv.setOnClickListener(null);

                        //if not done, then remove all listeners.
                        if(!isDone)
                        {
                            disableScheduleTaskListeners(task_content_tv, task_type_rl, task_start_time_rl, task_end_time_rl, task_interests_rl, importance_setting_sb);
                            removeScheduleTaskValidators(task_content_tv, taskTitleValidator, task_type_tv, taskTypeValidator, task_start_time_tv, taskStartTimeValidator, task_end_time_tv, taskEndTimeValidator);

                            task_done_tv.setOnClickListener(null);
                            task_edit_tv.setOnClickListener(null);
                        }
                        else removeItemFromLists(item_index);

                        break;

                    case R.id.schedule_input_edit:
                        //Better to write here (isDone) or put adding listener when it's done in done_button case?
                        enableScheduleTaskListeners(task_content_tv, task_type_rl, task_start_time_rl, task_end_time_rl, task_interests_rl, importance_setting_sb, this, taskImportanceListener);
                        addScheduleTaskValidators(task_content_tv, task_type_tv, task_start_time_tv, task_end_time_tv, taskTitleValidator, taskTypeValidator, taskStartTimeValidator, taskEndTimeValidator);

                        task_done_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.colorNotificationText));

                        v.setOnClickListener(null);
                        task_done_tv.setOnClickListener(this);

                        removeItemFromLists(item_index);

                        break;

                    case R.id.schedule_input_done:

                        boolean isTitleEmpty  = checkIfTaskContentEmpty(task_content_tv, scheduleTaskView);
                        boolean isTypeEmpty  = checkIfTypeEmpty(task_type_tv, scheduleTaskView);
                        boolean isStartTimeEmpty  = checkIfStartTimeEmpty(task_start_time_tv, scheduleTaskView);
                        boolean isEndTimeEmpty  =  checkIfEndTimeEmpty(task_end_time_tv, scheduleTaskView);

                        if (isTitleEmpty || isTypeEmpty || isStartTimeEmpty || isEndTimeEmpty)
                        {
                            task_done_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.colorError));
                        }
                        else
                        {
                            addItemToLists(task_content_tv, scheduleType, start_time_tv, end_time_tv);

                            task_done_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTypeBirthday));
                            disableScheduleTaskListeners(task_content_tv, task_type_rl, task_start_time_rl, task_end_time_rl, task_interests_rl, importance_setting_sb);
                            removeScheduleTaskValidators(task_content_tv, taskTitleValidator, task_type_tv, taskTypeValidator, task_start_time_tv, taskStartTimeValidator, task_end_time_tv, taskEndTimeValidator);

                            v.setOnClickListener(null);
                            task_edit_tv.setOnClickListener(this);

                            isDone = true;
                        }
                        break;
                }
            }
        };

        //Input listeners

        //Expand_collapse listener
        task_expand_collapse_iv.setOnClickListener(taskExpandCollapse);

        //Down panel listeners
        task_delete_tv.setOnClickListener(taskClickListener);
        task_done_tv.setOnClickListener(taskClickListener);
        //All other listeners
        enableScheduleTaskListeners(task_content_tv, task_type_rl, task_start_time_rl, task_end_time_rl, task_interests_rl, importance_setting_sb, taskClickListener, taskImportanceListener);
        //Validators
        addScheduleTaskValidators(task_content_tv, task_type_tv, task_start_time_tv, task_end_time_tv, taskTitleValidator, taskTypeValidator, taskStartTimeValidator, taskEndTimeValidator);

        task_container_ly.addView(scheduleTaskView);
    }

    private void removeScheduleTaskValidators(TextInputEditText task_content_tv, TextValidator taskTitleValidator, TextView task_type_tv, TextValidator taskTypeValidator, TextView task_start_time_tv, TextValidator taskStartTimeValidator, TextView task_end_time_tv, TextValidator taskEndTimeValidator) {
        //Validators
        task_content_tv.removeTextChangedListener(taskTitleValidator);
        task_type_tv.removeTextChangedListener(taskTypeValidator);
        task_start_time_tv.removeTextChangedListener(taskStartTimeValidator);
        task_end_time_tv.removeTextChangedListener(taskEndTimeValidator);
    }

    private void addScheduleTaskValidators(TextInputEditText task_content_tv, TextView task_type_tv, TextView task_start_time_tv, TextView task_end_time_tv, TextValidator taskTitleValidator, TextValidator taskTypeValidator, TextValidator taskStartTimeValidator, TextValidator taskEndTimeValidator) {
        //Validators
        task_content_tv.addTextChangedListener(taskTitleValidator);
        task_type_tv.addTextChangedListener(taskTypeValidator);
        task_start_time_tv.addTextChangedListener(taskStartTimeValidator);
        task_end_time_tv.addTextChangedListener(taskEndTimeValidator);
    }

    //TODO: make less parameters.
    private void enableScheduleTaskListeners(TextInputEditText task_content_tv, RelativeLayout task_type_rl, RelativeLayout task_start_time_rl, RelativeLayout task_end_time_rl, RelativeLayout task_interests_rl, SeekBar importance_setting_sb, View.OnClickListener taskClickListener, ProgressChecker taskImportanceListener) {
        //enable task content input
        task_content_tv.setEnabled(true);

        //Type listener
        task_type_rl.setOnClickListener(taskClickListener);

        //Time listeners
        task_start_time_rl.setOnClickListener(taskClickListener);
        task_end_time_rl.setOnClickListener(taskClickListener);

        //Interest listeners
        task_interests_rl.setOnClickListener(taskClickListener);

        //Importance listener
        importance_setting_sb.setOnSeekBarChangeListener(taskImportanceListener);
        importance_setting_sb.setEnabled(true);
    }

    //TODO: make less parameters.
    private void disableScheduleTaskListeners(TextInputEditText task_content_tv, RelativeLayout task_type_rl, RelativeLayout task_start_time_rl, RelativeLayout task_end_time_rl, RelativeLayout task_interests_rl, SeekBar importance_setting_sb) {
        //disable task content input
        task_content_tv.setEnabled(false);

        //disable type listener
        task_type_rl.setOnClickListener(null);

        //disable time listeners
        task_start_time_rl.setOnClickListener(null);
        task_end_time_rl.setOnClickListener(null);

        //disable interest listeners
        task_interests_rl.setOnClickListener(null);

        //disable importance bar listeners
        importance_setting_sb.setOnSeekBarChangeListener(null);
        importance_setting_sb.setEnabled(false);
    }

    private void addItemToLists(TextView content, ScheduleType scheduleType, TextView start_time, TextView end_time)
    {
        schedule_tasks_list.add(new Schedule(1,
                content.getText().toString(),
                scheduleType,
                start_time.getText().toString(),
                end_time.getText().toString(),
                date,
                "Waiting",
                importance_value));

        Log.e(NAME, "Schedule " + schedule_tasks_list.size() + " added");
    }

    private void removeItemFromLists(int item_index)
    {
        schedule_tasks_list.remove(item_index);
        Log.e(NAME, "Schedule " + item_index + 1 + " removed");
    }

    private boolean checkIfTaskContentEmpty(TextInputEditText task_content_tv, View subTaskView)
    {
        if (!TextUtils.isEmpty(task_content_tv.getText()))
        {
            ((TextView) subTaskView.findViewById(R.id.schedule_content_title)).setTextColor(ContextCompat.getColor(getContext(), R.color.colorNotificationText));
            task_content_tv.setHintTextColor(ContextCompat.getColor(getContext(), R.color.colorNotificationText));
            return false;
        }
        else
        {
            ((TextView) subTaskView.findViewById(R.id.schedule_content_title)).setTextColor(ContextCompat.getColor(getContext(), R.color.colorTypeToDo));
            task_content_tv.setHintTextColor(ContextCompat.getColor(getContext(), R.color.colorTypeToDo));
            return true;
        }
    }

    private boolean checkIfTypeEmpty(TextView task_type_tv, View subTaskView)
    {
        if(!task_type_tv.getText().toString().equals(ScheduleType.NO_TYPE.toString()))
        {
            ((TextView) subTaskView.findViewById(R.id.schedule_type_title)).setTextColor(ContextCompat.getColor(getContext(), R.color.colorNotificationText));
            task_type_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.colorNotificationText));
            return false;
        }
        else {
            ((TextView) subTaskView.findViewById(R.id.schedule_type_title)).setTextColor(ContextCompat.getColor(getContext(), R.color.colorTypeToDo));
            task_type_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTypeToDo));
            return true;
        }
    }

    private boolean checkIfStartTimeEmpty(TextView task_start_time_tv, View subTaskView)
    {
        if(!task_start_time_tv.getText().toString().equals(getString(R.string.schedule_task_pick_time)))
        {
            ((TextView) subTaskView.findViewById(R.id.schedule_start_time_title)).setTextColor(ContextCompat.getColor(getContext(), R.color.colorNotificationText));
            task_start_time_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.colorNotificationText));
            return false;
        }
        else {
            ((TextView) subTaskView.findViewById(R.id.schedule_start_time_title)).setTextColor(ContextCompat.getColor(getContext(), R.color.colorTypeToDo));
            task_start_time_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTypeToDo));
            return true;
        }
    }

    private boolean checkIfEndTimeEmpty(TextView task_end_time_tv, View subTaskView)
    {
        if(!task_end_time_tv.getText().toString().equals(getString(R.string.schedule_task_pick_time)))
        {
            ((TextView) subTaskView.findViewById(R.id.schedule_end_time_title)).setTextColor(ContextCompat.getColor(getContext(), R.color.colorNotificationText));
            task_end_time_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.colorNotificationText));
            return false;
        }
        else {
            ((TextView) subTaskView.findViewById(R.id.schedule_end_time_title)).setTextColor(ContextCompat.getColor(getContext(), R.color.colorTypeToDo));
            task_end_time_tv.setTextColor(ContextCompat.getColor(getContext(), R.color.colorTypeToDo));
            return true;
        }
    }

    public void setTime(int hour, int minute, boolean am_pm, int type)
    {
        Calendar calendar = Calendar.getInstance();
        String myFormatTime;

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

        if(type != -1)
        {
            if(type == R.id.schedule_start_time_title_section)
            {
                start_time_tv.setText(simpleDateFormat.format(calendar.getTime()));
            }
            else end_time_tv.setText(simpleDateFormat.format(calendar.getTime()));
        }
    }

    private void subItemNumberReorder()
    {
        for(int i = 0; i < task_container_ly.getChildCount(); i++)
        {
            final View thisChild = task_container_ly.getChildAt(i);
            ((TextView) thisChild.findViewById(R.id.schedule_task_number)).setText(String.format(Locale.US, "%d", i + 1));
        }
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

    //TODO: refactoring!
    public void expandCollapseView(final View v, final boolean isExpanded)
    {
      final int initialHeight;
        final int targetHeight;

        if(isExpanded) {
            initialHeight = v.getMeasuredHeight();
          targetHeight = (int) (v.getContext().getResources().getDisplayMetrics().density * 60);
        }
        else {
            initialHeight = (int) (v.getContext().getResources().getDisplayMetrics().density * 60);

            v.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
          targetHeight = v.getMeasuredHeight();
        }

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t)
            {
                int newHeight;
                if(isExpanded)
                {
                    newHeight = (int)(initialHeight - (((initialHeight - targetHeight) * interpolatedTime)));
                }
                else
                {
                    if(interpolatedTime == 1)
                    {
                        newHeight = LinearLayout.LayoutParams.WRAP_CONTENT;
                    }
                    else newHeight = (int)(initialHeight + ((targetHeight - initialHeight) * interpolatedTime));
                }

                v.getLayoutParams().height = newHeight;
                v.requestLayout();
                Log.e(NAME, String.valueOf(v.getLayoutParams().height));
            }

            @Override
            public boolean willChangeBounds()
            {
                return true;
            }
        };

        if(isExpanded) {
            // 1dp/ms
            a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        }
        else {
            // 1dp/ms
            a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        }
        v.startAnimation(a);
    }

    abstract class ProgressChecker implements SeekBar.OnSeekBarChangeListener
    {
        abstract void progress(int i);

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b)
        {
            progress(i);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar)
        {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar)
        {

        }
    }

}
