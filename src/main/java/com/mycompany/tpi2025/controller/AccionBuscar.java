/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.mycompany.tpi2025.controller;

/**
 *
 * @author aquin
 */
public enum AccionBuscar {
    SELECCIONAR("SELECCIONAR"),
    DETALLES("DETALLES"),
    ELIMINAR("ELIMINAR");
    
    private final String texto;
    
    private AccionBuscar(String texto) {
        this.texto = texto;
    }
    
    public String getTexto() {
        return texto;
    }
}
