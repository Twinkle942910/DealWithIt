package com.example.twinkle94.dealwithit.adding_task_page.sub_items;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.example.twinkle94.dealwithit.database.EventDAO;
import com.example.twinkle94.dealwithit.database.InterestDAO;
import com.example.twinkle94.dealwithit.events.EventInterest;
import com.example.twinkle94.dealwithit.events.task_types.Schedule;
import com.example.twinkle94.dealwithit.events.type_enums.EventType;
import com.example.twinkle94.dealwithit.events.type_enums.ScheduleType;
import com.example.twinkle94.dealwithit.fragments.AddingScheduleFragment;
import com.example.twinkle94.dealwithit.util.TextValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ScheduleTask extends SubTask implements AddingScheduleFragment.OnInterestPickedListener
{
    private static final String NAME = AddingScheduleFragment.class.getSimpleName();

    //Up panel views
    private TextView task_number_tv;
    private ImageView task_expand_collapse_iv;

    //Title view
    private TextInputEditText task_content_tv;

    //Type views
    private RelativeLayout task_type_rl;
    private TextView task_type_tv;

    //Time views
    private RelativeLayout task_start_time_rl;
    private RelativeLayout task_end_time_rl;

    private TextView task_start_time_tv;
    private TextView task_end_time_tv;

    //Interest views
    private RelativeLayout task_interests_rl;
    private TextView task_interests_tv;

    //Importance views
    private SeekBar importance_setting_sb;
    private TextView importance_percent_tv;
    private TextView importance_type_tv;

    //Down panel buttons
    private TextView task_done_tv;
    private TextView task_edit_tv;

    //Validators
    private TextValidator task_title_validator;
    private TextValidator task_type_validator;
    private TextValidator task_start_time_validator;
    private TextValidator task_end_time_validator;

    //Time set Listener.
    private OnTimeSetListener onTimeSetListener = null;
    //TODO: awful callback, think of something better!
    private OnInterestPickedListener onInterestPickedListener = null;

    private Schedule schedule;
    private ScheduleType scheduleType;
    private List<EventInterest> pickedInterests = new ArrayList<>();
    private int importance_value;

    private String date;
    private boolean isExpanded = false;

    private boolean isDone = false;

    private int interests_count = 0;

    public ScheduleTask(Context context, ViewGroup container_layout, int resource, String date)
    {
        super(context, container_layout, resource);

        this.date = date;
        this.scheduleType = ScheduleType.NO_TYPE;
        this.importance_value = 0;

        addEmptySchedule();
    }

    @Override
    void initializeComponents()
    {
        //Up panel views
       task_number_tv = (TextView) subTaskView_v.findViewById(R.id.schedule_task_number);
        task_expand_collapse_iv = (ImageView) subTaskView_v.findViewById(R.id.schedule_expand_collapse_task_icon);

        //=======================================Input views==============================================
        //Title view
        task_content_tv = (TextInputEditText) subTaskView_v.findViewById(R.id.schedule_task_content);

        //Type views
        task_type_rl = (RelativeLayout) subTaskView_v.findViewById(R.id.schedule_type_title_section);
        task_type_tv = (TextView) subTaskView_v.findViewById(R.id.schedule_type_of_task);

        //Time views
        task_start_time_rl = (RelativeLayout) subTaskView_v.findViewById(R.id.schedule_start_time_title_section);
        task_end_time_rl = (RelativeLayout) subTaskView_v.findViewById(R.id.schedule_end_time_title_section);

        task_start_time_tv = (TextView) subTaskView_v.findViewById(R.id.schedule_start_time);
        task_end_time_tv = (TextView) subTaskView_v.findViewById(R.id.schedule_end_time);

        //Interest views
        task_interests_rl = (RelativeLayout) subTaskView_v.findViewById(R.id.schedule_interests_title_section);
        task_interests_tv = (TextView) subTaskView_v.findViewById(R.id.schedule_interests);

        //Importance views
        importance_setting_sb = (SeekBar) subTaskView_v.findViewById(R.id.importance_value);
        importance_percent_tv = (TextView) subTaskView_v.findViewById(R.id.importance_percent_value);
        importance_type_tv = (TextView) subTaskView_v.findViewById(R.id.importance_type);
        //===============================================================================================

        //Down panel buttons
        task_done_tv = (TextView) subTaskView_v.findViewById(R.id.schedule_input_done);
        task_edit_tv = (TextView) subTaskView_v.findViewById(R.id.schedule_input_edit);
        TextView task_delete_tv = (TextView) subTaskView_v.findViewById(R.id.schedule_input_delete);

        //Expand_collapse listener
        task_expand_collapse_iv.setOnClickListener(this);

        //Down panel listeners
        task_delete_tv.setOnClickListener(this);
        task_done_tv.setOnClickListener(this);

        super.initializeComponents();
    }

    @Override
    public void setComponents()
    {
        task_number_tv.setText(String.format(Locale.US, "%d", container_layout_vg.getChildCount() + 1));
    }

    @Override
    public void setComponentsListeners()
    {
        //enable task content input
        task_content_tv.setEnabled(true);

        //Type listener
        task_type_rl.setOnClickListener(this);

        //Time listeners
        task_start_time_rl.setOnClickListener(this);
        task_end_time_rl.setOnClickListener(this);

        //Interest listeners
        task_interests_rl.setOnClickListener(this);

        //Importance listener
        importance_setting_sb.setOnSeekBarChangeListener(new ProgressChecker()
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
        importance_setting_sb.setEnabled(true);
    }

    public void setOnTimeSetListener(OnTimeSetListener listener)
    {
        onTimeSetListener = listener;
    }

    public void setInterestPickedListener(OnInterestPickedListener listener)
    {
        onInterestPickedListener = listener;
    }


    @Override
    public void removeComponentsListeners()
    {
        //disable task content input
        task_content_tv.setEnabled(false);

        //disable type listener
        task_type_rl.setOnClickListener(null);

        //disable time listeners
        task_start_time_rl.setOnClickListener(null);
        task_end_time_rl.setOnClickListener(null);

        //disable interest listeners
        task_interests_rl.setOnClickListener(null);

        //disable importance_value bar listeners
        importance_setting_sb.setOnSeekBarChangeListener(null);
        importance_setting_sb.setEnabled(false);
    }

    @Override
    public void initComponentsValidators()
    {
        task_title_validator = validator(task_content_tv);
        task_type_validator = validator(task_type_tv);
        task_start_time_validator = validator(task_start_time_tv);
        task_end_time_validator = validator(task_end_time_tv);
    }

    @Override
    public void addComponentsValidators()
    {
        task_content_tv.addTextChangedListener(task_title_validator);
        task_type_tv.addTextChangedListener(task_type_validator);
        task_start_time_tv.addTextChangedListener(task_start_time_validator);
        task_end_time_tv.addTextChangedListener(task_end_time_validator);
    }

    @Override
    public void removeComponentsValidators()
    {
        task_content_tv.removeTextChangedListener(task_title_validator);
        task_type_tv.removeTextChangedListener(task_type_validator);
        task_start_time_tv.removeTextChangedListener(task_start_time_validator);
        task_end_time_tv.removeTextChangedListener(task_end_time_validator);
    }

    private void OnTimeClick(TextView time_tv, View view)
    {
        if(onTimeSetListener != null)
        {
            onTimeSetListener.onTimeSet(time_tv, view);
        }
    }

    @Override
    public void onItemClick(View view)
    {
        switch (view.getId())
        {
            case R.id.schedule_expand_collapse_task_icon:
                isExpanded();
                expandCollapseView(subTaskView_v, isExpanded);
                break;

            case R.id.schedule_type_title_section:
                pickScheduleTypeDialog(task_type_tv);
                break;

            case R.id.schedule_start_time_title_section:
                OnTimeClick(task_start_time_tv, view);
                break;

            case R.id.schedule_end_time_title_section:
                OnTimeClick(task_end_time_tv, view);
                break;

            case R.id.schedule_interests_title_section:
                onInterestPickedListener.onInterestPicked(view);
                break;

            case R.id.schedule_input_delete:

                //disable expand_collapse listener
                task_expand_collapse_iv.setOnClickListener(null);

                //if not done, then remove all listeners.
                if (!isDone)
                {
                    removeComponentsListeners();
                    removeComponentsValidators();

                    task_done_tv.setOnClickListener(null);
                    task_edit_tv.setOnClickListener(null);
                }
              /*  else
                    {
                        removeTaskFromDB();
                    }*/

                removeInterest();

                removeView();
                view.setOnClickListener(null);

                subItemNumberReorder(task_number_tv);

                break;

            case R.id.schedule_input_edit:
                //Better to write here (isDone) or put adding listener when it's done in done_button case?
                setComponentsListeners();
                addComponentsValidators();

                task_done_tv.setTextColor(setColor(R.color.colorNotificationText));

                view.setOnClickListener(null);
                task_done_tv.setOnClickListener(this);

                removeTaskFromDB();
                removeInterest();

                break;

            case R.id.schedule_input_done:

                boolean isTitleEmpty  = isInputEmpty(task_content_tv);
                boolean isTypeEmpty  = isInputEmpty(task_type_tv);
                boolean isStartTimeEmpty  = isInputEmpty(task_start_time_tv);
                boolean isEndTimeEmpty  =  isInputEmpty(task_end_time_tv);

                if (isTitleEmpty || isTypeEmpty || isStartTimeEmpty || isEndTimeEmpty)
                {
                    setInputError(task_content_tv, ((TextView) subTaskView_v.findViewById(R.id.schedule_content_title)));
                    setInputError(task_type_tv, ((TextView) subTaskView_v.findViewById(R.id.schedule_type_title)));
                    setInputError(task_start_time_tv, ((TextView) subTaskView_v.findViewById(R.id.schedule_start_time_title)));
                    setInputError(task_end_time_tv, ((TextView) subTaskView_v.findViewById(R.id.schedule_end_time_title)));

                    task_done_tv.setTextColor(setColor(R.color.colorError));
                }
                else
                {
                    addTaskToDB();

                    task_done_tv.setTextColor(setColor(R.color.colorTypeBirthday));
                    removeComponentsListeners();
                    removeComponentsValidators();

                    view.setOnClickListener(null);
                    task_edit_tv.setOnClickListener(this);

                    isDone = true;
                }
                break;
        }
    }

    private void isExpanded()
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
    }

    @Override
    public void validate(TextView textView)
    {
        switch (textView.getId())
        {
            case R.id.schedule_task_content:
                setInputError(task_content_tv, ((TextView) subTaskView_v.findViewById(R.id.schedule_content_title)));
                break;

            case R.id.schedule_type_of_task:
                setInputError(task_type_tv, ((TextView) subTaskView_v.findViewById(R.id.schedule_type_title)));
                break;

            case R.id.schedule_start_time:
                setInputError(task_start_time_tv, ((TextView) subTaskView_v.findViewById(R.id.schedule_start_time_title)));
                break;

            case R.id.schedule_end_time:
                setInputError(task_end_time_tv, ((TextView) subTaskView_v.findViewById(R.id.schedule_end_time_title)));
                break;
        }
    }

    private void setInputError(TextView textView, TextView title)
    {
        if (!isInputEmpty(textView))
        {
            title.setTextColor(setColor(R.color.colorNotificationText));
            textView.setHintTextColor(setColor(R.color.colorNotificationText));
        }
        else
        {
            title.setTextColor(setColor(R.color.colorTypeToDo));
            textView.setHintTextColor(setColor(R.color.colorTypeToDo));
        }
    }

    @Override
    void addTaskToDB()
    {
        int schedule_id = schedule.getId();

        schedule = new Schedule(schedule_id,
                task_content_tv.getText().toString(),
                scheduleType,
                task_start_time_tv.getText().toString(),
                task_end_time_tv.getText().toString(),
                date,
                "Waiting",
                importance_value);
        new EventDAO(context).updateEventOnBG(schedule);
    }

    @Override
    void removeTaskFromDB()
    {
        if(schedule != null)
        //TODO: delete item only last added schedule item (not item of this instance!).
            new EventDAO(context).deleteEventOnBG(schedule);
    }

    @Override
    public void onInterestsPick(int interest_id)
    {
        final EventInterest interest = new EventInterest(-1, schedule.getId(), interest_id);
        pickedInterests.add(interest);
        new InterestDAO(context).addTaskOnBG(interest);
        interests_count++;

        task_interests_tv.setText("Interests added: " + interests_count);
    }

    private void removeInterest()
    {
        //TODO: provide possibility to remove more than 1 interest.!
        if(pickedInterests != null && pickedInterests.size() > 0)
        {
            for(int i = 0; i < pickedInterests.size(); i++)
                new InterestDAO(context).deleteTaskOnBG(pickedInterests.get(i));
        }
    }

    private void addEmptySchedule()
    {
        schedule = new Schedule();
        schedule.setType(EventType.SCHEDULE);
        new EventDAO(context).addEventOnBG(schedule);
    }

    private void pickScheduleTypeDialog(final TextView task_type)
    {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.dialog_schedule_type_choice, null);
        dialogBuilder.setView(dialogView);

        dialogBuilder.setTitle(R.string.pick_schedule_type_dialog_title);

        final RadioGroup radioGroup = (RadioGroup) dialogView.findViewById(R.id.schedule_type_group);

        dialogBuilder.setPositiveButton(context.getString(R.string.dialog_ok), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                if(radioGroup.getCheckedRadioButtonId() >= 0)
                {
                    task_type.setText(scheduleType.toString());
                }
                else
                {
                    //TODO: check this out!
                    Toast.makeText(context, "You must chose something!", Toast.LENGTH_LONG).show();
                }
            }
        });

        dialogBuilder.setNegativeButton(context.getString(R.string.dialog_cancel), new DialogInterface.OnClickListener()
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

    //TODO: refactoring!
    private void expandCollapseView(final View v, final boolean isExpanded)
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

    // Define our custom Listener interface (for time set)
    public interface OnTimeSetListener
    {
        void onTimeSet(TextView time_tv, View view);
    }

    public interface OnInterestPickedListener
    {
        void onInterestPicked(View view);
    }
}
