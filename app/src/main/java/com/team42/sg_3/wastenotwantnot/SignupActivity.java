package com.team42.sg_3.wastenotwantnot;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.test.ViewAsserts;
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

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class SignupActivity extends AppCompatActivity {

    // UI references.
    private EditText name;
    private EditText username;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        // Set up the login form.
        name = (EditText) findViewById(R.id.name);
        username = (EditText) findViewById(R.id.username);
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptSignup();
                    Intent navigationPage = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(navigationPage);
                    return true;
                }
                return false;
            }
        });

        Log.d("Shared Preferences", getSharedPreferences("firebase_info", MODE_PRIVATE).getString("reg_token", "token not found"));
    }

    /**
     * Signup.
     *
     * @param v the v
     */
    public void signup(View v){
        attemptSignup();
    }

    /**
     * Go to login.
     */
    public void goToLogin() {
        Intent loginPage = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(loginPage);
    }

    /**
     * Attempts to sign in or register the account specified by the signup form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptSignup() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        name.setError(null);
        username.setError(null);

        // Store values at the time of the login attempt.
        final String email = mEmailView.getText().toString().trim();
        final String password = mPasswordView.getText().toString().trim();
        final String nm = name.getText().toString().trim();
        final String un = username.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        /*
        These next two checks were added by Cory 2/23/17
         */

        // check for valid username
        if(TextUtils.isEmpty(un)){
            username.setError("This field is required");
            focusView = username;
            cancel = true;
        }

        // check for valid name
        if(TextUtils.isEmpty(nm)){
            name.setError("This field is required");
            focusView = name;
            cancel = true;
        }


        if (cancel) {
            focusView.requestFocus();
        } else {
            RequestQueue queue = Volley.newRequestQueue(this);
            final String url ="http://proj-309-sg-3.cs.iastate.edu/signup.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){
                @Override
                public void onResponse(String response){
                    if(response.equals("Signup Successful")) {
                        Toast.makeText(SignupActivity.this, response, Toast.LENGTH_LONG).show();
                        goToLogin();
                    }
                    else {
                        Toast.makeText(SignupActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error){
                    Toast.makeText(SignupActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String,String>();//name
                    params.put("username",un);//username
                    params.put("password",password);//password
                    params.put("email",email);//email
                    //need to signup and register this string to get push notifications
                    params.put("fcm_token", getSharedPreferences("firebase_info", MODE_PRIVATE).getString("reg_token", "token not found"));
                    return params;
                }
            };
            queue.add(stringRequest);

        }
    }

    private boolean hasDomain(String email) {
        if(!email.contains(".")) {
            return false;
        }
        String end = email.split("\\.")[1];
        switch(end) {
            case "net":
            case "edu":
            case "gov":
            case "co":
            case "de":
            case "ru":
            case "be":
            case "com":
                return true;
            default:
                return false;
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@") && email.split("@").length == 2 && hasDomain(email);
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 6;
    }
}

