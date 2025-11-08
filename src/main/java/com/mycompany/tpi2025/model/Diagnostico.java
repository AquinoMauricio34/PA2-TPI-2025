/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author aquin
 */
@Entity
public class Diagnostico implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "tratamiento_id")
    private Tratamiento tratamiento=null;
    
    @ManyToOne
    @JoinColumn(name = "historial_id")
    private HistorialGato historial=null;


    private String descripcion; // descripcion del estado general del gato (como se encuentra y como esta, los sintomas que tienen, como mejoró, etc.)
    private String diagnostico; // diagnóstico médico
    private LocalDate fecha_diagnostico;

    public Diagnostico() {}

    public Diagnostico(String descripcion, String diagnostico, LocalDate fecha_diagnostico, Tratamiento tratamiento) {
        this.descripcion = descripcion;
        this.diagnostico = diagnostico;
        this.fecha_diagnostico = fecha_diagnostico;
        this.tratamiento = tratamiento;
    }

    public Diagnostico(String descripcion, String diagnostico, LocalDate fecha_diagnostico) {
        this(descripcion, diagnostico, fecha_diagnostico, null);
    }

    public Long getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public LocalDate getFecha_diagnostico() {
        return fecha_diagnostico;
    }

    public Tratamiento getTratamiento() {
        return tratamiento;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public void setFecha_diagnostico(LocalDate fecha_diagnostico) {
        this.fecha_diagnostico = fecha_diagnostico;
    }

    public void setTratamiento(Tratamiento tratamiento) {
        this.tratamiento = tratamiento;
    }

    public HistorialGato getHistorial() {
        return historial;
    }

    public void setHistorial(HistorialGato historial) {
        this.historial = historial;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    
    
}
