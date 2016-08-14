package ru.example.weatherappl.Helper;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;

import io.realm.Realm;
import io.realm.RealmResults;
import ru.example.weatherappl.Model.LocationMapObject;


public class RController {
    private static RController instance;
    private final Realm realm;

    public RController(Application application){

        realm = Realm.getDefaultInstance();
    }
    public static RController with(Fragment fragment){

        if (instance == null) {
            instance = new RController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RController with(Activity activity){

        if (instance == null) {
            instance = new RController(activity.getApplication());
        }
        return instance;
    }

    public static RController with(Application application){

        if (instance == null) {
            instance = new RController(application);
        }
        return instance;
    }

    public static RController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }





    public RealmResults<LocationMapObject> getCities() {

        return realm.where(LocationMapObject.class).findAll();
    }

    public LocationMapObject getCity(String name) {

        return realm.where(LocationMapObject.class).equalTo("name", name).findFirst();
    }




}
