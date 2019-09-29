/*package com.example.demo;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class ThreadHandler extends AsyncTask<Void, Integer, Void> {
    private static int counter = 0;
    private EditText editText;
    private TextView textView = null;

    ThreadHandler(EditText editText) {
        this.editText = editText;
    }

    public void setTextView(TextView tv) {
        this.textView = tv;
    }

    public void setEditText(EditText et) {
        this.editText = et;
    }

    public void nullTextView() { this.textView = null; }

    @Override
    protected Void doInBackground(Void... voids) {
        while (true) {
            if (isCancelled())
                break;
            counter++;
            publishProgress(counter);
            try {
                Long sleepTime = Long.parseLong(editText.getText().toString());
                Log.d("WS12", "Sleeptime: " + 1/sleepTime*1000);
                Thread.sleep(1/sleepTime*1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Log.d("WS12", "counter: " + counter);
    }
}*/