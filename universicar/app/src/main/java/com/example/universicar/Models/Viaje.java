package com.example.universicar.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@ParseClassName("Viaje")
public class Viaje extends ParseObject implements Serializable {

    // Default Constructor
    public Viaje() {
        super();
    }

    // Constructor
    public Viaje(String origen, String destino) {
        super();
        setOrigen(origen);
        setDestino(destino);
    }

    public ParseUser getConductor() {
        return getParseUser("Conductor");
    }

    public void setConductor(ParseUser conductor) {
        put("Conductor", conductor);
    }

    public String getFecha() {
        Date fecha = getDate("fecha");
        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        return (sdf.format(fecha));
    }

    public Date getFechaDate() {
        return getDate("fecha");
    }

    public void setFecha(Date fecha) {
        put("fecha", fecha);
    }

    public String getHoraSalida() {
        Date fecha = getDate("fecha");
        String myFormat = "hh:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        return (sdf.format(fecha));
    }

    public String getOrigen() {
        return getString("origen");
    }

    private void setOrigen(String origen) {
        put("origen", origen);
    }

    public String getDestino() {
        return getString("destino");
    }

    private void setDestino(String destino) {
        put("destino", destino);
    }

    public int getNPlazas() {
        return getInt("nPlazas");
    }

    public void setNPlazas(int nPlazas) { put("nPlazas", nPlazas); }

    public int getNPlazasDisp() {
        return getInt("nPlazasDisp");
    }

    public void setNPlazasDisp(int nPlazasDisp) { put("nPlazasDisp", nPlazasDisp); }

    public ParseRelation<ParseUser> getPasajeros() {
        return getRelation("pasajeros");
    }

    public void addPasajero(ParseUser pasajero) {
        getPasajeros().add(pasajero);
        saveInBackground();
    }

    public void removePasajero(ParseUser pasajero) {
        getPasajeros().remove(pasajero);
        saveInBackground();
    }

//    public String[] getEstado() {
//        return estado;
//    }

//    public void setEstado(String[] estado) {
//        this.estado = estado;
//    }

    public int getPrecio() {
        return getInt("precio");
    }

    public void setPrecio(int precio) { put("precio", precio); }
}
