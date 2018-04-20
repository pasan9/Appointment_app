package com.cwk2.appointments;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MoveAppointment extends AppCompatActivity {

    DatabaseHelper apoinDB;
    CustomAdapter customAdapter;
    ArrayList<Appointment> appointments;
    String date;
    String moveDate;
    CalendarView Calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointments);

        //set move date to curent date , in case user sdoesnt click the calendar view
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        Date cdate = new Date();
        moveDate = sdf.format(cdate.getTime());



        setContentView(R.layout.activity_view_appointments);

        //get the date
        date = getIntent().getStringExtra("date");



        //set app bar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.createApt_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Move Appointments");

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
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                // custom dialog
                final Dialog dialog = new Dialog(parent.getContext());
                dialog.setContentView(R.layout.date_pick_dialog);
                dialog.setTitle("Date Picker");

                Calendar = (CalendarView) dialog.findViewById(R.id.calendar2);
                Button btnMove = (Button) dialog.findViewById(R.id.btnMove);
                Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btnMove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(date.equalsIgnoreCase(moveDate)){
                            Toast.makeText(getApplicationContext(), "You picked the same date!", Toast.LENGTH_SHORT).show();
                        }
                        else{

                            boolean Success = apoinDB.moveAppointment(appointments.get(position).getId(),moveDate,appointments.get(position).getTitle());

                            if(Success){
                                Toast.makeText(getApplicationContext(), "Appointment Moved!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                finish();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Simillar appointment exists on that date!", Toast.LENGTH_SHORT).show();
                            }

                        }



                    }
                });

                dialog.show();





                // Set action when a date is clicked
                Calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                        int monthCorrect = month+1;
                       moveDate = dayOfMonth+"/"+monthCorrect+"/"+year;

                        //Toast.makeText(getApplicationContext(), dayOfMonth+"/"+month+"/"+year+"", Toast.LENGTH_SHORT).show();
                    }
                });


                //Toast.makeText(getBaseContext(),appointments.get(position).getTitle() , Toast.LENGTH_SHORT).show();

                //Intent i = new Intent(ViewAppointments.this,ViewAptDetails.class);
                //i.putExtra("selected_appointment",appointments.get(position));
                //startActivity(i);
            }
        });




    }

















}
