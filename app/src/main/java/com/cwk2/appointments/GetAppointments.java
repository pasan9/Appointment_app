package com.cwk2.appointments;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by DELL 5559 on 15/05/2017.
 */

public class GetAppointments {


    //ArrayList<Appointment> appointments;


    public ArrayList<Appointment> getAllappointments(DatabaseHelper apoinDB) {


        Cursor result = apoinDB.getAlldata();

        if (result.getCount() == 0) {

            //Toast.makeText(getApplicationContext(), "No Appointments", Toast.LENGTH_SHORT).show();

            return null;


        } else {

            //Toast.makeText(getApplicationContext(), "Got Appointments", Toast.LENGTH_SHORT).show();

            ArrayList<Appointment> appointments = new ArrayList<Appointment>();

            while (result.moveToNext()) {
                Appointment appointment = new Appointment(
                        Integer.parseInt(result.getString(0)),
                        result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4)
                );

                appointments.add(appointment);

            }

            //sort in time

            Collections.sort(appointments, new CustomTimeComparator());

            return appointments;

        }

    }



    public ArrayList<Appointment> getAllappointmentsByDate(DatabaseHelper apoinDB,String date) {


        Cursor result = apoinDB.getAppointmentsByDate(date);

        if (result.getCount() == 0) {

            //Toast.makeText(getApplicationContext(), "No Appointments", Toast.LENGTH_SHORT).show();

            return null;


        } else {

            //Toast.makeText(getApplicationContext(), "Got Appointments", Toast.LENGTH_SHORT).show();

            ArrayList<Appointment> appointments = new ArrayList<Appointment>();

            while (result.moveToNext()) {
                Appointment appointment = new Appointment(
                        Integer.parseInt(result.getString(0)),
                        result.getString(1),
                        result.getString(2),
                        result.getString(3),
                        result.getString(4)
                );

                appointments.add(appointment);

            }

            //sort in time

            Collections.sort(appointments, new CustomTimeComparator());

            return appointments;

        }

    }

    public ArrayList<Appointment> getAppointmentsWith(String phrase,DatabaseHelper apoinDB){

        ArrayList<Appointment> appointments = getAllappointments(apoinDB);
        ArrayList<Appointment> positiveAppointments = new ArrayList<Appointment>();

        for(Appointment singleAppointment:appointments){

            boolean statement = singleAppointment.getTitle().contains(phrase)||singleAppointment.getDescription().contains(phrase);//checking if the phrase is in description or title

            if(statement){

                positiveAppointments.add(singleAppointment);// if true add, to a new array list

            }

        }



        if(positiveAppointments.size()>0) {

            Collections.sort(positiveAppointments, new CustomTimeComparator());//sort
            return positiveAppointments;
        }
        else{
            return null;//if nothing is found return null
        }

    }







    class CustomTimeComparator implements Comparator<Appointment> {


        @Override
        public int compare(Appointment o1, Appointment o2) {

            return (o1.getTimeCompareable()).compareTo(o2.getTimeCompareable());
        }
    }

}



