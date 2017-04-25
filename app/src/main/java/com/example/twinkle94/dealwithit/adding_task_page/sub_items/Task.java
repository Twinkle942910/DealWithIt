package com.example.twinkle94.dealwithit.adding_task_page.sub_items;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.twinkle94.dealwithit.R;
import com.example.twinkle94.dealwithit.background.FetchEventsTask;
import com.example.twinkle94.dealwithit.events.Comment;
import com.example.twinkle94.dealwithit.events.Sub_task;
import com.example.twinkle94.dealwithit.util.TextValidator;

import java.util.List;
import java.util.Locale;

public class Task extends SubTask
{
    //Components
    private TextView number_tv;
    private ImageView remove_image_iv;
    private TextInputEditText content_et;

    //Validators
    private TextValidator content_validator;

    private Comment comment;
    private Sub_task sub_task;

    public Task(Context context, ViewGroup container_layout, int resource)
    {
        super(context, container_layout, resource);
    }

    @Override
    public void initializeComponents()
    {
        number_tv = (TextView) subTaskView_v.findViewById(R.id.sub_item_number);
        remove_image_iv = (ImageView) subTaskView_v.findViewById(R.id.remove_sub_task_icon);
        content_et = (TextInputEditText) subTaskView_v.findViewById(R.id.sub_item_content);

        super.initializeComponents();
    }

    @Override
    public void setComponents()
    {
        number_tv.setText(String.format(Locale.US, "%d.", container_layout_vg.getChildCount() + 1));
    }

    @Override
    public void setComponentsListeners()
    {
        remove_image_iv.setOnClickListener(this);
        content_et.setOnEditorActionListener(new TextInputEditText.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event)
            {
                if (actionId == EditorInfo.IME_ACTION_DONE)
                {
                    if (!isInputEmpty(content_et))
                    {
                        addTaskToDB();

                        subTaskView_v.setBackgroundColor(setColor(R.color.colorGreyText10));

                        removeComponentsValidators();
                        textView.setEnabled(false);
                        content_et.setOnEditorActionListener(null);
                    }
                    else {
                        textView.setHint(R.string.error_hint);
                        subTaskView_v.setBackgroundColor(setColor(R.color.colorError30));
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void removeComponentsListeners()
    {
        remove_image_iv.setOnClickListener(null);
        content_et.setOnEditorActionListener(null);
    }

    @Override
    public void initComponentsValidators()
    {
        content_validator = validator(content_et);
    }

    @Override
    public void addComponentsValidators()
    {
        content_et.addTextChangedListener(content_validator);
    }

    @Override
    public void removeComponentsValidators()
    {
        content_et.removeTextChangedListener(content_validator);
    }

    @Override
    public void onItemClick(View view)
    {
       // removeTaskFromDB();
        removeView();
        subItemNumberReorder(content_et);
        view.setOnClickListener(null);
    }

    @Override
    public void validate(TextView textView)
    {
        if(!isInputEmpty(content_et)) subTaskView_v.setBackgroundColor(setColor(R.color.colorItem30));
        else subTaskView_v.setBackgroundColor(setColor(R.color.colorError30));
    }

    @Override
    void addTaskToDB()
    {
        switch (container_layout_vg.getId())
        {
            case R.id.comment_container:
                comment = new Comment(-1, -1, content_et.getText().toString());
                new FetchEventsTask(context).execute("add_data", comment);
                break;

            case R.id.sub_tasks_container:
                sub_task = new Sub_task(-1, -1, content_et.getText().toString(), false);
                new FetchEventsTask(context).execute("add_data", sub_task);
                break;
        }
    }

    @Override
    void removeTaskFromDB()
    {
        if (!isInputEmpty(content_et))
        {
            switch (container_layout_vg.getId())
            {
                case R.id.comment_container:
                    new FetchEventsTask(context).execute("remove_data", comment);
                    break;

                case R.id.sub_tasks_container:
                    new FetchEventsTask(context).execute("remove_data", sub_task);
                    break;
            }
        }
    }
}
