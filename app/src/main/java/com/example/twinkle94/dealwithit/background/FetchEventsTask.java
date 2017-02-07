package com.example.twinkle94.dealwithit.background;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.example.twinkle94.dealwithit.database.EventInfoContract;
import com.example.twinkle94.dealwithit.database.EventInfoDB;

public class FetchEventsTask extends AsyncTask <Void, Void, Void>
{

    private Context context;

    public FetchEventsTask(Context context)
    {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids)
    {
        EventInfoDB eventInfoDB = new EventInfoDB(context);

        addDataToEvent(eventInfoDB);
        addDataToComment(eventInfoDB);
        addDataToSubTask(eventInfoDB);
        addDataToInterest(eventInfoDB);
        addDataToLocation(eventInfoDB);
        addDataToNotification(eventInfoDB);
        addDataToScheduleType(eventInfoDB);

        return null;
    }

    private void addDataToEvent(EventInfoDB eventInfoDB)
    {
        int  Id = 1;
        String  title = "Check things3";
        String  date = "January 7";
        String  time_start = "12:00 PM";
        String  time_end = "03:00 PM";
        String  type = "Work Task";
        String  state = "Waiting";
        int  importance = 83;

        SQLiteDatabase db = eventInfoDB.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(EventInfoContract.EventEntry._ID, Id);
        contentValues.put(EventInfoContract.EventEntry.TITLE, title);
        contentValues.put(EventInfoContract.EventEntry.DATE, date);
        contentValues.put(EventInfoContract.EventEntry.TIME_START, time_start);
        contentValues.put(EventInfoContract.EventEntry.TIME_END, time_end);
        contentValues.put(EventInfoContract.EventEntry.TYPE, type);
        contentValues.put(EventInfoContract.EventEntry.STATE, state);
        contentValues.put(EventInfoContract.EventEntry.IMPORTANCE, importance);

        long newRowId = db.insert(EventInfoContract.EventEntry.TABLE_NAME, null, contentValues);

        Log.d("FetchEventsTask", "New Row " + newRowId + " Inserted..." + EventInfoContract.EventEntry.TABLE_NAME);

    }

    private void addDataToComment(EventInfoDB eventInfoDB)
    {
        int  event_Id = 1;
        String  content = "Take  das is fussssss";


        SQLiteDatabase db = eventInfoDB.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

       // contentValues.put(EventInfoContract.CommentEntry._ID, Id);
        contentValues.put(EventInfoContract.CommentEntry.ID_EVENT, event_Id);
        contentValues.put(EventInfoContract.CommentEntry.CONTENT, content);

        long newRowId = db.insert(EventInfoContract.CommentEntry.TABLE_NAME, null, contentValues);

        Log.d("FetchEventsTask", "New Row " + newRowId + " Inserted..." + EventInfoContract.CommentEntry.TABLE_NAME);

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

        Log.d("FetchEventsTask", "New Row " + newRowId + " Inserted..." + EventInfoContract.SubTaskEntry.TABLE_NAME);

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

    }

}
