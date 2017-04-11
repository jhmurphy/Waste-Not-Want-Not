package com.team42.sg_3.wastenotwantnot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class newPostActivity extends AppCompatActivity {
    private EditText PostTitle;
    private EditText PostDesc;
    private String Username;
    private String selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        Intent intent = getIntent();
        selectedItem = intent.getExtras().getString("selected");
        PostTitle = (EditText) findViewById(R.id.postName);
        PostDesc = (EditText) findViewById(R.id.postDescription);
        Button SubmitButton = (Button) findViewById(R.id.submitPost);
        SubmitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    trySubmit();
                }
            });
        Username = getSharedPreferences("user_details", MODE_PRIVATE).getString("username", "username not found");
    }

    public void onSubmit(View view){
        trySubmit();
    }

    /**
     * Handles user submitting new thread
     */
    public void trySubmit(){

        final String postName = PostTitle.getText().toString().trim();
        final String postDescription = PostDesc.getText().toString();

        if(postName.equals("")){
            View focusView = PostTitle;
            PostTitle.setError("This field is required");
            focusView.requestFocus();
        }else{
            RequestQueue queue = Volley.newRequestQueue(this);
            String url ="http://proj-309-sg-3.cs.iastate.edu/createPost.php";
            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) throws JSONException {
                    if(response.equals("post successful")){
                        Toast.makeText(newPostActivity.this, response, Toast.LENGTH_LONG).show();
                        /*Back to posts*/
                        Intent intent = new Intent(newPostActivity.this, PostViewActivity.class);
                        intent.putExtra("selected", selectedItem);
                        startActivity(intent);
                    }else{
                        Toast.makeText(newPostActivity.this, response, Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error){
                    Toast.makeText(newPostActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                }
            }

            ){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String,String>();
                    params.put("name",postName);
                    params.put("description",postDescription);
                    params.put("user", Username);
                    params.put("thread", selectedItem);
                    return params;
                }
            };
            queue.add(request);
        }
    }

    public void onCancel(View view){
        Intent returnIntent = new Intent();
        setResult(newPostActivity.RESULT_CANCELED, returnIntent);
        finish();
    }
}


