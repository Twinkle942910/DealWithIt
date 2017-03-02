package com.example.twinkle94.dealwithit.util;

import android.content.Context;

import com.example.twinkle94.dealwithit.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateTimeValidator
{
    private Context context;

    private final String TIME12HOURS_PATTERN;
    private final String TIME24HOURS_PATTERN;
    private final boolean TIME_FORMAT;  //false - 12 hours, true - 24 hours.
    private final String DATE_PATTERN;

    private String timeErrorMessage;
    private String dateErrorMessage;

    public DateTimeValidator(Context context, boolean am_pm)
    {
        this.context = context;

        TIME12HOURS_PATTERN = context.getString(R.string.format_time_12h);
        TIME24HOURS_PATTERN = context.getString(R.string.format_time_24h);
        TIME_FORMAT = am_pm;
        DATE_PATTERN = context.getString(R.string.format_date);

        dateErrorMessage = context.getString(R.string.date_input_error);
    }

    public boolean validateDate(final String date)
    {
        return isMatching(date, DATE_PATTERN);
    }

    public boolean validateTime(final String time)
    {
        if(TIME_FORMAT)
        {
            timeErrorMessage = context.getString(R.string.time_input_error_24);
            return validate24Hours(time);
        }
        timeErrorMessage = context.getString(R.string.time_input_error_12);
        return validate12Hours(time);
    }

    public String timeErrorMessage()
    {
        return timeErrorMessage;
    }

    public String dateErrorMessage()
    {
        return dateErrorMessage;
    }

    private boolean validate12Hours(final String time)
    {
        return isMatching(time, TIME12HOURS_PATTERN);
    }

    private boolean validate24Hours(final String time)
    {
        return isMatching(time, TIME24HOURS_PATTERN);
    }

    private boolean isMatching(final String text, final String format_pattern)
    {
        Pattern pattern = Pattern.compile(format_pattern);
        Matcher matcher = pattern.matcher(text);

        return matcher.matches();
    }
}
