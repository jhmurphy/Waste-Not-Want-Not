package com.team42.sg_3.wastenotwantnot;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Carter on 3/4/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String token = "Registration Token";
    @Override
    public void onTokenRefresh() {
        SharedPreferences.Editor editor = getSharedPreferences("firebase_info", MODE_PRIVATE).edit();
        editor.putString("reg_token", FirebaseInstanceId.getInstance().getToken());
        editor.commit();
    }
}
