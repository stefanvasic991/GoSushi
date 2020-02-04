package com.easyswitch.gosushi;

import android.os.Build;

import androidx.multidex.MultiDexApplication;

import com.google.gson.Gson;
import com.jakewharton.threetenabp.AndroidThreeTen;

import timber.log.Timber;

public class App extends MultiDexApplication {


    private static App INSTANCE = new App();
    private Gson gson;

    public static App getInstance() {
        if (INSTANCE == null) {
            synchronized (App.class) {
                if (INSTANCE == null) {
                    INSTANCE = new App();
                }
            }
        }
        return INSTANCE;
    }

    public App() {
        INSTANCE = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();


        if (BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());

        AndroidThreeTen.init(this);

        //create notification channels
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        }
    }

    public Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }


}
