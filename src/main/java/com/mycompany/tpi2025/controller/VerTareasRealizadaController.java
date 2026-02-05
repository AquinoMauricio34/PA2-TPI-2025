/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.DAOImpl.TareaJpaController;
import com.mycompany.tpi2025.model.Tarea;
import com.mycompany.tpi2025.view.JPanels.VerTareasRealizadasView;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

/**
 *
 * @author aquin
 */
public class VerTareasRealizadaController {

    private VerTareasRealizadasView view;
    private TareaJpaController dao;
    private Tarea tarea = null;//utilizado para guardar la tarea seleccionada de la tabla para ser eliminada

    public VerTareasRealizadaController(VerTareasRealizadasView view, EntityManagerFactory emf) {
        this.view = view;
        this.dao = new TareaJpaController(emf);
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
                throw new Exception("No hay una tarea seleccionada para ser eliminada.");
            }
            dao.destroy(tarea.getId());
            tarea = null;
            view.mostrarInfoMensaje("Tarea eliminada exitosamente.");
            view.activarEliminacion(false);
            iniciarTabla();

        } catch (Exception e) {
            e.printStackTrace();
            view.mostrarErrorMensaje(e.getMessage());
        }
    }

    public void iniciarTabla() {
        List<Tarea> lista = obtenerLista();
        view.reloadTable(lista);
    }

    private List<Tarea> obtenerLista() {
        return dao.findTareaEntities();
    }

    private void seleccion() {
        int fila = view.obtenerIndiceFila();
        if (fila != -1) {
            String id = view.obtenerValorTabla(fila, 0);//primer parametro indice correspondiente a la columna del encabezado
            int indice = obtenerIndiceTarea(Long.parseLong(id));
            if (indice != -1) {
                view.resaltarFila(indice);
                tarea = obtenerLista().get(indice);
                view.activarEliminacion(true);
            }
        }
    }

    private int obtenerIndiceTarea(long idTarea) {
        List<Tarea> lista = obtenerLista();
        return lista.indexOf(lista.stream().filter(v -> v.getId() == idTarea).findFirst().orElse(null));
    }
}
