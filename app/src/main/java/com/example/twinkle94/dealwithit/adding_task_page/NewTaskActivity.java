package com.example.twinkle94.dealwithit.adding_task_page;

import android.content.res.Resources;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.twinkle94.dealwithit.R;
import com.example.twinkle94.dealwithit.fragments.AddingTaskFragment;

public class NewTaskActivity extends AppCompatActivity
{
    /*static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }*/

    private Toolbar toolbar;
    private AddingTaskFragment addingTaskFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        initToolbar();
        setHomeButton();

        //TODO: change it later(method name and fragment adding)
        initFragment();
    }

    private void initFragment()
    {
        // Get fragment manager
        FragmentManager fm = getSupportFragmentManager();

        // Begin transaction
        FragmentTransaction ft = fm.beginTransaction();

        // Create the Fragment and add
        addingTaskFragment = new AddingTaskFragment();
        ft.add(R.id.fragment_task_type, addingTaskFragment, "addTaskFragment");

        // Commit the changes
        ft.commit();
    }


    //init Toolbar
    private void initToolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //TODO: Move this, because it wastes a lot of time when activity loads.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            toolbar.setElevation((4 * Resources.getSystem().getDisplayMetrics().density));
        }
    }

    private void setHomeButton()
    {
        if(toolbar != null)
        {
            toolbar.setNavigationIcon(AppCompatResources.getDrawable(this, R.drawable.ic_cancel_button));
            toolbar.setNavigationOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                   finish();
                }
            });
        }
    }

    public void onAddTaskItem(View view)
    {
        addingTaskFragment.addItem(view);
    }

    public void onPickType(View view)
    {
        addingTaskFragment.pickTypeDialog();
    }

}
