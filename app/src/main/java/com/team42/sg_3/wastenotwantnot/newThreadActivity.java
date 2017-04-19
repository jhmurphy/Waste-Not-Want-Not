package com.team42.sg_3.wastenotwantnot;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
import android.widget.EditText;
import android.widget.TextView;
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


/**
 * Created by Cory on 3/7/2017.
 *
 * Handles code on the New Thread page
 */

public class newThreadActivity extends AppCompatActivity{
    private EditText ThreadTitle;
    private EditText ThreadDesc;
    private String Username;
    //private View SubmitButton;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newthread);

        ThreadTitle = (EditText) findViewById(R.id.threadName);
        ThreadDesc = (EditText) findViewById(R.id.threadDescription);
        Button SubmitButton = (Button) findViewById(R.id.submitThread);
        SubmitButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                trySubmit();
            }
        });
        SharedPreferences userDetails = getSharedPreferences("userDetails", MODE_PRIVATE);
        Username = userDetails.getString("username", "");
    }

    public void onSubmit(View view){
        trySubmit();
    }

    /**
     * Handles user submitting new thread
     */
    public void trySubmit(){

        final String threadName = ThreadTitle.getText().toString().trim();
        final String threadDescription = ThreadDesc.getText().toString();

        if(threadName.equals("")){
            View focusView = ThreadTitle;
            ThreadTitle.setError("This field is required");
            focusView.requestFocus();
        }else{
            RequestQueue queue = Volley.newRequestQueue(this);
            String url ="http://proj-309-sg-3.cs.iastate.edu/createThread.php";
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) throws JSONException {
                    if(response.equals("thread successful")){
                        Toast.makeText(newThreadActivity.this, response, Toast.LENGTH_LONG).show();
                        /*Back to threads*/
                        Intent discussionPage = new Intent(newThreadActivity.this, DiscussionHome.class);
                        startActivity(discussionPage);
                    }else{
                        Toast.makeText(newThreadActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error){
                    Toast.makeText(newThreadActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                }
            }

            ){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String,String>();
                    params.put("name",threadName);
                    params.put("description",threadDescription);
                    params.put("user", Username);
                    return params;
                }
            };
            queue.add(request);
        }
    }

    public void onCancel(View view){
        Intent in = new Intent(newThreadActivity.this, DiscussionHome.class);
        startActivity(in);
    }

}
