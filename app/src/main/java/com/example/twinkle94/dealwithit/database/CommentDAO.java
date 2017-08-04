package com.example.twinkle94.dealwithit.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import com.example.twinkle94.dealwithit.events.notes.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentDAO
{
    private static final String TAG = CommentDAO.class.getSimpleName();

    private EventInfoDB dbHandler;
    private SQLiteDatabase database;

    private static final String[] columns =
            {
                    EventInfoContract.CommentEntry._ID,
                    EventInfoContract.CommentEntry.ID_EVENT,
                    EventInfoContract.CommentEntry.CONTENT
            };

    public CommentDAO(Context context)
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

    public Comment addComment(Comment comment)
    {
        ContentValues commentValues = new ContentValues();

        commentValues.put(EventInfoContract.CommentEntry.ID_EVENT, comment.getId_event());
        commentValues.put(EventInfoContract.CommentEntry.CONTENT, comment.getContent());

        long newCommentId = database.insert(EventInfoContract.CommentEntry.TABLE_NAME, null, commentValues);
        comment.setId((int) newCommentId);

        Log.i(TAG, "New " + TAG + " " + comment.getId() + " added.");

        return comment;
    }

    public int updateComment(Comment comment)
    {
        ContentValues sub_taskValues = new ContentValues();

        sub_taskValues.put(EventInfoContract.CommentEntry.ID_EVENT, comment.getId_event());
        sub_taskValues.put(EventInfoContract.CommentEntry.CONTENT, comment.getContent());

        database.update(EventInfoContract.CommentEntry.TABLE_NAME,
                sub_taskValues,
                EventInfoContract.CommentEntry._ID + "= ?",
                new String[]{String.valueOf(comment.getId()) });

        Log.i(TAG, TAG + " " + comment.getId() + " updated.");

        return comment.getId();
    }

    public void deleteComment(Comment comment)
    {
        database.delete(EventInfoContract.CommentEntry.TABLE_NAME, EventInfoContract.CommentEntry._ID + "= ?", new String[]{String.valueOf(comment.getId())});
        Log.i(TAG, TAG + " " + comment.getId() + " deleted.");
    }

    public List<Comment> getCommentsByEventId(int eventId)
    {
        Cursor cursor = database.query(EventInfoContract.CommentEntry.TABLE_NAME,
                columns,
                EventInfoContract.CommentEntry.ID_EVENT + "= ?",
                new String[]{String.valueOf(eventId)},
                null,
                null,
                null);

        List<Comment> comments = new ArrayList<>();

        while (cursor.moveToNext())
        {
            Comment comment = new Comment();
            comment.setId((int) cursor.getLong(cursor.getColumnIndex(EventInfoContract.CommentEntry._ID)));
            comment.setId_event(cursor.getInt(cursor.getColumnIndex(EventInfoContract.CommentEntry.ID_EVENT)));
            comment.setContent(cursor.getString(cursor.getColumnIndex(EventInfoContract.CommentEntry.CONTENT)));
            comments.add(comment);
        }
        cursor.close();

        return comments;
    }

    public void addTaskOnBG(Comment comment)
    {
        open();
        new BackgroundCommentQueries().execute("add_data", comment);
    }

    public void updateTaskOnBG(Comment comment)
    {
        open();
        new BackgroundCommentQueries().execute("update_data", comment);
    }

    public void deleteTaskOnBG(Comment comment)
    {
        open();
        new BackgroundCommentQueries().execute("delete_data", comment);
    }

    private class BackgroundCommentQueries extends AsyncTask<Object, Void, String>
    {
        private final String TAG_THIS = BackgroundCommentQueries.class.getSimpleName();

        @Override
        protected String doInBackground(Object... params)
        {
            String operation = (String) params[0];
            String result = "";

            switch (operation)
            {
                case "add_data":
                    addComment((Comment) params[1]);
                    result = TAG + " " + "added on Background";
                    break;

                case "update_data":
                    updateComment((Comment) params[1]);
                    result = TAG + " " + "updated on Background";
                    break;

                case "delete_data":
                    deleteComment((Comment) params[1]);
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
