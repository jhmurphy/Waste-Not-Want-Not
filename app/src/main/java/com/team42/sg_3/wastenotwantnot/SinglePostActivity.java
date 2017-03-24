package com.team42.sg_3.wastenotwantnot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class SinglePostActivity extends AppCompatActivity {

    private String selectedItem;
    private String Username;
    private String URL = "http://proj-309-sg-3.cs.iastate.edu/singlePost.php";
    private ArrayList<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_post);
        Intent intent = getIntent();
        selectedItem = intent.getExtras().getString("selected");
        URL += "?selected=" + selectedItem;
        SharedPreferences userDetails = getSharedPreferences("userDetails", MODE_PRIVATE);
        Username = userDetails.getString("username", "");
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
                        Toast.makeText(SinglePostActivity.this,response.toString(),Toast.LENGTH_LONG).show();
                        TextView tv = (TextView)findViewById(R.id.textView1);
                        tv.setText(items.get(0));
                        TextView tv2 = (TextView)findViewById(R.id.textView2);
                        tv2.setText(items.get(1));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SinglePostActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
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

}
