package com.example.twinkle94.dealwithit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.example.twinkle94.dealwithit.adapter.interests_page_adapter.InterestHeader;
import com.example.twinkle94.dealwithit.adapter.interests_page_adapter.InterestsAdapter;
import com.example.twinkle94.dealwithit.events.EventInterest;
import com.example.twinkle94.dealwithit.events.Interest;

import java.util.ArrayList;
import java.util.List;

public class InterestDAO
{
    private static final String TAG = InterestDAO.class.getSimpleName();

    private EventInfoDB dbHandler;
    private SQLiteDatabase database;

    private static final String[] columns =
            {
                    EventInfoContract.InterestEntry._ID,
                    EventInfoContract.InterestEntry.TITLE,
                    EventInfoContract.InterestEntry.VALUE
            };

    public InterestDAO(Context context)
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

    public Interest addInterest(Interest interest)
    {
        ContentValues interestValues = new ContentValues();

        interestValues.put(EventInfoContract.InterestEntry.TITLE, interest.getTitle());
        interestValues.put(EventInfoContract.InterestEntry.VALUE, interest.getValue());

        long newInterestId = database.insert(EventInfoContract.InterestEntry.TABLE_NAME, null, interestValues);
        interest.setId((int) newInterestId);

        Log.i(TAG, "New " + TAG + " " + interest.getId() + " added.");

        return interest;
    }

    public int updateInterest(Interest interest)
    {
        ContentValues interestValues = new ContentValues();

        interestValues.put(EventInfoContract.InterestEntry.TITLE, interest.getTitle());
        interestValues.put(EventInfoContract.InterestEntry.VALUE, interest.getValue());

        database.update(EventInfoContract.InterestEntry.TABLE_NAME,
                interestValues,
                EventInfoContract.CommentEntry._ID + "= ?",
                new String[]{String.valueOf(interest.getId()) });

        Log.i(TAG, TAG + " " + interest.getId() + " updated.");

        return interest.getId();
    }

    public void deleteInterest(Interest interest)
    {
        database.delete(EventInfoContract.InterestEntry.TABLE_NAME, EventInfoContract.InterestEntry._ID + "= ?", new String[]{String.valueOf(interest.getId())});
        Log.i(TAG, TAG + " " + interest.getId() + " deleted.");
    }

    public void addEventsInterests(EventInterest eventInterest)
    {
        ContentValues eventsInterestsValues = new ContentValues();

        eventsInterestsValues.put(EventInfoContract.EventsInterestsEntry.ID_EVENT, eventInterest.getId_event());
        eventsInterestsValues.put(EventInfoContract.EventsInterestsEntry.ID_INTEREST, eventInterest.getId_interest());

        long newRowId = database.insert(EventInfoContract.EventsInterestsEntry.TABLE_NAME, null, eventsInterestsValues);
        eventInterest.setId((int) newRowId);

        Log.i(TAG, "New " + TAG + " " + eventInterest.getId() + " added.");
    }

    public int updateEventsInterests(EventInterest eventInterest)
    {
        ContentValues eventsInterestsValues = new ContentValues();

        eventsInterestsValues.put(EventInfoContract.EventsInterestsEntry.ID_EVENT, eventInterest.getId_event());
        eventsInterestsValues.put(EventInfoContract.EventsInterestsEntry.ID_INTEREST, eventInterest.getId_interest());

        database.update(EventInfoContract.EventsInterestsEntry.TABLE_NAME,
                eventsInterestsValues, EventInfoContract.EventsInterestsEntry._ID + "= ?",
                new String[]{String.valueOf(eventInterest.getId())});

        Log.i(TAG, "New " + TAG + " " + eventInterest.getId() + " added.");

        return eventInterest.getId();
    }

    public void deleteEventInterest(EventInterest eventInterest)
    {
        database.delete(EventInfoContract.EventsInterestsEntry.TABLE_NAME,
                EventInfoContract.EventsInterestsEntry._ID + "= ?",
                new String[]{String.valueOf(eventInterest.getId())});

        Log.i(TAG, TAG + " " + eventInterest.getId() + " deleted.");
    }

    public Interest getInterest(int interestId)
    {
        Cursor cursor = database.query(EventInfoContract.InterestEntry.TABLE_NAME,
                columns,
                EventInfoContract.InterestEntry._ID + "=?",
                new String[]{String.valueOf(interestId)},
                null,
                null,
                null,
                null);

        if (cursor != null)
            cursor.moveToFirst();

        Interest interest = new Interest((int) cursor.getLong(0),cursor.getString(1),cursor.getInt(2));

        cursor.close();
        return interest;
    }

    public List<Interest> getAllInterests()
    {
        Cursor cursor = database.query(EventInfoContract.InterestEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null);

        List<Interest> interests = new ArrayList<>();

        while (cursor.moveToNext())
        {
            Interest interest = new Interest();
            interest.setId((int) cursor.getLong(cursor.getColumnIndex(EventInfoContract.InterestEntry._ID)));
            interest.setTitle(cursor.getString(cursor.getColumnIndex(EventInfoContract.InterestEntry.TITLE)));
            interest.setValue(cursor.getInt(cursor.getColumnIndex(EventInfoContract.InterestEntry.VALUE)));
            interests.add(interest);
        }
        cursor.close();

        return interests;
    }

