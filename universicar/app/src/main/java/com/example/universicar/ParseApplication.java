package com.example.universicar;

import android.app.Application;

import com.example.universicar.Models.Coche;
import com.example.universicar.Models.Imagen;
import com.example.universicar.Models.Opinion;
import com.example.universicar.Models.Viaje;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Parse Models Registration
        ParseObject.registerSubclass(Viaje.class);
        ParseObject.registerSubclass(Coche.class);
        ParseObject.registerSubclass(Imagen.class);
        ParseObject.registerSubclass(Opinion.class);


        // Initialize Parse
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("universicar")
                .server("https://rabiixxserver.herokuapp.com/parse/").build());
        ParseUser.enableAutomaticUser();



    }
}
