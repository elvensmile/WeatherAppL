package ru.example.weatherappl.Adapters;


import android.content.Context;


import io.realm.RealmResults;
import ru.example.weatherappl.Model.LocationMapObject;


public class RealmCitiesAdapter extends RealmModelAdapter<LocationMapObject> {

    public RealmCitiesAdapter(Context context, RealmResults<LocationMapObject> realmResults, boolean automaticUpdate) {

        super(context, realmResults, automaticUpdate);
    }
}