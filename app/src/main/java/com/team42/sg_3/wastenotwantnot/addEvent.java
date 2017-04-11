package com.team42.sg_3.wastenotwantnot;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.provider.CalendarContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.READ_CONTACTS;


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
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        name = (EditText) findViewById(R.id.eventName);
        start = (TimePicker) findViewById(R.id.startTime);
        end = (TimePicker) findViewById(R.id.endTime);
        day = (DatePicker) findViewById(R.id.eventDate);

        //end.setHour(start.getHour() + 1);
    }

    /**
     * Handles user submitting new event
     */
    public void submitEvent(){
        String ename = name.getText().toString().trim();
        int startHour = start.getCurrentHour();
        int startMin = start.getCurrentMinute();
        int endHour = end.getCurrentHour();
        int endMin = end.getCurrentMinute();
        int mon = day.getMonth();
        int yr = day.getYear();
        int d = day.getDayOfMonth();
        long Smillis = 0;
        long Emillis = 0;

        Intent in = new Intent(Intent.ACTION_INSERT_OR_EDIT);
        in.setType("vnd.android.cursor.item/event");

        startActivity(in);
        
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

    }

    /**
     * Back to calendar activity
     * @param view
     */
    public void back(View view){
        Intent in = new Intent(addEvent.this, CalendarActivity.class);
        startActivity(in);
    }

}
