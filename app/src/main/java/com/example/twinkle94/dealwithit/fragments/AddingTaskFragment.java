package com.example.twinkle94.dealwithit.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private EditText type;
    private EditText title;
    //TODO: change to dateTime, maybe?
    private EditText date;
    private EditText start_time;
    private EditText end_time;

    private List<Comment> commentList;
    private List<Sub_task> subTaskList;
    private List<Interest> interestList;

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
        View viewHierarchy = inflater.inflate(R.layout.fragment_new_task_type, container, false);

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

        isCommentsAvailable();
        isSubTasksAvailable();
        isImportanceAvailable();
        isInterestsAvailable();

        setImportanceBar();

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

        RadioGroup radioGroup = (RadioGroup) dialogView.findViewById(R.id.type_group);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId)
                {
                    case R.id.birthday_type:
                        type.setText(EventType.BIRTHDAY.toString());
                        task_type_output.setText(EventType.BIRTHDAY.toString());
                        task_type_output.setTextColor(ContextCompat.getColor(activity, R.color.colorTypeBirthday));
                        break;

                    case R.id.work_tasks_type:
                        type.setText(EventType.WORKTASK.toString());
                        task_type_output.setText(EventType.WORKTASK.toString());
                        task_type_output.setTextColor(ContextCompat.getColor(activity, R.color.colorTypeWorkTasks));
                        break;

                    case R.id.todo_type:
                        type.setText(EventType.TODO.toString());
                        task_type_output.setText(EventType.TODO.toString());
                        task_type_output.setTextColor(ContextCompat.getColor(activity, R.color.colorTypeToDo));
                        break;

                    case R.id.schedule_type:
                        type.setText(EventType.SCHEDULE.toString());
                        task_type_output.setText(EventType.SCHEDULE.toString());
                        task_type_output.setTextColor(ContextCompat.getColor(activity, R.color.colorTypeSchedule));
                        break;

                    default:
                        type.setText(getString(R.string.no_type_selected));
                        task_type_output.setText(getString(R.string.no_type_selected));
                        task_type_output.setTextColor(ContextCompat.getColor(activity, R.color.colorTitleText));
                        break;
                }
            }
        });

        dialogBuilder.setPositiveButton(getString(R.string.dialog_ok), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {

            }
        });

        dialogBuilder.setNegativeButton(getString(R.string.dialog_cancel), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {

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
        else  myFormatTime = "kk:mm";

        SimpleDateFormat stf = new SimpleDateFormat(myFormatTime, Locale.US);

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
        type = (EditText) viewHierarchy.findViewById(R.id.task_type_input);
        title = (EditText) viewHierarchy.findViewById(R.id.task_title_input);
        date = (EditText) viewHierarchy.findViewById(R.id.task_date_input);
        start_time = (EditText) viewHierarchy.findViewById(R.id.task_start_time_input);
        end_time = (EditText) viewHierarchy.findViewById(R.id.task_end_time_input);

        commentList = new ArrayList<>();
        subTaskList = new ArrayList<>();
        interestList = new ArrayList<>();

        type.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                String input_type = (type.getText().toString()).toLowerCase();

                switch(input_type)
                {
                    case "todo":
                        task_type_output.setText(EventType.TODO.toString());
                        task_type_output.setTextColor(ContextCompat.getColor(activity, R.color.colorTypeToDo));
                        break;

                    case "birthday":
                        task_type_output.setText(EventType.BIRTHDAY.toString());
                        task_type_output.setTextColor(ContextCompat.getColor(activity, R.color.colorTypeBirthday));
                        break;

                    case "schedule":
                        task_type_output.setText(EventType.SCHEDULE.toString());
                        task_type_output.setTextColor(ContextCompat.getColor(activity, R.color.colorTypeSchedule));
                        break;

                    case "work task":
                        task_type_output.setText(EventType.WORKTASK.toString());
                        task_type_output.setTextColor(ContextCompat.getColor(activity, R.color.colorTypeWorkTasks));
                        break;

                    default:
                        task_type_output.setText(getString(R.string.no_type_selected));
                        task_type_output.setTextColor(ContextCompat.getColor(activity, R.color.colorTitleText));
                        break;
                }
            }
        });
    }

    private void isCommentsAvailable()
    {
        comments_available.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                if(b)
                {
                    comment_container_layout.setVisibility(View.VISIBLE);
                }
                else comment_container_layout.setVisibility(View.GONE);
            }
        });
    }

    private void isSubTasksAvailable()
    {
        sub_tasks_available.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                if(b)
                {
                    sub_task_container_layout.setVisibility(View.VISIBLE);
                }
                else sub_task_container_layout.setVisibility(View.GONE);
            }
        });
    }

    private void isImportanceAvailable()
    {
        importance_available.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                if(b)
                {
                    importance_container_layout.setVisibility(View.VISIBLE);
                }
                else importance_container_layout.setVisibility(View.GONE);
            }
        });
    }

    private void isInterestsAvailable()
    {
        interests_available.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                if(b)
                {
                    interests_container_layout.setVisibility(View.VISIBLE);
                }
                else interests_container_layout.setVisibility(View.GONE);
            }
        });
    }

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
