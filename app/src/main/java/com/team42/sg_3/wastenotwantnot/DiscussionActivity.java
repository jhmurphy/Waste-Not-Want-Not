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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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
public class DiscussionActivity extends AppCompatActivity {

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
    private ArrayList<String> items;

    private Button buttonRegister;


    /**
     * @param savedInstanceState
     * This sets up the instance of this activity
     */
    @Override
    @TargetApi(23)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);

        SharedPreferences userDetails = getSharedPreferences("user_details", MODE_PRIVATE);
        Username = userDetails.getString(KEY_USERNAME, "");
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        items = new ArrayList<String>();
                        for(int i=0; i < response.length(); i++){
                            try {
                                items.add(response.getString(i));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        //Toast.makeText(DiscussionActivity.this,response.toString(),Toast.LENGTH_LONG).show();
                        String[] stringArray = new String[items.size()];
                        items.toArray(stringArray);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DiscussionActivity.this, android.R.layout.simple_list_item_1, stringArray);
                        ListView lv = (ListView)findViewById(R.id.listView);
                        lv.setAdapter(adapter);

                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position,
                                                    long id) {

                                String item = ((TextView)view).getText().toString();

                                //Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(DiscussionActivity.this, PostViewActivity.class);
                                intent.putExtra("selected", item);
                                startActivity(intent);

                            }
                        });
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
                params.put(KEY_USERNAME,Username);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

    }

    /**
     * Takes user to create a new thread
     */
    public void goToNewThread(View view){
        Intent intent = new Intent(DiscussionActivity.this, newThreadActivity.class);
        startActivity(intent);
    }


}
