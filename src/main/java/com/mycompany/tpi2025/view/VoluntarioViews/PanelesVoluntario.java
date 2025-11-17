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
    MI_PERFIL("MI_PERFIL"),
    CREAR_GATO("CREAR_GATOS"),
    BUSCAR_HOGAR_ASIGNACION("BUSCAR_HOGAR_ASIGNACION"),
    BUSCAR_FAMILIA_ASIGNACION("BUSCAR_FAMILIA_ASIGNACION"),
    POSTULARSE("POSTULARSE"),
    ASIGNAR_GATO_FAMILIA("ASIGNAR_GATO_FAMILIA"),
    ASIGNAR_GATO_HOGAR("ASIGNAR_GATO_HOGAR"),
    BUSCAR_VOLUNTARIO_TAREA_REALIZADA("BUSCAR_VOLUNTARIO_TAREA_REALIZADA"),
    TAREA_REALIZADA("TAREA_REALIZADA"),
    MODIFICAR_GATO("MODIFICAR_GATOS"),
    VISITA("VISITA");
    
    private final String texto;
    
    private PanelesVoluntario(String texto) {
        this.texto = texto;
    }
    
    public String getTexto() {
        return texto;
    }
}