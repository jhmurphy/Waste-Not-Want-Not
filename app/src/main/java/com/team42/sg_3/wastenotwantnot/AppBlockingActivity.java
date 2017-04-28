package com.team42.sg_3.wastenotwantnot;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class AppBlockingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_blocking);
        AppUsageStatistics.updateMostUsedApps(this);
        String[] menu = {AppUsageStatistics.getForegroundApp(this)};
        ListAdapter menuAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, AppUsageStatistics.getMostUsedApps());
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(menuAdapter);

        listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                AppUsageStatistics.toggleDisable(position);
                if(AppUsageStatistics.getTheDisabledApps()[position]) {
                    view.setBackgroundColor(Color.GREEN);
                }else{
                    view.setBackgroundColor(Color.WHITE);
                }
            }
        });

        //listView.setAdapter(new myListAdapter(this, android.R.layout.simple_list_item_1, AppUsageStatistics.getMostUsedApps()));
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

