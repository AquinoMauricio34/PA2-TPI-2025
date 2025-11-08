/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.mycompany.tpi2025.controller;

/**
 *
 * @author aquin
 */
public enum Paneles {
    DATOS_PRINCIPALES("DATOS_PRINCIPALES"),
    CREAR_ADMINISTRADOR("CREAR_ADMINISTRADOR"),
    CREAR_FAMILIA("CREAR_FAMILIA"),
    CREAR_VETERINARIO("CREAR_VETERINARIO"),
    CREAR_VOLUNTARIO("CREAR_VOLUNTARIO"),
    CREAR_HOGAR("CREAR_HOGAR");
    
    private final String texto;
    
    private Paneles(String texto) {
        this.texto = texto;
    }
    
    public String getTexto() {
        return texto;
    }
}