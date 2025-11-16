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
import java.util.Optional;

/**
 *
 * @author aquin
 */
@Entity
public class Hogar extends Usuario{
    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private final List<Gato> gatos = new ArrayList<>();
    
    private boolean transitorio;
    private boolean aptoAdopcion=false;

    public Hogar() {}

    public Hogar(String nombre, String contrasenia, String telefono, String usuario, boolean transitorio) {
        super(nombre, contrasenia, telefono, usuario);
        this.transitorio = transitorio;
    }
    
    public void addGato(Gato gato) {
        //hacemos que la familia sea esta misma instancia
        gato.setUsuario(this);
        this.gatos.add(gato);
    }
    
    public void removeGato(long id) throws Exception{
        //no no removio nada, devuelve falso, si !falso, lanza la exception
        if(!gatos.removeIf(v -> v.getId() == id)) throw new Exception("No la familia no tiene un gato con el id: "+id);
    }

    public Optional<Gato> getGato(long id) {
        return gatos.stream().filter(v -> v.getId() == id).findFirst();
    }
    
    public List<Gato> getAllGatos(){
        return new ArrayList<>(gatos);
    }

    public boolean isTransitorio() {
        return transitorio;
    }

    public void setTransitorio(boolean transitorio) {
        this.transitorio = transitorio;
    }

    public boolean isAptoAdopcion() {
        return aptoAdopcion;
    }

    public void setAptoAdopcion(boolean aptoAdopcion) {
        this.aptoAdopcion = aptoAdopcion;
    }
    
    @Override
    public Object[] obtenerDatos() {
        return new Object[] { this.getNombre(), this.getNombreUsuario(), this.getTelefono(), this.isAptoAdopcion()? "SI":"NO"};
    }
    
}
