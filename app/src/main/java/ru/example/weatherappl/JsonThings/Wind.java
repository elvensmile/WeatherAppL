package ru.example.weatherappl.JsonThings;

import io.realm.RealmObject;

public class Wind  extends RealmObject {

    private String speed;

    private String deg;

    public Wind() {}

    public Wind(String speed, String deg) {
        this.speed = speed;
        this.deg = deg;
    }

    public String getSpeed() {
        return speed;
    }

    public String getDeg() {
        return deg;
    }
}
