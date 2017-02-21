package com.example.twinkle94.dealwithit.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.twinkle94.dealwithit.R;

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

        isCommentsAvailable();
        isSubTasksAvailable();
        isImportanceAvailable();
        isInterestsAvailable();

        setImportanceBar();

        return viewHierarchy;
    }

    public void addItem(View view)
    {
          switch (view.getId())
          {
              case button_add_comment:
                  addItemView(comment_layout);
                  break;

              case button_add_sub_task:
                  addItemView(sub_task_layout);
                  break;

              case button_add_interest:
                //  addItemView(sub_task_layout);
                  Toast.makeText(activity, "LOoooooool!", Toast.LENGTH_LONG).show();
                  break;
          }
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

            number.setText(container_layout.getChildCount());
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
