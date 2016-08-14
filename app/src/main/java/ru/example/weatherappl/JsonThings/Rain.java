package ru.example.weatherappl.JsonThings;


import io.realm.RealmObject;

public class Rain  extends RealmObject {

    private String h;

    public Rain() {

    }

    public Rain(String h) {
        this.h = h;
    }

    public String getH() {
        return h;
    }
}
