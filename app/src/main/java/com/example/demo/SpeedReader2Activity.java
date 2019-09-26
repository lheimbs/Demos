package com.example.demo;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.heimbsle69869.demo.R;

import java.io.InputStream;
import java.util.Locale;
import java.util.Scanner;

public class SpeedReader2Activity extends AppCompatActivity {
    private String text;
    private double frequency = 1;
    private TextView multilineTextView;
    private TextView singleTextView;
    private TextView frequencyDisplay;
    private SeekBar frequencySeekBar;
    private ToggleButton toggleButton;
    private WordsFrequencyClass wordFreq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_reader2);

        // Get Views
        multilineTextView = findViewById(R.id.multilineTextView);
        singleTextView = findViewById(R.id.singleTextView);
        frequencyDisplay = findViewById(R.id.frequencyDisplay);
        frequencySeekBar = findViewById(R.id.frequencySeekBar);
        toggleButton = findViewById(R.id.wordsToggleButton);

        // Make multilineTextView scrollable
        multilineTextView.setMovementMethod(new ScrollingMovementMethod());

        // Get Asset Text
        text = readTextAsset("text.txt");

        // On Seekbar change listener
        frequencySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int xPos = (progress * (frequencySeekBar.getWidth() - 2 * frequencySeekBar.getThumbOffset())) / frequencySeekBar.getMax();
                frequency = (progress < 50 ? (0.018 * (progress+5.55556)) : (9*progress-400)/50);
                frequencyDisplay.setText(String.format(Locale.GERMAN,"%.1f", frequency));
                frequencyDisplay.setX(frequencySeekBar.getX() + xPos + frequencySeekBar.getThumbOffset() / 2);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (toggleButton.isChecked()) {
                    if (wordFreq != null) {
                        wordFreq.cancel(true);
                        wordFreq = null;
                    }
                    wordFreq = new WordsFrequencyClass(singleTextView, text.split(" "));
                    wordFreq.execute(frequency);
                }
            }
        });

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    wordFreq = new WordsFrequencyClass(singleTextView, text.split(" "));
                    wordFreq.execute(frequency);
                }
                else {
                    if (wordFreq != null) {
                        wordFreq.cancel(true);
                        wordFreq = null;
                    }
                }
            }
        });
    }

    public void s1Clicked(View view) {
        multilineTextView.setText(text);
    }

    public void s2Clicked(View view) {
        multilineTextView.setText("");
        singleTextView.setText("");
        toggleButton.setChecked(false);

        if (wordFreq == null) {
            wordFreq = new WordsFrequencyClass(null, null);
            wordFreq.resetCounter();
            wordFreq = null;
        }
        else {
            wordFreq.resetCounter();
            wordFreq.cancel(true);
            wordFreq = null;
        }
    }

    private String readTextAsset(String asset) {
        AssetManager manager = getAssets();
        String text;
        try {
            InputStream in = manager.open(asset, AssetManager.ACCESS_RANDOM);
            Scanner s = new Scanner(in, "UTF8").useDelimiter("\\x04");
            text = s.hasNext() ? s.next() : "";
        } catch (Exception e) {
            text = "";
            e.printStackTrace();
        }
        return text;
    }
}