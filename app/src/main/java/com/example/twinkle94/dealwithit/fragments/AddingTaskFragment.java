package com.example.twinkle94.dealwithit.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twinkle94.dealwithit.R;
import com.example.twinkle94.dealwithit.adding_task_page.sub_items.SubTask;
import com.example.twinkle94.dealwithit.adding_task_page.sub_items.Task;
import com.example.twinkle94.dealwithit.background.FetchEventsTask;
import com.example.twinkle94.dealwithit.events.task_types.ToDo;
import com.example.twinkle94.dealwithit.events.type_enums.EventType;
import com.example.twinkle94.dealwithit.util.DateTimeValidator;
import com.example.twinkle94.dealwithit.util.TextValidator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.example.twinkle94.dealwithit.R.id.button_add_comment;
import static com.example.twinkle94.dealwithit.R.id.button_add_interest;
import static com.example.twinkle94.dealwithit.R.id.button_add_sub_task;

public class AddingTaskFragment extends AbstractAddingFragment implements CompoundButton.OnCheckedChangeListener
{
    private static final String NAME = AddingTaskFragment.class.getSimpleName();

    private LinearLayout comment_container_ly;
    private LinearLayout sub_task_container_ly;

    //Data form user.
    private TextInputEditText title_iet;
    private TextInputEditText date_iet;
    private TextInputEditText start_time_iet;
    private TextInputEditText end_time_iet;

    //Transfer data
    private int importance_value;

    private TextValidator titleValidator;
    private TextValidator dateValidator;
    private TextValidator startTimeValidator;
    private TextValidator endTimeValidator;
    private DateTimeValidator dateTimeValidator;

