package com.team42.sg_3.wastenotwantnot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

public class DiscussionHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView =  navigationView.getHeaderView(0);
        TextView username  = (TextView) hView.findViewById(R.id.username);
        username.setText(getSharedPreferences("user_details", MODE_PRIVATE).getString("username", "username not found"));
        TextView email  = (TextView) hView.findViewById(R.id.email);
        email.setText(getSharedPreferences("user_details", MODE_PRIVATE).getString("email", "email not found"));

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
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(DiscussionHome.this, android.R.layout.simple_list_item_1, stringArray);
                        ListView lv = (ListView)findViewById(R.id.listView);
                        lv.setAdapter(adapter);

                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position,
                                                    long id) {

                                String item = ((TextView)view).getText().toString();

                                //Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(DiscussionHome.this, PostViewActivity.class);
                                intent.putExtra("selected", item);
                                startActivity(intent);

                            }
                        });
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DiscussionHome.this,error.toString(),Toast.LENGTH_LONG).show();
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.discussion_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent;
        switch(item.getItemId()){
            case R.id.nav_calendar:
                finish();
                intent = new Intent(this, EventHome.class);
                startActivity(intent);
                return true;
            case R.id.nav_discussion:
                finish();
                intent = new Intent(this, DiscussionHome.class);
                startActivity(intent);
                return true;
            case R.id.nav_settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_manage:
                intent = new Intent(this, AppBlockingActivity.class);
                startActivity(intent);
                return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Takes user to create a new thread
     */
    public void goToNewThread(View view){
        Intent intent = new Intent(this, newThreadActivity.class);
        startActivity(intent);
    }
}
