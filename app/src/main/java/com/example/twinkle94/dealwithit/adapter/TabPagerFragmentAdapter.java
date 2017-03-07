package com.example.twinkle94.dealwithit.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.twinkle94.dealwithit.fragments.AbstractTabFragment;
import com.example.twinkle94.dealwithit.fragments.PlaningFragment;
import com.example.twinkle94.dealwithit.fragments.ScheduleFragment;
import com.example.twinkle94.dealwithit.fragments.TodayFragment;
import com.example.twinkle94.dealwithit.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class TabPagerFragmentAdapter extends FragmentPagerAdapter
{
    private List<AbstractTabFragment> tabs;

    public TabPagerFragmentAdapter(FragmentManager fm, Context context)
    {
        super(fm);
        initTansMap(context);
    }

    private void initTansMap(Context context)
    {
        tabs = new ArrayList<>();

        tabs.add(TodayFragment.newInstance(Constants.TAB_ONE, context));
        tabs.add(ScheduleFragment.newInstance(Constants.TAB_TWO, context));
        tabs.add(PlaningFragment.newInstance(Constants.TAB_THREE, context));

    }

    @Override
    public Fragment getItem(int position)
    {
        return tabs.get(position);
    }

    @Override
    public int getCount()
    {
        return tabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return tabs.get(position).getTitle();
    }
}



