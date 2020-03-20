package com.example.universicar.Models;

import com.parse.ParseClassName;
import com.parse.ParseUser;
import com.parse.ParseObject;

@ParseClassName("Coche")
public class Opinion extends ParseObject {

    public Opinion() {
        super();
    }

    public void setTitulo(String titulo) {
        put("titulo", titulo);
    }

    public String getTitulo() { return getString("marca"); }

    public void setDescripcion(String descripcion) {
        put("descripcion", descripcion);
    }

    public String getDescripcion() { return getString("descripcion"); }

    public void setCalidadConduccion(String calidadConduccion) {
        put("calidadConduccion", calidadConduccion);
    }

    public void setPuntuacion(Double puntuacion) {
        put("puntuacion", puntuacion);
    }

    public Double getPuntuacion() {
        return getDouble("puntuacion");
    }

    public String getCalidadConduccion() {
        return getString("calidadConduccion");
    }

    public void setUsuario(ParseUser user) {
        put("usuario", user);
    }

    public ParseUser getUsuario() {
        return getParseUser("usuario");
    }
}
