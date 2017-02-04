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

        return null;
    }

    private void addDataToEvent(EventInfoDB eventInfoDB)
    {
        int  Id = 3;
        String  title = "Check things2";
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
        String  content = "Take a list";


        SQLiteDatabase db = eventInfoDB.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

       // contentValues.put(EventInfoContract.CommentEntry._ID, Id);
        contentValues.put(EventInfoContract.CommentEntry.ID_EVENT, event_Id);
        contentValues.put(EventInfoContract.CommentEntry.CONTENT, content);

        long newRowId = db.insert(EventInfoContract.CommentEntry.TABLE_NAME, null, contentValues);

        Log.d("FetchEventsTask", "New Row " + newRowId + " Inserted..." + EventInfoContract.CommentEntry.TABLE_NAME);

    }

}
