package com.example.demo;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.icu.text.LocaleDisplayNames;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.heimbsle69869.demo.R;

import java.lang.ref.WeakReference;

public class WS12_thread extends AppCompatActivity {
    public ThreadHandler threader;
    public static int counter = 0;

    private CheckBox checkBox;
    private EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ws12_thread);
        setTitle("Thread-Steuerung");
        Log.d("WS12Info", "thread onCreate");

        editText = findViewById(R.id.editText);
        checkBox = findViewById(R.id.threadCheckBox);
        threader = new ThreadHandler(editText);

        editText.setText("10");
        Log.d("WS12Info", "EditText: " + editText.getText());
        checkBox.setChecked(false);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("WS12Info", "Checkbox: " + isChecked);
                if (isChecked) {
                    Log.d("WS12Info", "Checkbox Checked");
                    threader.execute();
                }
                else {
                    Log.d("WS12Info", "Checkbox Unchecked");
                    threader.cancel(true);
                    threader = new ThreadHandler(editText);
                }
            }
        });
        /*checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    threader.execute();
                    Log.d("WS12Info", "Checkbox Checked");
                }
                else {
                    threader.cancel(true);
                    Log.d("WS12Info", "Checkbox unchecked");
                }
            }
        });*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("WS12Info", "thread onResume");
        editText = findViewById(R.id.editText);
        checkBox = findViewById(R.id.threadCheckBox);
        checkBox.setChecked(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("WS12Info", "thread onPause");
        threader.cancel(true);
    }

    @Override
    public void onBackPressed() {
        Log.d("WS12Info", "thread onBackPressed");
        Intent intent = new Intent();
        intent.putExtra("RES", counter);
        //intent.setData(Uri.parse(String.valueOf(counter)));
        setResult(RESULT_OK, intent);
        finish();
        super.onBackPressed();
    }

    class ThreadHandler extends AsyncTask<Void, Integer, Void> {
        EditText editText;

        ThreadHandler(EditText editText) {
            Log.d("WS12Info", "ThreadHandler()");
            this.editText = editText;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            while (true) {
                if (isCancelled())
                    break;
                counter++;
                publishProgress(counter);

                try {
                    Long sleepTime = 1/Long.parseLong(editText.getText().toString())*1000;
                    Log.d("WS12Info", "SleepTime: " + sleepTime);
                    Thread.sleep(sleepTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.d("WS12Info", "counter: " + counter);
        }
    }
}
