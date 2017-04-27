package com.team42.sg_3.wastenotwantnot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class AlarmReceiverActivity extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent)
    {
        if(intent.getExtras().getString("start") != null && intent.getExtras().getString("start").equals("start")){
            Log.d("myTag", "Testing");
            context.startService(new Intent(context, MyAppListener.class));
        }
        else{
            context.stopService(new Intent(context, MyAppListener.class));
        }

    }

}
