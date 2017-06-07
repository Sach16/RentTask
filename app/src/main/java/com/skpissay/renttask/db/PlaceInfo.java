package com.skpissay.renttask.db;

import com.orm.SugarRecord;

/**
 * Created by skpissay on 07/06/17.
 */
public class PlaceInfo extends SugarRecord{

    //uniquecode
    private String uniquecode;

    //latitude
    private double latitude;

    //longitude
    private double longitude;

    //name
    private String name;

    //time
    private String time;

    public PlaceInfo(){}

    public String getUniquecode() {
        return uniquecode;
    }

    public void setUniquecode(String uniquecode) {
        this.uniquecode = uniquecode;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
