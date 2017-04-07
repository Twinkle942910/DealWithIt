package com.example.twinkle94.dealwithit.background;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.example.twinkle94.dealwithit.database.EventInfoContract;
import com.example.twinkle94.dealwithit.database.EventInfoDB;
import com.example.twinkle94.dealwithit.events.Comment;
import com.example.twinkle94.dealwithit.events.Event;
import com.example.twinkle94.dealwithit.events.Interest;
import com.example.twinkle94.dealwithit.events.Sub_task;
import com.example.twinkle94.dealwithit.events.task_types.Schedule;
import com.example.twinkle94.dealwithit.events.task_types.ToDo;
import com.example.twinkle94.dealwithit.events.type_enums.EventType;
import com.example.twinkle94.dealwithit.events.type_enums.ScheduleType;

import java.util.List;

public class FetchEventsTask extends AsyncTask <Object, Void, String>
{
    private static final String TAG = FetchEventsTask.class.getSimpleName();
    private Context context;

    public FetchEventsTask(Context context)
    {
        this.context = context;
    }

    @Override
    protected String doInBackground(Object... params)
    {
        String operation = (String)params[0];
        EventType event_type = ((Event)params[1]).getType();
        String result = null;
        EventInfoDB eventInfoDB = new EventInfoDB(context);

        switch (operation)
        {
            case "add_data":
                result = addDataToDB(event_type, eventInfoDB, (Event)params[1]);
                break;
        }
        return result;
    }

    //TODO: change String types to Enum!
    private String addDataToDB(EventType event_type, EventInfoDB eventInfoDB, Event event)
    {
        switch (event_type)
        {
            case SCHEDULE:
                addScheduleTask(eventInfoDB, event);
                break;

            case TODO:
                addToDoTask(eventInfoDB, event);
                break;

            case WORKTASK:

                break;

            case BIRTHDAY:

                break;
        }

        return event_type + " was added to DB";
    }

    private void addScheduleTask(EventInfoDB eventInfoDB, Event event)
    {
        EventType type = EventType.SCHEDULE;
        String  title = event.getTitle();
        String  date = event.getDate();
        String  time_start = event.getTime_start();
        String  time_end = event.getTime_end();
        String  state = event.getState();
        int  importance = event.getImportance();
        List<Interest> interests = event.getListInterests();
        ScheduleType schedule_type = ((Schedule)event).getScheduleType();

        SQLiteDatabase db = eventInfoDB.getWritableDatabase();

        //Adding Schedule
        ContentValues contentValues = new ContentValues();

        contentValues.put(EventInfoContract.EventEntry.TITLE, title);
        contentValues.put(EventInfoContract.EventEntry.DATE, date);
        contentValues.put(EventInfoContract.EventEntry.TIME_START, time_start);
        contentValues.put(EventInfoContract.EventEntry.TIME_END, time_end);
        contentValues.put(EventInfoContract.EventEntry.TYPE, type.toString());
        contentValues.put(EventInfoContract.EventEntry.STATE, state);
        contentValues.put(EventInfoContract.EventEntry.IMPORTANCE, importance);

        long newRowId = db.insert(EventInfoContract.EventEntry.TABLE_NAME, null, contentValues);

        if(interests != null)
        //Adding interests
        for(Interest interest : interests)
        {
            ContentValues contentValuesInterests = new ContentValues();

            contentValuesInterests.put(EventInfoContract.InterestEntry.ID_EVENT, newRowId);
            contentValuesInterests.put(EventInfoContract.InterestEntry.TITLE, interest.getTitle());
            contentValuesInterests.put(EventInfoContract.InterestEntry.VALUE, interest.getValue());

            db.insert(EventInfoContract.InterestEntry.TABLE_NAME, null, contentValuesInterests);
        }

        //Adding Schedule type
         ContentValues contentValuesScheduleType = new ContentValues();

        contentValuesScheduleType.put(EventInfoContract.ScheduleTypeEntry.ID_EVENT, newRowId);
        contentValuesScheduleType.put(EventInfoContract.ScheduleTypeEntry.CONTENT, schedule_type.toString());

        db.insert(EventInfoContract.ScheduleTypeEntry.TABLE_NAME, null, contentValuesScheduleType);

        Log.i(TAG, "New Row " + newRowId + " Inserted..." + EventInfoContract.EventEntry.TABLE_NAME);

    }

