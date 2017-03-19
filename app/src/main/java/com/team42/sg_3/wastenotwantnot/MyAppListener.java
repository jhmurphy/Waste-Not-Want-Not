package com.team42.sg_3.wastenotwantnot;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


/**
 * This is a custom service that will listen for the foreground apps and lock them.</br>
 * This is where our app locker functionality is embedded
 */
public class MyAppListener extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /**
     * initial setup of the service
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyAppListener", "Service Created");
    }

    /**
     * Starts the service and does the necessary work to initialize any custom information for the service
     * @param intent
     * @param flags
     * @param startId
     * @return whatever the supoer class returns
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyAppListener", "Service Started");
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * When the service stops, any information collected or needed for the service will be destroyed here
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyAppListener", "Service Destroyed");
    }
}


