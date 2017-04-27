package com.team42.sg_3.wastenotwantnot;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by Cory on 4/25/2017.
 */

public class internalStorage {

    private Context context;

    public internalStorage(Context con){
        context = con;
    }

    /**
     * Puts event into sqlite table events
     * @param title
     * @param start
     * @param end
     * @return
     */
    public String inputEvent(String title, long start, long end){

        SQLiteDatabase db = context.openOrCreateDatabase("309WNWN", Context.MODE_PRIVATE, null);

        db.execSQL("CREATE TABLE IF NOT EXISTS Events(Title STRING, Start long, End long);");

        db.execSQL("INSERT INTO Events(Title, Start, End) VALUES('"+ title + "', '"+start+"', '" + end + "');");

        String s = "Event "+title + " Added for "+ millisToDate(start) + " to " + millisToDate(end);

        return s;

    }

    /**
     * returns sorted arraylist of object arrays. for each array, 1st item is string, 2nd and 3rd are longs
     * @return
     */
    public ArrayList<Object[]> retrieveEvents(){
        ArrayList<Object[]> array = new ArrayList<Object[]>();
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("309WNWN", null);
        Cursor c = db.rawQuery("SELECT * FROM Events", null);
        c.moveToFirst();
        int i = 0;
        while(!c.isAfterLast()){
            Object[] s = new Object[3];
            s[0] = c.getString(c.getColumnIndex("Title"));
            s[1] = c.getLong(c.getColumnIndex("Start"));
            s[2] = c.getLong(c.getColumnIndex("End"));
            array.add(i, s);
            i++;
            c.moveToNext();
        }
        c.close();
        sort(array);
        return array;
    }

    private ArrayList<Object[]> sort(ArrayList<Object[]> toSort){
        ArrayList<Object[]> sorted = toSort;
        Object[] temp;
        int i, j;
        for(i = 0; i < sorted.size(); i++){
            for(j = i + 1; j < sorted.size(); j++){
                if((long)sorted.get(i)[1] > (long)sorted.get(j)[1]){
                    temp = sorted.get(i);
                    sorted.get(i)[0] = sorted.get(j)[0];
                    sorted.get(i)[1] = sorted.get(j)[1];
                    sorted.get(i)[2] = sorted.get(j)[2];
                    sorted.get(j)[0] = temp[0];
                    sorted.get(j)[1] = temp[1];
                    sorted.get(j)[2] = temp[2];
                }
            }
        }
        return sorted;
    }

    /**
     * returns string of date represented by long
     * @param m
     * @return
     */
    public String millisToDate(long m){
        Date date = new Date(m);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        String text = sdf.format(date);
        return text;
    }

    /**
     * deletes specefied event
     * @param title
     * @param start
     * @param end
     */
    public void deleteEvent(String title, long start, long end){
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("309WNWN", null);

        db.execSQL("DELETE FROM Events WHERE Title = " + title + " AND Start = " + start + " AND End = " + end);
    }

    /**
     * deletes all events that have ended
     */
    public void cleanEvents(){
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase("309WNWN", null);
        db.execSQL("DELETE FROM Events WHERE End < " + System.currentTimeMillis() + ";");
        return;
    }
}