    private void addToDoTask(EventInfoDB eventInfoDB, Event event)
    {
        EventType type = EventType.TODO;
        String  title = event.getTitle();
        String  date = event.getDate();
        String  time_start = event.getTime_start();
        String  time_end = event.getTime_end();
        String  state = event.getState();
        int  importance = event.getImportance();

        List<Interest> interests = event.getListInterests();
        List<Sub_task> sub_tasks = ((ToDo)event).getListSubTasks();
        List<Comment> comments = ((ToDo)event).getListComments();

        SQLiteDatabase db = eventInfoDB.getWritableDatabase();

        //Adding ToDo
        ContentValues contentValues = new ContentValues();

        contentValues.put(EventInfoContract.EventEntry.TITLE, title);
        contentValues.put(EventInfoContract.EventEntry.DATE, date);
        contentValues.put(EventInfoContract.EventEntry.TIME_START, time_start);
        contentValues.put(EventInfoContract.EventEntry.TIME_END, time_end);
        contentValues.put(EventInfoContract.EventEntry.TYPE, type.toString());
        contentValues.put(EventInfoContract.EventEntry.STATE, state);
        contentValues.put(EventInfoContract.EventEntry.IMPORTANCE, importance);

        long newRowId = db.insert(EventInfoContract.EventEntry.TABLE_NAME, null, contentValues);

        if(interests != null)
        //Adding interests
        for(Interest interest : interests)
        {
            ContentValues contentValuesInterests = new ContentValues();

            contentValuesInterests.put(EventInfoContract.InterestEntry.ID_EVENT, newRowId);
            contentValuesInterests.put(EventInfoContract.InterestEntry.TITLE, interest.getTitle());
            contentValuesInterests.put(EventInfoContract.InterestEntry.VALUE, interest.getValue());

            db.insert(EventInfoContract.InterestEntry.TABLE_NAME, null, contentValuesInterests);
        }

        if(sub_tasks != null)
        //Adding subTasks
        for(Sub_task sub_task : sub_tasks)
        {
            ContentValues contentValuesSubTasks = new ContentValues();

            contentValuesSubTasks.put(EventInfoContract.SubTaskEntry.ID_EVENT, newRowId);
            contentValuesSubTasks.put(EventInfoContract.SubTaskEntry.CONTENT, sub_task.getContent());
            contentValuesSubTasks.put(EventInfoContract.SubTaskEntry.CHECKED, sub_task.isChecked());

            db.insert(EventInfoContract.SubTaskEntry.TABLE_NAME, null, contentValuesSubTasks);
        }

        if(comments != null)
        //Adding Comments
        for(Comment comment : comments)
        {
            ContentValues contentValuesComments = new ContentValues();

            contentValuesComments.put(EventInfoContract.CommentEntry.ID_EVENT, newRowId);
            contentValuesComments.put(EventInfoContract.CommentEntry.CONTENT, comment.getContent());

            db.insert(EventInfoContract.CommentEntry.TABLE_NAME, null, contentValuesComments);
        }

        Log.i(TAG, "New Row " + newRowId + " Inserted..." + EventInfoContract.EventEntry.TABLE_NAME);
    }

