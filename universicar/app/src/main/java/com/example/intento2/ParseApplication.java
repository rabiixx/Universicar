package com.example.intento2;

import android.app.Application;

import com.example.intento2.Models.Viaje;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Parse Models Registration
        ParseObject.registerSubclass(Viaje.class);

        // Initialize Parse
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("universicar") // should correspond to APP_ID env variable
                //.clientKey(null)  // set explicitly unless clientKey is explicitly configured on Parse server
                //.clientBuilder(builder)
                .server("https://rabiixxserver.herokuapp.com/parse/").build());

    }
}
