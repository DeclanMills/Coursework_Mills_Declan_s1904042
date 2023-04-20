package org.me.gcu.coursework;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
//Name:Declan Mills
//Student Number: s1904042
public class searchDate extends AppCompatActivity implements DatePicker.OnDateChangedListener {


    int day = 0;
    int mon = 0;
    String dayS = "";

    String month = "";

    String year = "2023";
    String userDate;
    DatePicker datePicker;
    Button search;
    Button back;
    @SuppressLint("NewApi")
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        //sets the ids of the datePicker etc
        datePicker = findViewById(R.id.date);
        search = findViewById(R.id.search);
        back = findViewById(R.id.back);
        //back button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), searchActivity.class);
                startActivity(intent);
            }
        });
        //datePicker on click listener
        datePicker.setOnDateChangedListener(this);
        //search button on click
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //sets count to 0
                int count = 0;
                //gets the earthquakes
                ArrayList<Earthquake> earthquakes = getIntent().getParcelableArrayListExtra("values");
                //creates a new arraylist for earthquakeDates
                ArrayList<Earthquake> earthquakesDate = new ArrayList<>();
                //loops through the earthquake list
                for (int i=0;i<earthquakes.size();i++){
                    System.out.println(earthquakes.get(i).getDate());
                    System.out.println(earthquakes.get(i).getLocality());
                    //if the earthquake date is on the same date as the user picked date
                    if (earthquakes.get(i).getDate().equals(userDate)){
                        //adds the quake to the new list
                        earthquakesDate.add(earthquakes.get(i));
                        //adds on to count
                        count += 1;
                    }

                }
                //if count is 0
                if (count == 0){
                    //Displays message saying no quakes found
                    Toast.makeText(searchDate.this, "No Earthquakes on " + userDate, Toast.LENGTH_SHORT).show();

                } else if (count == 1) {//if count is 1
                    //Launches the ListdataActivity class to display all the information on that quake
                    Intent intent = new Intent(getApplicationContext(), ListdataActivity.class);
                    intent.putExtra("name", earthquakesDate.get(0).getLocality());
                    intent.putExtra("time", earthquakesDate.get(0).getTime());
                    intent.putExtra("date", earthquakesDate.get(0).getDate());
                    intent.putExtra("mag", earthquakesDate.get(0).getMagnitude());
                    intent.putExtra("reg", earthquakesDate.get(0).getRegion());
                    intent.putExtra("lon", earthquakesDate.get(0).getLongitude());
                    intent.putExtra("lat", earthquakesDate.get(0).getLatitude());
                    startActivity(intent);
                } else if (count > 1) {// if count is more than 1, launches the multiQuake class
                    Intent intent = new Intent(getApplicationContext(), multiQuake.class);


                    intent.putParcelableArrayListExtra("values", earthquakesDate);
                    startActivity(intent);
                }


            }
        });




    }
//listens for the date to change
    @Override
    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
        day = datePicker.getDayOfMonth();
        mon = datePicker.getMonth();



        //adds a zero of the date is 1 through 9
        if(day >0 && day <9)
        {
            dayS = "0" + day;
        }else{//gets the value of the day
            dayS = String.valueOf(day);
        }//sets the month
        if(mon == 0){
            month = "Jan";
        } else if (mon == 1) {
            month = "Feb";
        } else if (mon == 2) {
            month = "Mar";
        } else if (mon == 3) {
            month = "Apr";
        } else if (mon == 4) {
            month = "May";
        }

        //sets the user date to the chosen date
        userDate = dayS + " " + month + " " + year;



    }
}
