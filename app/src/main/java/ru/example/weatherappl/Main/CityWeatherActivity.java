package ru.example.weatherappl.Main;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.text.Html;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.pavlospt.CircleView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ru.example.weatherappl.Helper.Helper;
import ru.example.weatherappl.Helper.RController;
import ru.example.weatherappl.Model.LocationMapObject;

import ru.example.weatherappl.R;

public class CityWeatherActivity extends AppCompatActivity {



    private TextView cityCountry;
    private TextView currentDate;
    private TextView weatherIcon;
    private CircleView circleTitle;
    private TextView windResult;
    private TextView humidityResult;
    Typeface weatherFont;
    private TextView pressure1;
    private TextView sunrise1;
    private TextView sunset1;

    private LocationMapObject locationMapObject;




    @Override
    protected void onCreate( Bundle savedInstanceState) {
        weatherFont = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/weather.ttf");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        String name = getIntent().getStringExtra("name");
        locationMapObject = RController.getInstance().getCity(name);
        setContentView(R.layout.city_weather_activity);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }



        cityCountry = (TextView) findViewById(R.id.city_country);
        currentDate = (TextView) findViewById(R.id.current_date);
        weatherIcon = (TextView) findViewById(R.id.weather_icon);
        weatherIcon.setTypeface(weatherFont);
        circleTitle = (CircleView) findViewById(R.id.weather_result);
        windResult = (TextView) findViewById(R.id.wind_result);
        humidityResult = (TextView) findViewById(R.id.humidity_result);
        pressure1 = (TextView)findViewById(R.id.pressure);
        sunrise1 = (TextView)findViewById(R.id.sunrise);
        sunset1 = (TextView)findViewById(R.id.sunset);

        fillMyFields();
    }






    private void fillMyFields() {
try {
    String city = locationMapObject.getName() + ", " + locationMapObject.getSys().getCountry();
    String todayDate = getTodayDateInStringFormat();
    Long tempVal = Math.round(Math.floor(Double.parseDouble(locationMapObject.getMain().getTemp())));
    String weatherTemp = String.valueOf(tempVal) + "°";
    String weatherDescription = Helper.capitalizeFirstLetter(locationMapObject.getWeather().get(0).getDescription());
    String windSpeed = locationMapObject.getWind().getSpeed() + " m/ps";
    String humidityValue = locationMapObject.getMain().getHumidity() + "%";
    String pressure = "Pressure: " + locationMapObject.getMain().getPressure() + " hPa";
    String sunrise = "Sunrise: " + new SimpleDateFormat("K:mm a, z").format(locationMapObject.getSys().getSunrise()*1000);
    String sunset = "Sunset: " + new SimpleDateFormat("K:mm a, z").format(locationMapObject.getSys().getSunset()*1000);
    cityCountry.setText(city);
    currentDate.setText(todayDate);
    circleTitle.setTitleText(weatherTemp);
    circleTitle.setSubtitleText(weatherDescription);
    windResult.setText(windSpeed);
    humidityResult.setText(humidityValue);
    weatherIcon.setText(Html.fromHtml(setWeatherIcon(locationMapObject.getWeather().get(0).getMain())));
    pressure1.setText(pressure);
    sunrise1.setText(sunrise);
    sunset1.setText(sunset);
}  catch (NullPointerException e) {

        }
    }



    private String getTodayDateInStringFormat(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("E, d MMMM", Locale.getDefault());
        return df.format(c.getTime());
    }

    public static String setWeatherIcon(String actualId){
        String id = actualId;
        String icon = "";

            switch(id) {
                case "Thunderstorm" : icon = "&#xf01e;";
                    break;

                case "Drizzle" : icon = "&#xf01c;";
                    break;

                case "Rain" : icon = "&#xf019;";
                    break;

                case "Snow" : icon = "&#xf01b;";
                    break;

                case "Atmosphere" : icon = "&#xf014;";
                    break;

                case "Clear" : icon = "&#xf00d;";
                    break;

                case "Clouds" : icon = "&#xf013;";
                    break;


            }

        return icon;
    }


}