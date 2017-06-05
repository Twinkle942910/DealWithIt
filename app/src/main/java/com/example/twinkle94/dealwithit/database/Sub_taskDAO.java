package com.example.twinkle94.dealwithit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.example.twinkle94.dealwithit.events.Sub_task;

import java.util.ArrayList;
import java.util.List;

public class Sub_taskDAO
{
    private static final String TAG = Sub_taskDAO.class.getSimpleName();

    private EventInfoDB dbHandler;
    private SQLiteDatabase database;

    private static final String[] columns =
            {
                    EventInfoContract.SubTaskEntry._ID,
                    EventInfoContract.SubTaskEntry.ID_EVENT,
                    EventInfoContract.SubTaskEntry.CONTENT,
                    EventInfoContract.SubTaskEntry.CHECKED
            };

    public Sub_taskDAO(Context context)
    {
        this.dbHandler = new EventInfoDB(context);
    }

    public void open() throws SQLException
    {
        Log.i(TAG, "Database is opened");
        database = dbHandler.getWritableDatabase();
    }

    public void close()
    {
        Log.i(TAG, "Database is closed");
        dbHandler.close();
    }

    public Sub_task addSub_task(Sub_task sub_task)
    {
        ContentValues sub_taskValues = new ContentValues();

        sub_taskValues.put(EventInfoContract.SubTaskEntry.ID_EVENT, sub_task.getEvent_id());
        sub_taskValues.put(EventInfoContract.SubTaskEntry.CONTENT, sub_task.getContent());
        sub_taskValues.put(EventInfoContract.SubTaskEntry.CHECKED, sub_task.isChecked());

        long new_sub_taskId = database.insert(EventInfoContract.SubTaskEntry.TABLE_NAME, null, sub_taskValues);
        sub_task.setId((int) new_sub_taskId);

        Log.i(TAG, "New " + TAG + " " + sub_task.getId() + " added.");

        return sub_task;
    }

    public int updateSub_task(Sub_task sub_task)
    {
        ContentValues sub_taskValues = new ContentValues();

        sub_taskValues.put(EventInfoContract.SubTaskEntry.ID_EVENT, sub_task.getEvent_id());
        sub_taskValues.put(EventInfoContract.SubTaskEntry.CONTENT, sub_task.getContent());
        sub_taskValues.put(EventInfoContract.SubTaskEntry.CHECKED, sub_task.isChecked());

        // Which row to update, based on the ID
        String selection = EventInfoContract.SubTaskEntry._ID + "= ?";
        String[] selectionArgs =  new String[]{String.valueOf(sub_task.getId()) };

        database.update(EventInfoContract.SubTaskEntry.TABLE_NAME, sub_taskValues, selection, selectionArgs);

        Log.i(TAG, TAG + " " + sub_task.getId() + " updated.");

        return sub_task.getId();
    }

    public void deleteSub_task(Sub_task sub_task)
    {
        database.delete(EventInfoContract.SubTaskEntry.TABLE_NAME, EventInfoContract.SubTaskEntry._ID + "= ?", new String[]{String.valueOf(sub_task.getId())});
        Log.i(TAG, TAG + " " + sub_task.getId() + " deleted.");
    }

    public List<Sub_task> getSubTasksByEventId(int eventId)
    {
        Cursor cursor = database.query(EventInfoContract.SubTaskEntry.TABLE_NAME,
                columns,
                EventInfoContract.SubTaskEntry.ID_EVENT + "= ?",
                new String[]{String.valueOf(eventId)},
                null,
                null,
                null);

        List<Sub_task> sub_tasks = new ArrayList<>();

        while (cursor.moveToNext())
        {
            Sub_task sub_task = new Sub_task();
            sub_task.setId((int) cursor.getLong(cursor.getColumnIndex(EventInfoContract.SubTaskEntry._ID)));
            sub_task.setEvent_id(cursor.getInt(cursor.getColumnIndex(EventInfoContract.SubTaskEntry.ID_EVENT)));
            sub_task.setContent(cursor.getString(cursor.getColumnIndex(EventInfoContract.SubTaskEntry.CONTENT)));
            boolean checked = cursor.getInt(cursor.getColumnIndex(EventInfoContract.SubTaskEntry.CHECKED)) != 0;
            sub_task.setChecked(checked);
            sub_tasks.add(sub_task);
        }
        cursor.close();

        return sub_tasks;
    }

    public void addTaskOnBG(Sub_task sub_task)
    {
        open();
        new BackgroundSub_taskQueries().execute("add_data", sub_task);
    }

    public void updateTaskOnBG(Sub_task sub_task)
    {
        open();
        new BackgroundSub_taskQueries().execute("update_data", sub_task);
    }

    public void deleteTaskOnBG(Sub_task sub_task)
    {
        open();
        new BackgroundSub_taskQueries().execute("delete_data", sub_task);
    }

    private class BackgroundSub_taskQueries extends AsyncTask<Object, Void, String>
    {
        private final String TAG_THIS = BackgroundSub_taskQueries.class.getSimpleName();

        @Override
        protected String doInBackground(Object... params)
        {
            String operation = (String) params[0];
            String result = "";

            switch (operation)
            {
                case "add_data":
                    addSub_task((Sub_task) params[1]);
                    result = TAG + " " + "added on Background";
                    break;

                case "update_data":
                    updateSub_task((Sub_task) params[1]);
                    result = TAG + " " + "updated on Background";
                    break;

                case "delete_data":
                    deleteSub_task((Sub_task) params[1]);
                    result = TAG + " " + "deleted on Background";
                    break;
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s)
        {
            Log.i(TAG_THIS, s);
            close();
        }
    }
}
