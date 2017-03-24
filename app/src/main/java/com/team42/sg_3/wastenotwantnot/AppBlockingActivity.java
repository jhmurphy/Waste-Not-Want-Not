package com.team42.sg_3.wastenotwantnot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ToggleButton;

public class AppBlockingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_blocking);
        AppUsageStatistics.updateMostUsedApps(this);
        String[] menu = {"test1", "test2", AppUsageStatistics.getForegroundApp(this)};
        ListAdapter menuAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, AppUsageStatistics.getMostUsedApps());
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(menuAdapter);
        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleService(isChecked);
            }
        });
    }

    private void toggleService(boolean isChecked) {
        if(isChecked) {
            startService(new Intent(this, MyAppListener.class));
        }
        else {
            stopService(new Intent(this, MyAppListener.class));
        }

    }



}