    /*private void addDataToComment(EventInfoDB eventInfoDB)
    {
        int  event_Id = 1;
        String  content = "Take  das is fussssss";


        SQLiteDatabase db = eventInfoDB.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

       // contentValues.put(EventInfoContract.CommentEntry._ID, Id);
        contentValues.put(EventInfoContract.CommentEntry.ID_EVENT, event_Id);
        contentValues.put(EventInfoContract.CommentEntry.CONTENT, content);

        long newRowId = db.insert(EventInfoContract.CommentEntry.TABLE_NAME, null, contentValues);

        Log.i("FetchEventsTask", "New Row " + newRowId + " Inserted..." + EventInfoContract.CommentEntry.TABLE_NAME);

    }

    private void addDataToSubTask(EventInfoDB eventInfoDB)
    {
        int  event_Id = 1;
        String  content = "Gustlers";
        int checked = 0;

        SQLiteDatabase db = eventInfoDB.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(EventInfoContract.SubTaskEntry.ID_EVENT, event_Id);
        contentValues.put(EventInfoContract.SubTaskEntry.CONTENT, content);
        contentValues.put(EventInfoContract.SubTaskEntry.CHECKED, checked);

        long newRowId = db.insert(EventInfoContract.SubTaskEntry.TABLE_NAME, null, contentValues);

        Log.i("FetchEventsTask", "New Row " + newRowId + " Inserted..." + EventInfoContract.SubTaskEntry.TABLE_NAME);

    }

    private void addDataToInterest(EventInfoDB eventInfoDB)
    {
        int  event_Id = 1;
        String  title = "Boys";
        int value = 53;

        SQLiteDatabase db = eventInfoDB.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(EventInfoContract.InterestEntry.ID_EVENT, event_Id);
        contentValues.put(EventInfoContract.InterestEntry.TITLE, title);
        contentValues.put(EventInfoContract.InterestEntry.VALUE, value);

        long newRowId = db.insert(EventInfoContract.InterestEntry.TABLE_NAME, null, contentValues);

        Log.d("FetchEventsTask", "New Row " + newRowId + " Inserted..." + EventInfoContract.InterestEntry.TABLE_NAME);

    }

    private void addDataToScheduleType(EventInfoDB eventInfoDB)
    {
        int  event_Id = 1;
        String  content = "Lesson";

        SQLiteDatabase db = eventInfoDB.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(EventInfoContract.ScheduleTypeEntry.ID_EVENT, event_Id);
        contentValues.put(EventInfoContract.ScheduleTypeEntry.CONTENT, content);

        long newRowId = db.insert(EventInfoContract.ScheduleTypeEntry.TABLE_NAME, null, contentValues);

        Log.d("FetchEventsTask", "New Row " + newRowId + " Inserted..." + EventInfoContract.ScheduleTypeEntry.TABLE_NAME);

    }

    private void addDataToLocation(EventInfoDB eventInfoDB)
    {
        int  event_Id = 1;
        String  street = "Roksolany, 25";
        String  city = "Lviv";
        String  country = "Ukraine";


        SQLiteDatabase db = eventInfoDB.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(EventInfoContract.LocationEntry.ID_EVENT, event_Id);
        contentValues.put(EventInfoContract.LocationEntry.STREET, street);
        contentValues.put(EventInfoContract.LocationEntry.CITY, city);
        contentValues.put(EventInfoContract.LocationEntry.COUNTRY, country);

        long newRowId = db.insert(EventInfoContract.LocationEntry.TABLE_NAME, null, contentValues);

        Log.d("FetchEventsTask", "New Row " + newRowId + " Inserted..." + EventInfoContract.LocationEntry.TABLE_NAME);

    }

    private void addDataToNotification(EventInfoDB eventInfoDB)
    {
        int  event_Id = 1;
        String  content = "buy grocery task";
        int image = 123454;
        String time = "01:12 PM";
        String date = "January, 14";

        SQLiteDatabase db = eventInfoDB.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(EventInfoContract.NotificationEntry.ID_EVENT, event_Id);
        contentValues.put(EventInfoContract.NotificationEntry.CONTENT, content);
        contentValues.put(EventInfoContract.NotificationEntry.IMAGE, image);
        contentValues.put(EventInfoContract.NotificationEntry.TIME, time);
        contentValues.put(EventInfoContract.NotificationEntry.DATE, date);

        long newRowId = db.insert(EventInfoContract.NotificationEntry.TABLE_NAME, null, contentValues);

        Log.d("FetchEventsTask", "New Row " + newRowId + " Inserted..." + EventInfoContract.NotificationEntry.TABLE_NAME);

    }*/

    @Override
    protected void onPostExecute(String result)
    {
        Log.i(TAG, result);
    }
}
