package ru.example.weatherappl.JsonThings;

import io.realm.RealmObject;

public class Coord  extends RealmObject{

    private String lon;

    private String lat;

    public Coord() {}

    public Coord(String lon, String lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public String getLat() {
        return lat;
    }
}
