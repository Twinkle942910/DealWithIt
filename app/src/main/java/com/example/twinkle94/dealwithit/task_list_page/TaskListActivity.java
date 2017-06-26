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
import com.example.twinkle94.dealwithit.fragments.task_list_fragments.ToDoListFragment;

public class TaskListActivity extends AppCompatActivity {
    private static final int LAYOUT = R.layout.activity_task_list;
    private static final int FRAGMENT_CONTAINER = R.id.task_list_container;
    public static final String TASK_TYPE = "task_type";

    private Toolbar toolbar;
    private ToDoListFragment mToDoListFragment;

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

        switch (caller.getStringExtra(TASK_TYPE)) {
            case "ToDo":
                setToolbarTitle("ToDo List");

                FragmentManager fragmentManager = getSupportFragmentManager();
                mToDoListFragment = (ToDoListFragment) fragmentManager.findFragmentByTag(ToDoListFragment.TAG);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                if (mToDoListFragment == null) {
                    mToDoListFragment = new ToDoListFragment();

                    fragmentTransaction.add(FRAGMENT_CONTAINER, mToDoListFragment, ToDoListFragment.TAG);
                    fragmentTransaction.commit();
                } else fragmentTransaction.replace(FRAGMENT_CONTAINER, mToDoListFragment);

                break;

            case "WorkTask":

                break;

            case "Birthday":

                break;

            case "Schedule":

                break;
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