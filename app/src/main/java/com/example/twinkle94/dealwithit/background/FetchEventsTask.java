package com.example.twinkle94.dealwithit.background;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.example.twinkle94.dealwithit.adapter.interests_page_adapter.InterestHeader;
import com.example.twinkle94.dealwithit.adapter.interests_page_adapter.InterestsAdapter;
import com.example.twinkle94.dealwithit.adapter.today_page_adapter.EventTypeSection;
import com.example.twinkle94.dealwithit.adapter.today_page_adapter.TodayTaskAdapter;
import com.example.twinkle94.dealwithit.database.EventInfoContract;
import com.example.twinkle94.dealwithit.database.EventInfoDB;
import com.example.twinkle94.dealwithit.events.Comment;
import com.example.twinkle94.dealwithit.events.Event;
import com.example.twinkle94.dealwithit.events.EventInterest;
import com.example.twinkle94.dealwithit.events.Interest;
import com.example.twinkle94.dealwithit.events.Location;
import com.example.twinkle94.dealwithit.events.Sub_task;
import com.example.twinkle94.dealwithit.events.task_types.Birthday;
import com.example.twinkle94.dealwithit.events.task_types.Schedule;
import com.example.twinkle94.dealwithit.events.task_types.ToDo;
import com.example.twinkle94.dealwithit.events.task_types.WorkTask;
import com.example.twinkle94.dealwithit.events.type_enums.EventType;
import com.example.twinkle94.dealwithit.events.type_enums.ScheduleType;

