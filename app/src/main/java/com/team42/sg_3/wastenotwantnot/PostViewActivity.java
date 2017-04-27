package com.team42.sg_3.wastenotwantnot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostViewActivity extends AppCompatActivity {

    private String Username;
    private ArrayList<String> items;
    private String item;
    private String selectedItem;
    private String URL = "http://proj-309-sg-3.cs.iastate.edu/viewPost.php";
    public static final String KEY_USERNAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_view);
        Intent intent = getIntent();
        selectedItem = intent.getExtras().getString("selected");
        URL += "?selected=" + selectedItem;
        SharedPreferences userDetails = getSharedPreferences("user_details", MODE_PRIVATE);
        Username = userDetails.getString("username", "");
        Log.d("postview debug", "on create");
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("postview debug", "got response");
                        items = new ArrayList<String>();
                        for(int i=0; i < response.length(); i++){
                            try {
                                items.add(response.getString(i));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.d("postview debug", "json response array" + items.toString());
                        //Toast.makeText(PostViewActivity.this,response.toString(),Toast.LENGTH_LONG).show();
                        String[] stringArray = new String[items.size()];
                        items.toArray(stringArray);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PostViewActivity.this, android.R.layout.simple_list_item_1, stringArray);
                        ListView lv = (ListView)findViewById(R.id.listView);
                        lv.setAdapter(adapter);

                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position,
                                                    long id) {

                                item = ((TextView)view).getText().toString();
                                Intent intent = new Intent(PostViewActivity.this, SinglePostActivity.class);
                                intent.putExtra("selected", item);
                                startActivity(intent);

                            }
                        });
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PostViewActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_USERNAME,Username);
                params.put("thread", selectedItem);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);



        if (AppUsageStatistics.getUsageStatsList(this).isEmpty()){
            Intent intent1 = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivity(intent1);
        }

    }

    public void goToNewPost(View view){
        Intent intent = new Intent(PostViewActivity.this, newPostActivity.class);
        intent.putExtra("selected", selectedItem);
        startActivityForResult(intent, 1);
    }

}
