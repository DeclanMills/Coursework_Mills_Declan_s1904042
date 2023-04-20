package org.me.gcu.coursework;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.VolumeShaper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

//Name:Declan Mills
//Student Number: s1904042
public class searchActivity extends AppCompatActivity {

    Button back;
    Button byDate;
    Button byLocation;
    Button nsew;
    @SuppressLint("MissingInflatedId")

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //gets the ID of the buttons
        setContentView(R.layout.activity_search);
        back =findViewById(R.id.back);
        byDate = findViewById(R.id.byDate);
        byLocation = findViewById(R.id.byLocation);
        nsew = findViewById(R.id.nsew);

        //onClick listner for back button
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(searchActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
        //onClick to launch the searchDate class, to search for an earthquake on a specific date
        byDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(searchActivity.this, searchDate.class);

                ArrayList<Earthquake> earthquakes = getIntent().getParcelableArrayListExtra("values");


                intent1.putParcelableArrayListExtra("values", earthquakes);

                startActivity(intent1);
            }
        });

        //onClick to search for location and by date
        byLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(searchActivity.this, searchLocation.class);
                ArrayList<Earthquake> earthquakes = getIntent().getParcelableArrayListExtra("values");
                intent1.putParcelableArrayListExtra("values", earthquakes);
                startActivity(intent1);
            }
        });

        //onClick to get the most N,S,E,W points
        nsew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(searchActivity.this, nsewActivity.class);
                ArrayList<Earthquake> earthquakes = getIntent().getParcelableArrayListExtra("values");
                intent.putParcelableArrayListExtra("values", earthquakes);
                startActivity(intent);
            }
        });


    }


}
