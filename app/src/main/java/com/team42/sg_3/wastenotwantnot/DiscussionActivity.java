package com.team42.sg_3.wastenotwantnot;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * The main home page for the Discussion Board module of the app
 */
public class DiscussionActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String URL = "http://proj-309-sg-3.cs.iastate.edu/DiscussionBoard.php";
    /**
     * The constant KEY_USERNAME.
     */
    public static final String KEY_USERNAME = "username";
    /**
     * The constant KEY_POST.
     */
    public static final String KEY_POST = "post";

    private String Username;
    private JSONArray jsonArray;

    private Button buttonRegister;


    /**
     * @param savedInstanceState
     * This sets up the instance of this activity
     */
    @Override
    @TargetApi(22)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);

        //editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        //editTextPost = (EditText) findViewById(R.id.editTextPost);

        //buttonRegister = (Button) findViewById(R.id.buttonSubmit);

        //buttonRegister.setOnClickListener(this);
        sendPost();
        String[] stringArray = {"Thread1", "Thread2"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, stringArray);
        ListView lv = (ListView)findViewById(R.id.listView);
        lv.setAdapter(adapter);
        SharedPreferences userDetails = getSharedPreferences("userDetails", MODE_PRIVATE);
        Username = userDetails.getString("username", "");


        if (AppUsageStatistics.getUsageStatsList(this).isEmpty()){
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent);
        }

    }

    /**
     * Sends the username and the post text to the server to store into the database
     */
    private void sendPost() {
        final String username = Username;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        jsonArray = response;
                        Toast.makeText(DiscussionActivity.this,response.toString(),Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DiscussionActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_USERNAME,username);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    /**
     * Called by the button to start the process of sending the post to the server
     * @param v current view
     */
    @Override
    @TargetApi(22)
    public void onClick(View v) {

    }

    /**
     * Takes user to create a new thread
     */
    public void goToNewThread(View view){
        Intent intent = new Intent(DiscussionActivity.this, newThreadActivity.class);
        startActivity(intent);
    }


}
