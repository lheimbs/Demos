package com.example.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;
import android.widget.ToggleButton;

import com.example.heimbsle69869.demo.R;

public class WS15Activity2 extends AppCompatActivity {
    ToggleButton tb1, tb2, tb3, tb4, tb5;
    SeekBar sb;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ws15_activity_2);

        tb1 = findViewById(R.id.toggleButton1);
        tb2 = findViewById(R.id.toggleButton2);
        tb3 = findViewById(R.id.toggleButton3);
        tb4 = findViewById(R.id.toggleButton4);
        tb5 = findViewById(R.id.toggleButton5);
        sb = findViewById(R.id.seekBar);
    }
}
