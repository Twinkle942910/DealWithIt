package com.example.twinkle94.dealwithit.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class EventInfoDB extends SQLiteOpenHelper
{
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "EventInfo.db";

    //Create tables
    private static final String CREATE_EVENTS_QUERY = "CREATE TABLE " + EventInfoContract.EventEntry.TABLE_NAME +
            "(" + EventInfoContract.EventEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"  + EventInfoContract.EventEntry.TITLE  + " TEXT NOT NULL,"
            + EventInfoContract.EventEntry.TIME_START  + " TEXT NOT NULL," + EventInfoContract.EventEntry.TIME_END  + " TEXT NOT NULL,"
            + EventInfoContract.EventEntry.DATE  + " TEXT NOT NULL," + EventInfoContract.EventEntry.TYPE  + " TEXT NOT NULL,"
            + EventInfoContract.EventEntry.STATE  + " TEXT NOT NULL," + EventInfoContract.EventEntry.COMMENT  + " INTEGER NOT NULL,"
            + EventInfoContract.EventEntry.SUB_TASKS  + " INTEGER NOT NULL," + EventInfoContract.EventEntry.IMPORTANCE  + " INTEGER NOT NULL,"
            + EventInfoContract.EventEntry.INTERESTS  + " INTEGER NOT NULL," + EventInfoContract.EventEntry.LOCATION + " INTEGER NOT NULL);";

    //Delete tables
    private static final String SQL_DELETE_EVENT_ENTRY =
            "DROP TABLE IF EXISTS " + EventInfoContract.EventEntry.TABLE_NAME;

    public EventInfoDB(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("EventInfoDB", "Database object created...");
    }
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_EVENTS_QUERY);
        Log.d("EventInfoDB", "Database created...");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_EVENT_ENTRY);
        onCreate(db);
        Log.d("EventInfoDB", "Database updated...");
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onUpgrade(db, oldVersion, newVersion);
        Log.d("EventInfoDB", "Database downgraded...");
    }
}
