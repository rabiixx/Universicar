package com.example.intento2.Models;

public class Coche {
    private int nPlazas;
    private String marca;
    private String modelo;
    private int estadoVehiculo;
    private String color;
    private String descripcion;

    public int getnPlazas() {
        return nPlazas;
    }

    public void setnPlazas(int nPlazas) {
        this.nPlazas = nPlazas;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getEstadoVehiculo() {
        return estadoVehiculo;
    }

    public void setEstadoVehiculo(int estadoVehiculo) {
        this.estadoVehiculo = estadoVehiculo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
