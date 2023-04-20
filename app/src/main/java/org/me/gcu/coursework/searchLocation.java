package org.me.gcu.coursework;

import android.R.layout;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

//Name:Declan Mills
//Student Number: s1904042
public class searchLocation extends AppCompatActivity implements DatePicker.OnDateChangedListener {

    DatePicker datePicker;
    int day = 0;
    int mon = 0;
    String month = "";
    String userDate = "";
    String year = "2023";
    String dayS = "";

    Button button;
    Button back;
    @SuppressLint("NewApi")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        //gets the id of all the items needed
        Spinner spinner = findViewById(R.id.spinner);
        datePicker = findViewById(R.id.date);
        datePicker.setOnDateChangedListener(this);
        button = findViewById(R.id.button);
        back = findViewById(R.id.back);

        Intent intent1 = new Intent(getApplicationContext(), searchDate.class);
        //gets all the earthquakes
        ArrayList<Earthquake> items = getIntent().getParcelableArrayListExtra("values");
        //creates new list for uniqueItems
        ArrayList<String> uniqueItem = new ArrayList<>();
        //list for date
        ArrayList<Earthquake> earthquakesDate = new ArrayList<>();

        int count = 0;

        //foreach searchs entire quake list
        for (Earthquake item: items) {

            //if the location is unique
            if (!uniqueItem.contains(item.getLocality())){
                //adds uniqueLocation to arraylist
                uniqueItem.add(item.getLocality());
                //adds to count
                count += 1;
                //skips over duplicate locations
            } else if (uniqueItem.contains(item)) {

            }

        }


        //back button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(searchLocation.this, searchActivity.class);
                startActivity(intent);
            }
        });
        //Array adapter
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, layout.simple_spinner_item, uniqueItem);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //adds all the unique items to the dropdown menu
        spinner.setAdapter(adapter1);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = 0;
                for (int i=0;i<items.size();i++){
                        //loops through all quakes, if the date is the same as users date launches next if
                    if (items.get(i).getDate().equals(userDate)){
                        //checks to see if there was on earthquake at that location
                        if (items.get(i).getLocality().equals(spinner.getSelectedItem().toString())) {
                            //adds the selected earthquakes to the arraylist
                            count += 1 ;
                            earthquakesDate.add(items.get(i));

                        }
                    }

                }
                if(count == 1) {//if count is 1, launches listdataActivity to show more detailed info
                    Intent intent = new Intent(getApplicationContext(), ListdataActivity.class);
                    intent.putExtra("name", earthquakesDate.get(0).getLocality());
                    intent.putExtra("time", earthquakesDate.get(0).getTime());
                    intent.putExtra("date", earthquakesDate.get(0).getDate());
                    intent.putExtra("mag", earthquakesDate.get(0).getMagnitude());
                    intent.putExtra("reg", earthquakesDate.get(0).getRegion());
                    intent.putExtra("lon", earthquakesDate.get(0).getLongitude());
                    intent.putExtra("lat", earthquakesDate.get(0).getLatitude());
                    startActivity(intent);

                }else if (count > 1) {//if count is mroe than 1, launches the  multiQuake class
                    Intent intent = new Intent(getApplicationContext(), multiQuake.class);


                    intent.putParcelableArrayListExtra("values", earthquakesDate);
                    startActivity(intent);
                }else{//if no quakes found, sends an error message
                    Toast.makeText(searchLocation.this, "No earthquakes in " + spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

                }

            }
        });

    }
//listens for date changed
    @Override
    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
        //gets the day and month
        day = datePicker.getDayOfMonth();
        mon = datePicker.getMonth();



        //if day is 0-9
        if(day >0 && day <9)
        {//adds a zero before the day, so 07 if you picked the 7th
            dayS = "0" + day;
        }else{
            dayS = String.valueOf(day);
        }
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

        //sets the user date
        userDate = dayS + " " + month + " " + year;
    }
}
