package com.example.demo;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends ListActivity {
    private String PACKAGE_NAME;
    ArrayList<String> demos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(this.getClass().getSimpleName());
        PACKAGE_NAME = this.getPackageName();
        demos = new ArrayList<>();

        try {
            PackageManager pm = this.getPackageManager();
            PackageInfo info = pm.getPackageInfo(getApplicationContext().getPackageName(), PackageManager.GET_ACTIVITIES);
            for (ActivityInfo activityInfo : info.activities) {
                String name = activityInfo.name.replace(PACKAGE_NAME + ".", "");
                if (!name.equals(MainActivity.class.getSimpleName()) && !name.equals(WS15Activity2.class.getSimpleName()) && !name.equals(SS13_Sideactivity.class.getSimpleName()))
                    demos.add(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, demos);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView list, View view, int position, long id) {
        super.onListItemClick(list, view, position, id);
        String name = demos.get(position);

        try {
            Class<?> c = Class.forName(PACKAGE_NAME + "." + name);
            Intent intent = new Intent(this, c);
            startActivity(intent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
