package com.cwk2.appointments;
import android.database.Cursor;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.text.format.DateFormat;

/**
 * Created by DELL 5559 on 14/05/2017.
 */

public class Appointment implements Serializable{

    int id;
    String title;
    String date;
    String time;
    String description;




   public Appointment(int id,String title,String date,String time,String description){


       this.id = id;
       this.title = title;
       this.date = date;
       this.time = time;
       this.description = description;


    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public Date getTimeCompareable(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        Date TimeObj = null;

        try {
            TimeObj = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return TimeObj;

    }

}


//public Appointment fetchFromId(String id){
   //return id;
//}