import java.util.ArrayList;
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
        String event_type;

        if(params[1] instanceof Event)
        {
            event_type = ((Event) params[1]).getType().toString();
        }
        else
        {
            event_type = params[1].getClass().getSimpleName();
        }

        String result = null;
        EventInfoDB eventInfoDB = new EventInfoDB(context);

        switch (operation)
        {
            case "add_data":
                result = addDataToDB(event_type, eventInfoDB, params[1]);
                break;

            case "get_data":
                String get_data_type = (String)params[2];
                result = getDataFromDB(get_data_type, eventInfoDB, params[1]);
                break;

            case "update_data":
                result = updateDataInDB(event_type, eventInfoDB, params[1]);
                break;

            case "remove_data":
                result = removeDataFromDB(event_type, eventInfoDB, params[1]);
                break;

            case "remove_data_by":
                String delete_type = (String)params[2];
                result = removeDataFromDBby(delete_type, eventInfoDB, params[1]);
                break;
        }
        return result;
    }

    private String addDataToDB(String event_type, EventInfoDB eventInfoDB, Object event)
    {
        switch (event_type)
        {
            case "Schedule":
                addScheduleTask(eventInfoDB, (Schedule) event);
                break;

            case "ToDo":
                addToDoTask(eventInfoDB,  (ToDo) event);
                break;

            case "Work Task":
                //TODO: add to work!
                break;

            case "Birthday":

                break;

            case "Comment":
                addDataToComment(eventInfoDB, (Comment) event);
                break;

            case "Sub_task":
                addDataToSub_task(eventInfoDB, (Sub_task) event);
                break;

            case "Interest":
                addDataToInterest(eventInfoDB, (Interest) event);
                break;

            case "EventInterest":
                addDataToEventsInterests(eventInfoDB, (EventInterest) event);
                break;
        }

        return event_type + " was added to DB";
    }

    private String getDataFromDB(String get_data_type, EventInfoDB eventInfoDB, Object event)
    {
        switch (get_data_type)
        {
            case "Schedule":

                break;

            case "ToDo":

                break;

            case "Work Task":

                break;

            case "Birthday":

                break;

            case "Sub_task":

                break;

            case "Interest":
                getDataListFromInterest(eventInfoDB, (InterestsAdapter) event);
                break;

            case "TodayList":
                getTodayDataListFromEvents(eventInfoDB, (TodayTaskAdapter) event);
                break;
        }

        return get_data_type + " was got from DB";
    }

    private String updateDataInDB(String event_type, EventInfoDB eventInfoDB, Object event)
    {
        switch (event_type)
        {
            case "Schedule":

                break;

            case "ToDo":
                updateDataInToDo(eventInfoDB, (ToDo)event);
                break;

            case "Work Task":
                updateDataInWorkTask(eventInfoDB, (WorkTask)event);
                break;

            case "Birthday":

                break;

            case "Comment":

                break;

            case "Sub_task":
                updateDataInSub_task(eventInfoDB, (Sub_task) event);
                break;

            case "Interest":

                break;
        }

        return event_type + " was updated in DB";
    }

    private String removeDataFromDB(String event_type, EventInfoDB eventInfoDB, Object event)
    {
        switch (event_type)
        {
            case "Schedule":
                removeScheduleTask(eventInfoDB, ((Schedule) event).getId());
                break;

            case "ToDo":
                removeToDoTask(eventInfoDB, ((ToDo) event).getId());
                break;

            case "Work Task":

                break;

            case "Birthday":

                break;

            case "Comment":
                removeDataFromComment(eventInfoDB, ((Comment) event).getId());
                break;

            case "Sub_task":
                removeDataFromSub_task(eventInfoDB, ((Sub_task) event).getId());
                break;

            case "Interest":
                removeDataFromInterest(eventInfoDB, ((Interest) event).getId());
                break;

            case "EventInterest":
                removeDataFromEventInterest(eventInfoDB, ((EventInterest) event).getId());
                break;
        }

        return event_type + " was removed from DB";
    }

    private String removeDataFromDBby(String delete_type, EventInfoDB eventInfoDB, Object event)
    {
        String remove_from = event.getClass().getSimpleName() + " " + delete_type;
        switch (remove_from)
        {
            case "Schedule":

                break;

            case "ToDo":

                break;

            case "Work Task":

                break;

            case "Birthday":

                break;

            case "Comment event_id":
                removeDataFromCommentByEventId(eventInfoDB, ((Comment) event).getId_event());
                break;

            case "Sub_task event_id":
                removeDataFromSub_taskByEventId(eventInfoDB, ((Sub_task) event).getEvent_id());
                break;

            case "EventInterest event_id":
                removeDataFromEventsInterestsByEventId(eventInfoDB, ((EventInterest) event).getId_event());
                break;

            case "Interest":

                break;
        }

        return event.getClass().getSimpleName() + " was removed from DB by " + delete_type;
    }

    private void addScheduleTask(EventInfoDB eventInfoDB, Schedule schedule)
    {
        EventType type = EventType.SCHEDULE;
        String  title = schedule.getTitle();
        String  date = schedule.getDate();
        String  time_start = schedule.getTime_start();
        String  time_end = schedule.getTime_end();
        String  state = schedule.getState();
        int  importance = schedule.getImportance();
        ScheduleType schedule_type = schedule.getScheduleType();

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
        schedule.setId((int) newRowId);

        //Adding Schedule type
         ContentValues contentValuesScheduleType = new ContentValues();

        contentValuesScheduleType.put(EventInfoContract.ScheduleTypeEntry.ID_EVENT, newRowId);
        contentValuesScheduleType.put(EventInfoContract.ScheduleTypeEntry.CONTENT, schedule_type.toString());

        db.insert(EventInfoContract.ScheduleTypeEntry.TABLE_NAME, null, contentValuesScheduleType);


        Log.i(TAG, "New Row " + newRowId + " Inserted..." + EventInfoContract.EventEntry.TABLE_NAME);

    }

    private void removeScheduleTask(EventInfoDB eventInfoDB, int schedule_id)
    {
        SQLiteDatabase db = eventInfoDB.getWritableDatabase();

        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(EventInfoContract.EventEntry.TABLE_NAME, EventInfoContract.EventEntry._ID + "= ?", new String[]{String.valueOf(schedule_id)});
        db.delete(EventInfoContract.ScheduleTypeEntry.TABLE_NAME, EventInfoContract.ScheduleTypeEntry.ID_EVENT + "= ?", new String[]{String.valueOf(schedule_id)});
        db.close();
    }

    private void addToDoTask(EventInfoDB eventInfoDB, ToDo event)
    {
        EventType type = EventType.TODO;
        String  title = event.getTitle();
        String  date = event.getDate();
        String  time_start = event.getTime_start();
        String  time_end = event.getTime_end();
        String  state = event.getState();
        int  importance = event.getImportance();

        SQLiteDatabase db = eventInfoDB.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(EventInfoContract.EventEntry.TITLE, title);
        contentValues.put(EventInfoContract.EventEntry.DATE, date);
        contentValues.put(EventInfoContract.EventEntry.TIME_START, time_start);
        contentValues.put(EventInfoContract.EventEntry.TIME_END, time_end);
        contentValues.put(EventInfoContract.EventEntry.TYPE, type.toString());
        contentValues.put(EventInfoContract.EventEntry.STATE, state);
        contentValues.put(EventInfoContract.EventEntry.IMPORTANCE, importance);

        long newRowId = db.insert(EventInfoContract.EventEntry.TABLE_NAME, null, contentValues);
        event.setId((int) newRowId);

        Log.i(TAG, "New Row " + newRowId + " Inserted..." + EventInfoContract.EventEntry.TABLE_NAME);
    }

    private void removeToDoTask(EventInfoDB eventInfoDB, int toto_id)
    {
        SQLiteDatabase db = eventInfoDB.getWritableDatabase();

        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(EventInfoContract.EventEntry.TABLE_NAME, EventInfoContract.EventEntry._ID + "= ?", new String[]{String.valueOf(toto_id)});
        db.close();
    }

   /* public void getLastEventId(EventInfoDB eventInfoDB, Integer lastId)
    {
        String query = "SELECT _id from event_table order by _id DESC limit 1";
        SQLiteDatabase db = eventInfoDB.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst())
        {
            lastId = (int) cursor.getLong(0); //The 0 is the column index, we only have 1 column, so the index is 0
            cursor.close();
        }

        db.close();
    }*/


    private void addDataToInterest(EventInfoDB eventInfoDB, Interest interest)
    {
        SQLiteDatabase db = eventInfoDB.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(EventInfoContract.InterestEntry.TITLE, interest.getTitle());
        contentValues.put(EventInfoContract.InterestEntry.VALUE, interest.getValue());

        long newRowId = db.insert(EventInfoContract.InterestEntry.TABLE_NAME, null, contentValues);

        interest.setId((int) newRowId);

        Log.d("FetchEventsTask", "New Row " + newRowId + " Inserted..." + EventInfoContract.InterestEntry.TABLE_NAME);
    }

    private void addDataToEventsInterests(EventInfoDB eventInfoDB, EventInterest eventInterest)
    {
        SQLiteDatabase db = eventInfoDB.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(EventInfoContract.EventsInterestsEntry.ID_EVENT, eventInterest.getId_event());
        contentValues.put(EventInfoContract.EventsInterestsEntry.ID_INTEREST, eventInterest.getId_interest());

        long newRowId = db.insert(EventInfoContract.EventsInterestsEntry.TABLE_NAME, null, contentValues);

        eventInterest.setId((int) newRowId);

        Log.d("FetchEventsTask", "New Row " + newRowId + " Inserted..." + EventInfoContract.EventsInterestsEntry.TABLE_NAME);
    }

    //Get list of all Interests
    private void getDataListFromInterest(EventInfoDB eventInfoDB, InterestsAdapter interests_adapter)
    {
        //Open connection to read only
        SQLiteDatabase db = eventInfoDB.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                EventInfoContract.InterestEntry._ID + "," +
                EventInfoContract.InterestEntry.TITLE + "," +
                EventInfoContract.InterestEntry.VALUE +
                " FROM " + EventInfoContract.InterestEntry.TABLE_NAME;

        //using rawQuery
        Cursor interest_cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        //Move from first ToDo to last
        if (interest_cursor.moveToFirst())
        {
            do
            {
                int interest_id = interest_cursor.getInt(interest_cursor.getColumnIndex(EventInfoContract.InterestEntry._ID));
                String interest_title = interest_cursor.getString(interest_cursor.getColumnIndex(EventInfoContract.InterestEntry.TITLE));
                int interest_value = interest_cursor.getInt(interest_cursor.getColumnIndex(EventInfoContract.InterestEntry.VALUE));

                Interest interest = new Interest(interest_id, interest_title, interest_value);

                if(interest_value >= 60)
                {
                    if (interests_adapter.headerPosition("Important") == -1)
                    {
                        interests_adapter.add(new InterestHeader("Important"));
                        interests_adapter.add(interests_adapter.headerPosition("Important") + 1, interest);
                    }
                    else interests_adapter.add(interests_adapter.headerPosition("Important") + 1, interest);
                }
                else if(interest_value < 60)
                {
                    if (interests_adapter.headerPosition("Not important") == -1)
                    {
                        interests_adapter.add(new InterestHeader("Not important"));
                        interests_adapter.add(interests_adapter.headerPosition("Not important") + 1, interest);
                    }
                    else interests_adapter.add(interests_adapter.headerPosition("Not important") + 1, interest);
                }

                interests_adapter.updateAll();
            }
            while (interest_cursor.moveToNext());
        }

        interest_cursor.close();
        db.close();
    }

    //Get list of all Events
    private void getTodayDataListFromEvents(EventInfoDB eventInfoDB, TodayTaskAdapter todayTaskAdapter)
    {
        //Open connection to read only
        SQLiteDatabase database1 = eventInfoDB.getReadableDatabase();

        String simple_event_select_query =  "SELECT  " +
                EventInfoContract.EventEntry.TABLE_NAME + "." + EventInfoContract.EventEntry._ID + "," +
                EventInfoContract.EventEntry.TITLE + "," +
                EventInfoContract.EventEntry.DATE + "," +
                EventInfoContract.EventEntry.TIME_START + "," +
                EventInfoContract.EventEntry.TIME_END + "," +
                EventInfoContract.EventEntry.TYPE + "," +
                EventInfoContract.EventEntry.STATE + "," +
                EventInfoContract.EventEntry.IMPORTANCE +
                " FROM " + EventInfoContract.EventEntry.TABLE_NAME +
                " WHERE " +  EventInfoContract.EventEntry.DATE + " = "  + " '" +
                todayTaskAdapter.getTodaysDate() + "' ";

        String schedule_event_select_query =  "SELECT  " +
                EventInfoContract.EventEntry.TABLE_NAME + "." + EventInfoContract.EventEntry._ID + "," +
                EventInfoContract.EventEntry.TITLE + "," +
                EventInfoContract.ScheduleTypeEntry.CONTENT + "," +
                EventInfoContract.EventEntry.DATE + "," +
                EventInfoContract.EventEntry.TIME_START + "," +
                EventInfoContract.EventEntry.TIME_END + "," +
                EventInfoContract.EventEntry.STATE + "," +
                EventInfoContract.EventEntry.IMPORTANCE +
                " FROM " + EventInfoContract.EventEntry.TABLE_NAME + " INNER JOIN " +
                EventInfoContract.ScheduleTypeEntry.TABLE_NAME + " ON " +  EventInfoContract.EventEntry.TABLE_NAME + "." +
                EventInfoContract.EventEntry._ID + " = " +
                EventInfoContract.ScheduleTypeEntry.ID_EVENT +
                " WHERE " +  EventInfoContract.EventEntry.DATE + " = "  + " '" +
                todayTaskAdapter.getTodaysDate() + "' ";

        String birthday_event_select_query =  "SELECT  " +
                EventInfoContract.EventEntry.TABLE_NAME + "." + EventInfoContract.EventEntry._ID + "," +
                EventInfoContract.EventEntry.TITLE + "," +
                EventInfoContract.EventEntry.DATE + "," +
                EventInfoContract.EventEntry.TIME_START + "," +
                EventInfoContract.EventEntry.TIME_END + "," +
                EventInfoContract.EventEntry.STATE + "," +
                EventInfoContract.EventEntry.IMPORTANCE + "," +
                EventInfoContract.LocationEntry.STREET + "," +
                EventInfoContract.LocationEntry.CITY + "," +
                EventInfoContract.LocationEntry.COUNTRY +
                " FROM " + EventInfoContract.EventEntry.TABLE_NAME + " INNER JOIN " +
                EventInfoContract.LocationEntry.TABLE_NAME + " ON " +  EventInfoContract.EventEntry.TABLE_NAME + "." +
                EventInfoContract.EventEntry._ID + " = " +
                EventInfoContract.LocationEntry.ID_EVENT +
                " WHERE " +  EventInfoContract.EventEntry.DATE + " = " + " '" +
                todayTaskAdapter.getTodaysDate() + "' ";

        Cursor simple_event_cursor = database1.rawQuery(simple_event_select_query, null);

        //TODO: cursor here goes by all values that have this date, even when there's Schedule and B-day type. Check this!
        if (simple_event_cursor.moveToFirst())
        {
            do
            {
                int simple_event_id = (int) simple_event_cursor.getLong(simple_event_cursor.getColumnIndex(EventInfoContract.EventEntry._ID));
                String simple_event_title = simple_event_cursor.getString(simple_event_cursor.getColumnIndex(EventInfoContract.EventEntry.TITLE));
                String simple_event_date = simple_event_cursor.getString(simple_event_cursor.getColumnIndex(EventInfoContract.EventEntry.DATE));
                String simple_event_time_start = simple_event_cursor.getString(simple_event_cursor.getColumnIndex(EventInfoContract.EventEntry.TIME_START));
                String simple_event_time_end = simple_event_cursor.getString(simple_event_cursor.getColumnIndex(EventInfoContract.EventEntry.TIME_END));
                String simple_event_type = simple_event_cursor.getString(simple_event_cursor.getColumnIndex(EventInfoContract.EventEntry.TYPE));
                String simple_event_state = simple_event_cursor.getString(simple_event_cursor.getColumnIndex(EventInfoContract.EventEntry.STATE));
                int simple_event_importance = simple_event_cursor.getInt(simple_event_cursor.getColumnIndex(EventInfoContract.EventEntry.IMPORTANCE));

                EventType event_type = EventType.getName(simple_event_type);
                Event event = null;

                if (event_type != null)
                {
                    switch (event_type)
                    {
                        case TODO:
                            event = new ToDo(simple_event_id, simple_event_title, simple_event_time_start, simple_event_time_end, simple_event_date, simple_event_state, simple_event_importance);
                            ((ToDo)event).setListSubTasks(getSubTasksByEventId(eventInfoDB, event.getId()));
                            ((ToDo)event).setListComments(getCommentsByEventId(eventInfoDB, event.getId()));
                            event.setListInterests(getInterestsByEventId(eventInfoDB, event.getId()));
                                                                 //TODO: maybe, its more efficient to use just String?
                            if (todayTaskAdapter.headerPosition(EventType.TODO.toString()) == -1)
                            {
                                todayTaskAdapter.add(new EventTypeSection(EventType.TODO.toString()));
                                todayTaskAdapter.add(todayTaskAdapter.headerPosition(EventType.TODO.toString()) + 1, event);
                            }
                            else todayTaskAdapter.add(todayTaskAdapter.headerPosition(EventType.TODO.toString()) + 1, event);
                            break;

                        case WORKTASK:
                            event = new WorkTask(simple_event_id, simple_event_title, simple_event_time_start, simple_event_time_end, simple_event_date, simple_event_state, simple_event_importance);
                            ((WorkTask)event).setListSubTasks(getSubTasksByEventId(eventInfoDB, event.getId()));
                            ((WorkTask)event).setListComments(getCommentsByEventId(eventInfoDB, event.getId()));
                            event.setListInterests(getInterestsByEventId(eventInfoDB, event.getId()));

                            if (todayTaskAdapter.headerPosition(EventType.WORKTASK.toString()) == -1)
                            {
                                todayTaskAdapter.add(new EventTypeSection(EventType.WORKTASK.toString()));
                                todayTaskAdapter.add(todayTaskAdapter.headerPosition(EventType.WORKTASK.toString()) + 1, event);
                            }
                            else todayTaskAdapter.add(todayTaskAdapter.headerPosition(EventType.WORKTASK.toString()) + 1, event);
                            break;
                    }
                }

               // todayTaskAdapter.updateAll();
            }
            while (simple_event_cursor.moveToNext());
        }
        simple_event_cursor.close();
        database1.close();

        SQLiteDatabase database2 = eventInfoDB.getReadableDatabase();
        Cursor schedule_event_cursor = database2.rawQuery(schedule_event_select_query, null);

        if (schedule_event_cursor.moveToFirst())
        {
            do
            {
                int schedule_event_id = (int) schedule_event_cursor.getLong(schedule_event_cursor.getColumnIndex(EventInfoContract.EventEntry._ID));
                String schedule_event_title = schedule_event_cursor.getString(schedule_event_cursor.getColumnIndex(EventInfoContract.EventEntry.TITLE));
                String schedule_event_date = schedule_event_cursor.getString(schedule_event_cursor.getColumnIndex(EventInfoContract.EventEntry.DATE));
                String schedule_event_time_start = schedule_event_cursor.getString(schedule_event_cursor.getColumnIndex(EventInfoContract.EventEntry.TIME_START));
                String schedule_event_time_end = schedule_event_cursor.getString(schedule_event_cursor.getColumnIndex(EventInfoContract.EventEntry.TIME_END));
                String schedule_event_type = schedule_event_cursor.getString(schedule_event_cursor.getColumnIndex(EventInfoContract.ScheduleTypeEntry.CONTENT));
                String schedule_event_state = schedule_event_cursor.getString(schedule_event_cursor.getColumnIndex(EventInfoContract.EventEntry.STATE));
                int schedule_event_importance = schedule_event_cursor.getInt(schedule_event_cursor.getColumnIndex(EventInfoContract.EventEntry.IMPORTANCE));

                ScheduleType schedule_type = ScheduleType.getName(schedule_event_type);


                Event event = new Schedule(schedule_event_id, schedule_event_title, schedule_type, schedule_event_time_start, schedule_event_time_end, schedule_event_date, schedule_event_state, schedule_event_importance);
                event.setListInterests(getInterestsByEventId(eventInfoDB, event.getId()));

                //TODO: maybe, its more efficient to use just String?
                if (todayTaskAdapter.headerPosition(EventType.SCHEDULE.toString()) == -1)
                {
                    todayTaskAdapter.add(new EventTypeSection(EventType.SCHEDULE.toString()));
                    todayTaskAdapter.add(todayTaskAdapter.headerPosition(EventType.SCHEDULE.toString()) + 1, event);
                }
                else todayTaskAdapter.add(todayTaskAdapter.headerPosition(EventType.SCHEDULE.toString()) + 1, event);

                //todayTaskAdapter.updateAll();
            }
            while (schedule_event_cursor.moveToNext());
        }
        schedule_event_cursor.close();
        database2.close();

        SQLiteDatabase database3 = eventInfoDB.getReadableDatabase();
        Cursor birthday_event_cursor = database3.rawQuery(birthday_event_select_query, null);

        if (birthday_event_cursor.moveToFirst())
        {
            do
            {
                int birthday_event_id = (int) birthday_event_cursor.getLong(birthday_event_cursor.getColumnIndex(EventInfoContract.EventEntry._ID));
                String birthday_event_title = birthday_event_cursor.getString(birthday_event_cursor.getColumnIndex(EventInfoContract.EventEntry.TITLE));
                String birthday_event_date = birthday_event_cursor.getString(birthday_event_cursor.getColumnIndex(EventInfoContract.EventEntry.DATE));
                String birthday_event_time_start = birthday_event_cursor.getString(birthday_event_cursor.getColumnIndex(EventInfoContract.EventEntry.TIME_START));
                String birthday_event_time_end = birthday_event_cursor.getString(birthday_event_cursor.getColumnIndex(EventInfoContract.EventEntry.TIME_END));
                String birthday_event_state = birthday_event_cursor.getString(birthday_event_cursor.getColumnIndex(EventInfoContract.EventEntry.STATE));
                int birthday_event_importance = birthday_event_cursor.getInt(birthday_event_cursor.getColumnIndex(EventInfoContract.EventEntry.IMPORTANCE));
                String birthday_event_street = birthday_event_cursor.getString(birthday_event_cursor.getColumnIndex(EventInfoContract.LocationEntry.STREET));
                String birthday_event_city = birthday_event_cursor.getString(birthday_event_cursor.getColumnIndex(EventInfoContract.LocationEntry.CITY));
                String birthday_event_country = birthday_event_cursor.getString(birthday_event_cursor.getColumnIndex(EventInfoContract.LocationEntry.COUNTRY));

                                                //TODO: possibly, can add real id.
                Location location = new Location(-1, birthday_event_id, birthday_event_street, birthday_event_city, birthday_event_country);

                Event event = new Birthday(birthday_event_id, birthday_event_title, birthday_event_time_start, birthday_event_time_end, birthday_event_date, birthday_event_state, birthday_event_importance, location);
                ((Birthday)event).setListSubTasks(getSubTasksByEventId(eventInfoDB, event.getId()));
                ((Birthday)event).setListComments(getCommentsByEventId(eventInfoDB, event.getId()));
                event.setListInterests(getInterestsByEventId(eventInfoDB, event.getId()));

                //TODO: maybe, its more efficient to use just String?
                if (todayTaskAdapter.headerPosition(EventType.BIRTHDAY.toString()) == -1)
                {
                    todayTaskAdapter.add(new EventTypeSection(EventType.BIRTHDAY.toString()));
                    todayTaskAdapter.add(todayTaskAdapter.headerPosition(EventType.BIRTHDAY.toString()) + 1, event);
                }
                else todayTaskAdapter.add(todayTaskAdapter.headerPosition(EventType.BIRTHDAY.toString()) + 1, event);

                //todayTaskAdapter.updateAll();
            }
            while (birthday_event_cursor.moveToNext());
        }
        birthday_event_cursor.close();

        database3.close();

        todayTaskAdapter.updateAll();
    }

    //TODO; make them convenient (3 methods).
    private List<Sub_task> getSubTasksByEventId(EventInfoDB eventInfoDB, int event_id)
    {
        SQLiteDatabase database = eventInfoDB.getReadableDatabase();
        List<Sub_task> sub_tasks = new ArrayList<>();

        String sub_tasks_select_query =  "SELECT  " +
                EventInfoContract.SubTaskEntry.TABLE_NAME + "." + EventInfoContract.SubTaskEntry._ID + "," +
                EventInfoContract.SubTaskEntry.ID_EVENT + "," +
                EventInfoContract.SubTaskEntry.CONTENT + "," +
                EventInfoContract.SubTaskEntry.CHECKED +  " FROM " +
                EventInfoContract.SubTaskEntry.TABLE_NAME + " WHERE " +
                EventInfoContract.SubTaskEntry.ID_EVENT + " =? ";

        Cursor cursor = database.rawQuery(sub_tasks_select_query, new String[] { String.valueOf(event_id) });

        if (cursor.moveToFirst())
        {
            do
            {
                Sub_task sub_task = new Sub_task();

                sub_task.setId(cursor.getInt(cursor.getColumnIndex(EventInfoContract.SubTaskEntry._ID)));
                sub_task.setEvent_id(cursor.getInt(cursor.getColumnIndex(EventInfoContract.SubTaskEntry.ID_EVENT)));
                sub_task.setContent(cursor.getString(cursor.getColumnIndex(EventInfoContract.SubTaskEntry.CONTENT)));
                boolean checked = cursor.getInt(cursor.getColumnIndex(EventInfoContract.SubTaskEntry.CHECKED)) != 0;
                sub_task.setChecked(checked);

                sub_tasks.add(sub_task);
            }
            while (cursor.moveToNext());
        }

        cursor.close();

        database.close();

        return sub_tasks;
    }

    private List<Comment> getCommentsByEventId(EventInfoDB eventInfoDB, int event_id)
    {
        SQLiteDatabase database = eventInfoDB.getReadableDatabase();
        List<Comment> comments = new ArrayList<>();

        String comments_select_query =  "SELECT  " +
                EventInfoContract.CommentEntry.TABLE_NAME + "." + EventInfoContract.CommentEntry._ID + "," +
                EventInfoContract.CommentEntry.ID_EVENT + "," +
                EventInfoContract.CommentEntry.CONTENT +  " FROM " +
                EventInfoContract.CommentEntry.TABLE_NAME + " WHERE " +
                EventInfoContract.CommentEntry.ID_EVENT + " =? ";

        Cursor cursor = database.rawQuery(comments_select_query, new String[] { String.valueOf(event_id) } );

        if (cursor.moveToFirst())
        {
            do
            {
                Comment comment = new Comment();

                comment.setId(cursor.getInt(cursor.getColumnIndex(EventInfoContract.CommentEntry._ID)));
                comment.setId_event(cursor.getInt(cursor.getColumnIndex(EventInfoContract.CommentEntry.ID_EVENT)));
                comment.setContent(cursor.getString(cursor.getColumnIndex(EventInfoContract.CommentEntry.CONTENT)));

                comments.add(comment);
            }
            while (cursor.moveToNext());
        }

        cursor.close();

        database.close();

        return comments;
    }

    private List<Interest> getInterestsByEventId(EventInfoDB eventInfoDB, int event_id)
    {
        SQLiteDatabase database = eventInfoDB.getReadableDatabase();
        List<Interest> interests = new ArrayList<>();

        String interests_select_query =  "SELECT  " +
                EventInfoContract.InterestEntry.TABLE_NAME + "." + EventInfoContract.InterestEntry._ID + "," +
                EventInfoContract.InterestEntry.TABLE_NAME + "." +EventInfoContract.InterestEntry.TITLE + "," +
                EventInfoContract.InterestEntry.TABLE_NAME + "." +EventInfoContract.InterestEntry.VALUE +  " FROM " +
                EventInfoContract.EventEntry.TABLE_NAME + " INNER JOIN " +
                EventInfoContract.EventsInterestsEntry.TABLE_NAME + " ON " +
                EventInfoContract.EventEntry.TABLE_NAME + "." + EventInfoContract.EventEntry._ID + " = " + EventInfoContract.EventsInterestsEntry.ID_EVENT + " INNER JOIN " +
                EventInfoContract.InterestEntry.TABLE_NAME + " ON " +
                EventInfoContract.InterestEntry.TABLE_NAME + "." + EventInfoContract.InterestEntry._ID + " = " + EventInfoContract.EventsInterestsEntry.ID_INTEREST + " WHERE " +
                EventInfoContract.EventEntry.TABLE_NAME + "." + EventInfoContract.EventEntry._ID + " =? ";

        Cursor cursor = database.rawQuery(interests_select_query, new String[] { String.valueOf(event_id) });

        if (cursor.moveToFirst())
        {
            do
            {
                Interest interest = new Interest();

                interest.setId(cursor.getInt(cursor.getColumnIndex(EventInfoContract.InterestEntry._ID)));
                interest.setTitle(cursor.getString(cursor.getColumnIndex(EventInfoContract.InterestEntry.TITLE)));
                interest.setValue(cursor.getInt(cursor.getColumnIndex(EventInfoContract.InterestEntry.VALUE)));

                interests.add(interest);
            }
            while (cursor.moveToNext());
        }

        cursor.close();

        database.close();

        return interests;
    }


    private void addDataToComment(EventInfoDB eventInfoDB, Comment comment)
    {
        SQLiteDatabase db = eventInfoDB.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(EventInfoContract.CommentEntry.ID_EVENT, comment.getId_event());
        contentValues.put(EventInfoContract.CommentEntry.CONTENT, comment.getContent());

        long newRowId = db.insert(EventInfoContract.CommentEntry.TABLE_NAME, null, contentValues);
        comment.setId((int) newRowId);

        Log.i("FetchEventsTask", "New Row " + newRowId + " Inserted..." + EventInfoContract.CommentEntry.TABLE_NAME);
    }

    private void addDataToSub_task(EventInfoDB eventInfoDB, Sub_task sub_task)
    {
        SQLiteDatabase db = eventInfoDB.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(EventInfoContract.SubTaskEntry.ID_EVENT, sub_task.getEvent_id());
        contentValues.put(EventInfoContract.SubTaskEntry.CONTENT, sub_task.getContent());
        contentValues.put(EventInfoContract.SubTaskEntry.CHECKED, sub_task.isChecked());

        long newRowId = db.insert(EventInfoContract.SubTaskEntry.TABLE_NAME, null, contentValues);
        sub_task.setId((int) newRowId);

        Log.i("FetchEventsTask", "New Row " + newRowId + " Inserted..." + EventInfoContract.SubTaskEntry.TABLE_NAME);
    }

    private void updateDataInSub_task(EventInfoDB eventInfoDB, Sub_task sub_task)
    {
        SQLiteDatabase db = eventInfoDB.getReadableDatabase();

        // New value for one column
        ContentValues contentValues = new ContentValues();

        contentValues.put(EventInfoContract.SubTaskEntry.ID_EVENT, sub_task.getEvent_id());
        contentValues.put(EventInfoContract.SubTaskEntry.CONTENT, sub_task.getContent());
        contentValues.put(EventInfoContract.SubTaskEntry.CHECKED, sub_task.isChecked());

        // Which row to update, based on the ID
        String selection = EventInfoContract.SubTaskEntry._ID + "= ?";
        String[] selectionArgs =  new String[]{String.valueOf(sub_task.getId()) };

        int new_sub_task_id = db.update(
                EventInfoContract.SubTaskEntry.TABLE_NAME,
                contentValues,
                selection,
                selectionArgs);
    }

    private void updateDataInToDo(EventInfoDB eventInfoDB, ToDo todo)
    {
        EventType type = EventType.TODO;
        String  title = todo.getTitle();
        String  date = todo.getDate();
        String  time_start = todo.getTime_start();
        String  time_end = todo.getTime_end();
        String  state = todo.getState();
        int  importance = todo.getImportance();

        SQLiteDatabase db = eventInfoDB.getReadableDatabase();

        // New value for one column
        ContentValues contentValues = new ContentValues();

        contentValues.put(EventInfoContract.EventEntry.TITLE, title);
        contentValues.put(EventInfoContract.EventEntry.DATE, date);
        contentValues.put(EventInfoContract.EventEntry.TIME_START, time_start);
        contentValues.put(EventInfoContract.EventEntry.TIME_END, time_end);
        contentValues.put(EventInfoContract.EventEntry.TYPE, type.toString());
        contentValues.put(EventInfoContract.EventEntry.STATE, state);
        contentValues.put(EventInfoContract.EventEntry.IMPORTANCE, importance);

        // Which row to update, based on the ID
        String selection = EventInfoContract.EventEntry._ID + "= ?";
        String[] selectionArgs =  new String[]{String.valueOf(todo.getId()) };

        int new_todo_id = db.update(
                EventInfoContract.EventEntry.TABLE_NAME,
                contentValues,
                selection,
                selectionArgs);

        Log.i(TAG, "Row " + new_todo_id + " Updated..." + EventInfoContract.EventEntry.TABLE_NAME);
    }


    private void updateDataInWorkTask(EventInfoDB eventInfoDB, WorkTask workTask)
    {
        EventType type = EventType.WORKTASK;
        String  title = workTask.getTitle();
        String  date = workTask.getDate();
        String  time_start = workTask.getTime_start();
        String  time_end = workTask.getTime_end();
        String  state = workTask.getState();
        int  importance = workTask.getImportance();

        SQLiteDatabase db = eventInfoDB.getReadableDatabase();

        // New value for one column
        ContentValues contentValues = new ContentValues();

        contentValues.put(EventInfoContract.EventEntry.TITLE, title);
        contentValues.put(EventInfoContract.EventEntry.DATE, date);
        contentValues.put(EventInfoContract.EventEntry.TIME_START, time_start);
        contentValues.put(EventInfoContract.EventEntry.TIME_END, time_end);
        contentValues.put(EventInfoContract.EventEntry.TYPE, type.toString());
        contentValues.put(EventInfoContract.EventEntry.STATE, state);
        contentValues.put(EventInfoContract.EventEntry.IMPORTANCE, importance);

        // Which row to update, based on the ID
        String selection = EventInfoContract.EventEntry._ID + "= ?";
        String[] selectionArgs =  new String[]{String.valueOf(workTask.getId()) };

        int new_work_task_id = db.update(
                EventInfoContract.EventEntry.TABLE_NAME,
                contentValues,
                selection,
                selectionArgs);

        Log.i(TAG, "Row " + new_work_task_id + " Updated..." + EventInfoContract.EventEntry.TABLE_NAME);
    }

    private void removeDataFromInterest(EventInfoDB eventInfoDB, int interest_id)
    {
        SQLiteDatabase db = eventInfoDB.getWritableDatabase();

        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(EventInfoContract.InterestEntry.TABLE_NAME, EventInfoContract.InterestEntry._ID + "= ?", new String[]{String.valueOf(interest_id)});
        db.close();
    }

    private void removeDataFromComment(EventInfoDB eventInfoDB, int comment_id)
    {
        SQLiteDatabase db = eventInfoDB.getWritableDatabase();

        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(EventInfoContract.CommentEntry.TABLE_NAME, EventInfoContract.CommentEntry._ID + "= ?", new String[]{String.valueOf(comment_id)});
        db.close();
    }

    private void removeDataFromCommentByEventId(EventInfoDB eventInfoDB, int id_event)
    {
        SQLiteDatabase db = eventInfoDB.getWritableDatabase();

        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(EventInfoContract.CommentEntry.TABLE_NAME, EventInfoContract.CommentEntry.ID_EVENT + "= ?", new String[]{String.valueOf(id_event)});
        db.close();
    }

    private void removeDataFromSub_task(EventInfoDB eventInfoDB, int sub_task_id)
    {
        SQLiteDatabase db = eventInfoDB.getWritableDatabase();

        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(EventInfoContract.SubTaskEntry.TABLE_NAME, EventInfoContract.SubTaskEntry._ID + "= ?", new String[]{String.valueOf(sub_task_id)});
        db.close();
    }

    private void removeDataFromSub_taskByEventId(EventInfoDB eventInfoDB, int event_id)
    {
        SQLiteDatabase db = eventInfoDB.getWritableDatabase();

        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(EventInfoContract.SubTaskEntry.TABLE_NAME, EventInfoContract.SubTaskEntry.ID_EVENT + "= ?", new String[]{String.valueOf(event_id)});
        db.close();
    }

    private void removeDataFromEventInterest(EventInfoDB eventInfoDB, int event_interest_id)
    {
        SQLiteDatabase db = eventInfoDB.getWritableDatabase();

        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(EventInfoContract.EventsInterestsEntry.TABLE_NAME, EventInfoContract.EventsInterestsEntry.ID_EVENT + "= ?", new String[]{String.valueOf(event_interest_id)});
        db.close();
    }

    private void removeDataFromEventsInterestsByEventId(EventInfoDB eventInfoDB, int event_id)
    {
        SQLiteDatabase db = eventInfoDB.getWritableDatabase();

        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(EventInfoContract.EventsInterestsEntry.TABLE_NAME, EventInfoContract.EventsInterestsEntry.ID_EVENT + "= ?", new String[]{String.valueOf(event_id)});
        db.close();
    }

    /*
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
