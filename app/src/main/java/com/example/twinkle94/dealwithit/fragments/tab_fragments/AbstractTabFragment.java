package com.example.twinkle94.dealwithit.fragments.tab_fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

public class  AbstractTabFragment extends Fragment
{

    private String title;
    protected Context context;

    protected View view;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public void setContext(Context context)
    {
        this.context = context;
    }

}
