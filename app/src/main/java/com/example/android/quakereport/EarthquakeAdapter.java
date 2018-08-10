package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EarthquakeAdapter extends ArrayAdapter<EarthquakeItemClass>{

    public EarthquakeAdapter(@NonNull Context context, @NonNull List<EarthquakeItemClass> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null)
            convertView=LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        EarthquakeItemClass currentItem=getItem(position);
        TextView magTextView=(TextView)convertView.findViewById(R.id.mag);
        DecimalFormat formatter=new DecimalFormat("0.0");
        GradientDrawable magnitudeCircle=(GradientDrawable)magTextView.getBackground();
        int magnitudeColor=getMagnitudeColor(currentItem.getmMagnitude());
        magnitudeCircle.setColor(magnitudeColor);
        magTextView.setText(formatter.format(currentItem.getmMagnitude()));
        String location=currentItem.getmLocation();
        String offset, primaryLocation;
        int i=location.indexOf(" of ");
        if(i==-1){
            offset="Near the ";
            primaryLocation=location;
        }
        else{
            i+=4;
            offset=location.substring(0,i);
            primaryLocation=location.substring(i,location.length());
        }
        TextView offsetTextView=(TextView)convertView.findViewById(R.id.offset);
        offsetTextView.setText(offset);
        TextView primaryLocationTextView=(TextView)convertView.findViewById(R.id.primary_location);
        primaryLocationTextView.setText(primaryLocation);
        TextView dateTextView=(TextView)convertView.findViewById(R.id.date);
        Date earthquakeDate=new Date(currentItem.getmDate());
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MMM DD, yyyy");
        dateTextView.setText(simpleDateFormat.format(earthquakeDate));
        TextView timeTextView=(TextView)convertView.findViewById(R.id.time);
        SimpleDateFormat timeFormat=new SimpleDateFormat("h:mm a");
        timeTextView.setText(timeFormat.format(earthquakeDate));
        return convertView;
    }
    private int getMagnitudeColor(double magnitude){
        int magnitudeColorResourceId;
        int magnitudeFloor=(int)Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(),magnitudeColorResourceId);
    }
}
