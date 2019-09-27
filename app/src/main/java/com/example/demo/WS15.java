package com.example.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.heimbsle69869.demo.R;

public class WS15 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ws15);
    }

    public void a1ButtonClicked(View view) {
        Intent intent = new Intent(this, WS15Activity2.class);
        startActivity(intent);
    }
}
