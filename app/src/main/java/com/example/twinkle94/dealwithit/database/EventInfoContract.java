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
        public static final String TITLE = "title";
        public static final String VALUE = "value";

        public static final String TABLE_NAME = "interest";
    }

    //Interest table of database
    public static class EventsInterestsEntry implements BaseColumns
    {
        public static final String ID_EVENT = "id_event";
        public static final String ID_INTEREST = "id_interest";

        public static final String TABLE_NAME = "events_interests";
    }

    //Location table of database
    public static class LocationEntry implements BaseColumns
    {
        public static final String ID_EVENT = "id_event";
        public static final String STREET = "street";
        public static final String CITY = "city";
        public static final String COUNTRY = "country";

        public static final String TABLE_NAME = "location";
    }

    //Schedule_Type table of database
    public static class ScheduleTypeEntry implements BaseColumns
    {
        public static final String ID_EVENT = "id_event";
        public static final String CONTENT = "content";

        public static final String TABLE_NAME = "schedule_type";
    }

    //Notification table of database
    public static class NotificationEntry implements BaseColumns
    {
        public static final String ID_EVENT = "id_event";
        public static final String CONTENT = "content";
        public static final String IMAGE = "image";
        public static final String TIME = "time";
        public static final String DATE = "date";

        public static final String TABLE_NAME = "notification";
    }

    //Maybe it will be saved in preferences?!
   /* //Settings table of database
    public static class SettingsEntry implements BaseColumns
    {
        public static final String LANGUAGE = "language";
        public static final String BEGIN_DAY = "begin_day";
        public static final String IS_NOTIFICATION = "is_notification";
        public static final String IS_NOTIFICATION_SOUND = "is_notification_sound";
        public static final String NOTIFICATION_SOUND_TITLE = "notification_sound_title";
        public static final String IS_VIBRATION = "is_vibration";
        public static final String VIBRATION_TYPE = "vibration_type";
        public static final String NOTIFY_BEFORE_AT = "notify_before_at";
        public static final String NOTIFY_AFTER_AT = "notify_after_at";

        public static final String TABLE_NAME = "settings";
    }*/
}
