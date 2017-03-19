package com.team42.sg_3.wastenotwantnot;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * This class is here to encapsulate the Firebase ID Token refresh capture
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String token = "Registration Token";
    @Override
    /**
     * This class has one method to capture and store the devices Firebase ID Token
     */
    public void onTokenRefresh() {
        SharedPreferences.Editor editor = getSharedPreferences("firebase_info", MODE_PRIVATE).edit();
        editor.putString("reg_token", FirebaseInstanceId.getInstance().getToken());
        editor.commit();

        //since this happens on install and only once, this is a good place to do initial setup
        startService(new Intent(this, MyAppListener.class));
    }
}
