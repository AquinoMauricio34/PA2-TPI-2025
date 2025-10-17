/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tp4.pa2.entidades;

/**
 *
 * @author aquin
 */
public abstract class Electricidad extends Articulo {
    
    private double potenciaMaxima;

    public Electricidad(double potenciaMaxima, String nombre, double precio, TipoArticulo tipoArticulo) {
        super(nombre, precio, tipoArticulo);
        this.potenciaMaxima = potenciaMaxima;
    }

    /**
     * @return the potenciaMaxima
     */
    public double getPotenciaMaxima() {
        return potenciaMaxima;
    }

    /**
     * @param potenciaMaxima the potenciaMaxima to set
     */
    public void setPotenciaMaxima(double potenciaMaxima) {
        this.potenciaMaxima = potenciaMaxima;
    }

    @Override
    public String toString() {
        return "Electricidad{" + "potenciaMaxima=" + potenciaMaxima + '}';
    }
    
    
    
}
