package com.cwk2.appointments;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ViewAptDetails extends AppCompatActivity {

    Appointment appointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_apt_details);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.viewDetails_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Appointment Details");

        appointment = (Appointment) getIntent().getSerializableExtra("selected_appointment");

        TextView titleLabel = (TextView)findViewById(R.id.lblTitle);
        TextView timeLabel = (TextView)findViewById(R.id.lblTime);
        TextView descriptionLabel = (TextView)findViewById(R.id.lblDesc);
        TextView dateLabel = (TextView)findViewById(R.id.lblDate);

        //set data to fields

         titleLabel.setText(appointment.getTitle());
         timeLabel.setText(timeLabel.getText()+ " "+ appointment.getTime());
         descriptionLabel.setText(appointment.getDescription());
         dateLabel.setText(dateLabel.getText()+ " "+ appointment.getDate());




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case R.id.editApt:
                //Toast.makeText(getApplicationContext(), "Create Appointment", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(ViewAptDetails.this,EditAppointment.class);
                i.putExtra("selected_appointment",appointment);
                startActivity(i);

                return true;



            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    public void displayDetails(){

    }


}
