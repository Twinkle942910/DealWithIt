package com.example.twinkle94.dealwithit.task_list_page;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.twinkle94.dealwithit.R;
import com.example.twinkle94.dealwithit.events.Event;
import com.example.twinkle94.dealwithit.events.event_types.EventType;
import com.example.twinkle94.dealwithit.fragments.task_list_fragments.TaskListFragment;

public class TaskListActivity extends AppCompatActivity {
    private static final int LAYOUT = R.layout.activity_task_list;
    private static final int FRAGMENT_CONTAINER = R.id.task_list_container;
    public static final String TASK_TYPE = "task_type";
    public static final String CALL_TYPE = "call_type";

    private Toolbar toolbar;
    private TaskListFragment mTaskListFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: move, depending on task type!
        setTheme(R.style.TaskListTheme);

        setContentView(LAYOUT);

        initToolbar();
        setHomeButton();

        //See who called this activity.
        Intent caller = getIntent();

        String taskType = caller.getStringExtra(TASK_TYPE);
        String callType = caller.getStringExtra(CALL_TYPE);

        EventType eventType = EventType.getName(taskType);
        int eventColor = EventType.getColor(taskType);

        //TODO: set toolbar color depending on event type.

        if(callType.equals("Detail")){
            //TODO: get item from DB by id and then add info to the fragment(inside fragment).
            //TODO: load detail fragment.
        }
        else {
            setToolbarTitle(taskType + " List");

            FragmentManager fragmentManager = getSupportFragmentManager();
            //TODO: make this fragment available for different types of events (1 fragment for 4 lists).
            mTaskListFragment = (TaskListFragment) fragmentManager.findFragmentByTag(TaskListFragment.TAG);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            if (mTaskListFragment == null) {
                mTaskListFragment = TaskListFragment.newInstance(eventType);

                fragmentTransaction.add(FRAGMENT_CONTAINER, mTaskListFragment, TaskListFragment.TAG);
                fragmentTransaction.commit();
            } else fragmentTransaction.replace(FRAGMENT_CONTAINER, mTaskListFragment);
        }
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setHomeButton() {
        if (toolbar != null) {
            toolbar.setNavigationIcon(AppCompatResources.getDrawable(this, R.drawable.ic_cancel_button));
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    private void setToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public void editTaskDialog(Event task){
        EditDeleteTaskDialog editDialog = EditDeleteTaskDialog.newInstance("Edit task", task);
        editDialog.show(getSupportFragmentManager(), "Edit dialog");
    }

    public void deleteTaskDialog(Event task) {
        EditDeleteTaskDialog deleteDialog = EditDeleteTaskDialog.newInstance("Delete task", task);
        deleteDialog.show(getSupportFragmentManager(), "Delete dialog");
    }

    public static class EditDeleteTaskDialog extends DialogFragment{
        private static final String ID_KEY = "id";
        private static final String MESSAGE_KEY = "message";

        int mTaskId = -1;
        String mMessage = "No message";

        public static EditDeleteTaskDialog newInstance(String message, Event task) {
            Bundle args = new Bundle();

            //args.putInt(ID_KEY, task);
            args.putString(MESSAGE_KEY, message);

            EditDeleteTaskDialog fragment = new EditDeleteTaskDialog();
            fragment.setArguments(args);
            return fragment;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Bundle bundle = getArguments();
            if(bundle != null){
                mTaskId = bundle.getInt(ID_KEY);
                mMessage = bundle.getString(MESSAGE_KEY);
            }

            AlertDialog.Builder alertDialogBuilder =
                    new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
            alertDialogBuilder.setMessage(mMessage);

            alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                //    if(mMessage.equals("Delete")) new EventDAO(getActivity()).deleteEventOnBG(new ToDo(id));
                }
            });

            alertDialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                }
            });

            return alertDialogBuilder.create();

        }
    }
}
