package com.example.twinkle94.dealwithit;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.twinkle94.dealwithit.adapter.TabPagerFragmentAdapter;
import com.example.twinkle94.dealwithit.adding_task_page.NewTaskActivity;
import com.example.twinkle94.dealwithit.events.event_types.EventType;
import com.example.twinkle94.dealwithit.interests_page.InterestsActivity;
import com.example.twinkle94.dealwithit.task_list_page.TaskActivity;
import com.example.twinkle94.dealwithit.util.Constants;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    private static final int LAYOUT = R.layout.activity_main;
    public static String TAB_POSITION = "POSITION";

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private FloatingActionButton new_task_button;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.i("MainActivity", "(onCreate) The activity is created.");

        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        initToolbar();
        initNavigationView();
        initTabs();
        initTabIcons();

        initFAB();

       /* //calling background database
        FetchEventsTask fetchEventsTask = new FetchEventsTask(getApplicationContext());
        fetchEventsTask.execute();*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("MainActivity", "(onStart) The activity is visible and about to be started.");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("MainActivity", "(onRestart) The activity is visible and about to be restarted.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("MainActivity", "(onResume) The activity is and has focus (it is now \"resumed\")");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("MainActivity",
                "(onPause) Another activity is taking focus (this activity is about to be \"paused\")");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("MainActivity", "(onStop) The activity is no longer visible (it is now \"stopped\")");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("MainActivity", "(onDestroy) The activity is about to be destroyed.");
    }

    //get - select tab position (current page)
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putInt(TAB_POSITION, tabLayout.getSelectedTabPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        viewPager.setCurrentItem(savedInstanceState.getInt(TAB_POSITION));
    }
    //

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null)
        {
            if (drawer.isDrawerOpen(GravityCompat.START))
            {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }
    }

    //Toolbar menu implementation
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // int id = item.getItemId();

        switch(item.getItemId())
        {
            case R.id.interests:
                Intent intent = new Intent(this, InterestsActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        // Handle navigation view item clicks here.
        //int id = item.getItemId();

        switch(item.getItemId())
        {
            case R.id.nav_home:
                // Handle the camera action
            case R.id.nav_notifications:

            case R.id.nav_settings:

                return true;

            case R.id.todo_page:
                Intent taskType = new Intent(this, TaskActivity.class);
                taskType.putExtra(TaskActivity.TASK_TYPE, EventType.TODO.toString());
                taskType.putExtra(TaskActivity.CALL_TYPE, "List");
                startActivity(taskType);
                return true;

            case R.id.work_tasks_page:

                return true;

            case R.id.birthday_page:

                return true;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



    //init Toolbar
    private void initToolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    //init Navigation Drawer
    private void initNavigationView()
    {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.view_navigation_open, R.string.view_navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        if (navigationView != null)
        {
            navigationView.setNavigationItemSelectedListener(this);
        }
    }

    //Initialize Tabs
    private void initTabs()
    {
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        final TabPagerFragmentAdapter adapter = new TabPagerFragmentAdapter(getSupportFragmentManager(), MainActivity.this);
        if (viewPager != null)
        {
            viewPager.setAdapter(adapter);

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
            {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
                {
                }

                @Override
                public void onPageSelected(int position)
                {
                    switch (position)
                    {
                        case Constants.TAB_ONE:


                            break;

                        case Constants.TAB_TWO:


                            break;

                        case Constants.TAB_THREE:


                            break;

                        default:


                            break;
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state)
                {

                }
            });
        }

        // Give the TabLayout the ViewPager
        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        if (tabLayout != null)
        {
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    private void initTabIcons()
    {
        TabLayout.Tab tabSchedules = tabLayout.getTabAt(Constants.TAB_ONE);
        TabLayout.Tab tabLinks = tabLayout.getTabAt(Constants.TAB_TWO);
        TabLayout.Tab tabPlaning = tabLayout.getTabAt(Constants.TAB_THREE);

        if (tabSchedules != null)
        {
            tabSchedules.setIcon(Constants.TAB_ONE_ICON);
        }

        if (tabLinks != null)
        {
            tabLinks.setIcon(Constants.TAB_TWO_ICON);
        }

        if (tabPlaning != null)
        {
            tabPlaning.setIcon(Constants.TAB_THREE_ICON);
        }
    }
    //

    private void initFAB()
    {
        new_task_button = (FloatingActionButton) findViewById(R.id.new_task);
        new_task_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), NewTaskActivity.class);
                startActivity(intent);
            }
        });
    }
}
