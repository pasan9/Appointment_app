package com.cwk2.appointments;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DeleteAppointment extends AppCompatActivity {

    DatabaseHelper apoinDB;
    ArrayList<Appointment> appointments;
    CustomAdapter customAdapter;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLayout();

        //date
        date = getIntent().getStringExtra("date");

        //database
        apoinDB = new DatabaseHelper(this);

        // new get appointments object
        GetAppointments getApps = new GetAppointments();


        //data
        appointments = getApps.getAllappointmentsByDate(apoinDB,date);


        if (appointments == null) { //IF THERE ARE NO APPOINTMENTS , TELL USER
            Toast.makeText(getApplicationContext(), "No appointments", Toast.LENGTH_SHORT).show();
        } else {
            viewData();

        }


    }


    public void viewData() {


        ListView listView = (ListView) findViewById(R.id.lView);

        customAdapter = new CustomAdapter(this,appointments);

        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final int i = position;


                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteAppointment.this);
                builder.setTitle("Delete Alert");
                builder.setMessage("Do you want to delete event \"" + appointments.get(position).getTitle() + "\"?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean success = apoinDB.deleteFromId(appointments.get(i).getId());

                        if (success) {
                            Toast.makeText(getBaseContext(), "Appointment Deleted", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getBaseContext(), "Failed to delete Appointment", Toast.LENGTH_SHORT).show();
                        }

                        refreshAppointments();

                    }

                });

                builder.setNegativeButton("No", null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


                //Toast.makeText(getBaseContext(),appointments.get(position).getTitle() , Toast.LENGTH_SHORT).show();


                //Intent i = new Intent(ViewAppointments.this,ViewAptDetails.class);
                //i.putExtra("selected_appointment",appointments.get(position));
                //startActivity(i);
            }
        });


    }






    void refreshAppointments(){
        // new get appointments object
        GetAppointments getApps = new GetAppointments();


        //data
        appointments = getApps.getAllappointmentsByDate(apoinDB,date);

        if (appointments == null) { //IF THERE ARE NO APPOINTMENTS , TELL USER

            Toast.makeText(getApplicationContext(), "No appointments", Toast.LENGTH_SHORT).show();
            setLayout();
        } else {

            viewData();

        }
    }


    void setLayout(){
        setContentView(R.layout.activity_view_appointments);

        //set app bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.createApt_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Delete Appointment");
    }

}






