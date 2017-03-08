package com.example.twinkle94.dealwithit.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twinkle94.dealwithit.R;
import com.example.twinkle94.dealwithit.adding_task_page.NewTaskActivity;
import com.example.twinkle94.dealwithit.background.FetchEventsTask;
import com.example.twinkle94.dealwithit.events.Comment;
import com.example.twinkle94.dealwithit.events.Interest;
import com.example.twinkle94.dealwithit.events.Sub_task;
import com.example.twinkle94.dealwithit.events.task_types.ToDo;
import com.example.twinkle94.dealwithit.events.type_enums.EventType;
import com.example.twinkle94.dealwithit.util.DateTimeValidator;
import com.example.twinkle94.dealwithit.util.TextValidator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.example.twinkle94.dealwithit.R.id.button_add_comment;
import static com.example.twinkle94.dealwithit.R.id.button_add_interest;
import static com.example.twinkle94.dealwithit.R.id.button_add_sub_task;

public class AddingTaskFragment extends Fragment implements CompoundButton.OnCheckedChangeListener
{
    private static final String NAME = AddingTaskFragment.class.getSimpleName();
    private static final int LAYOUT = R.layout.fragment_new_task_type;

    private NewTaskActivity activity;

    private LinearLayout comment_container_ly;
    private LinearLayout sub_task_container_ly;

    private TextView task_type_output_tv;

    //Data form user.
    private TextInputEditText type_iet;
    private TextInputEditText title_iet;
    private TextInputEditText date_iet;
    private TextInputEditText start_time_iet;
    private TextInputEditText end_time_iet;

    private List<Comment> commentList;
    private List<Sub_task> subTaskList;
    private List<Interest> interestList;

    //Transfer data
    private String task_type;
    private int importance_value;

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
        Log.i(NAME, "onCreateView");

        final View viewHierarchy = inflater.inflate(LAYOUT, container, false);

        comment_container_ly = (LinearLayout)viewHierarchy.findViewById(R.id.comment_container);
        sub_task_container_ly = (LinearLayout)viewHierarchy.findViewById(R.id.sub_tasks_container);

        final SwitchCompat additional_task_info_sw = (SwitchCompat) viewHierarchy.findViewById(R.id.additional_task_available);
        additional_task_info_sw.setOnCheckedChangeListener(this);

        initializeInputViews(viewHierarchy);
        initializeOutputTypeView(viewHierarchy);
        initializeValidators(viewHierarchy);

