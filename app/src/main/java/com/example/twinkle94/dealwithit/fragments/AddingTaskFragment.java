package com.example.twinkle94.dealwithit.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

public class AddingTaskFragment extends Fragment
{
    private static final String NAME = AddingTaskFragment.class.getSimpleName();
    private static final int LAYOUT = R.layout.fragment_new_task_type;

    private Activity activity;

    //Comments
    private CheckBox comments_available;
    private LinearLayout comment_container_layout;
    private LinearLayout comment_layout;

    //SubTasks
    private CheckBox sub_tasks_available;
    private LinearLayout sub_task_container_layout;
    private LinearLayout sub_task_layout;

    //Importance
    private CheckBox importance_available;
    private LinearLayout importance_container_layout;

    private SeekBar importance_setting;
    private TextView importance_percent;
    private TextView importance_type;

    private int importance_value;

    //Interests
    private CheckBox interests_available;
    private RelativeLayout interests_container_layout;

    //Info
    private TextView task_type_output;

    //Data form user.
    private TextInputEditText type;
    private TextInputEditText title;
    //TODO: change to dateTime, maybe?
    private TextInputEditText date;
    private TextInputEditText start_time;
    private TextInputEditText end_time;

    private List<Comment> commentList;
    private List<Sub_task> subTaskList;
    private List<Interest> interestList;

    //Transfer data
    private String task_type;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.activity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        Log.i(NAME, "onCreateView1");

        View viewHierarchy = inflater.inflate(LAYOUT, container, false);

        comments_available = (CheckBox) viewHierarchy.findViewById(R.id.use_comment_checkBox);
        comment_container_layout = (LinearLayout)viewHierarchy.findViewById(R.id.comment_container_layout);
        comment_layout = (LinearLayout)viewHierarchy.findViewById(R.id.comment_container);

        sub_tasks_available = (CheckBox) viewHierarchy.findViewById(R.id.use_sub_tasks_checkBox);
        sub_task_container_layout = (LinearLayout)viewHierarchy.findViewById(R.id.sub_task_container_layout);
        sub_task_layout = (LinearLayout)viewHierarchy.findViewById(R.id.sub_tasks_container);

        importance_available = (CheckBox) viewHierarchy.findViewById(R.id.use_importance_checkBox);
        importance_container_layout = (LinearLayout)viewHierarchy.findViewById(R.id.importance_container_layout);

        importance_setting = (SeekBar) viewHierarchy.findViewById(R.id.importance);
        importance_percent = (TextView) viewHierarchy.findViewById(R.id.importance_percent_value);
        importance_type = (TextView) viewHierarchy.findViewById(R.id.importance_type);

        interests_available = (CheckBox) viewHierarchy.findViewById(R.id.use_interests_checkBox);
        interests_container_layout = (RelativeLayout) viewHierarchy.findViewById(R.id.interests_container_layout);

        initializeOutputTypeView(viewHierarchy);
        initializeInputViews(viewHierarchy);
        initializeValidators(viewHierarchy);

        isComponentAvailable(comments_available, comment_container_layout);
        isComponentAvailable(sub_tasks_available, sub_task_container_layout);
        isComponentAvailable(importance_available, importance_container_layout);
        isComponentAvailable(interests_available, interests_container_layout);

        setImportanceBar();

        Log.i(NAME, "onCreateView2");

        return viewHierarchy;

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
                  addItemView(comment_layout);
                //  addComment();
                  break;

              case button_add_sub_task:
                  addItemView(sub_task_layout);
                 // addSubTask();
                  break;

