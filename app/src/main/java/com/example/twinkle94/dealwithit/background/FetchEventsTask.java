package com.example.twinkle94.dealwithit.background;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.example.twinkle94.dealwithit.adapter.interests_page_adapter.InterestHeader;
import com.example.twinkle94.dealwithit.adapter.interests_page_adapter.InterestsAdapter;
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
                String get_data_type= (String)params[2];
                result = getDataFromDB(get_data_type, eventInfoDB, params[1]);
                break;

            case "update_data":
                result = updateDataInDB(event_type, eventInfoDB, params[1]);
                break;

            case "remove_data":
                result = removeDataFromDB(event_type, eventInfoDB, params[1]);
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

                break;

            case "Work Task":

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

        return event_type + " was added to DB";
    }

    private String removeDataFromDB(String event_type, EventInfoDB eventInfoDB, Object event)
    {
        switch (event_type)
        {
            case "Schedule":
                removeScheduleTask(eventInfoDB, ((Schedule) event).getId());
                break;

            case "ToDo":

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
        }

        return event_type + " was removed from DB";
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

        contentValues.put(EventInfoContract.InterestEntry.ID_EVENT, interest.getEvent_id());
        contentValues.put(EventInfoContract.InterestEntry.TITLE, interest.getTitle());
        contentValues.put(EventInfoContract.InterestEntry.VALUE, interest.getValue());

        long newRowId = db.insert(EventInfoContract.InterestEntry.TABLE_NAME, null, contentValues);

        interest.setId((int) newRowId);

        Log.d("FetchEventsTask", "New Row " + newRowId + " Inserted..." + EventInfoContract.InterestEntry.TABLE_NAME);
    }

    //Get list of all ToDo
    private void getDataListFromInterest(EventInfoDB eventInfoDB, InterestsAdapter interests_adapter)
    {
        //Open connection to read only
        SQLiteDatabase db = eventInfoDB.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                EventInfoContract.InterestEntry._ID + "," +
                EventInfoContract.InterestEntry.ID_EVENT + "," +
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
                int interest_event_id = interest_cursor.getInt(interest_cursor.getColumnIndex(EventInfoContract.InterestEntry.ID_EVENT));
                String interest_title = interest_cursor.getString(interest_cursor.getColumnIndex(EventInfoContract.InterestEntry.TITLE));
                int interest_value = interest_cursor.getInt(interest_cursor.getColumnIndex(EventInfoContract.InterestEntry.VALUE));

                Interest interest = new Interest(interest_id, interest_event_id, interest_title, interest_value);

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

   /* //Get ToDo by id
    public ToDo getToDoById(int Id)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery =  "SELECT  " +
                ToDo.ID + "," +
                ToDo.TASK + "," +
                ToDo.TYPE + "," +
                ToDo.DATE + "," +
                ToDo.TIME + "," +
                ToDo.COMMENT + "," +
                ToDo.IMPORTANCE + "," +
                ToDo.IMPORTANCE_VALUE +
                " FROM " + ToDo.TABLE
                + " WHERE " +
                ToDo.ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        ToDo todo = new ToDo();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(Id) } );

        if (cursor.moveToFirst())
        {
            do
            {
                todo.setID(cursor.getInt(cursor.getColumnIndex(ToDo.ID)));
                todo.setTask(cursor.getString(cursor.getColumnIndex(ToDo.TASK)));
                todo.setType(cursor.getString(cursor.getColumnIndex(ToDo.TYPE)));
                todo.setDate(cursor.getString(cursor.getColumnIndex(ToDo.DATE)));
                todo.setTime(cursor.getString(cursor.getColumnIndex(ToDo.TIME)));
                todo.setComment(cursor.getString(cursor.getColumnIndex(ToDo.COMMENT)));
                todo.setImportance(cursor.getString(cursor.getColumnIndex(ToDo.IMPORTANCE)));
                todo.setImportance_value(cursor.getInt(cursor.getColumnIndex(ToDo.IMPORTANCE_VALUE)));

            } while (cursor.moveToNext());
        }

        cursor.close();

        db.close();

        return todo;
    }*/

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

        int count = db.update(
                EventInfoContract.SubTaskEntry.TABLE_NAME,
                contentValues,
                selection,
                selectionArgs);
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

    private void removeDataFromSub_task(EventInfoDB eventInfoDB, int sub_task_id)
    {
        SQLiteDatabase db = eventInfoDB.getWritableDatabase();

        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(EventInfoContract.SubTaskEntry.TABLE_NAME, EventInfoContract.SubTaskEntry._ID + "= ?", new String[]{String.valueOf(sub_task_id)});
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
