package com.team42.sg_3.wastenotwantnot;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ProductiveActivity extends AppCompatActivity {

    private TimePicker start;
    private TimePicker end;

    @Override
    @TargetApi(23)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productive);
        start = (TimePicker) findViewById(R.id.startTime);
        end = (TimePicker) findViewById(R.id.endTime);
        end.setHour(start.getHour() + 1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @TargetApi(23)
    public void submitTime(View v){
        int startHour = start.getHour();
        int startMin = start.getMinute();
        int endHour = end.getHour();
        int endMin = end.getMinute();

        if(endHour < startHour || (endHour == startHour && endMin <= startMin)) {
            Toast.makeText(ProductiveActivity.this, "End Time must be after Start Time", Toast.LENGTH_LONG).show();
            return;
        }

        Calendar cur_cal = new GregorianCalendar();
        cur_cal.setTimeInMillis(System.currentTimeMillis());//set the current time and date for this calendar

        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_YEAR, cur_cal.get(Calendar.DAY_OF_YEAR));
        cal.set(Calendar.HOUR_OF_DAY, startHour);
        cal.set(Calendar.MINUTE, startMin);
        cal.set(Calendar.SECOND, cur_cal.get(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cur_cal.get(Calendar.MILLISECOND));
        cal.set(Calendar.DATE, cur_cal.get(Calendar.DATE));
        cal.set(Calendar.MONTH, cur_cal.get(Calendar.MONTH));

        Calendar calEnd = new GregorianCalendar();
        calEnd.add(Calendar.DAY_OF_YEAR, cur_cal.get(Calendar.DAY_OF_YEAR));
        calEnd.set(Calendar.HOUR_OF_DAY, endHour);
        calEnd.set(Calendar.MINUTE, endMin);
        calEnd.set(Calendar.SECOND, cur_cal.get(Calendar.SECOND));
        calEnd.set(Calendar.MILLISECOND, cur_cal.get(Calendar.MILLISECOND));
        calEnd.set(Calendar.DATE, cur_cal.get(Calendar.DATE));
        calEnd.set(Calendar.MONTH, cur_cal.get(Calendar.MONTH));

        Intent intentStartAlarm = new Intent(ProductiveActivity.this, AlarmReceiverActivity.class);
        intentStartAlarm.putExtra("start", "start");
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //set the alarm for particular start time
        alarmManager.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(), PendingIntent.getBroadcast(this,1,  intentStartAlarm, PendingIntent.FLAG_UPDATE_CURRENT));

        //Intent intentEndAlarm = new Intent(ProductiveActivity.this, AlarmReceiverActivity.class);
        //intentEndAlarm.putExtra("end", "end");
        //AlarmManager alarmEndManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //set the alarm for particular end time
        //alarmEndManager.set(AlarmManager.RTC_WAKEUP,calEnd.getTimeInMillis(), PendingIntent.getBroadcast(this,1,  intentEndAlarm, PendingIntent.FLAG_UPDATE_CURRENT));

        Toast.makeText(ProductiveActivity.this, "Productive Period Set", Toast.LENGTH_LONG).show();
        Intent in = new Intent(ProductiveActivity.this, EventHome.class);
        startActivity(in);
    }

    public void cancel(View v){
        Intent in = new Intent(ProductiveActivity.this, EventHome.class);
        startActivity(in);
    }

}
