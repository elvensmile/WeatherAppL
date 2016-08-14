package ru.example.weatherappl.Adapters;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;
import io.realm.RealmObject;
import io.realm.RealmResults;


public class RealmModelAdapter<T extends RealmObject> extends RealmBaseAdapter<T> {

    public RealmModelAdapter(Context context, OrderedRealmCollection<T> data, boolean automaticUpdate) {

        super(context, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return null;
    }
}