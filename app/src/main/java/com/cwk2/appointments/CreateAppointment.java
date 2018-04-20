package com.cwk2.appointments;


import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
//import org.json.simple.*;

public class CreateAppointment extends AppCompatActivity {

    DatabaseHelper apoinDB;

    Button saveBtn;
    EditText Title;
    EditText Time;
    EditText Description;
    String Date;
    TimePicker time;
    EditText thesaurusTxt;
    Button BtnSearchThesaurus;
    Button BtnSearchThesaurus2;
    String JSON_STRING;
    String JSON_RESULT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appointment);
        apoinDB = new DatabaseHelper(this);
        //getting date from main activity
        Date = getIntent().getExtras().getString("date");

        //getting components
        Title = (EditText)findViewById(R.id.aptTitle);
        saveBtn = (Button)findViewById(R.id.saveBtn);
        time = (TimePicker)findViewById(R.id.timePicker1);
        time.setIs24HourView(true);// setting time picker to 24 hour picker
        //Time = (EditText)findViewById(R.id.aptTime);
        Description = (EditText)findViewById(R.id.aptDescription);

        //thesaurus
       /* thesaurusTxt = (EditText)findViewById(R.id.thesaurusTxt);
        BtnSearchThesaurus = (Button)findViewById(R.id.searchThesaurus);
        BtnSearchThesaurus2 = (Button)findViewById(R.id.searchThesaurus2);*/



        Toolbar myToolbar = (Toolbar) findViewById(R.id.createApt_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Create Appointment");





        save();


      /*  BtnSearchThesaurus2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BackgroundTask().execute();
            }
        });*/





    }

    public void save(){

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(Title.getText()!=null){

                String Time = timeCheck(time.getCurrentHour()) + ":" + timeCheck(time.getCurrentMinute());


                boolean isInserted = apoinDB.insertData(//inserting data into database
                        Title.getText().toString(),
                        Date,
                        Time,
                        //Time.getText().toString(),
                        Description.getText().toString()
                );

                if (isInserted) {
                    Toast.makeText(getApplicationContext(), "Appointment Saved", Toast.LENGTH_SHORT).show();
                    finish();
                }
                //System.out.println(isInserted);
                else {
                    Toast.makeText(getApplicationContext(), "Simillar Appointment Exists!", Toast.LENGTH_SHORT).show();
                }

            }

            else{
                    Toast.makeText(getApplicationContext(), "The title can not be empty!", Toast.LENGTH_SHORT).show();
                }

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




    //Get JSON file

    /*class BackgroundTask extends AsyncTask<Void,Void,String>{

        String json_url;

        @Override
        protected void onPreExecute() {
            Log.d("COmes","Comes Here1");
            json_url = "http://thesaurus.altervista.org/thesaurus/v1?word=help&language=en_US&key=h3ppuIB1RH1z69425hJP&output=json";
        }

        @Override
        protected String doInBackground(Void... params) {


            try {

                //Log.d("COmes","Comes Here");
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                //read
                while((JSON_STRING = bufferedReader.readLine())!=null){

                    stringBuilder.append(JSON_STRING+"\n");
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                Log.d("COmes Result",stringBuilder.toString().trim());
                return stringBuilder.toString().trim();


            }
            catch(MalformedURLException e){
                e.printStackTrace();
            }
            catch(IOException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            JSON_RESULT = result;

            displaySimillarwords();


        }
    }


    void displaySimillarwords(){

        //new BackgroundTask().execute();

        //getJSONlist();

        View v = LayoutInflater.from(CreateAppointment.this).inflate(R.layout.activity_thesaurus,null);
        TextView text = (TextView)v.findViewById(R.id.textViewThes);


        AlertDialog.Builder builder = new AlertDialog.Builder(CreateAppointment.this);

        builder.setMessage("Simillar words")
                .setView(v)
                .setNegativeButton("Cancel",null);



        text.setText(JSON_RESULT);

        AlertDialog alert = builder.create();





        alert.show();




    }


    *//*void getJSONlist(){

        try {
            JSONObject obj = (JSONObject) JSONValue.parse(JSON_RESULT);
            JSONArray array = new JSONArray(jsonObject.getJSONArray("response"));

            for (int i=0; i < array.size(); i++) {
                JSONObject list = (JSONObject) ((JSONObject)array.get(i)).get("list");
                System.out.println(list.get("category")+":"+list.get("synonyms"));
            }





        }catch(Exception e){
            e.printStackTrace();
        }


    }*//*
*/


























}
