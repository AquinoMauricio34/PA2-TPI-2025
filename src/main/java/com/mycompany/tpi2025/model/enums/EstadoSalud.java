/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.mycompany.tpi2025.model.enums;

/**
 *
 * @author aquin
 */
public enum EstadoSalud {
    ENFERMO("ENFERMO"),
    SANO("SANO"),
    EN_TRATAMIENTO("EN_TRATAMIENTO"),
    ESTERILIZADO("ESTERILIZADO");
    
    private final String texto;
    
    private EstadoSalud(String texto) {
        this.texto = texto;
    }
    
    public String getTexto() {
        return texto;
    }
}
