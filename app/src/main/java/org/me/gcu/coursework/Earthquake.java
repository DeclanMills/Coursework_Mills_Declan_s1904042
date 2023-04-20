package org.me.gcu.coursework;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

//Name:Declan Mills
//Student Number: s1904042
public class Earthquake implements Serializable, Parcelable {


    private String Date;
    private String Time;
    private String Latitude;
    private String Longitude;
    private String Locality;
    private String Region;
    private int Depth;
    private String Magnitude;
    private int Distance;
    private int Bearing;
    @Override
    public String toString() {
      return Locality;
    }

    // GETTERS
    public String getDate() { return this.Date; }
    public String getTime() { return this.Time; }
    public String getLatitude() { return this.Latitude; }
    public String getLongitude() { return this.Longitude; }
    public String getLocality() { return this.Locality; }
    public String getRegion() { return this.Region; }
    public String getDepth() { return String.valueOf(this.Depth); }
    public String getMagnitude() { return this.Magnitude; }
    public String getDistance() { return String.valueOf(this.Distance); }
    public String getBearing() { return String.valueOf(Bearing); }

    // SETTERS
    public void setLocationRegion(String title) {
        // getting information to set earthquakes location name from Title
        String[] split = title.split(":");
        String locReg = split[2].trim().substring(0, split[2].indexOf(", "));

        // formats location to look pretty
        locReg = locReg.toLowerCase(); // sets all to lower case

        // sets the first letter at the start of each word to a capital
        split = locReg.split(",");
        String loc, reg;
        if (split.length > 1) {
            loc = split[0];
            reg = split[1];
        } else {
            loc = "No Locality";
            reg = split[0];
        }


        split = loc.split(" ");
        loc = "";
        for (int i = 0; i < split.length; i++) {
            if (!split[i].equals("and"))
                split[i] = split[i].substring(0, 1).toUpperCase() + split[i].substring(1);

            loc = loc + split[i] + " ";
        }

        split = reg.split(" ");
        reg = "";
        for (int i = 0; i < split.length; i++) {
            if (!split[i].equals("and"))
                split[i] = split[i].substring(0, 1).toUpperCase() + split[i].substring(1);

            reg = reg + split[i] + " ";
        }

        this.Locality = loc.trim();
        this.Region = reg.trim();
    }
    public void setDepthMagnitude(String description) {
        String[] splitDesc = description.split(" ; ");
        String depth, magnitude;

        for (int i = 0; i < splitDesc.length; i++) {
            if (splitDesc[i].contains("Depth: ")) {
                String depthString = "";
                depthString = splitDesc[i].replace("Depth: ", "").trim();
                depthString = depthString.substring(0, depthString.indexOf(' '));
                this.Depth = Integer.parseInt(depthString);
            } else if (splitDesc[i].contains("Magnitude: ")) {
                this.Magnitude = splitDesc[i].replace("Magnitude: ", "").trim();
            }
        }
    }
    public void setDate(String date) {
        date = date.substring(date.indexOf(' ') + 1);
        String[] dateSplit = date.split(" ");
        this.Date = dateSplit[0] + " " + dateSplit[1] + " " + dateSplit[2];
        this.Time = dateSplit[3];
    }
    public void setLatitude(String latitude) { this.Latitude = latitude; }
    public void setLongitude(String longitude) {this.Longitude = longitude; }
    public void setDistance(int distance) { this.Distance = distance; }
    public void setBearing(int bearing) { Bearing = bearing; }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(Date);
        parcel.writeString(Time);
        parcel.writeString(Latitude);
        parcel.writeString(Longitude);
        parcel.writeString(Locality);
        parcel.writeString(Region);
        parcel.writeInt(Depth);
        parcel.writeString(Magnitude);
        parcel.writeInt(Distance);
        parcel.writeInt(Bearing);
    }

    // CREATOR field that implements the Parcelable.Creator interface
    public static final Parcelable.Creator<Earthquake> CREATOR = new Parcelable.Creator<Earthquake>() {
        @Override
        public Earthquake createFromParcel(Parcel in) {
            return new Earthquake(in);
        }

        @Override
        public Earthquake[] newArray(int size) {
            return new Earthquake[size];
        }
    };

    // constructor that takes a Parcel and constructs an Earthquake object from it
    protected Earthquake(Parcel in) {
        Date = in.readString();
        Time = in.readString();
        Latitude = in.readString();
        Longitude = in.readString();
        Locality = in.readString();
        Region = in.readString();
        Depth = in.readInt();
        Magnitude = in.readString();
        Distance = in.readInt();
        Bearing = in.readInt();
    }

    protected Earthquake() {

    }
}
