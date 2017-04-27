package com.team42.sg_3.wastenotwantnot;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by Cory on 4/25/2017.
 */

public class internalStorage {

    public internalStorage(){}

    public void inputEvent(String title, long start, long end){

        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("309.db", null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Events(Title STRING, Start text, End text);");
        Date sd = new Date(start);
        Date ed = new Date(end);

        String startDate = ""+sd.getYear()+"-"+sd.getMonth()+"-"+sd.getDay()+" "+sd.getHours()%24+":"+sd.getMinutes()%60+":"+sd.getSeconds()%60+".000";
        String endDate = ""+ed.getYear()+"-"+ed.getMonth()+"-"+ed.getDay()+" "+ed.getHours()%24+":"+ed.getMinutes()%60+":"+ed.getSeconds()%60+".000";

        db.execSQL("INSERT INTO Events Values "+ title + ", "+startDate+", " + endDate + ";");

        return;

    }

    public ArrayList<String[]> retrieveEvents(){
        ArrayList<String[]> array = new ArrayList<String[]>();
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("309.db", null);
        Cursor c = db.rawQuery("SELECT * FROM Events", null);
        c.moveToFirst();
        int i = 0;
        while(!c.isAfterLast()){
            String[] s = new String[3];
            s[0] = c.getString(c.getColumnIndex("Title"));
            s[1] = c.getString(c.getColumnIndex("Start"));
            s[2] = c.getString(c.getColumnIndex("End"));
            array.add(i, s);
            i++;
            c.moveToNext();
        }
        c.close();
        return array;
    }

    public void deleteEvent(String title, long start, long end){
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("309.db", null);

        Date sd = new Date(start);
        Date ed = new Date(end);

        String startDate = ""+sd.getYear()+"-"+sd.getMonth()+"-"+sd.getDay()+" "+sd.getHours()%24+":"+sd.getMinutes()%60+":"+sd.getSeconds()%60+".000";
        String endDate = ""+ed.getYear()+"-"+ed.getMonth()+"-"+ed.getDay()+" "+ed.getHours()%24+":"+ed.getMinutes()%60+":"+ed.getSeconds()%60+".000";

        db.execSQL("DELETE * FROM Events WHERE Title = " + title + " AND Start = " + startDate + " AND End = " + endDate);
    }

    public void cleanEvents(){
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("309.db", null);
        db.execSQL("DELETE * FROM Events WHERE julianday('now') > julianday(End)");
        return;
    }
}
