package com.example.twinkle94.dealwithit.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Time format that match (for TIME12HOURS_PATTERN):
 1. “1:00am”, “1:00 am”,”1:00 AM” ,
 2. “1:00pm”, “1:00 pm”, “1:00 PM”,
 3. “12:50 pm”

 Time format doesn’t match (for TIME12HOURS_PATTERN):
 1. “0:00 am” – hour is out of range [1-12]
 2. “10:00 am” – only one white space is allow
 3. “1:00” – must end with am or pm
 4. “23:00 am” -24-hour format is not allow
 5. “1:61 pm” – minute is out of range [0-59]
 6. “13:00 pm” – hour is out of range [1-12]
 7. “001:50 pm” – invalid hour format
 8. “10:99 am” – minute is out of range [0-59]
 9. “01:00 pm” – 24-hour format is not allow
 10. “1:00 bm” – must end with am or pm






 Time format that match (for TIME24HOURS_PATTERN):
 1. “01:00”, “02:00”, “13:00”,
 2. “1:00”, “2:00”, “13:01”,
 3. “23:59″,”15:00”
 4. “00:00″,”0:00”

 Time format doesn’t match (for TIME24HOURS_PATTERN):
 1. “24:00” – hour is out of range [0-23]
 2. “12:60” – minute is out of range [00-59]
 3. “0:0” – invalid format for minute, at least 2 digits
 4. “13:1” – invalid format for minute, at least 2 digits
 5. “101:00” – hour is out of range [0-23]






 Date format that match:
 1. “1/1/2010” , “01/01/2020”
 2. “31/1/2010”, “31/01/2020”
 3. “29/2/2008”, “29/02/2008”
 4. “28/2/2009”, “28/02/2009”
 5. “31/3/2010”, “31/03/2010”
 6. “30/4/2010”, “30/04/2010”
 7. “31/5/2010”, “31/05/2010”
 8. “30/6/2010”, “30/06/2010”
 9. “31/7/2010”, “31/07/2010”
 10. “31/8/2010”, “31/08/2010”
 11. “30/9/2010”, “30/09/2010”
 12. “31/10/2010”, “31/10/2010”
 13. “30/11/2010”, “30/11/2010”
 14. “31/12/2010”, “31/12/2010”

 Date format doesn’t match:
 1. “32/1/2010” , “32/01/2020” – day is out of range [1-31]
 2. “1/13/2010”, “01/01/1820” – month is out of range [1-12], year is out of range [1900-2999]
 3. “29/2/2007”, “29/02/2007” – 2007 is not leap year, only has 28 days
 4. “30/2/2008”, “31/02/2008” – leap year in February has 29 days only
 5. “29/a/2008”, “a/02/2008” – month is invalid, day is invalid
 6. “333/2/2008”, “29/02/200a” – day is invalid, year is invalid
 7. “31/4/20100”, “31/04/2010” – April has 30 days only
 8. “31/6/2010”, “31/06/2010” -June has 30 days only
 9. “31/9/2010”, “31/09/2010” – September has 30 days only
 10. “31/11/2010” – November has 30 days only
 */

public class DateTimeValidator
{
    private static final String TIME12HOURS_PATTERN = "(1[012]|0?[1-9]):[0-5][0-9](\\s)?(?i)(am|pm)";
    private static final String TIME24HOURS_PATTERN = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
    private static final String DATE_PATTERN = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";

    private static String timeErrorMessage;
    private static final String dateErrorMessage = "Wrong input! Your date should be in DD/MM/YYYY format";

    public DateTimeValidator(boolean is24Hours) {
        if(is24Hours)
        {
            timeErrorMessage = "Wrong input! Your time should be in HH:MM format";

        }
        else timeErrorMessage = "Wrong input! Your time should be in HH:MM AM|PM format";
    }

    public static boolean validateDate(final String date)
    {
        return isMatching(date, DATE_PATTERN);
    }

    public static boolean validateTime(final boolean is24Hours, final String time)
    {
        if(is24Hours)
        {
            timeErrorMessage = "Wrong input! Your time should be in HH:MM format";
            return validate24Hours(time);
        }
        timeErrorMessage = "Wrong input! Your time should be in HH:MM AM|PM format";
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

    private static boolean validate12Hours(final String time)
    {
        return isMatching(time, TIME12HOURS_PATTERN);
    }

    private static boolean validate24Hours(final String time)
    {
        return isMatching(time, TIME24HOURS_PATTERN);
    }

    private static boolean isMatching(final String text, final String format_pattern)
    {
        Pattern pattern = Pattern.compile(format_pattern);
        Matcher matcher = pattern.matcher(text);

        return matcher.matches();
    }
}
