package com.example.universicar.Models;

import android.widget.Toast;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

@ParseClassName("Imagen")
public class Imagen extends ParseObject {

    public Imagen() {
    }

    public ParseFile getMedia() {
        return getParseFile("imagenPerfil");
    }

    public void setMedia(ParseFile parseFile) {
        put("imagenPerfil", parseFile);
    }

}
