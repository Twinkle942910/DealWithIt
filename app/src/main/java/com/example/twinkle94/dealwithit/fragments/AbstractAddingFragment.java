package com.example.twinkle94.dealwithit.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.twinkle94.dealwithit.R;
import com.example.twinkle94.dealwithit.adding_task_page.NewTaskActivity;
import com.example.twinkle94.dealwithit.events.type_enums.EventType;
import com.example.twinkle94.dealwithit.util.Constants;
import com.example.twinkle94.dealwithit.util.TextValidator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public abstract class AbstractAddingFragment extends Fragment
{
    private static final String NAME = AbstractAddingFragment.class.getSimpleName();

    public static final String TASK_TYPE = "task_type";
    public static final String TYPE_NOT_SET = EventType.NO_TYPE.toString();

    public static final String TASK_TYPE_IMAGE = "task_type_image";
    public static final int TYPE_IMAGE_NOT_SET = Constants.NO_TYPE_IMAGE;

    protected NewTaskActivity activity;
    protected OnTypePickListener typePickListener;

    //Data form user.
    protected TextInputEditText type_iet;

    //Views
    protected TextView task_type_output_tv;
    protected ImageView task_type_output_image_iv;

    //Transfer data
    protected EventType task_type = EventType.NO_TYPE;
    protected int task_type_image = Constants.NO_TYPE_IMAGE;

    private TextValidator outputTypeValidator;
    private TextValidator inputTypeValidator;
    private View.OnFocusChangeListener inputTypeFocusChangeListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.activity = (NewTaskActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        final View fragmentView = inflater.inflate(getFragmentLayout(), container, false);
        initializeTypeViews(fragmentView);
        initializeViews(fragmentView);
        setTaskType();
        return fragmentView;
    }

    private void initializeTypeViews(View fragmentView)
    {
        type_iet = (TextInputEditText) fragmentView.findViewById(R.id.task_type_input);
        task_type_output_tv = (TextView) fragmentView.findViewById(R.id.task_type);
        task_type_output_image_iv = (ImageView) fragmentView.findViewById(R.id.task_type_image);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        initializeValidators();
        addValidators();

        //output type validator
        initializeOutputTypeValidator();
        addOutputTypeValidator();

        //input type validator
        initializeInputTypeValidator();
        addInputTypeValidator();

        //input type listener
        initializeInputTypeListener();
        setInputTypeListener();

        initializeListeners();
        setListeners();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        removeValidators();
        removeInputTypeValidator();
        removeOutputTypeValidator();
        removeListeners();
        removeInputTypeListener();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(getFragmentMenu(), menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int menu_item_id = item.getItemId();
        onMenuItemClick(menu_item_id);

        return super.onOptionsItemSelected(item);
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

  /*  protected boolean isInputErrors()
    {
        ViewGroup fragment_view = (ViewGroup) getView();

        if(fragment_view != null) return checkAll(fragment_view);
        return true;
    }

    private boolean checkAll(ViewGroup currentView)
    {
            for(int i = 0; i < currentView.getChildCount(); i++)
            {
                if (currentView.getChildAt(i) instanceof TextInputLayout)
                {
                    if (!TextUtils.isEmpty(((TextInputLayout) currentView).getError()))
                        return false;
                }

                if (currentView.getChildAt(i) instanceof ViewGroup)
                {
                    ViewGroup innerView = (ViewGroup) currentView.getChildAt(i);
                    return checkAll(innerView);
                }
                else
                    {
                }
            }

        return true;
    }*/

    protected void setInputType(String type)
    {
        type_iet.setText(type);
    }

    protected void setOutputType(String type)
    {
        task_type_output_tv.setText(type);
        task_type_output_tv.setTextColor(ContextCompat.getColor(activity, EventType.getColor(type)));
    }

    protected void setOutputTypeImage(int type_image)
    {
        task_type_output_image_iv.setImageResource(type_image);
    }

    protected void setTaskType()
    {
        Bundle bundle = getArguments();
        String type = bundle != null ? bundle.getString(TASK_TYPE, TYPE_NOT_SET) : TYPE_NOT_SET;
        int type_image = bundle != null ? bundle.getInt(TASK_TYPE_IMAGE, TYPE_IMAGE_NOT_SET) : TYPE_IMAGE_NOT_SET;

        if (!type.equals(TYPE_NOT_SET))
        {
            setInputType(type);
            setOutputType(type);
            setOutputTypeImage(type_image);
        }
    }

    //TODO: Make it simpler(refactor).
    private void checkTypeInput(TextView textView, String text)
    {
        final View fragmentView = getView();
        TextInputLayout typeInputLayout = null;

        if(fragmentView != null)
        {
            typeInputLayout = (TextInputLayout) fragmentView.findViewById(R.id.task_type_input_layout);
        }

        boolean fullWord = false;

        if(checkIfTextEmpty(textView)) setInputError(false, typeInputLayout, getString(R.string.empty_error));
        else
            {
            for (EventType type : EventType.values())
            {
                if (text.toLowerCase().equals(type.toString().toLowerCase()))
                {
                    setInputError(true, typeInputLayout, getString(R.string.type_error));
                    task_type = type;
                    task_type_image = EventType.getImage(task_type.toString());

                    fullWord = true;
                    break;
                } else {
                    setInputError(false, typeInputLayout, getString(R.string.type_error));
                    task_type = EventType.NO_TYPE;
                    task_type_image = Constants.NO_TYPE_IMAGE;
                }
            }
            if (fullWord)
            {
                setOutputType(task_type.toString());
                setOutputTypeImage(task_type_image);
            }
        }
    }

    private void checkTypeOutput(String outputType)
    {
        EventType type = EventType.getName(outputType);
        boolean isSchedule = EventType.compare(type, EventType.SCHEDULE);
        checkTypeOutputAction(type, isSchedule);
    }

    //TODO: compare with task method. Do we need type parameter when we have task_type field?
    protected void taskTypeReplace(EventType type)
    {
        try
        {
           typePickListener = (OnTypePickListener) activity;
            replaceScheduleOrTask(type);
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString() + " must implement OnTypePickListener");
        }
    }

    protected TextValidator validator(TextView textView)
    {
        return new TextValidator(textView)
        {
            @Override
            public void validate(TextView textView, String text)
            {
                validateType(textView, text);
                AbstractAddingFragment.this.validate(textView, text);
            }
        };
    }

    private void validateType(TextView textView, String text)
    {
        switch (textView.getId())
        {
            case R.id.task_type:
                checkTypeOutput(text);
                break;

            case R.id.task_type_input:
                checkTypeInput(textView, text);
                break;
        }
    }

    private void initializeOutputTypeValidator()
    {
        outputTypeValidator = validator(task_type_output_tv);
    }

    private void addOutputTypeValidator()
    {
        task_type_output_tv.addTextChangedListener(outputTypeValidator);
    }

    protected void removeOutputTypeValidator()
    {
        task_type_output_tv.removeTextChangedListener(outputTypeValidator);
    }

    private void initializeInputTypeValidator()
    {
        inputTypeValidator = validator(type_iet);
    }

    private void addInputTypeValidator()
    {
        type_iet.addTextChangedListener(inputTypeValidator);
    }

    private void removeInputTypeValidator()
    {
        type_iet.removeTextChangedListener(inputTypeValidator);
    }

    private void initializeInputTypeListener()
    {
        inputTypeFocusChangeListener = new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean b)
            {
                if (!b)
                {
                    checkTypeInput(type_iet, type_iet.getText().toString());
                }
            }
        };
    }

    private void setInputTypeListener()
    {
        type_iet.setOnFocusChangeListener(inputTypeFocusChangeListener);
    }

    protected void removeInputTypeListener()
    {
        type_iet.setOnFocusChangeListener(null);
    }

    public void pickTypeDialog()
    {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, R.style.AppCompatAlertDialogStyle);
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.dialog_type_choice, null);
        dialogBuilder.setView(dialogView);

        dialogBuilder.setTitle(R.string.pick_type_dialog_title);

        final RadioGroup radioGroup = (RadioGroup) dialogView.findViewById(R.id.type_group);

        dialogBuilder.setPositiveButton(getString(R.string.dialog_ok), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
              onTypePickPositive(radioGroup);
            }
        });

        dialogBuilder.setNegativeButton(getString(R.string.dialog_cancel), new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                onTypePickNegative(dialog);
            }
        });

        final AlertDialog b = dialogBuilder.create();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
               onTypePickChanged(checkedId, b);
            }
        });

        b.show();
    }

    public void setTime(int hour, int minute, boolean am_pm, int type)
    {
        Calendar c = Calendar.getInstance();
        String myFormatTime;

        if (!am_pm)
        {
            myFormatTime = "hh:mm a";
        }
        else
        {
            myFormatTime = "kk:mm";
        }

        SimpleDateFormat stf = new SimpleDateFormat(myFormatTime, Locale.US);

        //TODO: when it's 00:12 or something, picker returns 24 hours.
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);

        setTimeOutput(type, stf, c);
    }

    public void setDate(int year, int month, int day)
    {
       setDateOutput(year, month, day);
    }

    protected abstract void onMenuItemClick(int menu_item_id);
    public abstract int getFragmentMenu();
    public abstract int getFragmentLayout();
    protected abstract void initializeViews(View fragmentView);
    protected abstract void initializeValidators();
    protected abstract void addValidators();
    protected abstract void validate(TextView textView, String text);
    protected abstract boolean isInputErrors();
    protected abstract void removeValidators();
    protected abstract void initializeListeners();
    protected abstract void setListeners();
    protected abstract void removeListeners();

    public abstract void addSubItem(View view);
    protected abstract void addSubTaskToDB();
    protected abstract void replaceScheduleOrTask(EventType type);
    protected abstract void checkTypeOutputAction(EventType type, boolean isSchedule);
    protected abstract void onTypePickPositive(RadioGroup radioGroup);
    protected abstract void onTypePickNegative(DialogInterface dialog);
    protected abstract void onTypePickChanged(int checkedId, AlertDialog b);
    protected abstract void setTimeOutput(int type, SimpleDateFormat output_format, Calendar calendar);
    protected abstract void setDateOutput(int year, int month, int day);


}
