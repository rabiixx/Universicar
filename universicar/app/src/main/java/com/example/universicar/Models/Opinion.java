package com.example.universicar.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.Serializable;

@ParseClassName("Opinion")
public class Opinion extends ParseObject implements Serializable {

    public Opinion() {
        super();
    }

    public void setTitulo(String titulo) {
        put("titulo", titulo);
    }

    public String getTitulo() { return getString("titulo"); }

    public void setDescripcion(String descripcion) {
        put("descripcion", descripcion);
    }

    public String getDescripcion() { return getString("descripcion"); }

    public void setHabilidadConduccion(int habilidadConduccion) {
        put("habilidadConduccion", habilidadConduccion);
    }

    public int getHabilidadConduccion() {
        return getInt("habilidadConduccion");
    }

    public void setPuntuacion(Double puntuacion) {
        put("puntuacion", puntuacion);
    }

    public Double getPuntuacion() {
        return getDouble("puntuacion");
    }


    public void setCreador(ParseUser user) {
        put("creador", user);
    }

    public ParseUser getCreador() {
        return getParseUser("creador");
    }

    public void setUsuario(ParseUser user) {
        put("usuario", user);
    }

    public ParseUser getUsuario() {
        return getParseUser("usuario");
    }



}
