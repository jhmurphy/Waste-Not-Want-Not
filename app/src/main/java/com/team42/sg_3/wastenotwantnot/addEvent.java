package com.team42.sg_3.wastenotwantnot;

import android.annotation.TargetApi;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by Cory on 3/23/2017.
 */

public class addEvent extends AppCompatActivity{
    private EditText name;
    private TimePicker start;
    private TimePicker end;
    private DatePicker day;
    //private View SubmitButton;

    @Override
    @TargetApi(23)
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        name = (EditText) findViewById(R.id.eventName);
        start = (TimePicker) findViewById(R.id.startTime);
        end = (TimePicker) findViewById(R.id.endTime);
        day = (DatePicker) findViewById(R.id.eventDate);

        end.setHour(start.getHour() + 1);
    }

    /**
     * Handles user submitting new event
     */
    @TargetApi(23)
    public void submitEvent(View view){
        String ename = name.getText().toString().trim();
        int startHour = start.getHour();
        int startMin = start.getMinute();
        int endHour = end.getHour();
        int endMin = end.getMinute();
        int mon = day.getMonth();
        int yr = day.getYear();
        int d = day.getDayOfMonth();
        long Smillis = 0;
        long Emillis = 0;

        Intent in = new Intent(Intent.ACTION_EDIT);
        in.setType("vnd.android.cursor.item/event");


        if(endHour < startHour || (endHour == startHour && endMin <= startMin)){
            Toast.makeText(addEvent.this, "End Time must be after Start Time", Toast.LENGTH_LONG).show();
            return;
        }else{
            //Turning start and end times into longs
            String StartToMillis = yr+"/"+mon+"/"+d+" "+startHour+":"+startMin+":00";
            String EndToMillis = yr+"/"+mon+"/"+d+" "+endHour+":"+endMin+":00";
            SimpleDateFormat s = new SimpleDateFormat(StartToMillis);
            SimpleDateFormat e = new SimpleDateFormat(EndToMillis);
            Date date1;
            Date date2;
            try {
                date1 = s.parse(StartToMillis);
                date2 = e.parse(EndToMillis);
                Smillis = date1.getTime();
                Emillis = date2.getTime();
            } catch (ParseException e1) {
                Toast.makeText(addEvent.this, "An error occurred", Toast.LENGTH_LONG).show();
                e1.printStackTrace();
            }
        }

        in.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, Smillis);
        in.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, Emillis);
        in.putExtra(CalendarContract.Events.TITLE, ename);


        startActivity(in);

    }

    /**
     * Back to calendar activity
     * @param view
     */
    public void back(View view){
        Intent in = new Intent(addEvent.this, EventHome.class);
        startActivity(in);
    }

}
