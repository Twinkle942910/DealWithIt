package com.example.twinkle94.dealwithit.background;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.example.twinkle94.dealwithit.R;
import com.example.twinkle94.dealwithit.adapter.today_page_adapter.EventTypeSection;
import com.example.twinkle94.dealwithit.adapter.today_page_adapter.Item;
import com.example.twinkle94.dealwithit.adapter.today_page_adapter.TodayTaskAdapter;
import com.example.twinkle94.dealwithit.events.Event;
import com.example.twinkle94.dealwithit.events.Location;
import com.example.twinkle94.dealwithit.events.task_types.Birthday;
import com.example.twinkle94.dealwithit.events.task_types.Schedule;
import com.example.twinkle94.dealwithit.events.task_types.ToDo;
import com.example.twinkle94.dealwithit.events.task_types.WorkTask;
import com.example.twinkle94.dealwithit.events.type_enums.EventType;
import com.example.twinkle94.dealwithit.events.type_enums.ScheduleType;

public class TodayTaskListLoader extends AsyncTask<Void, Item, Void>
{
    private ListView task_list;
    private TodayTaskAdapter today_task_adapter;

    private Context context;

    public TodayTaskListLoader(Context context)
    {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids)
    {
        Log.i("TodayTaskListLoader", "doInBackground");

        task_list = (ListView) ((Activity)context).findViewById(R.id.tasks_list);
        today_task_adapter = new TodayTaskAdapter(context, R.layout.today_list_item);

        for (int i = 0; i < 13; i++)
        {
            if(i == 0)
            {
                publishProgress(new EventTypeSection("Schedule"));
            }
            if(i == 3)
            {
                publishProgress(new EventTypeSection("ToDo"));
            }
            if(i == 6)
            {
                publishProgress(new EventTypeSection("Work Task"));
            }
            if(i == 9)
            {
                publishProgress(new EventTypeSection("Birthday"));
            }

            if(i > 0 && i < 3)
            {
                Event event = new Schedule(i,"Algorythm Theory " + i, ScheduleType.LESSON, "10:20 AM", "11:55 AM", "07.01.2017", "Waiting", 79/2 + i*2);
                publishProgress(event);
            }

            if(i > 3 && i < 6)
            {
                Event event = new ToDo(i,"Clean house " + i, "09:30 AM", "03:35 PM", "07.01.2017", EventType.TODO, "Waiting", 91/2 + i*2);
                publishProgress(event);
            }

            if(i > 6 && i < 9)
            {
                Event event = new WorkTask(i,"Refactoring " + i, "09:30 AM", "01:35 PM", "07.01.2017", EventType.WORKTASK, "Waiting", 81/2 + i*2);
                publishProgress(event);
            }

            if(i > 9 && i < 13)
            {
                Event event = new Birthday(i,"Taras's birthday " + i, "09:30 AM", "01:35 PM", "07.01.2017", EventType.BIRTHDAY, "Waiting", 71/2 + i*2, new Location(i, i+1, "Roksolany", "Lviv", "Ukraine"));
                publishProgress(event);
            }
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Item... items)
    {
        today_task_adapter.add(items[0]);
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        task_list.setAdapter(today_task_adapter);
    }
}
