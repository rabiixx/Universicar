package com.example.universicar.Models;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;



@ParseClassName("Imagen")
public class Imagen extends ParseObject {

    private static final String TAG = "universicar";


    public Imagen() {

    }

    public void setImageName(String imageName){
        put("imageName", imageName);
    }

    public ParseFile getMedia() {
        return getParseFile("imagenPerfil");
    }

    public void setMedia(ParseFile parseFile) {
        Log.i(TAG, "HACK2: " + parseFile.toString());
        put("imagenPerfil", parseFile);

    }

}
