package com.example.demo;

import android.util.Log;
import android.widget.TextView;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Logger
{
    private TextView textView;
    private StringBuffer sb = new StringBuffer();
    private String tag;

    public Logger(String tag, TextView textView, String logInitText)
    {
        Log.d(tag, "Logger.Constructer");
        this.tag = tag;
        this.textView = textView;
        if (!logInitText.isEmpty())
            sb.append(tag + ": " + logInitText);
    }

    public void log(String s)
    {
        Log.d(tag, s);
        sb.append(tag + ": " + s).append("\n");
        if (textView != null)
        {
            textView.setText(sb.toString());
        }
    }

    public void log(Exception e)
    {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        Log.d(tag, "log(" + sw.toString() + ")");
        log(tag + ": " + sw.toString());
    }

    public void clearLog()
    {
        Log.d(tag, "ClearLog()");
        sb.setLength(0);
        if (textView != null)
        {
            textView.setText("");
        }
    }

    public void setNewLog(String newText) {
        Log.d(tag, "ClearLog()");
        sb.setLength(0);
        sb.append(newText);
        if (textView != null)
        {
            textView.setText(sb.toString());
        }
    }

    public void addPrevLog(String newLog) {
        Log.d(tag, "ClearLog()");
        if (!this.getLoggedText().isEmpty())
            sb.insert(getLoggedText().length(), newLog);
        else
            sb.append(newLog);
        if (textView != null)
        {
            textView.setText(sb.toString());
        }
    }

    public String getLoggedText()
    {
        Log.d(tag, "getLoggedText()");
        return sb.toString();
    }
}
