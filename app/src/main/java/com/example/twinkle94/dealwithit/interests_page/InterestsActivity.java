package com.example.twinkle94.dealwithit.interests_page;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.twinkle94.dealwithit.R;
import com.example.twinkle94.dealwithit.adapter.interests_page_adapter.InterestHeader;
import com.example.twinkle94.dealwithit.adapter.interests_page_adapter.InterestsAdapter;
import com.example.twinkle94.dealwithit.events.Interest;

public class InterestsActivity extends AppCompatActivity
{
    private Toolbar toolbar;
    private ListView interests_lv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);

        initToolbar();
        initInterestList();
    }

    private void initInterestList()
    {
        interests_lv = (ListView) findViewById(R.id.interests_list);
        InterestsAdapter interestsAdapter  =  new InterestsAdapter(this);

        interestsAdapter.add(new InterestHeader("Important"));
        interestsAdapter.add(new Interest(1, 1, "Study", 64));
        interestsAdapter.add(new Interest(1, 1, "Friends", 83));
        interestsAdapter.add(new InterestHeader("Not important"));
        interestsAdapter.add(new Interest(1, 1, "Soc. Networks", 33));
        interestsAdapter.add(new Interest(1, 1, "Books", 56));
        interestsAdapter.add(new Interest(1, 1, "Work", 23));

        interestsAdapter.add(new InterestHeader("Important"));
        interestsAdapter.add(new Interest(1, 1, "Study", 64));
        interestsAdapter.add(new Interest(1, 1, "Friends", 83));
        interestsAdapter.add(new InterestHeader("Not important"));
        interestsAdapter.add(new Interest(1, 1, "Soc. Networks", 33));
        interestsAdapter.add(new Interest(1, 1, "Books", 56));
        interestsAdapter.add(new Interest(1, 1, "Work", 23));

        interestsAdapter.add(new InterestHeader("Important"));
        interestsAdapter.add(new Interest(1, 1, "Study", 64));
        interestsAdapter.add(new Interest(1, 1, "Friends", 83));
        interestsAdapter.add(new InterestHeader("Not important"));
        interestsAdapter.add(new Interest(1, 1, "Soc. Networks", 33));
        interestsAdapter.add(new Interest(1, 1, "Books", 56));
        interestsAdapter.add(new Interest(1, 1, "Work", 23));

        interests_lv.setAdapter(interestsAdapter);
    }

    //Toolbar menu implementation
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_interests, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.interests_info:
                return true;

            case R.id.show_all:
                return true;

            case R.id.show_important:
                return true;

            case R.id.show_not_important:
                return true;

            case R.id.show_az:
                return true;

            case R.id.show_za:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //init Toolbar
    private void initToolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setHomeButton();
    }

    private void setHomeButton()
    {
        if(toolbar != null)
        {
            toolbar.setNavigationIcon(AppCompatResources.getDrawable(this, R.drawable.ic_arrow_back));
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
