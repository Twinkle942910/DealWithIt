package com.example.twinkle94.dealwithit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.example.twinkle94.dealwithit.adapter.ScheduleDayEventAdapter;
import com.example.twinkle94.dealwithit.adapter.ToDoListAdapter;
import com.example.twinkle94.dealwithit.adapter.today_page_adapter.EventTypeSection;
import com.example.twinkle94.dealwithit.adapter.today_page_adapter.TodayTaskAdapter;
import com.example.twinkle94.dealwithit.events.Event;
import com.example.twinkle94.dealwithit.events.Location;
import com.example.twinkle94.dealwithit.events.task_types.Birthday;
import com.example.twinkle94.dealwithit.events.task_types.Schedule;
import com.example.twinkle94.dealwithit.events.task_types.ToDo;
import com.example.twinkle94.dealwithit.events.task_types.WorkTask;
import com.example.twinkle94.dealwithit.events.type_enums.EventType;
import com.example.twinkle94.dealwithit.events.type_enums.ScheduleType;

import java.util.ArrayList;
import java.util.List;

public class EventDAO
{
    private static final String TAG = EventDAO.class.getSimpleName();

    private EventInfoDB dbHandler;
    private SQLiteDatabase database;

    //TODO: maybe we don't need this?
    private Context context;

    private static final String[] columns =
            {
                    EventInfoContract.EventEntry._ID,
                    EventInfoContract.EventEntry.TITLE,
                    EventInfoContract.EventEntry.DATE,
                    EventInfoContract.EventEntry.TIME_START,
                    EventInfoContract.EventEntry.TIME_END,
                    EventInfoContract.EventEntry.TYPE,
                    EventInfoContract.EventEntry.STATE,
                    EventInfoContract.EventEntry.IMPORTANCE
            };

