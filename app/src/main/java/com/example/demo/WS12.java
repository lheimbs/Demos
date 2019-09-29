package com.example.demo;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.heimbsle69869.demo.R;

public class WS12 extends AppCompatActivity {
    public TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ws12);

        textView = findViewById(R.id.threadTextView);
        textView.setText("Zähler");
    }

    public void threadButtonClicked(View view) {
        Intent intent = new Intent(this, WS12_thread.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("WS12Info", "ReqCode: " + requestCode + " ResCode: " + resultCode);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                int res = data.getIntExtra("RES", 0);
                //Log.d("WS12Info", "result: " + data.getData().toString());
                Log.d("WS12Info", "RES: " + res);
                //textView.setText(data.getData().toString());
            }
            else
                Log.d("WS12Info", "Wrong Result Code");
        }
        else
            Log.d("WS12Info", "Wrong Request Code");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