    //TODO: with EventInterest!
    public List<Interest> getInterestsByEventId(int eventId)
    {
        String interests_select_query =  "SELECT  " +
                EventInfoContract.InterestEntry.TABLE_NAME + "." + EventInfoContract.InterestEntry._ID + "," +
                EventInfoContract.InterestEntry.TABLE_NAME + "." + EventInfoContract.InterestEntry.TITLE + "," +
                EventInfoContract.InterestEntry.TABLE_NAME + "." + EventInfoContract.InterestEntry.VALUE +  " FROM " +
                EventInfoContract.EventEntry.TABLE_NAME + " INNER JOIN " +
                EventInfoContract.EventsInterestsEntry.TABLE_NAME + " ON " +
                EventInfoContract.EventEntry.TABLE_NAME + "." + EventInfoContract.EventEntry._ID + " = " + EventInfoContract.EventsInterestsEntry.ID_EVENT + " INNER JOIN " +
                EventInfoContract.InterestEntry.TABLE_NAME + " ON " +
                EventInfoContract.InterestEntry.TABLE_NAME + "." + EventInfoContract.InterestEntry._ID + " = " + EventInfoContract.EventsInterestsEntry.ID_INTEREST + " WHERE " +
                EventInfoContract.EventEntry.TABLE_NAME + "." + EventInfoContract.EventEntry._ID + " =? ";

        Cursor cursor = database.rawQuery(interests_select_query, new String[]{String.valueOf(eventId)});

        List<Interest> interests = new ArrayList<>();

        while (cursor.moveToNext())
        {
            Interest interest = new Interest();
            interest.setId((int) cursor.getLong(cursor.getColumnIndex(EventInfoContract.InterestEntry._ID)));
            interest.setTitle(cursor.getString(cursor.getColumnIndex(EventInfoContract.InterestEntry.TITLE)));
            interest.setValue(cursor.getInt(cursor.getColumnIndex(EventInfoContract.InterestEntry.VALUE)));
            interests.add(interest);
        }
        cursor.close();

        return interests;
    }

    public void addTaskOnBG(Interest interest)
    {
        open();
        new BackgroundInterestQueries().execute("add_data", interest);
    }

    public void updateTaskOnBG(Interest interest)
    {
        open();
        new BackgroundInterestQueries().execute("update_data", interest);
    }

    public void deleteTaskOnBG(Interest interest)
    {
        open();
        new BackgroundInterestQueries().execute("delete_data", interest);
    }

    //TODO: maybe there's more efficient way to do this.!
    public void addTaskOnBG(EventInterest eventInterest)
    {
        open();
        new BackgroundInterestQueries().execute("add_data", eventInterest);
    }

    public void updateTaskOnBG(EventInterest eventInterest)
    {
        open();
        new BackgroundInterestQueries().execute("update_data", eventInterest);
    }

    public void deleteTaskOnBG(EventInterest eventInterest)
    {
        open();
        new BackgroundInterestQueries().execute("delete_data", eventInterest);
    }
    //

    public void getTaskOnBG(InterestsAdapter interestsAdapter)
    {
        open();
        new BackgroundInterestQueries().execute("get_data", interestsAdapter);
    }

    private class BackgroundInterestQueries extends AsyncTask<Object, Interest, String>
    {
        private final String TAG_THIS = BackgroundInterestQueries.class.getSimpleName();
        private InterestsAdapter interestsAdapter;

        @Override
        protected String doInBackground(Object... params)
        {
            String operation = (String) params[0];
            Object interest_type = params[1];
            String result = "";

            switch (operation)
            {
                case "add_data":
                    if(interest_type instanceof Interest) addInterest((Interest) interest_type);
                    else addEventsInterests((EventInterest) interest_type);
                    result = TAG + " " + "added on Background";
                    break;

                case "update_data":
                    if(interest_type instanceof Interest) updateInterest((Interest) interest_type);
                    else updateEventsInterests((EventInterest) interest_type);
                    result = TAG + " " + "updated on Background";
                    break;

                case "delete_data":
                    if(interest_type instanceof Interest) deleteInterest((Interest) interest_type);
                    else deleteEventInterest((EventInterest) interest_type);
                    result = TAG + " " + "deleted on Background";
                    break;

                case "get_data":
                    this.interestsAdapter = (InterestsAdapter) interest_type;
                    getInterestList();
                    result = TAG + " " + "got on Background";
                    break;
            }

            return result;
        }

        @Override
        protected void onProgressUpdate(Interest... interests)
        {
            Interest interest = interests[0];

            if(interest.getValue() >= 60)
            {
                if (interestsAdapter.headerPosition("Important") == -1)
                {
                    interestsAdapter.add(new InterestHeader("Important"));
                    interestsAdapter.add(interestsAdapter.headerPosition("Important") + 1, interest);
                }
                else interestsAdapter.add(interestsAdapter.headerPosition("Important") + 1, interest);
            }
            else if(interest.getValue() < 60)
            {
                if (interestsAdapter.headerPosition("Not important") == -1)
                {
                    interestsAdapter.add(new InterestHeader("Not important"));
                    interestsAdapter.add(interestsAdapter.headerPosition("Not important") + 1, interest);
                }
                else interestsAdapter.add(interestsAdapter.headerPosition("Not important") + 1, interest);
            }
        }

        @Override
        protected void onPostExecute(String s)
        {
            Log.i(TAG_THIS, s);
            close();
        }

        private void getInterestList()
        {
            List<Interest> interests = getAllInterests();

            for(int i = 0; i < interests.size(); i++)
               publishProgress(interests.get(i));
        }
    }
}
