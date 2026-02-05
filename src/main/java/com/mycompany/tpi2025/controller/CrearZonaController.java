/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.DAOImpl.GatoJpaController;
import com.mycompany.tpi2025.DAOImpl.ZonaJpaController;
import com.mycompany.tpi2025.model.Zona;
import com.mycompany.tpi2025.view.JPanels.ABZonaView;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

/**
 *
 * @author aquin
 */
public class CrearZonaController {

    private ABZonaView view;
    private ZonaJpaController dao;
    private Zona zona = null;//utilizado para guardar la zona seleccionada de la tabla para ser eliminada

    public CrearZonaController(ABZonaView view, EntityManagerFactory emf) {
        this.view = view;
        this.dao = new ZonaJpaController(emf);
        iniciarView();
        view.setAniadirZonaListener(l -> registrar());
        view.setEliminarListener(l -> eliminar(emf));
        view.setSeleccionListaListener(l -> seleccion());
    }

    private void iniciarView() {
        view.setVisible(true);
        iniciarTabla();
        view.limpiarComponentes();
    }

    private void registrar() {
        String ubicacion = view.getUbicacionBtn().trim();
        try {
            if (ubicacion.isBlank()) {
                throw new Exception("Se debe indicar la ubicacion.");
            }
            Zona z = new Zona(ubicacion);
            dao.create(z);
            view.mostrarInfoMensaje("Zona registrada exitosamente.");
            iniciarTabla();
            view.limpiarComponentes();
        } catch (Exception e) {
            e.printStackTrace();
            view.mostrarErrorMensaje(e.getMessage());
        }
    }

    private void eliminar(EntityManagerFactory emf) {
        GatoJpaController daoG = new GatoJpaController(emf);
        try {
            if (zona == null) {
                throw new Exception("No hay una zona seleccionada para ser eliminada.");
            }
            long cantidadGatoZona = daoG.countByZonaId(zona.getId());
            if (cantidadGatoZona > 0) {
                throw new Exception("No se puede eliminar la zona porque hay " + cantidadGatoZona + " gatos relacionados a ella.");
            }
            dao.destroy(zona.getId());
            zona = null;
            view.mostrarInfoMensaje("Zona eliminada exitosamente.");
            view.activarEliminacion(false);
            iniciarTabla();

        } catch (Exception e) {
            e.printStackTrace();
            view.mostrarErrorMensaje(e.getMessage());
        }
    }

    public void iniciarTabla() {
        List<Zona> lista = obtenerLista();
        view.reloadTable(lista);
    }

    private List<Zona> obtenerLista() {
        return dao.findZonaEntities();
    }

    private void seleccion() {
        int fila = view.obtenerIndiceFila();
        if (fila != -1) {
            String id = view.obtenerValorTabla(fila, 0);//primer parametro indice correspondiente a la columna del encabezado
            int indice = obtenerIndiceZona(Long.parseLong(id));
            if (indice != -1) {
                view.resaltarFila(indice);
                zona = obtenerLista().get(indice);
                view.activarEliminacion(true);
            }
        }
    }

    private int obtenerIndiceZona(long idZona) {
        List<Zona> lista = obtenerLista();
        return lista.indexOf(lista.stream().filter(v -> v.getId() == idZona).findFirst().orElse(null));
    }
}
