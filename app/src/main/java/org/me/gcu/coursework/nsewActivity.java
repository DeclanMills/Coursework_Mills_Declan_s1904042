package org.me.gcu.coursework;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

//Name:Declan Mills
//Student Number: s1904042
public class nsewActivity extends AppCompatActivity {

TextView north;
TextView east;
TextView south;
TextView west;
TextView miDepth;
TextView maDepth;
TextView maMag;
Button nMap;
Button eMap;
Button sMap;
Button wMap;
Button miMap;
Button maMap;
Button magMap;
    double minN = 9999999;
    double minE = 9999999;
    double minS = 9999999;
    double minW = 9999999;
    double magMax = 0;
    double depthMin = 9999999;
    double depthMax = 0;
    double nw = 315;
    double en = 45;
    double es = 134;
    double se = 135;
    double sw = 224;
    double ws = 224;
    double wn = 315;
    double ne = 44;
    public String lonN = "";
    public String latN = "";
    public String lonE = "";
    public String latE = "";
    public String lonS = "";
    public String latS = "";
    public String latW = "";
    public String lonW = "";
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nsew);

        north = findViewById(R.id.northE);
        east = findViewById(R.id.eastE);
        south = findViewById(R.id.southE);
        west = findViewById(R.id.westE);
        nMap = findViewById(R.id.mapN);
        eMap = findViewById(R.id.mapE);
        sMap = findViewById(R.id.mapS);
        wMap = findViewById(R.id.mapW);
        miDepth = findViewById(R.id.minDepthE);
        miMap = findViewById(R.id.mapMin);
        maMap = findViewById(R.id.mapMax);
        maDepth = findViewById(R.id.maxDepthE);
        maMag = findViewById(R.id.magE);
        magMap = findViewById(R.id.mapMag);
        Intent intent1 = new Intent(nsewActivity.this, searchDate.class);

        //Gets all the values of the quakes
        ArrayList<Earthquake> earthquakes = getIntent().getParcelableArrayListExtra("values");
        //Creats a new earthquake for the most N,S,E,W points, the max Magnitude and the max and min depth
        Earthquake northE = new Earthquake();
        Earthquake eastE = new Earthquake();
        Earthquake southE = new Earthquake();
        Earthquake westE = new Earthquake();
        Earthquake maxMag = new Earthquake();
        Earthquake minDepth = new Earthquake();
        Earthquake maxDepth = new Earthquake();

        Intent intent = getIntent();
        //sends all the values of earthquakes in the intent
        intent1.putParcelableArrayListExtra("values", earthquakes);


        //foreach loop goes through all the items in earthquake
        for (Earthquake item: earthquakes) {
            //if statement to get the north points
            if (Double.parseDouble(item.getBearing()) > nw || Double.parseDouble(item.getBearing()) <ne){
                //if the north point is closer than previous, sets the new north point to current item
                if(minN> Double.parseDouble(item.getDistance())){
                    northE = item;
                    minN = Double.parseDouble(item.getDistance());
                }//else if to calculate the east point
            }else if(Double.parseDouble(item.getBearing()) > en && Double.parseDouble(item.getBearing()) < es) {
                //if east point is closer, sets it to current item
                if (minE > Double.parseDouble(item.getDistance())) {
                    eastE = item;
                    minE = Double.parseDouble(item.getDistance());
                }
                //elseif to calculate the South point
            }else if(Double.parseDouble(item.getBearing()) > se && Double.parseDouble(item.getBearing()) < sw){
                if(minS > Double.parseDouble(item.getDistance())){
                    southE = item;
                    minS = Double.parseDouble(item.getDistance());
                }//elseif to calculate the West point
            }else if(Double.parseDouble(item.getBearing())> ws && Double.parseDouble(item.getBearing()) < wn){
                if(minW > Double.parseDouble(item.getDistance())){
                    westE = item;
                    minW = Double.parseDouble(item.getDistance());
                }//if statement to get the maximum max
            }if (magMax < Double.parseDouble(item.getMagnitude())) {
                magMax = Double.parseDouble(item.getMagnitude());
                maxMag = item;
            }//if statement gets the min depth
            if (depthMin > Double.parseDouble(item.getDepth())){
                depthMin = Double.parseDouble(item.getDepth());
                minDepth = item;
                //if statement gets the max depth
            }if (depthMax < Double.parseDouble(item.getDepth())){
                depthMax = Double.parseDouble(item.getDepth());
                maxDepth = item;
            }



        }

        //sets the text of the textview to the data it calculated
        north.setText(northE.getLocality());
        east.setText(eastE.getLocality());
        west.setText(westE.getLocality());
        south.setText(southE.getLocality());
        miDepth.setText(minDepth.getLocality());
        maMag.setText(maxMag.getLocality());
        maDepth.setText(maxDepth.getLocality());

        Earthquake finalNorthE = northE;

        //OnClickListners to click for more details, and onClick to view it on a map
        nMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                lonN = finalNorthE.getLongitude();
                latN = finalNorthE.getLatitude();
                intent.putExtra("lat", latN);
                intent.putExtra("lon", lonN);

                startActivity(intent);
            }
        });

        Earthquake finalEastE = eastE;
        eMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                lonE = finalEastE.getLongitude();
                latE = finalEastE.getLatitude();
                intent.putExtra("lat", latE);
                intent.putExtra("lon", lonE);
                startActivity(intent);
            }
        });

        Earthquake finalSouthE = southE;
        sMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                lonS = finalSouthE.getLongitude();
                latS = finalSouthE.getLatitude();
                intent.putExtra("lat", latS);
                intent.putExtra("lon", lonS);
                startActivity(intent);
            }
        });

        Earthquake finalWestE = westE;
        wMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                lonW = finalWestE.getLongitude();
                latW = finalWestE.getLatitude();
                intent.putExtra("lat", latW);
                intent.putExtra("lon", lonW);
                startActivity(intent);
            }
        });

        west.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListdataActivity.class);
                intent.putExtra("name", finalWestE.getLocality());
                intent.putExtra("time", finalWestE.getTime());
                intent.putExtra("date", finalWestE.getDate());
                intent.putExtra("mag", finalWestE.getMagnitude());
                intent.putExtra("reg", finalWestE.getRegion());
                intent.putExtra("lon", finalWestE.getLongitude());
                intent.putExtra("lat", finalWestE.getLatitude());


                startActivity(intent);
            }
        });

        north.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListdataActivity.class);
                intent.putExtra("name", finalNorthE.getLocality());
                intent.putExtra("time", finalNorthE.getTime());
                intent.putExtra("date", finalNorthE.getDate());
                intent.putExtra("mag", finalNorthE.getMagnitude());
                intent.putExtra("reg", finalNorthE.getRegion());
                intent.putExtra("lon", finalNorthE.getLongitude());
                intent.putExtra("lat", finalNorthE.getLatitude());


                startActivity(intent);
            }
        });

        east.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListdataActivity.class);
                intent.putExtra("name", finalEastE.getLocality());
                intent.putExtra("time", finalEastE.getTime());
                intent.putExtra("date", finalEastE.getDate());
                intent.putExtra("mag", finalEastE.getMagnitude());
                intent.putExtra("reg", finalEastE.getRegion());
                intent.putExtra("lon", finalEastE.getLongitude());
                intent.putExtra("lat", finalEastE.getLatitude());


                startActivity(intent);
            }
        });

        south.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListdataActivity.class);
                intent.putExtra("name", finalSouthE.getLocality());
                intent.putExtra("time", finalSouthE.getTime());
                intent.putExtra("date", finalSouthE.getDate());
                intent.putExtra("mag", finalSouthE.getMagnitude());
                intent.putExtra("reg", finalSouthE.getRegion());
                intent.putExtra("lon", finalSouthE.getLongitude());
                intent.putExtra("lat", finalSouthE.getLatitude());


                startActivity(intent);
            }
        });

        Earthquake finalMinDepth = minDepth;
        miDepth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListdataActivity.class);
                intent.putExtra("name", finalMinDepth.getLocality());
                intent.putExtra("time", finalMinDepth.getTime());
                intent.putExtra("date", finalMinDepth.getDate());
                intent.putExtra("mag", finalMinDepth.getMagnitude());
                intent.putExtra("reg", finalMinDepth.getRegion());
                intent.putExtra("lon", finalMinDepth.getLongitude());
                intent.putExtra("lat", finalMinDepth.getLatitude());


                startActivity(intent);
            }
        });

        Earthquake finalMaxDepth = maxDepth;
        maDepth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListdataActivity.class);
                intent.putExtra("name", finalMaxDepth.getLocality());
                intent.putExtra("time", finalMaxDepth.getTime());
                intent.putExtra("date", finalMaxDepth.getDate());
                intent.putExtra("mag", finalMaxDepth.getMagnitude());
                intent.putExtra("reg", finalMaxDepth.getRegion());
                intent.putExtra("lon", finalMaxDepth.getLongitude());
                intent.putExtra("lat", finalMaxDepth.getLatitude());


                startActivity(intent);
            }
        });

        maMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                lonW = finalMaxDepth.getLongitude();
                latW = finalMaxDepth.getLatitude();
                intent.putExtra("lat", latW);
                intent.putExtra("lon", lonW);
                startActivity(intent);
            }
        });


        miMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                lonW = finalMinDepth.getLongitude();
                latW = finalMinDepth.getLatitude();
                intent.putExtra("lat", latW);
                intent.putExtra("lon", lonW);
                startActivity(intent);
            }
        });

        Earthquake finalMaxMag = maxMag;
        maMag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListdataActivity.class);
                intent.putExtra("name", finalMaxMag.getLocality());
                intent.putExtra("time", finalMaxMag.getTime());
                intent.putExtra("date", finalMaxMag.getDate());
                intent.putExtra("mag", finalMaxMag.getMagnitude());
                intent.putExtra("reg", finalMaxMag.getRegion());
                intent.putExtra("lon", finalMaxMag.getLongitude());
                intent.putExtra("lat", finalMaxMag.getLatitude());


                startActivity(intent);
            }
        });

        magMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                lonW = finalMaxMag.getLongitude();
                latW = finalMaxMag.getLatitude();
                intent.putExtra("lat", latW);
                intent.putExtra("lon", lonW);
                startActivity(intent);
            }
        });

    }

}
