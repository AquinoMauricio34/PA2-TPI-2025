/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.mycompany.tpi2025.model.enums;

import java.util.Set;

/**
 *
 * @author aquin
 */
public enum Rol {
    FAMILIA(Set.of(
            Permiso.MODIFICAR_DATOS_FAMILIA,
            Permiso.POSTULARSE_ADOPCION,
            Permiso.REGISTRAR_FAMILIA,
            Permiso.VER_GATOS_DISPONIBLES
    )),
    VOLUNTARIO(Set.of(
            Permiso.ACTUALIZAR_ESTADO_GATO,
            Permiso.ANIADIR_PUNTO_AVISTAMIENTO,
            Permiso.ASIGNAR_GATO_FAMILA,
            Permiso.ASIGNAR_QR,
            Permiso.MEDIFICAR_DATOS_VOLUNTARIO,
            Permiso.REGISTRAR_GATO,
            Permiso.REGISTRAR_TAREA_REALIZADA,
            Permiso.REGISTRAR_VISITA_SEGUIMIENTO,
            Permiso.VER_GATOS_DISPONIBLES,
            Permiso.VER_MAPA,
            Permiso.VER_PUNTOS_AVISTAMIENTO,
            Permiso.VER_ZONAS_GATOS
    )),
    VETERINARIO(Set.of(
            Permiso.ACTUALIZAR_DIAGNOSTICO_GATO,
            Permiso.ACTUALIZAR_ESTADO_GATO,
            Permiso.ACTUALIZAR_TRATAMIENTO,
            Permiso.EMITIR_CERTIFICADO_ADPTITUD,
            Permiso.MODIFICAR_DATOS_VETERINARIO,
            Permiso.REGISTRAR_VISITA_SEGUIMIENTO,
            Permiso.SUBIR_ESTUDIO,
            Permiso.VER_HISTORIAL_MEDICO_GATO,
            Permiso.VER_REPUTACION_FAMILIA,
            Permiso.MODIFICAR_REPUTACION_FAMILIA
    )),
    ADMINISTRADOR(Set.of(Permiso.TODO));
    
    private final Set<Permiso> permisos;

    private Rol(Set<Permiso> permisos) {
        this.permisos = permisos;
    }
    
    public Set<Permiso> getPermiso(){
        return permisos;
    }
}
