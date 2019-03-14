package com.example.demo;

import android.os.AsyncTask;
import android.widget.TextView;

public class WordsFrequencyClass extends AsyncTask<Double, String, Long> {
    private static int counter = 0;
    private final TextView singleTextView;
    private final String[] text;

    WordsFrequencyClass(TextView singleTextView, String[] text) {
        this.singleTextView = singleTextView;
        this.text = text;
    }

    void resetCounter() {
        counter = 0;
    }

    @Override
    protected Long doInBackground(Double... params) {
        Double frequency = (params.length>0) ? (1/params[0])*1000 : 0;

        while (counter < text.length) {
            if (isCancelled()) {
                break;
            }
            else {
                publishProgress(text[counter]);
                counter++;

                try {
                    Thread.sleep(frequency.longValue());
                } catch (InterruptedException e) { e.printStackTrace(); }
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        singleTextView.setText(values[0]);
    }

    @Override
    protected void onPostExecute(Long aLong) {
        super.onPostExecute(aLong);
        singleTextView.setText("");
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        //singleTextView.setText("");
    }
}