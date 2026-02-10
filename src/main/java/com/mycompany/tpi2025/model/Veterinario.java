/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.model;

import jakarta.persistence.Entity;

/**
 *
 * @author aquin
 */
@Entity
public class Veterinario extends Usuario {

    //para que el hibernate funcione necesita de un constructor vacio
    public Veterinario() {
        super();
    }

    public Veterinario(String nombre, String contrasenia, String telefono, String usuario) {
        super(nombre, contrasenia, telefono, usuario);
    }

}
