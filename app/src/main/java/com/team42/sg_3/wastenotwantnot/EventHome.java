package com.team42.sg_3.wastenotwantnot;

import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class EventHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ArrayList<String> productiveHours;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_home);
        productiveHours = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(EventHome.this, android.R.layout.simple_list_item_1, productiveHours);
        ListView lv = (ListView)findViewById(R.id.productiveList);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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

        //internalStorage is = new internalStorage(getApplicationContext());
        //ArrayList<Object[]> list = is.retrieveEvents();
        //Toast.makeText(EventHome.this, (String) list.get(0)[0], Toast.LENGTH_LONG).show();
        //is.clearEvents();



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == ProductiveActivity.RESULT_OK) {
                productiveHours.add(data.getStringExtra("times"));
                Toast.makeText(EventHome.this, "onActivityResult",Toast.LENGTH_LONG).show();
                adapter.notifyDataSetChanged();
            }
            if (resultCode == ProductiveActivity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    public void addEvent(View v){
        startActivity(new Intent(this, addEvent.class));
    }

    public void productiveHours(View v){
        startActivityForResult(new Intent(this, ProductiveActivity.class), 1);
    }

    public void weekView(View v){
        startActivity(new Intent(this, WeekActivity.class));
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
        getMenuInflater().inflate(R.menu.event_home, menu);
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
            case R.id.nav_home:
                finish();
                intent = new Intent(this, NavigationActivity.class);
                startActivity(intent);
                return true;
            case R.id.nav_calendar:
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
            case R.id.nav_app_blocker:
                intent = new Intent(this, AppBlockingActivity.class);
                startActivity(intent);
                return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
