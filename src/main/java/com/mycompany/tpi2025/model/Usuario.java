/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.model;
import com.mycompany.tpi2025.controller.DatosTabla;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import java.io.Serializable;

/**
 *
 * @author aquin
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario implements Serializable,DatosTabla {
    
    private String nombre;
    private String contrasenia;
    private String telefono;
    private String tipoUsuario;
    
    @Id
    private String nombreUsuario;
    
    public Usuario(){
        this.tipoUsuario = this.getClass().getSimpleName();
    }

    public Usuario(String nombre, String contrasenia, String telefono, String usuario) {
        this.nombre = nombre;
        this.contrasenia = contrasenia;
        this.telefono = telefono;
        this.nombreUsuario = usuario;
        this.tipoUsuario = this.getClass().getSimpleName();
    }

    public String getNombre() {
        return nombre;
    }

    public String getContrasena() {
        return contrasenia;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    //en caso de cambio de contrasenia
    public void setContrasenia(String contrasena) {
        this.contrasenia = contrasena;
    }

    //en caso de cambio de tel
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    @Override
    public Object[] obtenerDatos() {
        return new Object[] { nombre, nombreUsuario, telefono };
    }
    
    
    
}
