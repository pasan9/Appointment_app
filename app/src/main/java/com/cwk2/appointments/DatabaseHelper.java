package com.cwk2.appointments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by DELL 5559 on 10/04/2017.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "apt_db.db";
    public static final String TABLE_NAME = "apt_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "TITLE";
    public static final String COL_3 = "DATE";
    public static final String COL_4 = "TIME";
    public static final String COL_5 = "DESCRIPTION";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+TABLE_NAME+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,TITLE TEXT,DATE TEXT,TIME TEXT,DESCRIPTION TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);

    }

    public boolean insertData (String title,String date,String time,String description){

            if(!checkForSimillar(title, date)) {

                SQLiteDatabase aptdb = this.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(COL_2, title);
                contentValues.put(COL_3, date);
                contentValues.put(COL_4, time);
                contentValues.put(COL_5, description);

                long result = aptdb.insert(TABLE_NAME, null, contentValues);

                if (result != -1) {

                    return true;
                } else {

                    return false;
                }

            }

            else{

                //Log.d("me app","Simillar ones")
                return false;
            }


    }

    public boolean checkForSimillar(String title,String date){

        SQLiteDatabase aptdb = this.getWritableDatabase();
        Cursor result = aptdb.rawQuery("SELECT*FROM "+TABLE_NAME+" WHERE "+COL_2+" = "+"'"+title+"'"+" COLLATE NOCASE "+" AND "+COL_3+" = "+"'"+date+"'",null);



        if (result.getCount()==0){

            return false;
        }

        else{

            return true;
        }



    }


    public boolean checkForSimillarforEdit(String title,String date,int ID){

        SQLiteDatabase aptdb = this.getWritableDatabase();
        Cursor result = aptdb.rawQuery("SELECT*FROM "+TABLE_NAME+" WHERE "+COL_2+" = "+"'"+title+"'"+" COLLATE NOCASE "+" AND "+COL_3+" = "+"'"+date+"'"+" AND "+COL_1+" != "+"'"+ID+"'" ,null);



        if (result.getCount()==0){

            return false;
        }

        else{

            return true;
        }



    }


    public Cursor getAlldata(){
        SQLiteDatabase aptdb = this.getWritableDatabase();
        Cursor result = aptdb.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return result;
    }

    public Cursor getAppointmentsByDate(String date){

        SQLiteDatabase aptdb = this.getWritableDatabase();
        Cursor result = aptdb.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+COL_3+" = "+"'"+date+"'",null);

        return result;

    }


    public boolean moveAppointment(int ID,String moveDate,String title){

        if(!checkForSimillar(title,moveDate)){

            SQLiteDatabase aptdb = this.getWritableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_3, moveDate);


            aptdb.update(TABLE_NAME,contentValues,COL_1+"="+ID,null);




            return true;
        }

        else{
            return false;
        }


    }



    public boolean deleteAll(){

        SQLiteDatabase aptdb = this.getWritableDatabase();

         aptdb.execSQL("DELETE FROM "+ TABLE_NAME);

        return true;


    }



    public boolean deleteFromDate(String date){

        SQLiteDatabase aptdb = this.getWritableDatabase();

        long result = aptdb.delete(TABLE_NAME,COL_3+"="+"'"+date+"'",null);

        if (result != -1) {

            return true;
        } else {

            return false;
        }




    }









    public boolean updateAppointment(int ID,String title,String date,String time,String description){

        if(!checkForSimillarforEdit(title, date,ID)){

            //Log.d(title,date+"--------------------------------------------------");

            SQLiteDatabase aptdb = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_2, title);
            contentValues.put(COL_4, time);
            contentValues.put(COL_5, description);

            aptdb.update(TABLE_NAME,contentValues,COL_1+"="+ID,null);



            return true;
        }

        else{
            return false;
        }

    }


    public boolean deleteFromId(int ID){

        SQLiteDatabase aptdb = this.getWritableDatabase();

        long result = aptdb.delete(TABLE_NAME,COL_1+"="+ID,null);

        if (result != -1) {

            return true;
        } else {

            return false;
        }




    }





}
