package com.team42.sg_3.wastenotwantnot;

import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.usage.UsageEvents;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.provider.Settings;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Used for enumerating usage statistics for the app
 */

//Any code commented out can possibly be used later for necessary functionality
public class AppUsageStatistics {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("M-d-yyyy HH:mm:ss");
    public static final String TAG = AppUsageStatistics.class.getSimpleName();
    /*private static String currentApp = "";
    private static String[] mostUsedApps = new String[10];*/
    @SuppressWarnings("ResourceType")
    @TargetApi(22)
    public static void getStats(Context context){
        UsageStatsManager usm = (UsageStatsManager) context.getSystemService("usagestats");
        int interval = UsageStatsManager.INTERVAL_DAILY;
        Calendar calendar = Calendar.getInstance();
        long endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.DATE, -1);
        long startTime = calendar.getTimeInMillis();

        Log.d(TAG, "Range start:" + dateFormat.format(startTime) );
        Log.d(TAG, "Range end:" + dateFormat.format(endTime));

        UsageEvents uEvents = usm.queryEvents(startTime,endTime);
        while (uEvents.hasNextEvent()){
            UsageEvents.Event e = new UsageEvents.Event();
            uEvents.getNextEvent(e);

            if (e != null){
                Log.d(TAG, "Event: " + e.getPackageName() + "\t" +  e.getTimeStamp());
            }
        }
    }

    /*@TargetApi(22)
    private static String getNextHighest(List<UsageStats> usageStatsList) {
        UsageStats highest = usageStatsList.get(0);
        for(int i=0; i<usageStatsList.size(); i++) {

        }

        return highest.getPackageName();

    }

    private static void getMostUsedApps(List<UsageStats> usageStatsList) {
        for(int i=0; i<mostUsedApps.length; i++) {
            mostUsedApps[i] = getNextHighest(usageStatsList);
        }
    }*/

    /**
     * Gets the UsasgeStats of  apps that have been used in a certain period
     * @param context
     * @return list of UsageStats
     */
    @TargetApi(22)
    public static List<UsageStats> getUsageStatsList(Context context){
        UsageStatsManager usm = getUsageStatsManager(context);
        Calendar calendar = Calendar.getInstance();
        long endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.DATE, -7);
        long startTime = calendar.getTimeInMillis();

        Log.d(TAG, "Range start:" + dateFormat.format(startTime) );
        Log.d(TAG, "Range end:" + dateFormat.format(endTime));

        List<UsageStats> usageStatsList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,startTime,endTime);
        List<UsageStats> ret = usageStatsList;

        //getMostUsedApps(usageStatsList);

        return ret;
    }

    /*public void printMostUsed() {
        for (String app : mostUsedApps){
            Log.d(TAG, "Pkg: " + app) ;
        }
    }*/

    /**
     * Will log the apps that have been used and how much time they've been used
     * @param usageStatsList
     */
    @TargetApi(22)
    public static void printUsageStats(List<UsageStats> usageStatsList){
        for (UsageStats u : usageStatsList){
            Log.d(TAG, "Pkg: " + u.getPackageName() +  "\t" + "ForegroundTime: "
                    + u.getTotalTimeInForeground()) ;
        }
    }

    /**
     *  Prints in logcat the current usage of apps on the device
     * @param context
     */
    public static void printCurrentUsageStatus(Context context){
        printUsageStats(getUsageStatsList(context));
    }

    @SuppressWarnings("ResourceType")
    /**
     * gets the UsageStatsManager object to obtain the necessary statistics
     */
    private static UsageStatsManager getUsageStatsManager(Context context){
        return (UsageStatsManager) context.getSystemService("usagestats");
    }

    /**
     * Finds the foreground app and returns the package name as a string
     * @param context
     * @return package name
     */
    public static String getForegroundApp(Context context) {
        String current = "NULL";
        TreeMap<Long, UsageStats> mySortedMap;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager usm = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
            long time = System.currentTimeMillis();
            List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1000, time);
            if (appList != null && appList.size() > 0) {
                mySortedMap = new TreeMap<Long, UsageStats>();
                for (UsageStats usageStats : appList) {
                    mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                }
                if (mySortedMap != null && !mySortedMap.isEmpty()) {
                    current = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                }
            }
        } else {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> tasks = am.getRunningAppProcesses();
            current = tasks.get(0).processName;
        }

        return current;
    }

    //functionality will be used later for getting the foreground app in the app listener service
   /* public static void onAccessEvent(Context context) {
        final Context c = context;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                String foreground = getForegroundApp(c);
                if(!currentApp.equals(foreground)) {
                    Log.d(TAG, "Current App has changed to " + foreground);
                    currentApp = foreground;
                }
            }
        }, 0, 1000);
    }*/


}

