package com.example.twinkle94.dealwithit.interests_page;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.twinkle94.dealwithit.R;
import com.example.twinkle94.dealwithit.adapter.interests_page_adapter.InterestHeader;
import com.example.twinkle94.dealwithit.adapter.interests_page_adapter.InterestsAdapter;
import com.example.twinkle94.dealwithit.background.FetchEventsTask;
import com.example.twinkle94.dealwithit.events.Interest;
import com.example.twinkle94.dealwithit.util.SnackBarMessage;
import com.example.twinkle94.dealwithit.util.TextValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InterestsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{
    private Toolbar toolbar;
    private ListView interests_lv;
    private FloatingActionButton add_interest_fab;

    private InterestsAdapter interestsAdapter;
    private int importance_value;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);

        initToolbar();
        initFab();
        initInterestList();
    }

    private void initFab()
    {
        add_interest_fab = (FloatingActionButton) findViewById(R.id.new_interest);
        add_interest_fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                inputInterestDialog();
            }
        });
    }

    private void initInterestList()
    {
        interests_lv = (ListView) findViewById(R.id.interests_list);
        interestsAdapter  =  new InterestsAdapter(this);
        interests_lv.setAdapter(interestsAdapter);
        interests_lv.setOnItemClickListener(this);

       FetchEventsTask get_interests_task = new FetchEventsTask(this);
        get_interests_task.execute("get_data", interestsAdapter, "Interest");
    }

    //Toolbar menu implementation
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_interests, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.interests_info:
                return true;

            case R.id.show_all:
                return true;

            case R.id.show_important:
                return true;

            case R.id.show_not_important:
                return true;

            case R.id.show_az:
                return true;

            case R.id.show_za:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //init Toolbar
    private void initToolbar()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setHomeButton();
    }

    private void setHomeButton()
    {
        if(toolbar != null)
        {
            toolbar.setNavigationIcon(AppCompatResources.getDrawable(this, R.drawable.ic_arrow_back));
            toolbar.setNavigationOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    finish();
                }
            });
        }
    }

    public void inputInterestDialog()
    {
        final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_layout);

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.InterestAlertDialogStyle);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.dialog_new_interest, null);
        dialogBuilder.setView(dialogView);

        dialogBuilder.setTitle(R.string.input_dialog_title);

        final TextInputEditText interest_title_iet = (TextInputEditText) dialogView.findViewById(R.id.interest_title_input);
        final TextInputLayout interest_title_il = (TextInputLayout) dialogView.findViewById(R.id.interest_title_input_layout);

        final TextValidator interest_input_validator = new TextValidator(interest_title_iet)
        {
            @Override
            public void validate(TextView textView, String text)
            {
               setInputError(!checkIfTextEmpty(textView), interest_title_il, getString(R.string.empty_error));
            }
        };

        interest_title_iet.addTextChangedListener(interest_input_validator);
        interest_title_iet.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean b)
            {
                setInputError(!checkIfTextEmpty(interest_title_iet), interest_title_il, getString(R.string.empty_error));
            }
        });

        final SeekBar interest_importance_sb = (SeekBar) dialogView.findViewById(R.id.importance_value);
        final TextView importance_percent_tv = (TextView) dialogView.findViewById(R.id.importance_percent_value);
        final TextView importance_type_tv = (TextView) dialogView.findViewById(R.id.importance_type);

        interest_importance_sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
                                                           {
                                                               @Override
                                                               public void onProgressChanged(SeekBar seekBar, int i, boolean b)
                                                               {
                                                                   importance_value = i;
                                                                   importance_percent_tv.setText(importance_value + "%");

                                                                   if (importance_value >= 60)
                                                                       importance_type_tv.setText(R.string.important_text);
                                                                   else importance_type_tv.setText(R.string.not_important_text);
                                                               }

                                                               @Override
                                                               public void onStartTrackingTouch(SeekBar seekBar) {

                                                               }

                                                               @Override
                                                               public void onStopTrackingTouch(SeekBar seekBar) {

                                                               }
                                                           });

        dialogBuilder.setPositiveButton(getString(R.string.dialog_ok), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                if(TextUtils.isEmpty(interest_title_il.getError()) && !TextUtils.isEmpty(interest_title_iet.getText()) && importance_value > 0)
                {
                   final Interest interest = new Interest(-1, -1, interest_title_iet.getText().toString(), importance_value);

                    if(importance_value >= 60)
                    {
                       if (interestsAdapter.headerPosition("Important") == -1)
                       {
                           interestsAdapter.add(new InterestHeader("Important"));
                           interestsAdapter.add(interestsAdapter.headerPosition("Important") + 1, interest);
                       }
                       else interestsAdapter.add(interestsAdapter.headerPosition("Important") + 1, interest);
                    }
                    else if(importance_value < 60)
                    {
                        if (interestsAdapter.headerPosition("Not important") == -1)
                        {
                            interestsAdapter.add(new InterestHeader("Not important"));
                            interestsAdapter.add(interestsAdapter.headerPosition("Not important") + 1, interest);
                         }
                        else interestsAdapter.add(interestsAdapter.headerPosition("Not important") + 1, interest);
                    }

                    interestsAdapter.updateAll();

                    new FetchEventsTask(getApplicationContext()).execute("add_data", interest);

                    final int added_interest_position = interestsAdapter.itemPosition(interest);

                    interests_lv.setSelection(added_interest_position);

                    SnackBarMessage interestAddedMessage = new SnackBarMessage(coordinatorLayout, "Interest " + "Interest title" + " was created", Snackbar.LENGTH_LONG);
                    interestAddedMessage.action("UNDO", new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            interestsAdapter.remove(added_interest_position);
                            interestsAdapter.updateAll();

                            new FetchEventsTask(getApplicationContext()).execute("remove_data", interest);
                        }
                    });

                    interestAddedMessage.actionTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorInterestsAccent));
                    interestAddedMessage.messageLinesCount(2);
                    interestAddedMessage.show();
                }
                else
                    {
                        SnackBarMessage interestAddedMessage = new SnackBarMessage(coordinatorLayout, "Error! Interest was not created.\nYou must input name and importance", Snackbar.LENGTH_LONG);
                        interestAddedMessage.action("AGAIN", new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                inputInterestDialog();
                            }
                        });

                        interestAddedMessage.actionTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorInterestsAccent));
                        interestAddedMessage.messageLinesCount(2);
                        interestAddedMessage.show();
                }

                interest_title_iet.removeTextChangedListener(interest_input_validator);
                interest_title_iet.setOnFocusChangeListener(null);

                interest_importance_sb.setProgress(0);
                interest_importance_sb.setOnSeekBarChangeListener(null);
            }
        });

        dialogBuilder.setNegativeButton(getString(R.string.dialog_cancel), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                interest_title_iet.removeTextChangedListener(interest_input_validator);
                interest_title_iet.setOnFocusChangeListener(null);

                interest_importance_sb.setProgress(0);
                interest_importance_sb.setOnSeekBarChangeListener(null);
            }
        });

        dialogBuilder.setOnCancelListener(new DialogInterface.OnCancelListener()
        {
            @Override
            public void onCancel(DialogInterface dialogInterface)
            {
                SnackBarMessage interestAddedMessage = new SnackBarMessage(coordinatorLayout,  "Error! Interest was not created.\nYou must save your input", Snackbar.LENGTH_LONG);
                interestAddedMessage.action("AGAIN", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        inputInterestDialog();
                    }
                });

                interestAddedMessage.actionTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorInterestsAccent));
                interestAddedMessage.messageLinesCount(2);
                interestAddedMessage.show();
            }
        });

        final AlertDialog b = dialogBuilder.create();

        b.show();
    }

    public void editInterestDialog(final int interest_position)
    {
        final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_layout);

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.InterestAlertDialogStyle);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.dialog_new_interest, null);
        dialogBuilder.setView(dialogView);

        dialogBuilder.setTitle(R.string.edit_dialog_title);

        final Interest old_interest = (Interest) interestsAdapter.getItem(interest_position);

        final TextInputEditText interest_title_iet = (TextInputEditText) dialogView.findViewById(R.id.interest_title_input);
        final TextInputLayout interest_title_il = (TextInputLayout) dialogView.findViewById(R.id.interest_title_input_layout);

        final TextValidator interest_input_validator = new TextValidator(interest_title_iet)
        {
            @Override
            public void validate(TextView textView, String text)
            {
                setInputError(!checkIfTextEmpty(textView), interest_title_il, getString(R.string.empty_error));
            }
        };

        interest_title_iet.addTextChangedListener(interest_input_validator);
        interest_title_iet.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean b)
            {
                setInputError(!checkIfTextEmpty(interest_title_iet), interest_title_il, getString(R.string.empty_error));
            }
        });

        interest_title_iet.setText(old_interest.getTitle());

        final SeekBar interest_importance_sb = (SeekBar) dialogView.findViewById(R.id.importance_value);
        final TextView importance_percent_tv = (TextView) dialogView.findViewById(R.id.importance_percent_value);
        final TextView importance_type_tv = (TextView) dialogView.findViewById(R.id.importance_type);

        interest_importance_sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {
                importance_value = i;
                importance_percent_tv.setText(importance_value + "%");

                if (importance_value >= 60)
                    importance_type_tv.setText(R.string.important_text);
                else importance_type_tv.setText(R.string.not_important_text);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        interest_importance_sb.setProgress(old_interest.getValue());

        dialogBuilder.setPositiveButton(getString(R.string.dialog_ok), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                if(TextUtils.isEmpty(interest_title_il.getError()) && !TextUtils.isEmpty(interest_title_iet.getText()) && importance_value > 0)
                {
                    interestsAdapter.remove(interest_position);
                    new FetchEventsTask(getApplicationContext()).execute("remove_data", old_interest);

                    final Interest new_interest = new Interest(-1, -1, interest_title_iet.getText().toString(), importance_value);

                    if(importance_value >= 60)
                    {
                        if (interestsAdapter.headerPosition("Important") == -1)
                        {
                            interestsAdapter.add(new InterestHeader("Important"));
                            interestsAdapter.add(interestsAdapter.headerPosition("Important") + 1, new_interest);
                        }
                        else interestsAdapter.add(interestsAdapter.headerPosition("Important") + 1, new_interest);
                    }
                    else if(importance_value < 60)
                    {
                        if (interestsAdapter.headerPosition("Not important") == -1)
                        {
                            interestsAdapter.add(new InterestHeader("Not important"));
                            interestsAdapter.add(interestsAdapter.headerPosition("Not important") + 1, new_interest);
                        }
                        else interestsAdapter.add(interestsAdapter.headerPosition("Not important") + 1, new_interest);
                    }

                    interestsAdapter.updateAll();

                    new FetchEventsTask(getApplicationContext()).execute("add_data", new_interest);

                    final int added_interest_position = interestsAdapter.itemPosition(new_interest);

                    SnackBarMessage interestAddedMessage = new SnackBarMessage(coordinatorLayout, "Interest " + old_interest.getTitle() + " was edited", Snackbar.LENGTH_LONG);
                    interestAddedMessage.action("UNDO", new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            interestsAdapter.remove(added_interest_position);
                            interestsAdapter.updateAll();

                            new FetchEventsTask(getApplicationContext()).execute("remove_data", new_interest);

                            interestsAdapter.add(interest_position, old_interest);
                            interestsAdapter.updateAll();

                            new FetchEventsTask(getApplicationContext()).execute("add_data", old_interest);
                        }
                    });

                    interestAddedMessage.actionTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorInterestsAccent));
                    interestAddedMessage.messageLinesCount(2);
                    interestAddedMessage.show();
                }
                else
                {
                    SnackBarMessage interestAddedMessage = new SnackBarMessage(coordinatorLayout, "Error! Interest was not created.\nYou must input name and importance", Snackbar.LENGTH_LONG);
                    interestAddedMessage.action("AGAIN", new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                           editInterestDialog(interest_position);
                        }
                    });

                    interestAddedMessage.actionTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorInterestsAccent));
                    interestAddedMessage.messageLinesCount(2);
                    interestAddedMessage.show();
                }

                interest_title_iet.removeTextChangedListener(interest_input_validator);
                interest_title_iet.setOnFocusChangeListener(null);

                interest_importance_sb.setProgress(0);
                interest_importance_sb.setOnSeekBarChangeListener(null);
            }
        });

        dialogBuilder.setNegativeButton(getString(R.string.dialog_cancel), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                interest_title_iet.removeTextChangedListener(interest_input_validator);
                interest_title_iet.setOnFocusChangeListener(null);

                interest_importance_sb.setProgress(0);
                interest_importance_sb.setOnSeekBarChangeListener(null);
            }
        });

        dialogBuilder.setOnCancelListener(new DialogInterface.OnCancelListener()
        {
            @Override
            public void onCancel(DialogInterface dialogInterface)
            {
                interest_title_iet.removeTextChangedListener(interest_input_validator);
                interest_title_iet.setOnFocusChangeListener(null);

                interest_importance_sb.setProgress(0);
                interest_importance_sb.setOnSeekBarChangeListener(null);
            }
        });

        final AlertDialog b = dialogBuilder.create();

        b.show();
    }

    public void deleteInterestDialog(final int interest_position)
    {
        final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_layout);

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.InterestAlertDialogStyle);

        dialogBuilder.setTitle(R.string.delete_dialog_title);
        dialogBuilder.setMessage("Are you sure?");

        final Interest old_interest = (Interest) interestsAdapter.getItem(interest_position);

        dialogBuilder.setPositiveButton(getString(R.string.dialog_ok), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                    interestsAdapter.remove(interest_position);
                    interestsAdapter.updateAll();

                    new FetchEventsTask(getApplicationContext()).execute("remove_data", old_interest);

                    final int removed_interest_position = interest_position;

                    SnackBarMessage interestAddedMessage = new SnackBarMessage(coordinatorLayout, "Interest " + old_interest.getTitle() + " was removed", Snackbar.LENGTH_LONG);
                    interestAddedMessage.action("UNDO", new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            interestsAdapter.add(removed_interest_position, old_interest);
                            interestsAdapter.updateAll();

                            new FetchEventsTask(getApplicationContext()).execute("add_data", old_interest);
                        }
                    });

                    interestAddedMessage.actionTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorInterestsAccent));
                    interestAddedMessage.messageLinesCount(2);
                    interestAddedMessage.show();
            }
        });

        dialogBuilder.setNegativeButton(getString(R.string.dialog_cancel), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
            }
        });

        final AlertDialog b = dialogBuilder.create();

        b.show();
    }

    protected boolean checkIfTextEmpty(TextView textInput)
    {
        return TextUtils.isEmpty(textInput.getText());
    }

    protected void setInputError(boolean isAlright, TextInputLayout task_type_layout, String error)
    {
        if(task_type_layout != null)
        {
            if (isAlright)
            {
                task_type_layout.setErrorEnabled(false);
                task_type_layout.setError(null);
            }
            else task_type_layout.setError(error);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        //List item click!
    }
}
