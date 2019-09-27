package com.example.demo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.heimbsle69869.demo.R;

public class WS15 extends AppCompatActivity {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ws15);

        button = findViewById(R.id.a1Button);


    }

    public void a1ButtonClicked(View view) {
        Intent intent = new Intent(this, WS15Activity2.class);
        startActivityForResult(intent, 1);
    }

    /*@Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        int lastTb = intent.getIntExtra("last button", 0);
        if (lastTb != 0)
            button.setText(" " + lastTb + " ");
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("ActivityResult", resultCode + "");

        if (resultCode != 0)
            button.setText("" + resultCode);
        else
            button.setText("Kein Aktivierter Schalter");
    }
}
