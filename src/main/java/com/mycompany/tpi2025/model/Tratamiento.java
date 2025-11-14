/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author aquin
 */
@Entity
public class Tratamiento implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "diagnostico_id")
    private Diagnostico diagnostico=null;

    private String descripcion;
    private LocalDate fecha_inicio;
    private LocalDate fecha_fin;
    private Boolean abandono_tratamiento;

    public Tratamiento() {}
    

    public Tratamiento(String descripcion, LocalDate fecha_inicio, LocalDate fecha_fin) {
        this.descripcion = descripcion;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.abandono_tratamiento = false;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFecha_inicio(LocalDate fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public void setFecha_fin(LocalDate fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public void setAbandono_tratamiento(Boolean abandono_tratamiento) {
        this.abandono_tratamiento = abandono_tratamiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDate getFecha_inicio() {
        return fecha_inicio;
    }

    public LocalDate getFecha_fin() {
        return fecha_fin;
    }

    public Boolean getAbandono_tratamiento() {
        return abandono_tratamiento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Diagnostico getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(Diagnostico diagnostico) {
        this.diagnostico = diagnostico;
    }
    
    
    
    
    
}