    public EventDAO(Context context)
    {
        this.dbHandler = new EventInfoDB(context);
        this.context = context;
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

    public Event addEvent(Event event)
    {
        Event added_event = null;

        switch (event.getType())
        {
            case TODO:
                added_event = addTodo(event);
                break;

            case BIRTHDAY:
                added_event = addBirthday(event);
                break;

            case WORKTASK:
                added_event = addWorkTask(event);
                break;

            case SCHEDULE:
                added_event = addSchedule(event);
                break;
        }

        Log.i(TAG, "New " + added_event.getType().toString() + " event " + added_event.getId() + " added.");
        return added_event;
    }

    public int updateEvent(Event event)
    {
        int added_event_id = -1;

        switch (event.getType())
        {
            case TODO:
                added_event_id = updateToDo(event);
                break;

            case BIRTHDAY:
                added_event_id = updateBirthday(event);
                break;

            case WORKTASK:
                added_event_id = updateWorkTask(event);
                break;

            case SCHEDULE:
                added_event_id = updateSchedule(event);
                break;
        }

        Log.i(TAG, event.getType().toString() + " event " + added_event_id + " updated.");
        return added_event_id;
    }

    public Event deleteEvent(Event event)
    {
        switch (event.getType())
        {
            case TODO:
                deleteTodo(event);
                break;

            case BIRTHDAY:
                deleteBirthday(event);
                break;

            case WORKTASK:
                deleteWorkTask(event);
                break;

            case SCHEDULE:
                deleteSchedule(event);
                break;
        }

        Log.i(TAG, "New " + event.getType().toString() + " event " + event.getId() + " deleted.");
        return event;
    }

    private Event addTodo(Event event)
    {
        ContentValues event_values = new ContentValues();

        event_values.put(EventInfoContract.EventEntry.TITLE, event.getTitle());
        event_values.put(EventInfoContract.EventEntry.DATE, event.getStartDate());
        event_values.put(EventInfoContract.EventEntry.TIME_START, event.getStartTime());
        event_values.put(EventInfoContract.EventEntry.TIME_END, event.getEndTime());
        event_values.put(EventInfoContract.EventEntry.TYPE, event.getType().toString());
     //   event_values.put(EventInfoContract.EventEntry.STATE, event.getState());
        event_values.put(EventInfoContract.EventEntry.IMPORTANCE, event.getImportance());

        long new_todo_id = database.insert(EventInfoContract.EventEntry.TABLE_NAME, null, event_values);
        event.setId((int) new_todo_id);

        return event;
    }

    private Event addSchedule(Event event)
    {
        ContentValues event_values = new ContentValues();

        event_values.put(EventInfoContract.EventEntry.TITLE, event.getTitle());
        event_values.put(EventInfoContract.EventEntry.DATE, event.getStartDate());
        event_values.put(EventInfoContract.EventEntry.TIME_START, event.getStartTime());
        event_values.put(EventInfoContract.EventEntry.TIME_END, event.getEndTime());
        event_values.put(EventInfoContract.EventEntry.TYPE, event.getType().toString());
      //  event_values.put(EventInfoContract.EventEntry.STATE, event.getState());
        event_values.put(EventInfoContract.EventEntry.IMPORTANCE, event.getImportance());

        long new_schedule_id = database.insert(EventInfoContract.EventEntry.TABLE_NAME, null, event_values);
        event.setId((int) new_schedule_id);

        //Adding Schedule type
        ContentValues contentValuesScheduleType = new ContentValues();

        contentValuesScheduleType.put(EventInfoContract.ScheduleTypeEntry.ID_EVENT, new_schedule_id);
        contentValuesScheduleType.put(EventInfoContract.ScheduleTypeEntry.CONTENT, ((Schedule)event).getScheduleType().toString());

        database.insert(EventInfoContract.ScheduleTypeEntry.TABLE_NAME, null, contentValuesScheduleType);

        return event;
    }

    private Event addWorkTask(Event event)
    {
        ContentValues event_values = new ContentValues();

        event_values.put(EventInfoContract.EventEntry.TITLE, event.getTitle());
        event_values.put(EventInfoContract.EventEntry.DATE, event.getStartDate());
        event_values.put(EventInfoContract.EventEntry.TIME_START, event.getStartTime());
        event_values.put(EventInfoContract.EventEntry.TIME_END, event.getEndTime());
        event_values.put(EventInfoContract.EventEntry.TYPE, event.getType().toString());
    //    event_values.put(EventInfoContract.EventEntry.STATE, event.getState());
        event_values.put(EventInfoContract.EventEntry.IMPORTANCE, event.getImportance());

        long new_work_task_id = database.insert(EventInfoContract.EventEntry.TABLE_NAME, null, event_values);
        event.setId((int) new_work_task_id);

        return event;
    }

    private Event addBirthday(Event event)
    {
        ContentValues event_values = new ContentValues();

        event_values.put(EventInfoContract.EventEntry.TITLE, event.getTitle());
        event_values.put(EventInfoContract.EventEntry.DATE, event.getStartDate());
        event_values.put(EventInfoContract.EventEntry.TIME_START, event.getStartTime());
        event_values.put(EventInfoContract.EventEntry.TIME_END, event.getEndTime());
        event_values.put(EventInfoContract.EventEntry.TYPE, event.getType().toString());
     //   event_values.put(EventInfoContract.EventEntry.STATE, event.getState());
        event_values.put(EventInfoContract.EventEntry.IMPORTANCE, event.getImportance());

        long new_birthday_id = database.insert(EventInfoContract.EventEntry.TABLE_NAME, null, event_values);
        event.setId((int) new_birthday_id);

        //Adding location
        Location birthday_location =  ((Birthday) event).getLocation();

        ContentValues location_values = new ContentValues();

        location_values.put(EventInfoContract.LocationEntry.ID_EVENT, new_birthday_id);
        location_values.put(EventInfoContract.LocationEntry.STREET, birthday_location.getStreet());
        location_values.put(EventInfoContract.LocationEntry.CITY, birthday_location.getCity());
        location_values.put(EventInfoContract.LocationEntry.COUNTRY, birthday_location.getCountry());

        database.insert(EventInfoContract.LocationEntry.TABLE_NAME, null, location_values);

        return event;
    }

    private int updateSchedule(Event event)
    {
        ContentValues event_values = new ContentValues();

        event_values.put(EventInfoContract.EventEntry.TITLE, event.getTitle());
        event_values.put(EventInfoContract.EventEntry.DATE, event.getStartDate());
        event_values.put(EventInfoContract.EventEntry.TIME_START, event.getStartTime());
        event_values.put(EventInfoContract.EventEntry.TIME_END, event.getEndTime());
        event_values.put(EventInfoContract.EventEntry.TYPE, event.getType().toString());
       // event_values.put(EventInfoContract.EventEntry.STATE, event.getState());
        event_values.put(EventInfoContract.EventEntry.IMPORTANCE, event.getImportance());

        database.update(EventInfoContract.EventEntry.TABLE_NAME, event_values, EventInfoContract.EventEntry._ID + "= ?", new String[]{String.valueOf(event.getId())});

        //Updating Schedule type
        ContentValues contentValuesScheduleType = new ContentValues();

        contentValuesScheduleType.put(EventInfoContract.ScheduleTypeEntry.ID_EVENT, event.getId());
        contentValuesScheduleType.put(EventInfoContract.ScheduleTypeEntry.CONTENT, ((Schedule)event).getScheduleType().toString());

        database.update(EventInfoContract.ScheduleTypeEntry.TABLE_NAME, contentValuesScheduleType, EventInfoContract.ScheduleTypeEntry.ID_EVENT + "= ?", new String[]{String.valueOf(event.getId())});

        return event.getId();
    }

    private int updateWorkTask(Event event)
    {
        ContentValues event_values = new ContentValues();

        event_values.put(EventInfoContract.EventEntry.TITLE, event.getTitle());
        event_values.put(EventInfoContract.EventEntry.DATE, event.getStartDate());
        event_values.put(EventInfoContract.EventEntry.TIME_START, event.getStartTime());
        event_values.put(EventInfoContract.EventEntry.TIME_END, event.getEndTime());
        event_values.put(EventInfoContract.EventEntry.TYPE, event.getType().toString());
      //  event_values.put(EventInfoContract.EventEntry.STATE, event.getState());
        event_values.put(EventInfoContract.EventEntry.IMPORTANCE, event.getImportance());

        database.update(EventInfoContract.EventEntry.TABLE_NAME, event_values, EventInfoContract.EventEntry._ID + "= ?", new String[]{String.valueOf(event.getId())});

        return event.getId();
    }

    private int updateBirthday(Event event)
    {
        ContentValues event_values = new ContentValues();

        event_values.put(EventInfoContract.EventEntry.TITLE, event.getTitle());
        event_values.put(EventInfoContract.EventEntry.DATE, event.getStartDate());
        event_values.put(EventInfoContract.EventEntry.TIME_START, event.getStartTime());
        event_values.put(EventInfoContract.EventEntry.TIME_END, event.getEndTime());
        event_values.put(EventInfoContract.EventEntry.TYPE, event.getType().toString());
      //  event_values.put(EventInfoContract.EventEntry.STATE, event.getState());
        event_values.put(EventInfoContract.EventEntry.IMPORTANCE, event.getImportance());

        database.update(EventInfoContract.EventEntry.TABLE_NAME, event_values, EventInfoContract.EventEntry._ID + "= ?", new String[]{String.valueOf(event.getId())});

        //Updating location
        Location birthday_location =  ((Birthday) event).getLocation();

        ContentValues location_values = new ContentValues();

        location_values.put(EventInfoContract.LocationEntry.ID_EVENT, event.getId());
        location_values.put(EventInfoContract.LocationEntry.STREET, birthday_location.getStreet());
        location_values.put(EventInfoContract.LocationEntry.CITY, birthday_location.getCity());
        location_values.put(EventInfoContract.LocationEntry.COUNTRY, birthday_location.getCountry());

        database.update(EventInfoContract.LocationEntry.TABLE_NAME, location_values, EventInfoContract.LocationEntry.ID_EVENT + "= ?", new String[]{String.valueOf(event.getId())});

        return event.getId();
    }

    private int updateToDo(Event event)
    {
        ContentValues event_values = new ContentValues();

        event_values.put(EventInfoContract.EventEntry.TITLE, event.getTitle());
        event_values.put(EventInfoContract.EventEntry.DATE, event.getStartDate());
        event_values.put(EventInfoContract.EventEntry.TIME_START, event.getStartTime());
        event_values.put(EventInfoContract.EventEntry.TIME_END, event.getEndTime());
        event_values.put(EventInfoContract.EventEntry.TYPE, event.getType().toString());
      //  event_values.put(EventInfoContract.EventEntry.STATE, event.getState());
        event_values.put(EventInfoContract.EventEntry.IMPORTANCE, event.getImportance());

        database.update(EventInfoContract.EventEntry.TABLE_NAME, event_values, EventInfoContract.EventEntry._ID + "= ?", new String[]{String.valueOf(event.getId())});

        return event.getId();
    }

    private void deleteSchedule(Event event)
    {
        database.delete(EventInfoContract.EventEntry.TABLE_NAME, EventInfoContract.EventEntry._ID + "= ?", new String[]{String.valueOf(event.getId())});
        database.delete(EventInfoContract.ScheduleTypeEntry.TABLE_NAME, EventInfoContract.ScheduleTypeEntry.ID_EVENT + "= ?", new String[]{String.valueOf(event.getId())});
    }

    private void deleteTodo(Event event)
    {
        database.delete(EventInfoContract.EventEntry.TABLE_NAME, EventInfoContract.EventEntry._ID + "= ?", new String[]{String.valueOf(event.getId())});
    }

    private void deleteWorkTask(Event event)
    {
        database.delete(EventInfoContract.EventEntry.TABLE_NAME, EventInfoContract.EventEntry._ID + "= ?", new String[]{String.valueOf(event.getId())});
    }

    private void deleteBirthday(Event event)
    {
        database.delete(EventInfoContract.EventEntry.TABLE_NAME, EventInfoContract.EventEntry._ID + "= ?", new String[]{String.valueOf(event.getId())});
        database.delete(EventInfoContract.LocationEntry.TABLE_NAME, EventInfoContract.LocationEntry.ID_EVENT + "= ?", new String[]{String.valueOf(event.getId())});
    }

    private Location getLocation(int eventId)
    {
        String birthday_event_select_query =  "SELECT  " +
                EventInfoContract.LocationEntry.TABLE_NAME + "." + EventInfoContract.LocationEntry._ID + "," +
                EventInfoContract.LocationEntry.ID_EVENT + "," +
                EventInfoContract.LocationEntry.STREET + "," +
                EventInfoContract.LocationEntry.CITY + "," +
                EventInfoContract.LocationEntry.COUNTRY +
                " FROM " + EventInfoContract.LocationEntry.TABLE_NAME +
                " WHERE " + EventInfoContract.LocationEntry.ID_EVENT + "= ?";

        Cursor cursor = database.rawQuery(birthday_event_select_query,
                new String[]{String.valueOf(eventId)});

        if(cursor != null)
            cursor.moveToFirst();

        Location location = new Location((int) cursor.getLong(0),
                                                cursor.getInt(1),
                                                cursor.getString(2),
                                                cursor.getString(3),
                                                cursor.getString(4));

        cursor.close();

        return location;
    }

    private ScheduleType getScheduleType(int eventId)
    {
        String scheduleTypeQuery =  "SELECT  " +
                EventInfoContract.ScheduleTypeEntry.CONTENT +
                " FROM " + EventInfoContract.ScheduleTypeEntry.TABLE_NAME +
                " WHERE " + EventInfoContract.ScheduleTypeEntry.ID_EVENT + " =? ";

        Cursor cursor = database.rawQuery(scheduleTypeQuery,
                new String[]{String.valueOf(eventId)});

        if(cursor != null)
            cursor.moveToFirst();

        ScheduleType scheduleType = ScheduleType.getName(cursor.getString(0));

        cursor.close();

        return scheduleType;
    }

    public List<Event> getAllEventsByDate(String date)
    {
        Cursor cursor = database.query(EventInfoContract.EventEntry.TABLE_NAME,
                columns,
                EventInfoContract.EventEntry.DATE + "= ?",
                new String[]{String.valueOf(date)},
                null,
                null,
                null);

        List<Event> events = new ArrayList<>();

        Sub_taskDAO sub_taskDAO = new Sub_taskDAO(context);
        CommentDAO commentDAO = new CommentDAO(context);
        InterestDAO interestDAO = new InterestDAO(context);

        while (cursor.moveToNext())
        {
            Event event = null;

            int event_id = (int) cursor.getLong(cursor.getColumnIndex(EventInfoContract.EventEntry._ID));
            String event_title = cursor.getString(cursor.getColumnIndex(EventInfoContract.EventEntry.TITLE));
            String event_date = cursor.getString(cursor.getColumnIndex(EventInfoContract.EventEntry.DATE));
            String event_time_start = cursor.getString(cursor.getColumnIndex(EventInfoContract.EventEntry.TIME_START));
            String event_time_end = cursor.getString(cursor.getColumnIndex(EventInfoContract.EventEntry.TIME_END));
            String event_type = cursor.getString(cursor.getColumnIndex(EventInfoContract.EventEntry.TYPE));
            String event_state = cursor.getString(cursor.getColumnIndex(EventInfoContract.EventEntry.STATE));
            int event_importance = cursor.getInt(cursor.getColumnIndex(EventInfoContract.EventEntry.IMPORTANCE));

            EventType eventType = EventType.getName(event_type);

            if (eventType != null)
            {
                switch (eventType)
                {
                    case TODO:
                       // event = new ToDo(event_id, event_title, event_time_start, event_time_end, event_date, event_state, event_importance);
                        commentDAO.open();
                        ((ToDo)event).setListComments(commentDAO.getCommentsByEventId(event_id));
                        commentDAO.close();

                        sub_taskDAO.open();
                        ((ToDo)event).setListSubTasks(sub_taskDAO.getSubTasksByEventId(event_id));
                        sub_taskDAO.close();

                        interestDAO.open();
                        event.setListInterests(interestDAO.getInterestsByEventId(event_id));
                        interestDAO.close();
                        break;

                    case WORKTASK:
                       // event = new WorkTask(event_id, event_title, event_time_start, event_time_end, event_date, event_state, event_importance);
                        commentDAO.open();
                        ((WorkTask)event).setListComments(commentDAO.getCommentsByEventId(event_id));
                        commentDAO.close();

                        sub_taskDAO.open();
                        ((WorkTask)event).setListSubTasks(sub_taskDAO.getSubTasksByEventId(event_id));
                        sub_taskDAO.close();

                        interestDAO.open();
                        event.setListInterests(interestDAO.getInterestsByEventId(event_id));
                        interestDAO.close();
                        break;

                    case BIRTHDAY:
                        Location location = getLocation(event_id);
                       // event = new Birthday(event_id, event_title, event_time_start, event_time_end, event_date, event_state, event_importance, location);
                        commentDAO.open();
                        ((Birthday)event).setListComments(commentDAO.getCommentsByEventId(event_id));
                        commentDAO.close();

                        sub_taskDAO.open();
                        ((Birthday)event).setListSubTasks(sub_taskDAO.getSubTasksByEventId(event_id));
                        sub_taskDAO.close();

                        interestDAO.open();
                        event.setListInterests(interestDAO.getInterestsByEventId(event_id));
                        interestDAO.close();
                        break;

                    case SCHEDULE:
                        ScheduleType scheduleType = getScheduleType(event_id);
                        event = new Schedule(event_id, event_title, scheduleType, event_time_start, event_time_end, event_date, event_state, event_importance);

                        interestDAO.open();
                        event.setListInterests(interestDAO.getInterestsByEventId(event_id));
                        interestDAO.close();
                        break;
                }
            }

            events.add(event);
        }
        cursor.close();

        return events;
    }

    public List<ToDo> getAllToDoList(){
        Cursor cursor = database.query(EventInfoContract.EventEntry.TABLE_NAME,
                columns,
                EventInfoContract.EventEntry.TYPE + "= ?",
                new String[]{String.valueOf(EventType.TODO.toString())},
                null,
                null,
                null);

        List<ToDo> toDoList = new ArrayList<>();

        Sub_taskDAO sub_taskDAO = new Sub_taskDAO(context);
        CommentDAO commentDAO = new CommentDAO(context);
        InterestDAO interestDAO = new InterestDAO(context);

        while(cursor.moveToNext()){
            int id = (int) cursor.getLong(cursor.getColumnIndex(EventInfoContract.EventEntry._ID));
            String title = cursor.getString(cursor.getColumnIndex(EventInfoContract.EventEntry.TITLE));
            String startTime = cursor.getString(cursor.getColumnIndex(EventInfoContract.EventEntry.TIME_START));
            String endTime = cursor.getString(cursor.getColumnIndex(EventInfoContract.EventEntry.TIME_END));
            String date = cursor.getString(cursor.getColumnIndex(EventInfoContract.EventEntry.DATE));
            String state = cursor.getString(cursor.getColumnIndex(EventInfoContract.EventEntry.STATE));
            int importance = cursor.getInt(cursor.getColumnIndex(EventInfoContract.EventEntry.IMPORTANCE));

          /*  ToDo toDo = new ToDo(id, title, startTime, endTime, date, state, importance);

            commentDAO.open();
            toDo.setListComments(commentDAO.getCommentsByEventId(id));
            commentDAO.close();

            sub_taskDAO.open();
            toDo.setListSubTasks(sub_taskDAO.getSubTasksByEventId(id));
            sub_taskDAO.close();

            interestDAO.open();
            toDo.setListInterests(interestDAO.getInterestsByEventId(id));
            interestDAO.close();

            toDoList.add(toDo);*/
        }

        return toDoList;
    }

    public void addEventOnBG(Event event)
    {
        open();
        new BackgroundEventQueries().execute("add_data", event);
    }

    public void deleteEventOnBG(Event event)
    {
        open();
        new BackgroundEventQueries().execute("delete_data", event);
    }

    public void updateEventOnBG(Event event)
    {
        open();
        new BackgroundEventQueries().execute("update_data", event);
    }

    public void getTodayEventListOnBG(TodayTaskAdapter todayTaskAdapter)
    {
        open();
        new BackgroundEventQueries().execute("get_today_data", todayTaskAdapter);
    }

    public void getEventListByDateOnBG(ScheduleDayEventAdapter scheduleDayEventAdapter)
    {
        open();
        new BackgroundEventQueries().execute("get_data_by_date", scheduleDayEventAdapter);
    }

    public void getToDoListOnBG(ToDoListAdapter toDoListAdapter)
    {
        open();
        new BackgroundEventQueries().execute("get_todo_list", toDoListAdapter);
    }

    private class BackgroundEventQueries extends AsyncTask<Object, Event, String>
    {
        private final String TAG_THIS = BackgroundEventQueries.class.getSimpleName();

        //Adapters
        private TodayTaskAdapter todayTaskAdapter;
        private ScheduleDayEventAdapter scheduleDayEventAdapter;
        private ToDoListAdapter toDoListAdapter;

        @Override
        protected String doInBackground(Object... params)
        {
            String operation = (String) params[0];
            String result = "";

            switch (operation)
            {
                case "add_data":
                    addEvent((Event) params[1]);
                    result = TAG + " " + "added on Background";
                    break;

                case "update_data":
                    updateEvent((Event) params[1]);
                    result = TAG + " " + "updated on Background";
                    break;

                case "delete_data":
                    deleteEvent((Event) params[1]);
                    result = TAG + " " + "deleted on Background";
                    break;

                case "get_today_data":
                    this.todayTaskAdapter = (TodayTaskAdapter) params[1];
                    getTodayList();
                    result = TAG + " " + "got today list on Background";
                    break;

                case "get_data_by_date":
                    this.scheduleDayEventAdapter = (ScheduleDayEventAdapter) params[1];
                    getEventListByDate();
                    result = TAG + " " + "got list of events on picked date on Background";
                    break;

                case "get_todo_list":
                    this.toDoListAdapter = (ToDoListAdapter) params[1];
                    getToDoList();
                    result = TAG + " " + "got ToDo list on Background";
                    break;
            }
            return result;
        }

        @Override
        protected void onProgressUpdate(Event... events)
        {
            Event event = events[0];

            if(todayTaskAdapter == null && toDoListAdapter == null)
            {
                scheduleDayEventAdapter.add(event);
            }
            else if (scheduleDayEventAdapter == null && todayTaskAdapter == null){
                toDoListAdapter.add((ToDo) event);
            }
            else
            {
                if (todayTaskAdapter.headerPosition(event.getType().toString()) == -1)
                {
                    todayTaskAdapter.add(new EventTypeSection(event.getType().toString()));
                    todayTaskAdapter.add(todayTaskAdapter.headerPosition(event.getType().toString()) + 1, event);
                } else
                    todayTaskAdapter.add(todayTaskAdapter.headerPosition(event.getType().toString()) + 1, event);
            }
        }

        @Override
        protected void onPostExecute(String s)
        {
            //TODO: think of something better.!
            if(s.equals(TAG + " " + "got today list on Background")) todayTaskAdapter.updateAll();
            else if(s.equals(TAG + " " + "got list of events on picked date on Background"))
                scheduleDayEventAdapter.updateAll();
            else if(s.equals(TAG + " " + "got ToDo list on Background")) toDoListAdapter.updateAll();

            Log.i(TAG_THIS, s);
            close();
        }

        private void getTodayList()
        {
            List<Event> events = getAllEventsByDate(todayTaskAdapter.getTodaysDate());
            for(int i = 0; i < events.size(); i++)
                publishProgress(events.get(i));
        }

        public void getEventListByDate()
        {
            List<Event> events = getAllEventsByDate(scheduleDayEventAdapter.getDate());
            for(int i = 0; i < events.size(); i++)
                publishProgress(events.get(i));
        }

        public void getToDoList()
        {
            List<ToDo> toDoList = getAllToDoList();
            for(int i = 0; i < toDoList.size(); i++)
                publishProgress(toDoList.get(i));
        }
    }
}
