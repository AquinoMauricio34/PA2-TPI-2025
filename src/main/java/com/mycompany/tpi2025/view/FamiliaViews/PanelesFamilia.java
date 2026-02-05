/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.mycompany.tpi2025.view.FamiliaViews;

/**
 *
 * @author aquin
 */
public enum PanelesFamilia {
    DATOS_PRINCIPALES("DATOS_PRINCIPALES"),
    MI_PERFIL("MI_PERFIL"),
    POSTULARSE("POSTULARSE");

    private final String texto;

    private PanelesFamilia(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }
}
