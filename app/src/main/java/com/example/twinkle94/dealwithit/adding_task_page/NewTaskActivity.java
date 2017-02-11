package com.example.twinkle94.dealwithit.adding_task_page;

import android.content.res.Resources;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.twinkle94.dealwithit.R;

public class NewTaskActivity extends AppCompatActivity
{
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        initToolbar();
        setHomeButton();
    }

    //init Toolbar
    private void initToolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            toolbar.setElevation((4 * Resources.getSystem().getDisplayMetrics().density));
        }
    }

    private void setHomeButton()
    {
        if(toolbar != null)
        {
            toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_cancel_button));
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

}
