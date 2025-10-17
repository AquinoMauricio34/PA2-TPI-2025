/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tp4.pa2.entidades;

/**
 *
 * @author aquin
 */
public abstract class Articulo {
    private String nombre;
    private double precio;
    private TipoArticulo tipoArticulo;

    public Articulo(String nombre, double precio, TipoArticulo tipoArticulo) {
        this.nombre = nombre;
        this.precio = precio;
        this.tipoArticulo = tipoArticulo;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the precio
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Nombre:" + nombre + "\nPrecio: $" + precio + "\nTipo de Articulo: " + tipoArticulo;
    }

    public TipoArticulo getTipoArticulo() {
        return tipoArticulo;
    }
    
    
    
}
