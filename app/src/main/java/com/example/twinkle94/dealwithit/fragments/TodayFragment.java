package com.example.twinkle94.dealwithit.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.twinkle94.dealwithit.R;
import com.example.twinkle94.dealwithit.background.TodayTaskListLoader;

public class TodayFragment extends AbstractTabFragment
{
    public static final String TODAY_PAGE = "TODAY_PAGE";
    private static final int LAYOUT = R.layout.fragment_today;

    public static TodayFragment newInstance(int page, Context context)
    {
        Bundle args = new Bundle();
        args.putInt(TODAY_PAGE, page);

        TodayFragment fragment = new TodayFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setTitle(context.getString(R.string.tab_today_name));

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        Log.i(TODAY_PAGE, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        Log.i(TODAY_PAGE, "onAttach");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.i(TODAY_PAGE, "onCreateView");
        view = inflater.inflate(LAYOUT, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        Log.i(TODAY_PAGE, "onActivityCreated");

        //TODO: move from here!
        new TodayTaskListLoader(context).execute();
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Log.i(TODAY_PAGE, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TODAY_PAGE, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TODAY_PAGE, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TODAY_PAGE, "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TODAY_PAGE, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TODAY_PAGE, "onDetach");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TODAY_PAGE, "onDestroyView");
    }

}
