/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;

/**
 *
 * @author aquin
 */
@Entity
public class Tarea implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String fecha;
    private String hora;
    private String nombreUsuarioVoluntario;
    private String ubicacion;
    private String tipo;

    public Tarea() {
    }

    public Tarea(String fecha, String hora, String nombreUsuarioVoluntario, String ubicacion, String tipo) {
        this.fecha = fecha;
        this.hora = hora;
        this.nombreUsuarioVoluntario = nombreUsuarioVoluntario;
        this.ubicacion = ubicacion;
        this.tipo = tipo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombreUsuarioVoluntario() {
        return nombreUsuarioVoluntario;
    }

    public void setNombreUsuarioVoluntario(String nombreUsuarioVoluntario) {
        this.nombreUsuarioVoluntario = nombreUsuarioVoluntario;
    }

}
