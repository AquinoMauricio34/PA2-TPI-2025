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
public class VisitaSeguimiento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nombreUsuarioVoluntario;
    private long idGato;
    private String fecha;
    private String descripcion;

    public VisitaSeguimiento() {
    }

    public VisitaSeguimiento(String idVoluntario, long idGato, String fecha, String descripcion) {
        this.nombreUsuarioVoluntario = idVoluntario;
        this.idGato = idGato;
        this.fecha = fecha;
        this.descripcion = descripcion;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIdVoluntario() {
        return nombreUsuarioVoluntario;
    }

    public void setIdVoluntario(String nombreUsuarioVoluntario) {
        this.nombreUsuarioVoluntario = nombreUsuarioVoluntario;
    }

    public long getIdGato() {
        return idGato;
    }

    public void setIdGato(long idGato) {
        this.idGato = idGato;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
