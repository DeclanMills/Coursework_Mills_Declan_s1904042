package org.me.gcu.coursework;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.me.gcu.coursework.databinding.ActivityMapsBinding;

//Name:Declan Mills
//Student Number: s1904042
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Intent intent = getIntent();
        //Declares 2 doubles for lat and lon
        double latX = 0;
        double lonY = 0;

        //gets the lat and lon from the intents, and parses them to a double
        latX = Double.parseDouble(intent.getStringExtra("lat"));
        lonY = Double.parseDouble(intent.getStringExtra("lon"));
        // Add a marker on lat and lon
        LatLng marker = new LatLng(latX, lonY);
        //adds the marker to the map
        mMap.addMarker(new MarkerOptions().position(marker).title("Marker on Earthquake"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marker));
    }
}