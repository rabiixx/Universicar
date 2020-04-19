package com.example.universicar.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.Serializable;

@ParseClassName("Coche")
public class Coche extends ParseObject implements Serializable {

    public static final String USER_ID_KEY = "userId";


    public Coche() {
        super();
    }

    public void setMarca(String marca) {
        put("marca", marca);
    }

    public String getMarca() { return getString("marca"); }

    public void setTipoCoche(String tipo) {
        put("tipo", tipo);
    }

    public String getTipoCoche() { return getString("tipo"); }

    public void setColor(String color) {
        put("color", color);
    }

    public String getColor() {
        return getString("color");
    }

    public ParseUser getConductor() {
        return getParseUser("Conductor");
    }

    public void setConductor(ParseUser conductor) {
        put("Conductor", conductor);
    }


   /* public int getNPlazas() {
        return nPlazas;
    }

    public void setNPlazas(int nPlazas) {
        this.nPlazas = nPlazas;
    }

    public int getEstadoVehiculo() {
        return estadoVehiculo;
    }

    public void setEstadoVehiculo(int estadoVehiculo) {
        this.estadoVehiculo = estadoVehiculo;
    }



    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }*/
}

