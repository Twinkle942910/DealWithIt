package com.example.twinkle94.dealwithit.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.twinkle94.dealwithit.R;

public class PlaningFragment extends AbstractTabFragment
{
    public static final String PLANING_PAGE = "PLANING_PAGE";
    private static final int LAYOUT = R.layout.fragment_planing;

    
    public static PlaningFragment newInstance(int page, Context context)
    {
        Bundle args = new Bundle();
        args.putInt(PLANING_PAGE, page);

        PlaningFragment fragment = new PlaningFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_planing_name));

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(LAYOUT, container, false);

        //final Bundle args = getArguments();

        return view;
    }
}
