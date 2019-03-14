package com.example.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.heimbsle69869.demo.R;

public class SchalterActivity extends AppCompatActivity {
    static int i = 0;
    Button schalter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schalter);

        schalter = findViewById(R.id.schalter);
        i=i+1;

        schalter.setText(Integer.toString(i));
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isFinishing())
        {
            i = i-1;
        }
    }

    public void schalterPressed(View view) {
        Intent intent = new Intent(getApplicationContext(), SchalterActivity.class);
        intent.putExtra("level", i);
        startActivity(intent);
    }

}
