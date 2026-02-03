/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.model;

import com.mycompany.tpi2025.model.enums.EstadoSalud;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.io.Serializable;

/**
 *
 * @author aquin
 */
@Entity
public class Gato implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @Enumerated(EnumType.STRING)
    private EstadoSalud estadoSalud;
    
    
    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "historial_id")
    private HistorialGato historial=null;
    
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario=null;
    
    @ManyToOne
    @JoinColumn(name = "zona_id")
    private Zona zona=null;
    
    private String qr;
    private String nombre; //opcional
    private String color;
    private String caracteristicas;
    
    
    public Gato(){}
    
    public Gato(String qr, String nombre, String color, Zona zona, String caracteristicas, EstadoSalud estadoSalud) {
        this.qr = qr;
        this.caracteristicas = caracteristicas;
        this.nombre = nombre;
        this.color = color;
        this.zona = zona;
        this.estadoSalud = estadoSalud;
        this.usuario = null;
    }
    
    //atributo nombre es opcional
    public Gato(String qr, String color, Zona zona, String caracteristicas, EstadoSalud estadoSalud){
        this(qr,"",color,zona,caracteristicas,estadoSalud);
    }
    

    public void setEstadoSalud(EstadoSalud estadoSalud) {
        this.estadoSalud = estadoSalud;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public String getColor() {
        return color;
    }

    public Zona getZona() {
        return zona;
    }

    public EstadoSalud getEstadoSalud() {
        return estadoSalud;
    }

    public String getQr() {
        return qr;
    }

    public HistorialGato getHistorial() {
        return historial;
    }

    public void setHistorial() {
        if(historial == null){
            this.historial = new HistorialGato();
        }
    }
    
    public void setQr(String qr) {
        this.qr = qr;
    }
    
    public String getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(String caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setHistorial(HistorialGato historial) {
        this.historial = historial;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    @Override
    public String toString() {
        String usu;
        if(this.usuario!=null){
            usu = this.usuario.getNombreUsuario();
        }else
            usu = "---";
        return "Gato:\n\t* id=" + id + ",\n\t* estadoSalud=" + estadoSalud + ",\n\t* usuario=" + usu + ",\n\t* zona=" + zona.getLocalizacion() + ",\n\t* nombre=" + nombre + ",\n\t* color=" + color + ",\n\t* caracteristicas=" + caracteristicas;
    }
    
    
}
