/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aquin
 */
@Entity
public class Hogar extends Usuario{
    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private final List<Gato> gatos = new ArrayList<>();

    public Hogar() {}

    public Hogar(String nombre, String contrasenia, String telefono, String usuario) {
        super(nombre, contrasenia, telefono, usuario);
    }

    

    
}
