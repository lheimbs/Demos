package com.example.demo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.heimbsle69869.demo.R;

public class SS13_Sideactivity extends AppCompatActivity {
    static int buttonPresses = 0;
    Button button;
    TextView textView2;
    Logger logger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ss13_side);
        setTitle("Nebenaktivität");

        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this);

        button = findViewById(R.id.button2);
        button.setText("Button clicked " + buttonPresses + " times.");
        textView2 = findViewById(R.id.textView2);
        logger = new Logger("Nebenaktivität", textView2, "");
    }

    public void button2Clicked(View view) {
        buttonPresses++;
        logger.log("Button wurde " + buttonPresses + " mal gedrückt.");
        button.setText("Button clicked " + buttonPresses + " times.");
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
        if (!loggedText.isEmpty())
            logger.addPrevLog(loggedText);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = p.edit();
        editor.putString("LOGGED_TEXT", logger.getLoggedText());
        editor.apply();
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}