    private View.OnFocusChangeListener inputFocusChangeListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if(task_type != EventType.NO_TYPE)
            type_iet.setText(task_type_output_tv.getText().toString());
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
        return R.layout.fragment_new_task_type;
    }

    @Override
    protected void initializeViews(View fragmentView)
    {
        if(fragmentView != null)
        {
            //Input Views
            title_iet = (TextInputEditText) fragmentView.findViewById(R.id.task_title_input);
            date_iet = (TextInputEditText) fragmentView.findViewById(R.id.task_date_input);
            start_time_iet = (TextInputEditText) fragmentView.findViewById(R.id.task_start_time_input);
            end_time_iet = (TextInputEditText) fragmentView.findViewById(R.id.task_end_time_input);

            //Additional info switch
            final SwitchCompat additional_task_info_sw = (SwitchCompat) fragmentView.findViewById(R.id.additional_task_available);
            additional_task_info_sw.setOnCheckedChangeListener(this);

            //Sub task - Comment containers
            comment_container_ly = (LinearLayout) fragmentView.findViewById(R.id.comment_container);
            sub_task_container_ly = (LinearLayout) fragmentView.findViewById(R.id.sub_tasks_container);
        }
    }

    @Override
    protected void initializeValidators()
    {
        titleValidator = validator(title_iet);
        dateValidator = validator(date_iet);
        startTimeValidator = validator(start_time_iet);
        endTimeValidator = validator(end_time_iet);
        dateTimeValidator = new DateTimeValidator(activity, DateFormat.is24HourFormat(activity));
    }

    @Override
    protected void addValidators()
    {
        title_iet.addTextChangedListener(titleValidator);
        date_iet.addTextChangedListener(dateValidator);
        start_time_iet.addTextChangedListener(startTimeValidator);
        end_time_iet.addTextChangedListener(endTimeValidator);
    }

    @Override
    protected void removeValidators()
    {
        title_iet.removeTextChangedListener(titleValidator);
        date_iet.removeTextChangedListener(dateValidator);
        start_time_iet.removeTextChangedListener(startTimeValidator);
        end_time_iet.removeTextChangedListener(endTimeValidator);
    }

    @Override
    protected void initializeListeners()
    {
        inputFocusChangeListener = new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean b)
            {
                if (!b)
                {
                    switch (view.getId())
                    {
                        case R.id.task_title_input:
                            checkTitleInput();
                            break;

                        case R.id.task_date_input:
                            checkDateInput();
                            break;

                        case R.id.task_start_time_input:
                            checkStartTimeInput();
                            break;

                        case R.id.task_end_time_input:
                            checkEndTimeInput();
                            break;
                    }
                }
            }
        };
    }

    @Override
    protected void setListeners()
    {
        title_iet.setOnFocusChangeListener(inputFocusChangeListener);
        date_iet.setOnFocusChangeListener(inputFocusChangeListener);
        start_time_iet.setOnFocusChangeListener(inputFocusChangeListener);
        end_time_iet.setOnFocusChangeListener(inputFocusChangeListener);
    }

    @Override
    protected void removeListeners()
    {
        title_iet.setOnFocusChangeListener(null);
        date_iet.setOnFocusChangeListener(null);
        start_time_iet.setOnFocusChangeListener(null);
        end_time_iet.setOnFocusChangeListener(null);
    }

    @Override
    protected void replaceScheduleOrTask(EventType type)
    {
        typePickListener.onScheduleTypePick(type);
    }

    @Override
    protected void checkTypeOutputAction(EventType type, boolean isSchedule)
    {
        if(isSchedule)
        {
            taskTypeReplace(type);
            removeOutputTypeValidator();
        }
    }

    @Override
    public void addSubItem(View view)
    {
        switch (view.getId())
        {
            case button_add_comment:
                if (comment_container_ly != null)
                {
                    addSubTaskComment(comment_container_ly);
                }
                break;

            case button_add_sub_task:
                if (sub_task_container_ly != null)
                {
                    addSubTaskComment(sub_task_container_ly);
                }
                break;

            case button_add_interest:
                Toast.makeText(activity, "Looooooool!", Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void saveInput()
    {
        if(!isInputErrors())
        {
            switch (task_type)
            {
                case TODO:

                    ToDo todo = new ToDo(-1,
                            title_iet.getText().toString(),
                            start_time_iet.getText().toString(),
                            end_time_iet.getText().toString(),
                            date_iet.getText().toString(),
                            EventType.TODO,
                            "Waiting",
                            importance_value);

                    FetchEventsTask addingToDB = new FetchEventsTask(activity);
                    addingToDB.execute("add_data", todo);

                    break;



                case NO_TYPE:
                    Toast.makeText(activity, "You should pick a type, bro", Toast.LENGTH_LONG).show();
                    break;
            }

            activity.finish();
        }
        else Toast.makeText(activity, "Check your input!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onTypePickPositive(RadioGroup radioGroup)
    {
        if(radioGroup.getCheckedRadioButtonId() >= 0 && radioGroup.getCheckedRadioButtonId() != R.id.schedule_type_button)
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
                break;

            case R.id.work_tasks_type_button:
                task_type = EventType.WORKTASK;
                task_type_image = EventType.getImage(EventType.WORKTASK.toString());
                break;

            case R.id.todo_type_button:
                task_type = EventType.TODO;
                task_type_image = EventType.getImage(EventType.TODO.toString());
                break;

            case R.id.schedule_type_button:
                task_type = EventType.SCHEDULE;
                task_type_image = EventType.getImage(EventType.SCHEDULE.toString());
                taskTypeReplace(task_type);
                b.dismiss();
                break;
        }
    }

    @Override
    protected void setTimeOutput(int type, SimpleDateFormat output_format, Calendar calendar)
    {
        if(type != -1)
        {
            if(type == R.id.time_start_layout)
            {
                start_time_iet.setText(output_format.format(calendar.getTime()));
            }
            else end_time_iet.setText(output_format.format(calendar.getTime()));
        }
    }

    @Override
    protected void setDateOutput(int year, int month, int day)
    {
        date_iet.setText(day + "/" + (month + 1) + "/" + year);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b)
    {
        final View fragmentView = getView();
        final int[] container_layouts = {R.id.comment_layout, R.id.sub_task_layout, R.id.importance_layout, R.id.interests_layout};

        if(fragmentView != null)
        {
            switch (compoundButton.getId())
            {
                case R.id.use_comment_checkBox:
                    setLayoutVisibility(b, ((LinearLayout)fragmentView.findViewById(container_layouts[0])));
                    break;

                case R.id.use_sub_tasks_checkBox:
                    setLayoutVisibility(b, ((LinearLayout)fragmentView.findViewById(container_layouts[1])));
                    break;

                case R.id.use_importance_checkBox:
                    setLayoutVisibility(b, ((LinearLayout)fragmentView.findViewById(container_layouts[2])));
                    break;

                case R.id.use_interests_checkBox:
                    setLayoutVisibility(b, ((RelativeLayout)fragmentView.findViewById(container_layouts[3])));
                    break;

                case R.id.additional_task_available:
                    final int[] check_buttons = {R.id.comment_check, R.id.sub_tasks_check, R.id.importance_check, R.id.interests_check};

                    setButtonOff(b, check_buttons, fragmentView);
                    setButtonVisibility(b, check_buttons, fragmentView);
                    checkContainerLayoutVisibility(b, container_layouts, fragmentView);
                    setCheckListeners(b, fragmentView);
                    setImportanceBar(b);
                    break;
            }
        }
    }

    @Override
    protected void validate(TextView textView, String text)
    {
        switch (textView.getId())
        {
            case R.id.task_title_input:
                checkTitleInput();
                break;

            case R.id.task_date_input:
                checkDateInput();
                break;

            case R.id.task_start_time_input:
                checkStartTimeInput();
                break;

            case R.id.task_end_time_input:
                checkEndTimeInput();
                break;
        }
    }

    @Override
    protected boolean isInputErrors()
    {
        ViewGroup fragment_view = (ViewGroup) getView();
        boolean error_state = true;

        if(fragment_view != null)
        {
            int inner_views_count = fragment_view.getChildCount();

            for (int fragment_view_position = 0; fragment_view_position < inner_views_count; fragment_view_position++)
            {
                View innerView = fragment_view.getChildAt(fragment_view_position);

                if(innerView instanceof ViewGroup)
                {
                    for (int inner_view_position = 0; inner_view_position < ((ViewGroup) innerView).getChildCount(); inner_view_position++)
                    {
                        if (((ViewGroup) innerView).getChildAt(inner_view_position) instanceof TextInputLayout)
                        {
                            if (TextUtils.isEmpty(((TextInputLayout) ((ViewGroup) innerView).getChildAt(inner_view_position)).getError()) && !TextUtils.isEmpty(((TextInputLayout) ((ViewGroup) innerView).getChildAt(inner_view_position)).getEditText().getText()))
                            {
                                error_state = false;
                                break;
                            }
                        }
                    }
                }

                if(!error_state) break;
            }
        }
        return error_state;
    }

    private void setButtonVisibility(boolean isChecked, int[] resource_array, View fragmentView)
    {
        for(int check_button : resource_array)
        {
            if (isChecked) fragmentView.findViewById(check_button).setVisibility(View.VISIBLE);
            else fragmentView.findViewById(check_button).setVisibility(View.GONE);
        }
    }

    private void setLayoutVisibility(boolean checked, ViewGroup layout)
    {
        if(checked) layout.setVisibility(View.VISIBLE);
        else layout.setVisibility(View.GONE);
    }

    private void checkContainerLayoutVisibility(boolean checked, int[] layouts, View fragmentView)
    {
        int container_layout_element = 1;
        int layout_position = 0;

        for (int container_layout : layouts)
        {
            if (!checked)
            {
                fragmentView.findViewById(container_layout).setVisibility(View.VISIBLE);
            }

            if(!(layout_position == layouts.length - 1 || layout_position == layouts.length - 2))
            {
                final ViewGroup layout = (ViewGroup) ((ViewGroup) fragmentView.findViewById(container_layout)).getChildAt(container_layout_element);
                if (layout.getChildCount() != 0) layout.removeAllViews();
            }

            fragmentView.findViewById(container_layout).findViewById(container_layout).setVisibility(View.GONE);

            layout_position++;
        }
    }

    private void setButtonOff(boolean checked, int[] buttons, View fragmentView)
    {
        int check_button_element = 0;
        if (!checked)
        {
            for (int check_button : buttons)
            {
                CheckBox check = (CheckBox) ((LinearLayout) fragmentView.findViewById(check_button)).getChildAt(check_button_element);
                if(check.isChecked()) check.setChecked(false);
            }
        }
    }

    private void setCheckListeners(boolean b, View fragmentView)
    {
        final CheckBox check_comment = ((CheckBox) fragmentView.findViewById(R.id.use_comment_checkBox));
        final CheckBox check_sub_task = ((CheckBox) fragmentView.findViewById(R.id.use_sub_tasks_checkBox));
        final CheckBox check_importance = ((CheckBox) fragmentView.findViewById(R.id.use_importance_checkBox));
        final CheckBox check_interest = ((CheckBox) fragmentView.findViewById(R.id.use_interests_checkBox));

        if (b)
        {
            check_comment.setOnCheckedChangeListener(this);
            check_sub_task.setOnCheckedChangeListener(this);
            check_importance.setOnCheckedChangeListener(this);
            check_interest.setOnCheckedChangeListener(this);
        } else {
            check_comment.removeOnLayoutChangeListener(null);
            check_sub_task.setOnCheckedChangeListener(null);
            check_importance.setOnCheckedChangeListener(null);
            check_interest.setOnCheckedChangeListener(null);
        }
    }

    private void setImportanceBar(boolean checked)
    {
        importance_value = 0;

        final View fragmentView = getView();

        if(fragmentView != null)
        {
            final SeekBar importance_setting = (SeekBar) fragmentView.findViewById(R.id.importance_value);
            final TextView importance_percent_tv = (TextView) fragmentView.findViewById(R.id.importance_percent_value);
            final TextView importance_type_tv = (TextView) fragmentView.findViewById(R.id.importance_type);

            if(checked)
            {
                importance_setting.setOnSeekBarChangeListener(new ProgressChecker()
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
                });
            }
            else {
                importance_setting.setProgress(0);
                importance_setting.setOnSeekBarChangeListener(null);
            }
        }
    }

    private void checkTitleInput()
    {
        final View fragmentView = getView();

        TextInputLayout titleInputLayout = null;

        if(fragmentView != null) titleInputLayout = (TextInputLayout) fragmentView.findViewById(R.id.title_input_layout);

        setInputError(!checkIfTextEmpty(title_iet), titleInputLayout, getString(R.string.empty_error));
    }

    private void checkStartTimeInput()
    {
        final View fragmentView = getView();

        TextInputLayout startInputLayout = null;

        if(fragmentView != null)
        {
            startInputLayout = (TextInputLayout) fragmentView.findViewById(R.id.start_time_input_layout);
        }

        if (checkIfTextEmpty(start_time_iet)) setInputError(false, startInputLayout, getString(R.string.empty_error));
        else setInputError(dateTimeValidator.validateTime(start_time_iet.getText().toString()), startInputLayout, dateTimeValidator.timeErrorMessage());
    }

    private void checkEndTimeInput()
    {
        final View fragmentView = getView();

        TextInputLayout endInputLayout = null;

        if(fragmentView != null)
        {
            endInputLayout = (TextInputLayout) fragmentView.findViewById(R.id.end_time_input_layout);
        }

        if (checkIfTextEmpty(end_time_iet)) setInputError(false, endInputLayout, getString(R.string.empty_error));
        else setInputError(dateTimeValidator.validateTime(end_time_iet.getText().toString()), endInputLayout, dateTimeValidator.timeErrorMessage());
    }

    private void checkDateInput()
    {
        final View fragmentView = getView();

        TextInputLayout dateInputLayout = null;

        if(fragmentView != null)
        {
            dateInputLayout = (TextInputLayout) fragmentView.findViewById(R.id.date_input_layout);
        }

        if (checkIfTextEmpty(date_iet)) setInputError(false, dateInputLayout, getString(R.string.empty_error));
        else setInputError(dateTimeValidator.validateDate(date_iet.getText().toString()), dateInputLayout, dateTimeValidator.dateErrorMessage());
    }

    private void addSubTaskComment(final LinearLayout container_layout)
    {
        SubTask subTask = new Task(activity, container_layout, R.layout.sub_task_comment_item);
        subTask.addView();
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
