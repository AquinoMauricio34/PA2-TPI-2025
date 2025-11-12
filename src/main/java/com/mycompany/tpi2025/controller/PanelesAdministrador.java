/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.mycompany.tpi2025.controller;

/**
 *
 * @author aquin
 */
public enum PanelesAdministrador {
    DATOS_PRINCIPALES("DATOS_PRINCIPALES"),

    // Administrador
    CREAR_ADMINISTRADOR("CREAR_ADMINISTRADOR"),
    BUSCAR_ADMINISTRADOR("BUSCAR_ADMINISTRADOR"),
    MODIFICAR_ADMINISTRADOR("MODIFICAR_ADMINISTRADOR"),
    ELIMINAR_ADMINISTRADOR("ELIMINAR_ADMINISTRADOR"),

    // Familia
    CREAR_FAMILIA("CREAR_FAMILIA"),
    BUSCAR_FAMILIA("BUSCAR_FAMILIA"),
    MODIFICAR_FAMILIA("MODIFICAR_FAMILIA"),
    ELIMINAR_FAMILIA("ELIMINAR_FAMILIA"),

    // Veterinario
    CREAR_VETERINARIO("CREAR_VETERINARIO"),
    BUSCAR_VETERINARIO("BUSCAR_VETERINARIO"),
    MODIFICAR_VETERINARIO("MODIFICAR_VETERINARIO"),
    ELIMINAR_VETERINARIO("ELIMINAR_VETERINARIO"),

    // Voluntario
    CREAR_VOLUNTARIO("CREAR_VOLUNTARIO"),
    BUSCAR_VOLUNTARIO("BUSCAR_VOLUNTARIO"),
    MODIFICAR_VOLUNTARIO("MODIFICAR_VOLUNTARIO"),
    ELIMINAR_VOLUNTARIO("ELIMINAR_VOLUNTARIO"),

    // Hogar
    CREAR_HOGAR("CREAR_HOGAR"),
    BUSCAR_HOGAR("BUSCAR_HOGAR"),
    MODIFICAR_HOGAR("MODIFICAR_HOGAR"),
    ELIMINAR_HOGAR("ELIMINAR_HOGAR");
    
    private final String texto;
    
    private PanelesAdministrador(String texto) {
        this.texto = texto;
    }
    
    public String getTexto() {
        return texto;
    }
}