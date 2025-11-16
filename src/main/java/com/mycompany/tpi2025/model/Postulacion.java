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
public class Postulacion implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    String postulante;
    long idGato;

    public Postulacion() {
    }

    public Postulacion(String postulante, long gato) {
        this.postulante = postulante;
        this.idGato = gato;
    }

    public String getPostulante() {
        return postulante;
    }

    public void setPostulante(String postulante) {
        this.postulante = postulante;
    }

    public long getIdGato() {
        return idGato;
    }

    public void setIdGato(long idGato) {
        this.idGato = idGato;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
    
    
}
