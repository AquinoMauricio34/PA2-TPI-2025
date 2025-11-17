/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.DAOImpl.GatoJpaController;
import com.mycompany.tpi2025.DAOImpl.ZonaJpaController;
import com.mycompany.tpi2025.model.Gato;
import com.mycompany.tpi2025.model.Zona;
import com.mycompany.tpi2025.model.enums.EstadoSalud;
import com.mycompany.tpi2025.view.CrearGatoView;
import com.mycompany.tpi2025.view.VerGatoView;
import com.mycompany.tpi2025.view.ZonaView;
import jakarta.persistence.EntityManagerFactory;

/**
 *
 * @author aquin
 */
public class CrearGatoController {
    private CrearGatoView view;
    private GatoJpaController dao;
    private ZonaJpaController daoZ;
    private Zona zonaGato=null;
    private EntityManagerFactory emf;
    private Gato gato = null;

    public CrearGatoController(CrearGatoView view, String tipoAccion, EntityManagerFactory emf) {
        this(view,null,tipoAccion,emf);
    }
    
    public CrearGatoController(CrearGatoView view, Gato gato, String tipoAccion, EntityManagerFactory emf) {
        this.view = view;
        this.emf = emf;
        this.gato = gato;
        dao = new GatoJpaController(emf);
        daoZ = new ZonaJpaController(emf);
        view.setRegistrarText("Guardar");
        iniciarView();
        view.activarModificar(false);
        view.setSeleccionarZonaListener(l -> seleccion());
        view.setRegistrarListener(l -> accion(tipoAccion));
        view.setModificarListener(l -> {
            view.activarComponentes(true);
            view.activarRegistrar(true);
            view.activarModificar(false);
        });
        if("VER".equals(tipoAccion)){
            abrirSeleccion();
            view.activarComponentes(false);
            view.activarRegistrar(false);
            view.activarModificar(true);
        }
        if("MODIFICAR".equals(tipoAccion)){
            abrirSeleccion();
            view.activarComponentes(false);
            view.activarRegistrar(false);
            view.activarModificar(true);
        }
    }
    
    public void iniciarView(){
        view.setVisible(true);
        
    }
    
    private void accion(String tipoAccion){
        switch (tipoAccion) {
            case "GUARDAR" -> guardar();
            case "MODIFICAR" -> modificar();
            case "VER" -> {}
            default ->
                throw new AssertionError();
        }
    }

    public void setZonaGato(Zona zonaGato) {
        this.zonaGato = zonaGato;
    }

    private void seleccion() {
        ZonaView zview = new ZonaView();
        ZonaController controller = new ZonaController(zview, this, emf);
    }
    
    public void setZonaElegidaTexto(String texto){
        view.setZonaElegida(texto);
    }

    private void guardar() {
        try {
            Gato gato = new Gato("",view.getNombreGato(),view.getColorGato(),daoZ.findZona(zonaGato.getId()),view.getCaracteristicasGato(),EstadoSalud.valueOf(view.getEstadoSalud()));
            gato.setHistorial();
            dao.create(gato);
            gato.setQr("QR "+gato.getId());
            dao.edit(gato);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void modificar() {
        if(gato != null){
            System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkkm");
            try {
                gato.setNombre(view.getNombreGato());
                gato.setCaracteristicas(view.getCaracteristicasGato());
                gato.setColor(view.getColorGato());
                gato.setEstadoSalud(EstadoSalud.valueOf(view.getEstadoSalud()));
                gato.setZona(daoZ.findZona(zonaGato.getId()));
                dao.edit(gato);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else
            System.out.println("kkkkkkkkkkkkkkkkkkkkkkkkkkkk");
    }
    
    public void abrirSeleccion() {
        VerGatoView vview = new VerGatoView();
        VerGatoController controller = new VerGatoController(vview, emf);
        vview.habilitarEliminar(true);
        vview.setSeleccionListener(l -> {
            gato = controller.getGato();
            vview.dispose();
            cargarDatos();
            view.activarModificar(true);
        });
    }
    
    public void cargarDatos(){
        view.setNombreGato(gato.getNombre());
        view.setColorGato(gato.getColor());
        view.setCaracteristicasGato(gato.getCaracteristicas());
        view.setEstadoSalud(gato.getEstadoSalud());
        zonaGato = gato.getZona();
        try {
            view.setZonaElegida("Zona N° "+gato.getZona().getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
}
