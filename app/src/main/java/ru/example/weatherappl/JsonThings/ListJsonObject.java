package ru.example.weatherappl.JsonThings;


import com.google.gson.annotations.SerializedName;

public class ListJsonObject {

    @SerializedName("_id")
    private String _id;
    @SerializedName("name")
    private String name;
    @SerializedName("country")
    private String country;

    public ListJsonObject(String _id, String name, String country) {
        this._id = _id;
        this.name = name;
        this.country = country;

    }
    public String get_id() {
        return _id;
    }
    public String getName() {
        return name;
    }
    public String getCountry() {
        return country;
    }

    }



