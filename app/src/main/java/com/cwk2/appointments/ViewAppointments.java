package com.cwk2.appointments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
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

public class ViewAppointments extends AppCompatActivity {

    TextView Display;
    DatabaseHelper apoinDB;
    CustomAdapter customAdapter;
    ArrayList<Appointment> appointments;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointments);

        //get the date
        date = getIntent().getStringExtra("date");



        //set app bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.createApt_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("View Appointments");

        GetAppointments getApps = new GetAppointments();// new get appointments object



        //database
        apoinDB = new DatabaseHelper(this);


        //data
        appointments = getApps.getAllappointmentsByDate(apoinDB,date);



        //set other components
        //Display = (TextView)findViewById(R.id.displayView);



        if(appointments==null){
            Toast.makeText(getApplicationContext(), "No appointments", Toast.LENGTH_SHORT).show();
        }
        else {
            viewData();

        }



    }




    public void viewData(){



        ListView listView = (ListView)findViewById(R.id.lView);



        customAdapter = new CustomAdapter(this,appointments);



        listView.setAdapter(customAdapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Toast.makeText(getBaseContext(),appointments.get(position).getTitle() , Toast.LENGTH_SHORT).show();

                Intent i = new Intent(ViewAppointments.this,ViewAptDetails.class);
                i.putExtra("selected_appointment",appointments.get(position));
                startActivity(i);
            }
        });




    }




}
