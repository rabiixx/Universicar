package com.example.intento2.Models;

import com.parse.ParseObject;
import com.parse.ParseClassName;
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

    /*public Conductor getConductor() {
        return conductor;
    }*/

    public void setConductor(ParseUser conductor) {
        put("Conductor", conductor);
    }

    public String getFecha() {
        Date fecha = getDate("fecha");
        String myFormat = "dd/MM/yy hh:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        return (sdf.format(fecha));
    }

    public void setFecha(Date fecha) {
        put("fecha", fecha);
    }

    public Integer getHoraSalida() {
        return getInt("hora");
    }

    public void setHoraSalida(String horaSalida) {
        put("horaSalida", horaSalida);
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

    public int getnPlazasDisp() {
        return getInt("nPlazasDisp");
    }

    public void setnPlazasDisp(int nPlazasDisp) { put("nPlazasDisp", nPlazasDisp); }

//    public String[] getEstado() {
//        return estado;
//    }

//    public void setEstado(String[] estado) {
//        this.estado = estado;
//    }

    public int getPrecio() { return getInt("precio"); }

    public void setPrecio(int precio) { put("precio", precio); }
}
