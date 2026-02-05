/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.DAOImpl.PostulacionJpaController;
import com.mycompany.tpi2025.model.Postulacion;
import com.mycompany.tpi2025.view.JPanels.VerPostulacionesView;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

/**
 *
 * @author aquin
 */
public class VerPostulacionesController {

    private VerPostulacionesView view;
    private PostulacionJpaController dao;
    private Postulacion postulacion = null;//utilizado para guardar la postulacion seleccionada de la tabla para ser eliminada

    public VerPostulacionesController(VerPostulacionesView view, EntityManagerFactory emf) {
        this.view = view;
        this.dao = new PostulacionJpaController(emf);
        iniciarView();
        view.setEliminarListener(l -> eliminar());
        view.setSeleccionListaListener(l -> seleccion());
    }

    private void iniciarView() {
        view.setVisible(true);
        iniciarTabla();
    }

    private void eliminar() {
        try {
            if (postulacion == null) {
                throw new Exception("No hay una postulacion seleccionada para ser eliminada.");
            }
            dao.destroy(postulacion.getId());
            postulacion = null;
            view.mostrarInfoMensaje("Postulacion eliminada exitosamente.");
            view.activarEliminacion(false);
            iniciarTabla();

        } catch (Exception e) {
            e.printStackTrace();
            view.mostrarErrorMensaje(e.getMessage());
        }
    }

    public void iniciarTabla() {
        List<Postulacion> lista = obtenerLista();
        view.reloadTable(lista);
    }

    private List<Postulacion> obtenerLista() {
        return dao.findPostulacionEntities();
    }

    private void seleccion() {
        int fila = view.obtenerIndiceFila();
        if (fila != -1) {
            String id = view.obtenerValorTabla(fila, 0);//primer parametro indice correspondiente a la columna del encabezado
            int indice = obtenerIndicePostulacion(Long.parseLong(id));
            if (indice != -1) {
                view.resaltarFila(indice);
                postulacion = obtenerLista().get(indice);
                view.activarEliminacion(true);
            }
        }
    }

    private int obtenerIndicePostulacion(long idPostulacion) {
        List<Postulacion> lista = obtenerLista();
        return lista.indexOf(lista.stream().filter(v -> v.getId() == idPostulacion).findFirst().orElse(null));
    }
}
