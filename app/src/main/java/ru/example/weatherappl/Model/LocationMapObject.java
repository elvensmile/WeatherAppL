package ru.example.weatherappl.Model;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import ru.example.weatherappl.JsonThings.Clouds;
import ru.example.weatherappl.JsonThings.Coord;
import ru.example.weatherappl.JsonThings.Main;
import ru.example.weatherappl.JsonThings.Rain;
import ru.example.weatherappl.JsonThings.Sys;
import ru.example.weatherappl.JsonThings.WeatherResults;
import ru.example.weatherappl.JsonThings.Wind;

public class LocationMapObject extends RealmObject {

    private Coord coord;

    private RealmList<WeatherResults> weather;
    private WeatherResults weatherResults;

    private String base;

    private Main main = new Main();

    private String visibility;

    private Wind wind;

    private Rain rain;

    private Clouds clouds;

    private String dt;

    private Sys sys;
    @PrimaryKey
    private int id;

    private String name;

    private int cod;

    public LocationMapObject(){};

    public LocationMapObject(Coord coord, RealmList<WeatherResults> weather, String base, Main main, String visibility, Wind wind, Rain rain, Clouds clouds, String dt, Sys sys, int id, String name, int cod) {
        this.coord = coord;
        this.weather = weather;
        this.base = base;
        this.main = main;
        this.visibility = visibility;
        this.wind = wind;
        this.rain = rain;
        this.clouds = clouds;
        this.dt = dt;
        this.sys = sys;
        this.id = id;
        this.name = name;
        this.cod = cod;
    }

    public Coord getCoord() {
        return coord;
    }

   public List<WeatherResults> getWeather() {
        return weather;
    }

    public String getBase() {
        return base;
    }

    public Main getMain() {
        return main;
    }

    public String getVisibility() {
        return visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public Rain getRain() {
        return rain;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public String getDt() {
        return dt;
    }

    public Sys getSys() {
        return sys;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public  void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public int getCod() {
        return cod;
    }
}
