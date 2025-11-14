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
import jakarta.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aquin
 */
@Entity
public class Zona implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private String localizacion;
    
//    @OneToMany(mappedBy = "zona", cascade = CascadeType.ALL, orphanRemoval = false)
//    private List<Gato> gatos = new ArrayList<>();


    public Zona() {
    }

    public Zona(String localizacion) {
        this.localizacion = localizacion;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }
    
    
    
//    public void addGato(Gato g) {
//        gatos.add(g);
//        g.setZona(this);
//    }
//
//    public void removeGato(Gato g) {
//        gatos.remove(g);
//        g.setZona(null);
//    }

    @Override
    public String toString() {
        return "Zona{" + "id=" + id + ", localizacion=" + localizacion + '}';
    }

}
