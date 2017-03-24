package com.team42.sg_3.wastenotwantnot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

/**
 * The main homepage for the users event and calendar information
 */
public class CalendarActivity extends AppCompatActivity {

    /**
     * The Calendar.
     */
    CalendarView calendar;
    Button addEvent;
    /**
     * Not much now, but this is in place to setup the
     * necessary information on a new instance of this activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        addEvent = (Button) findViewById(R.id.add);
        calendar = (CalendarView) findViewById(R.id.calendar);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(getApplicationContext(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void addEvent(View view){
        Intent in = new Intent(CalendarActivity.this, addEvent.class);
        startActivity(in);
    }

}
