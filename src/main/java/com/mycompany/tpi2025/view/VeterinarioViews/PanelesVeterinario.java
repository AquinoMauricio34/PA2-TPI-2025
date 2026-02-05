/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.mycompany.tpi2025.view.VeterinarioViews;

/**
 *
 * @author aquin
 */
public enum PanelesVeterinario {
    DATOS_PRINCIPALES("DATOS_PRINCIPALES"),
    MI_PERFIL("MI_PERFIL"),
    // Administrador

    VER_HISTORIAL("VER_HISTORIAL"),
    CREAR_DIAGNOSTICO("CREAR_DIAGNOSTICO"),
    VER_DIAGNOSTICO("VER_DIAGNOSTICO"),
    BUSCAR_FAMILIA_APTITUD("BUSCAR_FAMILIA_APTITUD"),
    MODIFICAR_GATO("MODIFICAR_GATOS"),
    BUSCAR_HOGAR_APTITUD("BUSCAR_HOGAR_APTITUD");

    private final String texto;

    private PanelesVeterinario(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }
}
