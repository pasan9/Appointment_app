package com.cwk2.appointments;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button btnCreate,btnView,btnDelete,btnMove,btnSearch;
    DatabaseHelper apoinDB;
    CalendarView Calendar;

    String date ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //db object
        apoinDB = new DatabaseHelper(this);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.appointment_toolbar);
        setSupportActionBar(myToolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);//remove app title from toolbar

        getSupportActionBar().setTitle("  Appointments");//set app bar title

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_event_black_24dp);//set icon to toolbar




        //implement calendar view
        Calendar = (CalendarView) findViewById(R.id.calendar);

        //getSetdate

        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        Date cdate = new Date();
        date = sdf.format(cdate.getTime());



        // Set action when a date is clicked
        Calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                int monthCorrect = month+1;
                date = dayOfMonth+"/"+monthCorrect+"/"+year;

                //Toast.makeText(getApplicationContext(), dayOfMonth+"/"+month+"/"+year+"", Toast.LENGTH_SHORT).show();
            }
        });


        //add all buttons from layout
        btnCreate = (Button)findViewById(R.id.btnCreate);
        btnView = (Button)findViewById(R.id.btnView);
        btnDelete = (Button)findViewById(R.id.btnDelete);
        btnMove = (Button)findViewById(R.id.btnMove);
        btnSearch = (Button)findViewById(R.id.btnSearch);


        //OnClickListeners for buttons

        //Create Appointment button
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,CreateAppointment.class);
                i.putExtra("date",date);//pass the date
                startActivity(i);

            }
        });

        //View button
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent i = new Intent(MainActivity.this,ViewAppointments.class);
                i.putExtra("date",date);//pass the date
                startActivity(i);

            }
        });

        //Delete button
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Delete Alert");
                builder.setMessage("Do you want to delete all events on "+date+" or delete a specific event?");
                builder.setPositiveButton("All Events", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean success = apoinDB.deleteFromDate(date);

                        if (success) {
                            Toast.makeText(getBaseContext(), "Appointments Deleted!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getBaseContext(), "Failed to delete Appointment", Toast.LENGTH_SHORT).show();
                        }



                    }

                });

                builder.setNegativeButton("Choose Event", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent i = new Intent(MainActivity.this,DeleteAppointment.class);
                        i.putExtra("date",date);//pass the date
                        startActivity(i);

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();










            }
        });

        //Move button
        btnMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,MoveAppointment.class);
                i.putExtra("date",date);//pass the date
                startActivity(i);

            }
        });


        //Search button
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,SearchAppointments.class);

                startActivity(i);

            }
        });




    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case R.id.createApt:
                //Toast.makeText(getApplicationContext(), "Create Appointment", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this,CreateAppointment.class);
                i.putExtra("date",date);
                startActivity(i);

                return true;

            case R.id.editApt:

                startActivity(new Intent(MainActivity.this,ViewAppointments.class));

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }*/
}
