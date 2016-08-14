package ru.example.weatherappl.Main;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import ru.example.weatherappl.Adapters.CitiesAdapter;
import ru.example.weatherappl.Adapters.RealmCitiesAdapter;
import ru.example.weatherappl.Helper.Prefs;
import ru.example.weatherappl.Helper.RController;

import ru.example.weatherappl.Model.LocationMapObject;
import ru.example.weatherappl.R;


public class MainActivity extends AppCompatActivity {

    private CitiesAdapter adapter;
    private Realm realm;
    private LayoutInflater inflater;
    private FloatingActionButton fab;
    private RecyclerView recycler;
    private RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CitiesAdapter(this);
        this.realm = RController.with(this).getRealm();
        RealmResults<LocationMapObject>  citiesList = RController.with(this).getCities();

        setupRecycler();

        if (!Prefs.with(this).getPreLoad()) {

            setRealmData();

        }

        setRealmAdapter(citiesList);
        for (LocationMapObject lmo : citiesList){
            getCityData(lmo.getName());
        }

        Toast.makeText(this, "Press card item for edit, long press to remove item", Toast.LENGTH_LONG).show();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inflater = MainActivity.this.getLayoutInflater();
                View content = inflater.inflate(R.layout.edit_item, null);
                final AutoCompleteTextView editName = (AutoCompleteTextView) content.findViewById(R.id.name);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setView(content)
                        .setTitle("Add city")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LocationMapObject cityweather = new LocationMapObject();
                                cityweather.setName(editName.getText().toString());

                                if (editName.getText() == null || editName.getText().toString().equals("") || editName.getText().toString().equals(" ")) {

                                    Toast.makeText(MainActivity.this, "Ne sohraneno", Toast.LENGTH_SHORT).show();
                                } else {

                                    getCityData(cityweather.getName());



                                    recycler.scrollToPosition(RController.getInstance().getCities().size() - 1);

                                }
                            }
                        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }

    public void setRealmAdapter(RealmResults<LocationMapObject> cities) {

        RealmCitiesAdapter realmAdapter = new RealmCitiesAdapter(this.getApplicationContext(), cities, true);

        adapter.setRealmAdapter(realmAdapter);

        adapter.notifyDataSetChanged();

    }

    private void setupRecycler() {

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setAdapter(adapter);
    }

    private void setRealmData() {

        ArrayList<LocationMapObject> cityWeathers = new ArrayList<>();

        LocationMapObject cityWeather = new LocationMapObject();

        cityWeather.setName("Samara");
        cityWeathers.add(cityWeather);
        cityWeather.setId(499099);

        cityWeather = new LocationMapObject();


        cityWeather.setName("Moscow");
        cityWeathers.add(cityWeather);
        cityWeather.setId(524901);

        cityWeather = new LocationMapObject();

        cityWeather.setName("Phuket");
        cityWeathers.add(cityWeather);
        cityWeather.setId(1151254);

        cityWeather = new LocationMapObject();

        cityWeather.setName("Singapore");
        cityWeathers.add(cityWeather);
        cityWeather.setId(1880252);

        cityWeather = new LocationMapObject();

        cityWeather.setName("Taipei");
        cityWeathers.add(cityWeather);
        cityWeather.setId(1668341);

        for (LocationMapObject c : cityWeathers) {

            realm.beginTransaction();
            realm.copyToRealm(c);
            realm.commitTransaction();
            getCityData(c.getName());


        }

        Prefs.with(this).setPreLoad(true);
    }


    private void getCityData(String name) {
        String apiUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + name + "&APPID=1ce82a223bcfeccc03da7793600b029b&units=metric";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();


                LocationMapObject locationMapObject = gson.fromJson(response, LocationMapObject.class);

               if (null == locationMapObject) {
                    Toast.makeText(getApplicationContext(), "Nothing was returned", Toast.LENGTH_LONG).show();
                } else {
                  //  Toast.makeText(getApplicationContext(), "Response Good", Toast.LENGTH_LONG).show();
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    realm.insertOrUpdate(locationMapObject);
                    realm.commitTransaction();
                    adapter.notifyDataSetChanged();
               }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }
}
