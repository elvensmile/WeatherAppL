package ru.example.weatherappl.JsonThings;


import io.realm.RealmObject;

public class Weather  extends RealmObject {

    WeatherResults weatherResults;

    public  Weather(){    }

    public Weather(WeatherResults weatherResults){
        this.weatherResults = weatherResults;
    }

    public WeatherResults getWeatherResults() {
        return weatherResults;
    }
}
