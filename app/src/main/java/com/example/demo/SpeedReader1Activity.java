package com.example.demo;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.example.heimbsle69869.demo.R;

import java.io.InputStream;
import java.util.Scanner;

public class SpeedReader1Activity extends AppCompatActivity {
    private String text;
    private TextView multilineTextView;
    private TextView singleTextView;
    private static int wordCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_reader1);

        // Set wordCount
        wordCount = 0;

        // Get TextViews
        multilineTextView = findViewById(R.id.multilineTextView);
        singleTextView = findViewById(R.id.singleTextView);

        // Make multilineTextView scrollable
        multilineTextView.setMovementMethod(new ScrollingMovementMethod());

        // Get Asset Text
        text = readTextAsset("text.txt");
    }

    public void s1Clicked(View view) {
        multilineTextView.setText(text);
    }

    public void s2Clicked(View view) {
        multilineTextView.setText("");
        singleTextView.setText("");
        wordCount = 0;
    }

    public void s3Clicked(View view) {
        String[] words = text.split(" ");
        if (wordCount < words.length) {
            singleTextView.setText(words[wordCount]);
            wordCount++;
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

