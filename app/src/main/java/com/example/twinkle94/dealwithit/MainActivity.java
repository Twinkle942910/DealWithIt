package com.example.twinkle94.dealwithit;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{

    private static final int LAYOUT = R.layout.activity_main;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        initToolbar();
        initNavigationView();
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
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        if (navigationView != null)
        {
            navigationView.setNavigationItemSelectedListener(this);
        }
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
              //  Intent intent = new Intent(this, SettingsActivity.class);
             //   startActivity(intent);
                return true;

            case R.id.todo_page:
          /*      intent = new Intent(this, EventActivity.class);
                intent.putExtra("calling", "From Main Activity");
                intent.putExtra("type", "ToDo");
                startActivity(intent);*/
                return true;

            case R.id.work_tasks_page:
            /*    intent = new Intent(this, EventActivity.class);
                intent.putExtra("calling", "From Main Activity");
                intent.putExtra("type", "Work Task");
                startActivity(intent);*/
                return true;

            case R.id.birthday_page:
               /* intent = new Intent(this, EventActivity.class);
                intent.putExtra("calling", "From Main Activity");
                intent.putExtra("type", "Birthday");
                startActivity(intent);*/
                return true;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
