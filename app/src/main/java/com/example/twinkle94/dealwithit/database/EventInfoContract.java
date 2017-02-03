package com.example.twinkle94.dealwithit.database;

import android.provider.BaseColumns;

public final class EventInfoContract
{
    private EventInfoContract()
    {

    }

    public static class EventEntry implements BaseColumns
    {
        public static final String TITLE = "title";
        public static final String TIME_START = "time_start";
        public static final String TIME_END = "time_end";
        public static final String DATE = "date";
        public static final String TYPE = "type";
        public static final String STATE = "state";
        public static final String COMMENT = "comment";
        public static final String SUB_TASKS = "sub_tasks";
        public static final String IMPORTANCE = "importance";
        public static final String INTERESTS = "interests";
        public static final String LOCATION = "location";

        public static final String TABLE_NAME = "event_table";
    }
}
