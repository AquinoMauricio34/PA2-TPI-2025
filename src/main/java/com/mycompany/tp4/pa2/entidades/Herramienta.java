/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tp4.pa2.entidades;

/**
 *
 * @author aquin
 */
public class Herramienta extends Articulo {
    
    private String descripcion;
    
    public Herramienta(String nombre, double precio, TipoArticulo tipoArticulo, String descripcion) {
        super(nombre, precio, tipoArticulo);
        this.descripcion = descripcion;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Descripcion:\n" + descripcion;
    }
    
    
}
