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

    //Sub_Task table
    private static final String CREATE_SUB_TASK_QUERY = "CREATE TABLE " + EventInfoContract.SubTaskEntry.TABLE_NAME + "(" +
            EventInfoContract.SubTaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

            EventInfoContract.SubTaskEntry.ID_EVENT  + " INTEGER NOT NULL," +
            EventInfoContract.SubTaskEntry.CONTENT  + " TEXT NOT NULL," +
            EventInfoContract.SubTaskEntry.CHECKED  + " INTEGER NOT NULL," +

            // Set up the event column as a foreign key to event table.
            " FOREIGN KEY (" + EventInfoContract.SubTaskEntry.ID_EVENT + ") REFERENCES " +
            EventInfoContract.EventEntry.TABLE_NAME + " (" + EventInfoContract.EventEntry._ID + "));";

    //Interest table
    private static final String CREATE_INTEREST_QUERY = "CREATE TABLE " + EventInfoContract.InterestEntry.TABLE_NAME + "(" +
            EventInfoContract.InterestEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

            EventInfoContract.InterestEntry.ID_EVENT  + " INTEGER NOT NULL," +
            EventInfoContract.InterestEntry.TITLE  + " TEXT NOT NULL," +
            EventInfoContract.InterestEntry.VALUE  + " INTEGER NOT NULL," +

            // Set up the event column as a foreign key to event table.
            " FOREIGN KEY (" + EventInfoContract.InterestEntry.ID_EVENT + ") REFERENCES " +
            EventInfoContract.EventEntry.TABLE_NAME + " (" + EventInfoContract.EventEntry._ID + "));";

    //ScheduleType table
    private static final String CREATE_SCHEDULE_TYPE_QUERY = "CREATE TABLE " + EventInfoContract.ScheduleTypeEntry.TABLE_NAME + "(" +
            EventInfoContract.ScheduleTypeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

            EventInfoContract.ScheduleTypeEntry.ID_EVENT  + " INTEGER NOT NULL," +
            EventInfoContract.ScheduleTypeEntry.CONTENT  + " TEXT NOT NULL);";

    //Location table
    private static final String CREATE_LOCATION_QUERY = "CREATE TABLE " + EventInfoContract.LocationEntry.TABLE_NAME + "(" +
            EventInfoContract.LocationEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

            EventInfoContract.LocationEntry.ID_EVENT  + " INTEGER NOT NULL," +
            EventInfoContract.LocationEntry.STREET  + " TEXT NOT NULL," +
            EventInfoContract.LocationEntry.CITY  + " TEXT NOT NULL," +
            EventInfoContract.LocationEntry.COUNTRY  + " TEXT NOT NULL);";

    //Notification table
    private static final String CREATE_NOTIFICATION_QUERY = "CREATE TABLE " + EventInfoContract.NotificationEntry.TABLE_NAME + "(" +
            EventInfoContract.NotificationEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

            EventInfoContract.NotificationEntry.ID_EVENT  + " INTEGER NOT NULL," +
            EventInfoContract.NotificationEntry.CONTENT  + " TEXT NOT NULL," +
            EventInfoContract.NotificationEntry.IMAGE  + " INTEGER NOT NULL," +
            EventInfoContract.NotificationEntry.TIME  + " TEXT NOT NULL, " +
            EventInfoContract.NotificationEntry.DATE  + " TEXT NOT NULL);";

    //Delete tables
    //Delete event
    private static final String SQL_DELETE_EVENT_ENTRY =
            "DROP TABLE IF EXISTS " + EventInfoContract.EventEntry.TABLE_NAME;

    //Delete comment
    private static final String SQL_DELETE_COMMENT_ENTRY =
            "DROP TABLE IF EXISTS " + EventInfoContract.CommentEntry.TABLE_NAME;

    //Delete sub_task
    private static final String SQL_DELETE_SUB_TASK_ENTRY =
            "DROP TABLE IF EXISTS " + EventInfoContract.SubTaskEntry.TABLE_NAME;

    //Delete interest
    private static final String SQL_DELETE_INTEREST_ENTRY =
            "DROP TABLE IF EXISTS " + EventInfoContract.InterestEntry.TABLE_NAME;

    //Delete schedule_type
    private static final String SQL_DELETE_SCHEDULE_TYPE_ENTRY =
            "DROP TABLE IF EXISTS " + EventInfoContract.ScheduleTypeEntry.TABLE_NAME;

    //Delete location
    private static final String SQL_DELETE_LOCATION_ENTRY =
            "DROP TABLE IF EXISTS " + EventInfoContract.LocationEntry.TABLE_NAME;

    //Delete notification
    private static final String SQL_DELETE_NOTIFICATION_ENTRY =
            "DROP TABLE IF EXISTS " + EventInfoContract.NotificationEntry.TABLE_NAME;

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
        db.execSQL(CREATE_SUB_TASK_QUERY);
        db.execSQL(CREATE_INTEREST_QUERY);
        db.execSQL(CREATE_LOCATION_QUERY);
        db.execSQL(CREATE_NOTIFICATION_QUERY);
        db.execSQL(CREATE_SCHEDULE_TYPE_QUERY);
        Log.d("EventInfoDB", "Database created...");
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_EVENT_ENTRY);
        db.execSQL(SQL_DELETE_COMMENT_ENTRY);
        db.execSQL(SQL_DELETE_SUB_TASK_ENTRY);
        db.execSQL(SQL_DELETE_INTEREST_ENTRY);
        db.execSQL(SQL_DELETE_LOCATION_ENTRY);
        db.execSQL(SQL_DELETE_NOTIFICATION_ENTRY);
        db.execSQL(SQL_DELETE_SCHEDULE_TYPE_ENTRY);
        onCreate(db);
        Log.d("EventInfoDB", "Database updated...");
    }


    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onUpgrade(db, oldVersion, newVersion);
        Log.d("EventInfoDB", "Database downgraded...");
    }
}
