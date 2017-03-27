package com.example.twinkle94.dealwithit.adding_task_page.sub_items;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.twinkle94.dealwithit.util.TextValidator;

import java.util.Locale;

public abstract class SubTask implements View.OnClickListener
{
    private final int LAYOUT;

    final Context context;
    final ViewGroup container_layout_vg;
    final View subTaskView_v;

    SubTask(Context context, ViewGroup container_layout_vg, int resource)
    {
        this.context = context;
        this.container_layout_vg = container_layout_vg;

        LAYOUT = resource;

        subTaskView_v = getView();

        initializeComponents();
    }

    private View getView()
    {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return layoutInflater.inflate(LAYOUT, container_layout_vg, false);
    }

    public void addView()
    {
        container_layout_vg.addView(subTaskView_v);
    }

    void removeView()
    {
        container_layout_vg.removeView(subTaskView_v);
    }

    void initializeComponents()
    {
        setComponents();
        setComponentsListeners();
        initComponentsValidators();
        addComponentsValidators();
    }

    public abstract void setComponents();

    //Listeners
    abstract void setComponentsListeners();
    abstract void removeComponentsListeners();

    //Validators
    abstract void initComponentsValidators();
    abstract void addComponentsValidators();
    abstract void removeComponentsValidators();

    abstract void onItemClick(View view);
    abstract void validate(TextView textView);

    abstract void addTaskToList();
    abstract void removeTaskFromList();

    int setColor(int color)
    {
        return ContextCompat.getColor(context, color);
    }

    @Override
    public void onClick(View view)
    {
        onItemClick(view);
    }

    TextValidator validator(TextView textView)
    {
        return new TextValidator(textView)
        {
            @Override
            public void validate(TextView textView, String text)
            {
               SubTask.this.validate(textView);
            }
        };
    }

    boolean isInputEmpty(TextView textView)
    {
        return TextUtils.isEmpty(textView.getText());
    }

    void subItemNumberReorder(TextView number_tv)
    {
        int child_count = container_layout_vg.getChildCount();

        //TODO: do i need this condition?
        if(child_count > 0)
        {
            for (int i = 0; i < child_count; i++)
            {
                number_tv.setText(String.format(Locale.US, "%d.", i + 1));
            }
        }
    }
}
