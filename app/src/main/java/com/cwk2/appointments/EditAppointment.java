package com.cwk2.appointments;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * Created by DELL 5559 on 15/05/2017.
 */

public class EditAppointment extends AppCompatActivity {

    DatabaseHelper apoinDB;
    Appointment appointment;
    Button saveBtn;
    EditText Title;
    EditText Time;
    EditText Description;
    String Date;
    TimePicker time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appointment);

        apoinDB = new DatabaseHelper(this);



        //getting components
        Title = (EditText)findViewById(R.id.aptTitle);
        saveBtn = (Button)findViewById(R.id.saveBtn);
        time = (TimePicker)findViewById(R.id.timePicker1);
        time.setIs24HourView(true);// setting time picker to 24 hour picker
        //Time = (EditText)findViewById(R.id.aptTime);
        Description = (EditText)findViewById(R.id.aptDescription);
        appointment = (Appointment) getIntent().getSerializableExtra("selected_appointment");



        Toolbar myToolbar = (Toolbar) findViewById(R.id.createApt_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Edit Appointment");


        setValues();

        save();









    }


    public void setValues(){

        //getting hour and minute
        String[] strArray = appointment.getTime().split(":");
        int hour = Integer.parseInt(strArray[0]);
        int minute = Integer.parseInt(strArray[1]);

        Title.setText(appointment.getTitle());
        Description.setText(appointment.getDescription());
        time.setCurrentHour(hour);
        time.setCurrentMinute(minute);
    }


    public void save(){

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Time = timeCheck(time.getCurrentHour())+":"+timeCheck(time.getCurrentMinute());

                boolean isInserted = apoinDB.updateAppointment(//inserting data into database
                        appointment.getId(),
                        Title.getText().toString(),
                        appointment.getDate(),
                        Time,
                        //Time.getText().toString(),
                        Description.getText().toString()
                );

                if(isInserted) {
                    Toast.makeText(getApplicationContext(), "Saved Changes", Toast.LENGTH_SHORT).show();
                }
                //System.out.println(isInserted);
                else{
                    Toast.makeText(getApplicationContext(), "Simillar Appointment Exists!", Toast.LENGTH_SHORT).show();
                }


                //startActivity(new Intent(CreateAppointment.this,Play.class));
            }
        });

    }




    public String timeCheck(int i){

        String timelet;

        timelet =""+i;

        if(i<10){
            timelet = "0"+i;
        }

        return timelet;

    }

}
