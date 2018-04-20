package com.cwk2.appointments;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchAppointments extends AppCompatActivity {

    DatabaseHelper apoinDB;
    ArrayList<Appointment> appointments;
    CustomAdapter customAdapter;
    String searchPhrase;
    EditText edit_search;
    final GetAppointments getApps = new GetAppointments();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_appointments);

        //get components

        edit_search = (EditText)findViewById(R.id.edit_Search);
        ImageButton btnSearch = (ImageButton)findViewById(R.id.btnSearch);

        //database
        apoinDB = new DatabaseHelper(this);

        // toolbar

        Toolbar myToolbar = (Toolbar) findViewById(R.id.createApt_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Search Appointments");




        //data
        appointments = getApps.getAllappointments(apoinDB);


        if (appointments == null) { //IF THERE ARE NO APPOINTMENTS , TELL USER
            Toast.makeText(getApplicationContext(), "No appointments", Toast.LENGTH_SHORT).show();
        } else {
            viewData();

        }


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });


        edit_search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            search();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });




    }

    public void viewData(){



        ListView listView = (ListView)findViewById(R.id.lView);



        customAdapter = new CustomAdapter(this,appointments);



        listView.setAdapter(customAdapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Toast.makeText(getBaseContext(),appointments.get(position).getTitle() , Toast.LENGTH_SHORT).show();

                Intent i = new Intent(SearchAppointments.this,ViewAptDetails.class);
                i.putExtra("selected_appointment",appointments.get(position));
                startActivity(i);
            }
        });




    }


void search(){

    searchPhrase = edit_search.getText().toString();
    appointments = getApps.getAppointmentsWith(searchPhrase,apoinDB);

    if(appointments == null){
        Toast.makeText(getApplicationContext(), "No matching appointments!", Toast.LENGTH_SHORT).show();
    }

    else{
        viewData();
    }






}








}
