package com.example.twinkle94.dealwithit.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//Database handling class
public class EventInfoDB extends SQLiteOpenHelper
{
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "EventInfo.db";

    //Create tables
    //Event table
    private static final String CREATE_EVENT_QUERY = "CREATE TABLE " + EventInfoContract.EventEntry.TABLE_NAME + "(" +
            EventInfoContract.EventEntry._ID + " INTEGER PRIMARY KEY," +

            EventInfoContract.EventEntry.TITLE  + " TEXT NOT NULL," +
            EventInfoContract.EventEntry.TIME_START  + " TEXT NOT NULL," +
            EventInfoContract.EventEntry.TIME_END  + " TEXT NOT NULL," +
            EventInfoContract.EventEntry.DATE  + " TEXT NOT NULL," +
            EventInfoContract.EventEntry.TYPE  + " TEXT NOT NULL," +
            EventInfoContract.EventEntry.STATE  + " TEXT NOT NULL," +
            EventInfoContract.EventEntry.IMPORTANCE  + " INTEGER NOT NULL);";

    //Comment table
    private static final String CREATE_COMMENT_QUERY = "CREATE TABLE " + EventInfoContract.CommentEntry.TABLE_NAME + "(" +
            EventInfoContract.CommentEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

            EventInfoContract.CommentEntry.ID_EVENT  + " INTEGER NOT NULL," +
            EventInfoContract.CommentEntry.CONTENT  + " TEXT NOT NULL," +

            // Set up the event column as a foreign key to event table.
            " FOREIGN KEY (" + EventInfoContract.CommentEntry.ID_EVENT + ") REFERENCES " +
            EventInfoContract.EventEntry.TABLE_NAME + " (" + EventInfoContract.EventEntry._ID + "));";

    //Delete tables
    //Delete event
    private static final String SQL_DELETE_EVENT_ENTRY =
            "DROP TABLE IF EXISTS " + EventInfoContract.EventEntry.TABLE_NAME;

    //Delete comment
    private static final String SQL_DELETE_COMMENT_ENTRY =
            "DROP TABLE IF EXISTS " + EventInfoContract.CommentEntry.TABLE_NAME;

    public EventInfoDB(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("EventInfoDB", "Database object created...");
    }

    //Create all table using sql queries
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_EVENT_QUERY);
        db.execSQL(CREATE_COMMENT_QUERY);
        Log.d("EventInfoDB", "Database created...");
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_EVENT_ENTRY);
        db.execSQL(SQL_DELETE_COMMENT_ENTRY);
        onCreate(db);
        Log.d("EventInfoDB", "Database updated...");
    }


    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onUpgrade(db, oldVersion, newVersion);
        Log.d("EventInfoDB", "Database downgraded...");
    }
}
