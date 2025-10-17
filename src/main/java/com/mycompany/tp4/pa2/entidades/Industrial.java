/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tp4.pa2.entidades;

/**
 *
 * @author aquin
 */
public class Industrial extends Electricidad{

    private double temperaturaMinima;
    private double temperaturaMaxima;

    public Industrial(double temperaturaMinima, double temperaturaMaxima, double potenciaMaxima, String nombre, double precio, TipoArticulo tipoArticulo) {
        super(potenciaMaxima, nombre, precio, tipoArticulo);
        this.temperaturaMinima = temperaturaMinima;
        this.temperaturaMaxima = temperaturaMaxima;
    }

    /**
     * @return the temperaturaMinima
     */
    public double getTemperaturaMinima() {
        return temperaturaMinima;
    }

    /**
     * @param temperaturaMinima the temperaturaMinima to set
     */
    public void setTemperaturaMinima(double temperaturaMinima) {
        this.temperaturaMinima = temperaturaMinima;
    }

    /**
     * @return the temperaturaMaxima
     */
    public double getTemperaturaMaxima() {
        return temperaturaMaxima;
    }

    /**
     * @param temperaturaMaxima the temperaturaMaxima to set
     */
    public void setTemperaturaMaxima(double temperaturaMaxima) {
        this.temperaturaMaxima = temperaturaMaxima;
    }

    @Override
    public String toString() {
        return "Temperatura miníma: " + temperaturaMinima + "\nTemperatura máxima: " + temperaturaMaxima;
    }
    
    
    
    
}
