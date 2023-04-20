package org.me.gcu.coursework;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//Name:Declan Mills
//Student Number: s1904042
public class multiQuake extends AppCompatActivity {

    public ListView lstview;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiquake);
        //intent gets all the values
        Intent intent = new Intent();
        ArrayList<Earthquake> earthquakes = getIntent().getParcelableArrayListExtra("values");

        //gets the ID of the listview
        lstview = (ListView) findViewById(android.R.id.list);

        //ArrayAdapter<Earthquake> adapter = new ArrayAdapter<Earthquake>(getApplicationContext(), R.layout.list_view, earthquakes);
        //Create and EarthquakeAdapter
        EarthquakeAdapter adapter1 = new EarthquakeAdapter(getApplicationContext(),earthquakes);
        lstview.setAdapter(adapter1);

        //onClick for multiQuakes, if you click a quake it will show you more details of the quake
        lstview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ListdataActivity.class);
                intent.putExtra("name", earthquakes.get(i).getLocality());
                intent.putExtra("time", earthquakes.get(i).getTime());
                intent.putExtra("date", earthquakes.get(i).getDate());
                intent.putExtra("mag", earthquakes.get(i).getMagnitude());
                intent.putExtra("reg", earthquakes.get(i).getRegion());
                intent.putExtra("lon", earthquakes.get(i).getLongitude());
                intent.putExtra("lat", earthquakes.get(i).getLatitude());

                //starts the activity
                startActivity(intent);
            }
        });



    }
//Earthquake adapter class
    public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {
        private Context mContext;
        private List<Earthquake> mEarthqaukes;
        public EarthquakeAdapter(@NonNull Context context, List<Earthquake> earthquakes) {
            super(context, 0, earthquakes);
            mContext = context;
            mEarthqaukes = earthquakes;
        }
//Compares the magnitude
public  class MagnitudeComparator implements Comparator<Earthquake> {
    @Override

    //compares 2 earthquakes
    public int compare(Earthquake e1, Earthquake e2) {
        Double e3 = Double.parseDouble(e1.getMagnitude());
        Double e4 = Double.parseDouble(e2.getMagnitude());
        return Double.compare(e4, e3);
    }
}

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View listItem = convertView;
            if(listItem == null){
                listItem = LayoutInflater.from(mContext).inflate(R.layout.activity_main, parent, false);

            }
            //sorts the earthquakes
            Collections.sort(mEarthqaukes, new MagnitudeComparator());
            Earthquake current = mEarthqaukes.get(position);
            TextView magTextView = listItem.findViewById(R.id.mag_text_view);
            //sets the text to the location and the magnitude
            magTextView.setText(String.valueOf(current.getLocality() + " " + current.getMagnitude()));

            //if statements to get the magnitude and set the colour according to the magnitude
            if(Double.parseDouble(current.getMagnitude())>= 5){
                magTextView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.mag5plus));

            } else if (Double.parseDouble(current.getMagnitude()) >= 3) {
                magTextView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.mag3plus));

            } else if (Double.parseDouble(current.getMagnitude()) >= 2) {
                magTextView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.mag2plus));
            } else if (Double.parseDouble(current.getMagnitude()) >=1) {
                magTextView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.mag1plus));
            }else {
                magTextView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.magAny));
            }
            //onClick to view more details
            magTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ListdataActivity.class);
                    intent.putExtra("name", current.getLocality());
                    intent.putExtra("time", current.getTime());
                    intent.putExtra("date", current.getDate());
                    intent.putExtra("mag", current.getMagnitude());
                    intent.putExtra("reg", current.getRegion());
                    intent.putExtra("lon", current.getLongitude());
                    intent.putExtra("lat", current.getLatitude());
                    startActivity(intent);
                }
            });
            //returmns the listItem
            return listItem;
        }
    }
}
