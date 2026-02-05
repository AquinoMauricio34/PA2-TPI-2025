/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aquin
 */
@Entity
public class Diagnostico implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "diagnostico", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Tratamiento> tratamientos = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "historial_id")
    private HistorialGato historial = null;

    private String descripcion; // descripcion del estado general del gato (como se encuentra y como esta, los sintomas que tienen, como mejoró, etc.)
    private String diagnostico; // diagnóstico médico
    private LocalDate fecha_diagnostico;

    public Diagnostico() {
    }

    public Diagnostico(String descripcion, String diagnostico, LocalDate fecha_diagnostico) {
        this.descripcion = descripcion;
        this.diagnostico = diagnostico;
        this.fecha_diagnostico = fecha_diagnostico;
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

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public void setFecha_diagnostico(LocalDate fecha_diagnostico) {
        this.fecha_diagnostico = fecha_diagnostico;
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

    public List<Tratamiento> getTratamientos() {
        return tratamientos;
    }

    public void setTratamientos(List<Tratamiento> tratamientos) {
        this.tratamientos = tratamientos;
    }

    public void addTratamiento(Tratamiento tratamiento) {

        tratamiento.setDiagnostico(this);

        tratamientos.add(tratamiento);

    }

}
