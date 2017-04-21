package com.example.twinkle94.dealwithit.util;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SnackBarMessage
{
    private Snackbar snackbar;

    public SnackBarMessage(ViewGroup layout, String message, int duration)
    {
        snackbar = Snackbar.make(layout, message, duration);
    }

    public void action(String actionMessage, View.OnClickListener action)
    {
        snackbar.setAction(actionMessage,action);
    }

    public void actionTextColor(int color)
    {
        snackbar.setActionTextColor(color);
    }

    public void messageColor(int color)
    {
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
    }

    public void messageLinesCount(int lines)
    {
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(lines);
    }

    public void show()
    {
        snackbar.show();
    }
}
