package com.team42.sg_3.wastenotwantnot;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

    private EditText editTextUsername;
    private EditText editTextPost;

    private Button buttonRegister;


    /**
     * @param savedInstanceState
     * This sets up the instance of this activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPost = (EditText) findViewById(R.id.editTextPost);

        buttonRegister = (Button) findViewById(R.id.buttonSubmit);

        buttonRegister.setOnClickListener(this);

    }

    /**
     * Sends the username and the post text to the server to store into the database
     */
    private void sendPost() {
        final String username = editTextUsername.getText().toString().trim();
        final String post = editTextPost.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(DiscussionActivity.this,response,Toast.LENGTH_LONG).show();
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
                params.put(KEY_POST,post);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    /**
     * Called by the button to start the process of sending the post to the server
     * @param v current view
     */
    @Override
    public void onClick(View v) {
        sendPost();
    }


}