              case button_add_interest:
                 // addInterest();
                  Toast.makeText(activity, "Looooooool!", Toast.LENGTH_LONG).show();
                  break;
          }
    }

    public void pickTypeDialog()
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.dialog_type_choice, null);
        dialogBuilder.setView(dialogView);

        dialogBuilder.setTitle("Chose type of task");

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
                    type.setText(task_type);
                    task_type_output.setText(task_type);
                    task_type_output.setTextColor(ContextCompat.getColor(activity, EventType.getColor(task_type)));
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
                start_time.setText(stf.format(c.getTime()));
            }
            else end_time.setText(stf.format(c.getTime()));
        }
    }

    public void setDate(int year, int month, int day)
    {
        date.setText(day + "/" + (month + 1) + "/" + year);
    }

    private void addComment()
    {
        int childCount = comment_layout.getChildCount();

        for(int i=0; i<childCount; i++)
        {
            View thisChild = comment_layout.getChildAt(i);

            TextView comment_content = (TextView) thisChild.findViewById(R.id.sub_item_content);

            String content = comment_content.getText().toString();
                                       //TODO: id and eventId is not needed, possibly.
            commentList.add(new Comment(1, 1, content));
        }
    }

    private void addSubTask()
    {
        int childCount = sub_task_layout.getChildCount();

        for(int i=0; i<childCount; i++)
        {
            View thisChild = sub_task_layout.getChildAt(i);

            TextView subTask_content = (TextView) thisChild.findViewById(R.id.sub_item_content);

            String content = subTask_content.getText().toString();
            //TODO: id and eventId is not needed, possibly.
            subTaskList.add(new Sub_task(1, 1, content, false));
        }
    }

    //TODO: mock method.
    private void addInterest()
    {
        interestList.add(new Interest(1, 1, "Study", 73));
        interestList.add(new Interest(2, 1, "Book", 83));
        interestList.add(new Interest(3, 1, "School", 12));
    }

    private void addTask()
    {
        addComment();
        addSubTask();
        addInterest();

        ToDo todo = new ToDo(1,
                title.getText().toString(),
                start_time.getText().toString(),
                end_time.getText().toString(),
                date.getText().toString(),
                EventType.TODO,
                "Waiting",
                importance_value);

        todo.setListComments(commentList);
        todo.setListSubTasks(subTaskList);
        todo.setListInterests(interestList);

        FetchEventsTask addingToDB = new FetchEventsTask(activity);
        addingToDB.execute("add_data", todo);
    }

    private void addItemView(LinearLayout container_layout)
    {
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View subTaskView = layoutInflater.inflate(R.layout.sub_task_comment_item, container_layout, false);

        TextView number = null;
        ImageView remove_image = null;

        if (container_layout != null)
        {
            container_layout.addView(subTaskView);

            number  = (TextView) subTaskView.findViewById(R.id.sub_item_number);
            remove_image = (ImageView) subTaskView.findViewById(R.id.remove_sub_task_icon);

            //number.setText(container_layout.getChildCount());
        }

        final View.OnClickListener thisListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ((LinearLayout)subTaskView.getParent()).removeView(subTaskView);
            }
        };

        if (remove_image != null)
        {
            remove_image.setOnClickListener(thisListener);
        }
    }

    private void initializeOutputTypeView(View viewHierarchy)
    {
        task_type_output = (TextView) viewHierarchy.findViewById(R.id.task_type);
    }

    private void initializeInputViews(View viewHierarchy)
    {
        type = (TextInputEditText) viewHierarchy.findViewById(R.id.task_type_input);
        title = (TextInputEditText) viewHierarchy.findViewById(R.id.task_title_input);
        date = (TextInputEditText) viewHierarchy.findViewById(R.id.task_date_input);
        start_time = (TextInputEditText) viewHierarchy.findViewById(R.id.task_start_time_input);
        end_time = (TextInputEditText) viewHierarchy.findViewById(R.id.task_end_time_input);

        commentList = new ArrayList<>();
        subTaskList = new ArrayList<>();
        interestList = new ArrayList<>();
    }

    private void initializeValidators(View viewHierarchy)
    {
        final TextInputLayout title_layout = (TextInputLayout) viewHierarchy.findViewById(R.id.title_input_layout);
        final TextInputLayout task_type_layout = (TextInputLayout) viewHierarchy.findViewById(R.id.task_type_input_layout);
        final TextInputLayout start_time_layout = (TextInputLayout) viewHierarchy.findViewById(R.id.start_time_input_layout);
        final TextInputLayout end_time_layout = (TextInputLayout) viewHierarchy.findViewById(R.id.end_time_input_layout);
        final TextInputLayout date_layout = (TextInputLayout) viewHierarchy.findViewById(R.id.date_input_layout);

        final DateTimeValidator dateTimeValidator = new DateTimeValidator(activity, DateFormat.is24HourFormat(activity));

        title.addTextChangedListener(new TextValidator(title)
        {
            @Override
            public void validate(TextView textView, String text)
            {
                checkIfTextEmpty(title, title_layout);
            }
        });

        title.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean b)
            {
                if (!b)
                {
                    checkIfTextEmpty(title, title_layout);
                }
            }
        });

        type.addTextChangedListener(new TextValidator(type)
        {
            @Override
            public void validate(TextView textView, String text)
            {
                checkTypeInput(text, task_type_layout);
            }
        });

        type.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean b)
            {
                if (!b)
                {
                    if(checkIfTextEmpty(type)) task_type_layout.setError(getString(R.string.empty_error));
                    else checkIfTextEmpty(type, task_type_layout);
                }
            }
        });

        start_time.addTextChangedListener(new TextValidator(start_time)
        {
            @Override
            public void validate(TextView textView, String text)
            {
                checkTimeDateInput(start_time_layout, dateTimeValidator.validateTime(text), dateTimeValidator.timeErrorMessage());
            }
        });

        start_time.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean b)
            {
                if (!b)
                {
                    if(checkIfTextEmpty(start_time)) start_time_layout.setError(getString(R.string.empty_error));
                    else checkTimeDateInput(start_time_layout, dateTimeValidator.validateTime(start_time.getText().toString()), dateTimeValidator.timeErrorMessage());
                }
            }
        });

        end_time.addTextChangedListener(new TextValidator(end_time)
        {
            @Override
            public void validate(TextView textView, String text)
            {
                checkTimeDateInput(end_time_layout, dateTimeValidator.validateTime(text), dateTimeValidator.timeErrorMessage());
            }
        });

        end_time.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean b)
            {
                if (!b)
                {
                    if(checkIfTextEmpty(end_time)) end_time_layout.setError(getString(R.string.empty_error));
                    else  checkTimeDateInput(end_time_layout, dateTimeValidator.validateTime(end_time.getText().toString()), dateTimeValidator.timeErrorMessage());
                }
            }
        });

        date.addTextChangedListener(new TextValidator(date)
        {
            @Override
            public void validate(TextView textView, String text)
            {
                checkTimeDateInput(date_layout, dateTimeValidator.validateDate(text), dateTimeValidator.dateErrorMessage());
            }
        });

        date.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean b)
            {
                if (!b)
                {
                    if(checkIfTextEmpty(date)) date_layout.setError(getString(R.string.empty_error));
                    else checkTimeDateInput(date_layout, dateTimeValidator.validateDate(date.getText().toString()), dateTimeValidator.dateErrorMessage());
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

        task_type_output.setText(output_type);
        task_type_output.setTextColor(ContextCompat.getColor(activity, output_type_color));
    }

    private void checkTimeDateInput(TextInputLayout time_input_layout, boolean validation, final String errorMessage)
    {
        if(validation)
        {
                //TODO: you should get your time/date to DB from here.

                time_input_layout.setError(null);
                time_input_layout.setErrorEnabled(false);
        }
        else
        {
                time_input_layout.setError(errorMessage);
        }
    }

    private void isComponentAvailable(CheckBox checkBox, final ViewGroup view)
    {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                if(b)
                {
                    view.setVisibility(View.VISIBLE);
                }
                else view.setVisibility(View.GONE);
            }
        });
    }

    //TODO: Make interface or abstract class, to hide this 3 methods.
    private void setImportanceBar()
    {
        importance_value = 0;

        importance_setting.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser)
            {
                importance_value = progressValue;
                importance_percent.setText(importance_value + "%");

                if(importance_value >= 60) importance_type.setText("Important");
                else importance_type.setText("Not important");
            }


            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });
    }
}
