package com.example.twinkle94.dealwithit.fragments.tab_fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.twinkle94.dealwithit.R;
import com.example.twinkle94.dealwithit.adapter.today_page_adapter.TodayTaskAdapter;
import com.example.twinkle94.dealwithit.database.EventDAO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TodayFragment extends AbstractTabFragment
{
    public static final String TODAY_PAGE = "TODAY_PAGE";
    private static final int LAYOUT = R.layout.fragment_today;

    private ListView task_list;
    private TodayTaskAdapter today_task_adapter;
    private String today_date;

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
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Log.i(TODAY_PAGE, "onCreateView");
        view = inflater.inflate(LAYOUT, container, false);
        setTodayDate();
        setTodayEventsAdapter(view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        Log.i(TODAY_PAGE, "onActivityCreated");
    }

    @Override
    public void onStart()
    {
        super.onStart();

        if(!today_task_adapter.isEmpty())
        {
            today_task_adapter.clear();
        }

        //Get data into adapter from db.
        new EventDAO(context).getTodayEventListOnBG(today_task_adapter);

        Log.i(TODAY_PAGE, "onStart");
    }

    @Override
    public void onResume()
    {
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


    private void setTodayDate()
    {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat =  new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        today_date = dateFormat.format(calendar.getTime());
    }

    private void setTodayEventsAdapter(View view)
    {                            //TODO: Figure out, why this context doesn't work!
        //task_list = (ListView) ((Activity)context).findViewById(R.id.tasks_list);

            task_list = (ListView) view.findViewById(R.id.tasks_list);
            today_task_adapter = new TodayTaskAdapter(context, R.layout.today_list_item);
            today_task_adapter.setTodaysDate(today_date);
            task_list.setAdapter(today_task_adapter);
    }
}
