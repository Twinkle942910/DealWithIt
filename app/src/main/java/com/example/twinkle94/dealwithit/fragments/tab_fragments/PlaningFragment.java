package com.example.twinkle94.dealwithit.fragments.tab_fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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
        Log.i(PLANING_PAGE, "onCreateView");

        view = inflater.inflate(LAYOUT, container, false);

        //final Bundle args = getArguments();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.i(PLANING_PAGE, "onCreate");
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        Log.i(PLANING_PAGE, "onAttach");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        Log.i(PLANING_PAGE, "onActivityCreated");
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Log.i(PLANING_PAGE, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(PLANING_PAGE, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(PLANING_PAGE, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(PLANING_PAGE, "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(PLANING_PAGE, "onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(PLANING_PAGE, "onDetach");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(PLANING_PAGE, "onDestroyView");
    }
}
