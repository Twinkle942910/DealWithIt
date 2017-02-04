package com.example.twinkle94.dealwithit.database;

import android.provider.BaseColumns;

//Schema of database
public final class EventInfoContract
{
    private EventInfoContract()
    {

    }

    //Event table of database
    public static class EventEntry implements BaseColumns
    {
        public static final String TITLE = "title";
        public static final String TIME_START = "time_start";
        public static final String TIME_END = "time_end";
        public static final String DATE = "date";
        public static final String TYPE = "type";
        public static final String STATE = "state";
        public static final String IMPORTANCE = "importance";

        public static final String TABLE_NAME = "event_table";
    }

    //Comment table of database
    public static class CommentEntry implements BaseColumns
    {
        public static final String ID_EVENT = "id_event";
        public static final String CONTENT = "content";

        public static final String TABLE_NAME = "comment";
    }

    //Sub_task table of database
    public static class SubTaskEntry implements BaseColumns
    {
        public static final String ID_EVENT = "id_event";
        public static final String CONTENT = "content";
        public static final String CHECKED = "checked";

        public static final String TABLE_NAME = "sub_task";
    }

    //Interest table of database
    public static class InterestEntry implements BaseColumns
    {
        public static final String ID_EVENT = "id_event";
        public static final String TITLE = "title";
        public static final String VALUE = "value";

        public static final String TABLE_NAME = "interest";
    }
}
