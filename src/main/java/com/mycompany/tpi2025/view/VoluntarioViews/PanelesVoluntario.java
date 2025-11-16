/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.mycompany.tpi2025.view.VoluntarioViews;

/**
 *
 * @author aquin
 */
public enum PanelesVoluntario {
    DATOS_PRINCIPALES("DATOS_PRINCIPALES"),
    CREAR_GATO("CREAR_GATOS");
    
    private final String texto;
    
    private PanelesVoluntario(String texto) {
        this.texto = texto;
    }
    
    public String getTexto() {
        return texto;
    }
}
