package com.cwk2.appointments;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by DELL 5559 on 15/05/2017.
 */

public class CustomAdapter extends BaseAdapter{

    ArrayList<Appointment> appointments;
    LayoutInflater inflater;
    Context context;


    public CustomAdapter(Context context,ArrayList<Appointment>appointments){



        this.appointments = appointments;
        this.context = context;
        this.inflater = LayoutInflater.from(context);





    }


    @Override
    public int getCount() {
        return appointments.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



            convertView = inflater.inflate(R.layout.listview_layout, null);

            TextView indexTview = (TextView) convertView.findViewById(R.id.textViewIndex);
            TextView timeTview = (TextView) convertView.findViewById(R.id.textViewTime);
            TextView titleTview = (TextView) convertView.findViewById(R.id.textViewTitle);

            String index = String.valueOf(position + 1);

            indexTview.setText(index + ".");
            timeTview.setText(appointments.get(position).getTime());
            titleTview.setText(appointments.get(position).getTitle());




            return convertView;


    }


}
