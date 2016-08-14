package ru.example.weatherappl.JsonThings;


import io.realm.RealmObject;

public class Clouds extends RealmObject{

    private String all;

    public Clouds() {
    }

    public Clouds(String all) {
        this.all = all;
    }

    public String getAll() {
        return all;
    }
}
