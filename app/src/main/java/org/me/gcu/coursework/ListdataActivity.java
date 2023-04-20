package org.me.gcu.coursework;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;



public class ListdataActivity extends AppCompatActivity implements View.OnClickListener {
 //declares textviews called name etc, that i will use to display information
    TextView name;
    TextView time;
    TextView date;
    TextView mag;
    TextView reg;
    TextView lon;
    TextView lat;
//Buttons I will be using for maps and back
    Button maps;
    Button back;

    public String lonX = "";
    public String latY = "";
    public String reg1 = "";
    String bearing = "";
    String depth = "";
    String dist = "";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listdata);
        //sets the ids of the textviews and the buttons to the appropriate id
        name = findViewById(R.id.listdata);
        time = findViewById(R.id.timedata);
        date = findViewById(R.id.dateData);
        mag = findViewById(R.id.magdata);
        reg = findViewById(R.id.regiondata);
        lon = findViewById(R.id.longdata);
        lat = findViewById(R.id.latdata);
        maps = findViewById(R.id.mapsButton);
        back = findViewById(R.id.back);

        Intent intent = getIntent();
        //gets the text from the intent then sets the text to the data it pulled
        name.setText(intent.getStringExtra("name"));
        time.setText(intent.getStringExtra("time"));
        date.setText(intent.getStringExtra("date"));
        mag.setText(intent.getStringExtra("mag"));
        reg.setText(intent.getStringExtra("reg"));
        lon.setText(intent.getStringExtra("lon"));
        lat.setText(intent.getStringExtra("lat"));

        //sets variables
        lonX = intent.getStringExtra("lon");
        latY = intent.getStringExtra("lat");
        bearing = intent.getStringExtra("bearing");
        depth = intent.getStringExtra("depth");
        dist = intent.getStringExtra("dist");

        reg1 = intent.getStringExtra("name");

        //listens for if the map button is clicked
        maps.setOnClickListener(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if the back button is clicked, it will take you back to the main activity
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                //starts intent1, main activity
                startActivity(intent1);
            }
        });
    }

    @Override
    public void onClick(View view) {
        // if map button is clicked, starts the maps class, and sends the lat and lon to the maps class
        Intent intent1 = new Intent(getApplicationContext(),MapsActivity.class);
        intent1.putExtra("lat", latY);
        intent1.putExtra("lon", lonX);



        //displays to viewer that it is showing the eathquake they clicked on
        Toast.makeText(ListdataActivity.this, "Showing " + reg1  + " Earthquake", Toast.LENGTH_SHORT).show();
        startActivity(intent1);


    }
}
