package com.team42.sg_3.wastenotwantnot;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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

import java.util.ArrayList;
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
     * Handles user submitting new thread
     */
    public void submitEvent(){

    }

    public void back(View view){
        Intent in = new Intent(addEvent.this, DiscussionActivity.class);
        startActivity(in);
    }

}
