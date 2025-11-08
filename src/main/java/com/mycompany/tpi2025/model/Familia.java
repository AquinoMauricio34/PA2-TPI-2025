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
public class Familia extends Usuario{
    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Gato> gatos;

    //para que el hibernate funcione necesita de un constructor vacio
    public Familia() {super();}
    
    public Familia(String nombre, String contrasenia, String telefono, String usuario){
        super(nombre, contrasenia, telefono, usuario);
        this.gatos = new ArrayList<>();
    }

    public void addGato(Gato gato) {
        this.gatos.add(gato);
        //hacemos que la familia sea esta misma instancia
        gato.setUsuario(this);
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
    
    
    /*
    
        - regsitrarse (creo que esto es parte del login más que de la clase, la clase no puede crear a familia, se hace de forma externa)
        - ver gatos
        - postularse
     
     */
    
    
}
