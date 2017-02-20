package com.example.twinkle94.dealwithit.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.content.res.AppCompatResources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.twinkle94.dealwithit.R;

import static com.example.twinkle94.dealwithit.R.id.button_add_comment;
import static com.example.twinkle94.dealwithit.R.id.button_add_sub_task;
import static com.example.twinkle94.dealwithit.R.id.comment_container;

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

    //Interests
    private CheckBox interests_available;
    private RelativeLayout interests_container_layout;

    //TODO: do this more efficent
    private int comment_counter;
    private int sub_task_counter;

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

        interests_available = (CheckBox) viewHierarchy.findViewById(R.id.use_interests_checkBox);
        interests_container_layout = (RelativeLayout) viewHierarchy.findViewById(R.id.interests_container_layout);

        isCommentsAvailable();
        isSubTasksAvailable();
        isImportanceAvailable();
        isInterestsAvailable();

        comment_counter = 0;
        sub_task_counter = 0;

        return viewHierarchy;
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

    public void addComment(View view)
    {
          switch (view.getId())
          {
              case button_add_comment :

                  LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                 final View subTaskView = layoutInflater.inflate(R.layout.sub_task_comment_item, comment_layout, false);

                  comment_counter++;

                  if (comment_layout != null)
                  {
                      comment_layout.addView(subTaskView);
                  }

                  TextView   number = (TextView) subTaskView.findViewById(R.id.sub_item_number);
                  ImageView  remove_image = (ImageView) subTaskView.findViewById(R.id.remove_sub_task_icon);

                  number.setText(Integer.toString(comment_counter));

                  final View.OnClickListener thisListener = new View.OnClickListener()
                  {
                      @Override
                      public void onClick(View v)
                      {
                          ((LinearLayout)subTaskView.getParent()).removeView(subTaskView);
                          comment_counter--;
                      }
                  };

                  remove_image.setOnClickListener(thisListener);

                  break;

              case button_add_sub_task:

                  LayoutInflater layoutInflater1 = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                  final View subTaskView1 = layoutInflater1.inflate(R.layout.sub_task_comment_item, sub_task_layout, false);

                  sub_task_counter++;

                  if (sub_task_layout != null)
                  {
                      sub_task_layout.addView(subTaskView1);
                  }

                  TextView   number1 = (TextView) subTaskView1.findViewById(R.id.sub_item_number);
                  ImageView  remove_image1 = (ImageView) subTaskView1.findViewById(R.id.remove_sub_task_icon);

                  number1.setText(Integer.toString(sub_task_counter));

                  final View.OnClickListener thisListener1 = new View.OnClickListener()
                  {
                      @Override
                      public void onClick(View v)
                      {
                          ((LinearLayout)subTaskView1.getParent()).removeView(subTaskView1);
                          sub_task_counter--;
                      }
                  };

                  remove_image1.setOnClickListener(thisListener1);

                  break;
          }
    }
}
