package com.example.intento2.Models;

import com.parse.ParseObject;
import com.parse.ParseClassName;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@ParseClassName("Viaje")
public class Viaje extends ParseObject {
    private Conductor conductor;
    private String fecha;
    private String horaSalida;
    private String duracion;
    private String origen;
    private String destino;
    private int nPlazasDisp;
    private String[] estado;
    private int precioPorUsuario;

    // Default Constructor
    public Viaje() {
        super();
    }

    // Contructor
    public Viaje(String origen, String destino) {
        super();
        setOrigen(origen);
        setDestino(destino);
    }

    public Conductor getConductor() {
        return conductor;
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

    public void setFecha(Date fecha) {
        put("fecha", fecha);
    }

    public Integer getHoraSalida() {
        return getInt("hora");
    }

    public void setHoraSalida(String horaSalida) {
        put("horaSalida", horaSalida);
    }

    public Integer getDuracion() {
        return getInt("duracion");
    }

    public void setDuracion(String duracion) {
        put("duracion", duracion);
    }

    public String getOrigen() {
        return getString("origen");
    }

    public void setOrigen(String origen) {
        put("origen", origen);
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        put("destino", destino);
    }

    public int getnPlazasDisp() {
        return nPlazasDisp;
    }

    public void setnPlazasDisp(int nPlazasDisp) {
        this.nPlazasDisp = nPlazasDisp;
    }

    public String[] getEstado() {
        return estado;
    }

    public void setEstado(String[] estado) {
        this.estado = estado;
    }

    public int getPrecioPorUsuario() {
        return precioPorUsuario;
    }

    public void setPrecioPorUsuario(int precioPorUsuario) {
        this.precioPorUsuario = precioPorUsuario;
    }
}
