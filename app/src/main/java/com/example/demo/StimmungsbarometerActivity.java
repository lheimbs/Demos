package com.example.demo;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.heimbsle69869.demo.R;

public class StimmungsbarometerActivity extends AppCompatActivity {
    ListView moodList;
    static String lastChosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stimmungsbarometer);

        moodList = findViewById(R.id.moodList);

        String[] moodStringList = new String[] {
                "Wut", "Glücklich", "Böse", "Traurig"
        };

        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, moodStringList);
        moodList.setAdapter(adapter);

        moodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lastChosen = moodList.getItemAtPosition(position).toString();
                Toast.makeText(StimmungsbarometerActivity.this, lastChosen, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = p.edit();
        editor.putString("lastChosen", lastChosen);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(this);

        lastChosen = p.getString("lastChosen", "");
        if (!lastChosen.equals("")) {
            Toast.makeText(StimmungsbarometerActivity.this, lastChosen, Toast.LENGTH_SHORT).show();
        }
    }
}
