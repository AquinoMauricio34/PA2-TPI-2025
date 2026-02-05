/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.DAOImpl.VisitaSeguimientoJpaController;
import com.mycompany.tpi2025.model.VisitaSeguimiento;
import com.mycompany.tpi2025.view.JPanels.VerVisitasSeguimientoView;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

/**
 *
 * @author aquin
 */
public class VerVisitasSeguimientoController {

    private VerVisitasSeguimientoView view;
    private VisitaSeguimientoJpaController dao;
    private VisitaSeguimiento tarea = null;//utilizado para guardar la tarea seleccionada de la tabla para ser eliminada

    public VerVisitasSeguimientoController(VerVisitasSeguimientoView view, EntityManagerFactory emf) {
        this.view = view;
        this.dao = new VisitaSeguimientoJpaController(emf);
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
            if (tarea == null) {
                throw new Exception("No hay una visita seleccionada para ser eliminada.");
            }
            dao.destroy(tarea.getId());
            tarea = null;
            view.mostrarInfoMensaje("Visita de seguimiento eliminada exitosamente.");
            view.activarEliminacion(false);
            iniciarTabla();

        } catch (Exception e) {
            e.printStackTrace();
            view.mostrarErrorMensaje(e.getMessage());
        }
    }

    public void iniciarTabla() {
        List<VisitaSeguimiento> lista = obtenerLista();
        view.reloadTable(lista);
    }

    private List<VisitaSeguimiento> obtenerLista() {
        return dao.findVisitaSeguimientoEntities();
    }

    private void seleccion() {
        int fila = view.obtenerIndiceFila();
        if (fila != -1) {
            String id = view.obtenerValorTabla(fila, 0);//primer parametro indice correspondiente a la columna del encabezado
            int indice = obtenerIndiceVisitaSeguimiento(Long.parseLong(id));
            if (indice != -1) {
                view.resaltarFila(indice);
                tarea = obtenerLista().get(indice);
                view.activarEliminacion(true);
            }
        }
    }

    private int obtenerIndiceVisitaSeguimiento(long idVisitaSeguimiento) {
        List<VisitaSeguimiento> lista = obtenerLista();
        return lista.indexOf(lista.stream().filter(v -> v.getId() == idVisitaSeguimiento).findFirst().orElse(null));
    }
}