        return viewHierarchy;
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
        Log.i(NAME, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(NAME, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
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
    public void onDestroyView() {
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
            addTask();
            activity.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addItem(View view)
    {
          switch (view.getId())
          {
              case button_add_comment:
                  if (comment_container_ly != null)
                  {
                      addItemView(comment_container_ly);
                  }
                  break;

              case button_add_sub_task:
                  if (sub_task_container_ly != null)
                  {
                      addItemView(sub_task_container_ly);
                  }
                  break;

              case button_add_interest:
                  Toast.makeText(activity, "Looooooool!", Toast.LENGTH_LONG).show();
                  break;
          }
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
                    initAdditionalInfoLists(b);
                    setCheckListeners(b, fragmentView);
                    setImportanceBar(b);
                    break;
            }
        }
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

       if(!checked) clearSubItemsLists();
    }

    private void clearSubItemsLists()
    {
        if(!commentList.isEmpty())commentList.clear();
        if(!subTaskList.isEmpty())subTaskList.clear();
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
            final SeekBar importance_setting = (SeekBar) fragmentView.findViewById(R.id.importance);
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

    private void initAdditionalInfoLists(boolean checked)
    {
        if(checked)
        {
            commentList = new ArrayList<>();
            subTaskList = new ArrayList<>();
            interestList = new ArrayList<>();
        }
        else
            {
            commentList = null;
            subTaskList = null;
            interestList = null;
        }
    }

    public void pickTypeDialog()
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.dialog_type_choice, null);
        dialogBuilder.setView(dialogView);

        dialogBuilder.setTitle("Chose type_iet of task");

        final RadioGroup radioGroup = (RadioGroup) dialogView.findViewById(R.id.type_group);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId)
                {
                    case R.id.birthday_type:
                        task_type = EventType.BIRTHDAY.toString();
                        break;

                    case R.id.work_tasks_type:
                        task_type = EventType.WORKTASK.toString();
                        break;

                    case R.id.todo_type:
                        task_type = EventType.TODO.toString();
                        break;

                    case R.id.schedule_type:
                        task_type = EventType.SCHEDULE.toString();
                        break;

                    default:
                        task_type = EventType.NO_TYPE.toString();
                        break;
                }
            }
        });

        dialogBuilder.setPositiveButton(getString(R.string.dialog_ok), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                if(radioGroup.getCheckedRadioButtonId() >= 0)
                {
                    type_iet.setText(task_type);
                    task_type_output_tv.setText(task_type);
                    task_type_output_tv.setTextColor(ContextCompat.getColor(activity, EventType.getColor(task_type)));
                }
                else
                {
                    ((RadioButton)(radioGroup.findViewById(R.id.schedule_type))).setError("You didn't chose anything!");
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

        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public void setTime(int hour, int minute, boolean am_pm, int type)
    {
        Calendar c = Calendar.getInstance();
        String myFormatTime;

        if (!am_pm)
        {
            myFormatTime = "hh:mm a";
        }
        else
        {
            myFormatTime = "kk:mm";
        }

        SimpleDateFormat stf = new SimpleDateFormat(myFormatTime, Locale.US);

        //TODO: when it's 00:12 or something, picker returns 24 hours.
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);

        if(type != -1)
        {
            if(type == R.id.time_start_layout)
            {
                start_time_iet.setText(stf.format(c.getTime()));
            }
            else end_time_iet.setText(stf.format(c.getTime()));
        }
    }

    public void setDate(int year, int month, int day)
    {
        date_iet.setText(day + "/" + (month + 1) + "/" + year);
    }

    //TODO: mock method.
    private void addInterests()
    {
        interestList.add(new Interest(1, 1, "Study", 73));
        interestList.add(new Interest(2, 1, "Book", 83));
        interestList.add(new Interest(3, 1, "School", 12));
    }

    private void addTask()
    {
        addInterests();

        ToDo todo = new ToDo(1,
                title_iet.getText().toString(),
                start_time_iet.getText().toString(),
                end_time_iet.getText().toString(),
                date_iet.getText().toString(),
                EventType.TODO,
                "Waiting",
                importance_value);

        todo.setListComments(commentList);
        todo.setListSubTasks(subTaskList);
        todo.setListInterests(interestList);

        FetchEventsTask addingToDB = new FetchEventsTask(activity);
        addingToDB.execute("add_data", todo);
    }

    private void addItemView(final LinearLayout container_layout)
    {
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View subTaskView = layoutInflater.inflate(R.layout.sub_task_comment_item, container_layout, false);

        final TextView number = (TextView) subTaskView.findViewById(R.id.sub_item_number);
        final ImageView remove_image = (ImageView) subTaskView.findViewById(R.id.remove_sub_task_icon);
        final TextInputEditText content = (TextInputEditText) subTaskView.findViewById(R.id.sub_item_content);

        container_layout.addView(subTaskView);

        number.setText(String.format(Locale.US, "%d.", container_layout.getChildCount()));
        subTaskView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorError30));

        final View.OnClickListener thisRemoveListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int item_index = container_layout.indexOfChild(subTaskView);

                ((LinearLayout) subTaskView.getParent()).removeView(subTaskView);
                subItemNumberReorder(container_layout);

                removeItemFromLists(item_index, subTaskView, container_layout);

                v.setOnClickListener(null);
            }
        };

        final TextValidator thisValidator = new TextValidator(content)
        {
            @Override
            public void validate(TextView textView, String text)
            {
                checkCommentEmpty(content, subTaskView);
            }
        };

        final TextInputEditText.OnEditorActionListener thisDoneAction = new TextInputEditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE)
                {
                    if (!TextUtils.isEmpty(textView.getText()))
                    {
                        addItemToLists(textView, container_layout);

                        content.removeTextChangedListener(thisValidator);
                        subTaskView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorGreyText10));
                        textView.setEnabled(false);

                        content.setOnEditorActionListener(null);
                    }
                    else textView.setHint(R.string.error_hint);
                }
                return false;
            }
        };

        content.addTextChangedListener(thisValidator);
        content.setOnEditorActionListener(thisDoneAction);
        remove_image.setOnClickListener(thisRemoveListener);
    }

    private void removeItemFromLists(int item_index, View subTaskView, LinearLayout container_layout)
    {
        //TODO: Think of something better.
        if (!TextUtils.isEmpty(((TextView) subTaskView.findViewById(R.id.sub_item_content)).getText()))
        {
            switch (container_layout.getId())
            {
                case R.id.comment_container:
                        //String contentt = commentList.get(item_index).getContent();
                        commentList.remove(item_index);
                        //Log.i("AddingTaskFragment", "comment " + contentt + " removed");
                    break;

                case R.id.sub_tasks_container:
                       // String contentt1 = subTaskList.get(item_index).getContent();
                        subTaskList.remove(item_index);
                        //Log.i("AddingTaskFragment", "sub_task " + contentt1 + " removed");
                    break;
            }
        }
    }

    private void addItemToLists(TextView textView, LinearLayout container_layout)
    {
        switch (container_layout.getId())
        {
            case R.id.comment_container:
                commentList.add(new Comment(1, 1, textView.getText().toString()));
                //Log.i("AddingTaskFragment", "comment " + textView.getText().toString() + " added");
                break;

            case R.id.sub_tasks_container:
                subTaskList.add(new Sub_task(1, 1, textView.getText().toString(), false));
               // Log.i("AddingTaskFragment", "sub_task " + textView.getText().toString() + " added");
                break;
        }
    }

    private void subItemNumberReorder(ViewGroup container_layout)
    {
        int childCount = container_layout.getChildCount();

        for(int i = 0; i < childCount; i++)
        {
            final View thisChild = container_layout.getChildAt(i);
            final TextView child_number = (TextView) thisChild.findViewById(R.id.sub_item_number);

            child_number.setText(String.format(Locale.US, "%d.", i + 1));
        }
    }

    private void checkCommentEmpty(TextInputEditText content, View subTaskView)
    {
        if (!TextUtils.isEmpty(content.getText())) subTaskView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorItem30));
        else subTaskView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorError30));
    }

    private void initializeInputViews(View viewHierarchy)
    {
        type_iet = (TextInputEditText) viewHierarchy.findViewById(R.id.task_type_input);
        title_iet = (TextInputEditText) viewHierarchy.findViewById(R.id.task_title_input);
        date_iet = (TextInputEditText) viewHierarchy.findViewById(R.id.task_date_input);
        start_time_iet = (TextInputEditText) viewHierarchy.findViewById(R.id.task_start_time_input);
        end_time_iet = (TextInputEditText) viewHierarchy.findViewById(R.id.task_end_time_input);
    }

    private void initializeOutputTypeView(View viewHierarchy)
    {
        task_type_output_tv = (TextView) viewHierarchy.findViewById(R.id.task_type);
    }

    private void initializeValidators(View viewHierarchy)
    {
        final TextInputLayout title_layout = (TextInputLayout) viewHierarchy.findViewById(R.id.title_input_layout);
        final TextInputLayout task_type_layout = (TextInputLayout) viewHierarchy.findViewById(R.id.task_type_input_layout);
        final TextInputLayout start_time_layout = (TextInputLayout) viewHierarchy.findViewById(R.id.start_time_input_layout);
        final TextInputLayout end_time_layout = (TextInputLayout) viewHierarchy.findViewById(R.id.end_time_input_layout);
        final TextInputLayout date_layout = (TextInputLayout) viewHierarchy.findViewById(R.id.date_input_layout);

        final DateTimeValidator dateTimeValidator = new DateTimeValidator(activity, DateFormat.is24HourFormat(activity));

        title_iet.addTextChangedListener(new TextValidator(title_iet)
        {
            @Override
            public void validate(TextView textView, String text)
            {
                checkIfTextEmpty(title_iet, title_layout);
            }
        });

        title_iet.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean b)
            {
                if (!b)
                {
                    checkIfTextEmpty(title_iet, title_layout);
                }
            }
        });

        type_iet.addTextChangedListener(new TextValidator(type_iet)
        {
            @Override
            public void validate(TextView textView, String text)
            {
                checkTypeInput(text, task_type_layout);
            }
        });

        type_iet.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean b)
            {
                if (!b)
                {
                    if(checkIfTextEmpty(type_iet)) task_type_layout.setError(getString(R.string.empty_error));
                    else checkIfTextEmpty(type_iet, task_type_layout);
                }
            }
        });

        start_time_iet.addTextChangedListener(new TextValidator(start_time_iet)
        {
            @Override
            public void validate(TextView textView, String text)
            {
                checkTimeDateInput(start_time_layout, dateTimeValidator.validateTime(text), dateTimeValidator.timeErrorMessage());
            }
        });

        start_time_iet.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean b)
            {
                if (!b)
                {
                    if(checkIfTextEmpty(start_time_iet)) start_time_layout.setError(getString(R.string.empty_error));
                    else checkTimeDateInput(start_time_layout, dateTimeValidator.validateTime(start_time_iet.getText().toString()), dateTimeValidator.timeErrorMessage());
                }
            }
        });

        end_time_iet.addTextChangedListener(new TextValidator(end_time_iet)
        {
            @Override
            public void validate(TextView textView, String text)
            {
                checkTimeDateInput(end_time_layout, dateTimeValidator.validateTime(text), dateTimeValidator.timeErrorMessage());
            }
        });

        end_time_iet.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean b)
            {
                if (!b)
                {
                    if(checkIfTextEmpty(end_time_iet)) end_time_layout.setError(getString(R.string.empty_error));
                    else  checkTimeDateInput(end_time_layout, dateTimeValidator.validateTime(end_time_iet.getText().toString()), dateTimeValidator.timeErrorMessage());
                }
            }
        });

        date_iet.addTextChangedListener(new TextValidator(date_iet)
        {
            @Override
            public void validate(TextView textView, String text)
            {
                checkTimeDateInput(date_layout, dateTimeValidator.validateDate(text), dateTimeValidator.dateErrorMessage());
            }
        });

        date_iet.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean b)
            {
                if (!b)
                {
                    if(checkIfTextEmpty(date_iet)) date_layout.setError(getString(R.string.empty_error));
                    else checkTimeDateInput(date_layout, dateTimeValidator.validateDate(date_iet.getText().toString()), dateTimeValidator.dateErrorMessage());
                }
            }
        });

    }

    private void checkIfTextEmpty(TextInputEditText textInput, TextInputLayout textInputLayout)
    {
        if (TextUtils.isEmpty(textInput.getText()))
        {
            textInputLayout.setError(getString(R.string.empty_error));
        }
        else
        {
            textInputLayout.setErrorEnabled(false);
            textInputLayout.setError(null);
        }
    }

    private boolean checkIfTextEmpty(TextInputEditText textInput)
    {
        return TextUtils.isEmpty(textInput.getText());
    }

    //TODO: Make it simpler(refactor).
    private void checkTypeInput(String text, TextInputLayout task_type_layout)
    {
        String output_type = "";
        int output_type_color = 0;

        for (EventType type : EventType.values())
        {
            if (text.toLowerCase().equals(type.toString().toLowerCase()))
            {
                task_type_layout.setErrorEnabled(false);
                task_type_layout.setError(null);

                output_type = type.toString();
                output_type_color = EventType.getColor(output_type);
                break;
            }
            else
            {
                task_type_layout.setError(getString(R.string.type_error));

                output_type = EventType.NO_TYPE.toString();
                output_type_color = EventType.getColor(output_type);
            }
        }

        task_type_output_tv.setText(output_type);
        task_type_output_tv.setTextColor(ContextCompat.getColor(activity, output_type_color));
    }

    private void checkTimeDateInput(TextInputLayout time_input_layout, boolean validation, final String errorMessage)
    {
        if(validation)
        {
                //TODO: you should get your time/date_iet to DB from here.

                time_input_layout.setError(null);
                time_input_layout.setErrorEnabled(false);
        }
        else
        {
                time_input_layout.setError(errorMessage);
        }
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
