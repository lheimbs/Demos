package com.example.demo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.heimbsle69869.demo.R;

public class SS13 extends AppCompatActivity {
    Logger logger;
    TextView textView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ss13);
        setTitle("Hauptaktivität");

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        button.setText("Gehe zur Nebenaktivität");
        logger = new Logger("Hauptaktivität", textView, "");
        //logger.log("onCreate()");
    }

    public void buttonClicked(View view) {
        logger.log("onClick \"Gehe zur Nebenaktivität\".");
        Intent intent = new Intent(this, SS13_Sideactivity.class);
        //intent.putExtra("LOGGED_TEXT", logger.getLoggedText());
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = p.edit();
        editor.putString("LOGGED_TEXT", logger.getLoggedText());
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this);
        String loggedText = p.getString("LOGGED_TEXT", "");
        if (!loggedText.isEmpty()) {
            logger.clearLog();
            logger.setNewLog(loggedText);
        }
        logger.log("onCreate()");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this);
        String loggedText = p.getString("LOGGED_TEXT", "");
        if (!loggedText.isEmpty()) {
            logger.clearLog();
            logger.setNewLog(loggedText);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        logger.clearLog();
        SS13_Sideactivity.buttonPresses=0;
    }
}
