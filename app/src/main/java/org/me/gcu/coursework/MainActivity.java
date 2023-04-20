package org.me.gcu.coursework;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

//Name:Declan Mills
//Student Number: s1904042
public class MainActivity extends AppCompatActivity implements  OnClickListener {

    //All the textviews, list views etc that i will be using on the main activity xml
    private TextView magTextView;
    public ListView lstview;
    private Button startButton;
    private String result = "";
    private String url1="";
    //Create an ArrayList of earthquakes called values
    public ArrayList<Earthquake> values;

    Button search;

    //url source
    private String urlSource="http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //sets values equal to arraylist of earthquakes
        values = new ArrayList<Earthquake>();
        setContentView(R.layout.activity_main);
        //sets the ids of the search button
        search = findViewById(R.id.search);

        //calls the  method start progress
        startProgress();
        //sets id oflistview
        lstview = (ListView) findViewById(android.R.id.list);
        List<Earthquake> earthquakes = values;
        //Creates list of earthquakes equal to the arraylist of earthquakes

        //creates an adaparer from the Earthquake adapter class
        EarthquakeAdapter adapter = new EarthquakeAdapter(getApplicationContext(), earthquakes);
        lstview.setAdapter(adapter);

        //on click listner for search button
        search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //creates a new intent, sends the array list of values to the search activity class
                Intent intent = new Intent(MainActivity.this, searchActivity.class);
                intent.putParcelableArrayListExtra("values", values);
                //starts the activity
                startActivity(intent);
                
            }
        });

    }

    //Earthquake adapater class that extends the ArrayAdapter of type Earthquake
public class EarthquakeAdapter extends ArrayAdapter<Earthquake>{

        //no idea what this actually does, its just needed
        public EarthquakeAdapter(Context context, List<Earthquake> earthquakes){
            super(context, 0, earthquakes);
        }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_main, parent, false);
        }


        //This displays the earthquakes in a nice view, shows both the location of earthquake and the magnitude
        Earthquake earthquake = getItem(position);

        TextView nameTextView = convertView.findViewById(R.id.earthquake_name);
        nameTextView.setText(earthquake.getLocality() +  " " +earthquake.getMagnitude());

        //if you click the textview of earthquake, takes you to ListdataActivity class that shows you all the details about the selected eathquake

        nameTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListdataActivity.class);
                //sends all the needed info to the class
                intent.putExtra("name", earthquake.getLocality());
                intent.putExtra("time", earthquake.getTime());
                intent.putExtra("date", earthquake.getDate());
                intent.putExtra("mag", earthquake.getMagnitude());
                intent.putExtra("reg", earthquake.getRegion());
                intent.putExtra("lon", earthquake.getLongitude());
                intent.putExtra("lat", earthquake.getLatitude());
                intent.putExtra("dist", earthquake.getDistance()).toString();
                intent.putExtra("depth", earthquake.getDepth()).toString();
                intent.putExtra("bearing", earthquake.getBearing()).toString();

                //starts the intent
                startActivity(intent);
            }
        });

        //returns the view
        return convertView;
    }

}




    @Override
    public void onClick(View view)
    {
        startProgress();
    }
    public ArrayList<Earthquake> getValues(){

        return values;

    }

    public void startProgress()
    {
        try {
            Thread t = new Thread(new Task(urlSource));


            t.start();

            t.join();

        } catch (Exception e) {
            System.out.println("error");
        }

    } //


    private class Task implements Runnable
    {
        private String url;

        public Task(String aurl)
        {
            url = aurl;
        }
        @Override
        public void run()
        {


            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";


            Log.e("MyTag","in run");

            try
            {
                Log.e("MyTag","in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                while ((inputLine = in.readLine()) != null)
                {

                    result = result + inputLine;


                }


                if (!result.equals("")){
                    //PullParser Setup


                    XmlPullParserFactory xmlFactory = XmlPullParserFactory.newInstance();
                    xmlFactory.setNamespaceAware(true);
                    XmlPullParser xmlP = xmlFactory.newPullParser();
                    xmlP.setInput(new StringReader( result ) );

                    int eventType = xmlP.getEventType();

                    //Create earthquake object
                    Earthquake quake = new Earthquake();
                    //create buidling boolean
                    boolean building = false;

                    while (eventType != XmlPullParser.END_DOCUMENT) {



                        if(eventType == XmlPullParser.START_TAG)
                        {


                            if (xmlP.getName().equals("item")){
                                //initialise earthquake
                                quake =  new Earthquake();


                                //set buiding to true
                                building = true;


                            }else if (xmlP.getName().equals("title") && building){
                                xmlP.next();
                                //store title of earthquake
                                quake.setLocationRegion(xmlP.getText());
                                //sets the location


                            } else if (xmlP.getName().equals("description") && building) {
                                xmlP.next();
                                quake.setDepthMagnitude(xmlP.getText());
                                //store Depth and Mag


                            } else if (xmlP.getName().equals("pubDate") && building) {
                                xmlP.next();
                                //store pubDate
                                quake.setDate(xmlP.getText());
                                //sets the date

                            } else if (xmlP.getName().equals("lat") && building) {
                                xmlP.next();
                                //store lat
                                quake.setLatitude(xmlP.getText());
                                //sets the lat

                            } else if (xmlP.getName().equals("long") && building) {
                                xmlP.next();
                                //store long
                                quake.setLongitude(xmlP.getText());
                                //sets the lnog

                            }
                        } else if (eventType == XmlPullParser.END_TAG) {
                            if (xmlP.getName().equals("item") && building){
                                float[] tempDist = new float[3];
                                //Compares and sets the distance of the quake compared to glasgow(55 -4)
                                android.location.Location.distanceBetween(Double.parseDouble(quake.getLatitude()), Double.parseDouble(quake.getLongitude()), 55.8642, -4.2518, tempDist);
                                quake.setDistance((int)tempDist[0]);
                                //calculates the bearing of the quake compared to glasgow
                                quake.setBearing(calculateBearing(55.8642, -4.2518, Double.parseDouble(quake.getLatitude()), Double.parseDouble(quake.getLongitude())));
                                //adds the quake to the arraylist values
                                values.add(quake);
                                building = false;
                                //prints quake added
                                System.out.println("quake added");

                            }
                        }



                        eventType = xmlP.next();// moves to next event

                    }


                } else {
                    throw new Exception("Result was empty.");
                }
            }
            catch (Exception ex)
            {
                System.out.println("Error: " + ex.getMessage());
            }






            MainActivity.this.runOnUiThread(new Runnable()
            {
                public void run() {
                    Log.d("UI thread", "I am the UI thread");
                    //rawDataDisplay.setText(result);

                }
            });



        }

        public void getValues(){
            for(int i =0; i<=values.size(); i++){
                System.out.println(values.get(i));
            }
        }

        //method for calculating the bearing, takes in 2 lats and 2 longs, a start and an end
        public int calculateBearing(double startLatitude, double startLongitude, double endLatitude, double endLongitude){
            Location startLoc = new Location("");
            startLoc.setLatitude(startLatitude);
            startLoc.setLongitude((startLongitude));
            //sets location of start
            Location destination = new Location("");
            destination.setLatitude(endLatitude);
            destination.setLongitude(endLongitude);
            //sets location of destination
            int bearing = (int) startLoc.bearingTo(destination);
            //calculates bearing
            if (bearing < 0)
            {
                bearing = bearing + 360;
            }
            //returns bearing
            return bearing;
        }

    }

}