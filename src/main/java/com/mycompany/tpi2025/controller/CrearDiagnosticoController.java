/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.DAOImpl.DiagnosticoJpaController;
import com.mycompany.tpi2025.DAOImpl.GatoJpaController;
import com.mycompany.tpi2025.DAOImpl.TratamientoJpaController;
import com.mycompany.tpi2025.Utils;
import com.mycompany.tpi2025.model.Diagnostico;
import com.mycompany.tpi2025.model.Gato;
import com.mycompany.tpi2025.model.Tratamiento;
import com.mycompany.tpi2025.view.JPanels.CrearDiagnosticoView;
import com.mycompany.tpi2025.view.TratamientoView;
import jakarta.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author aquin
 */
public class CrearDiagnosticoController {

    private CrearDiagnosticoView view;
    private Diagnostico diagnostico = null;
    private TratamientoJpaController daoT;
    private DiagnosticoJpaController dao;
    private GatoJpaController daoG;
    private Gato gato = null;
    private Tratamiento tratamiento = null;

    public CrearDiagnosticoController(CrearDiagnosticoView view, Diagnostico diagnostico, EntityManagerFactory emf) {
        this.view = view;
        this.daoT = new TratamientoJpaController(emf);
        this.dao = new DiagnosticoJpaController(emf);
        this.daoG = new GatoJpaController(emf);
        iniciar();
        if (diagnostico == null) {
            this.diagnostico = new Diagnostico();
        } else if (diagnostico != null) {
            this.diagnostico = diagnostico;
            cargarCampos();
        }

        //se crea y guarda el diagnostico para poder luego cargar los tratamientos
        view.setCreacionListener(l -> crear());
        view.setAniadirTratamientoListener(l -> mostrarTratamientoView());
        view.setEliminarTratamientoListener(l -> eliminarTratamiento());
        view.setSeleccionListaListener(l -> seleccion());
    }

    public void iniciar() {//muestra la view
        view.setVisible(true);
        limpiar();
    }

    public void cargarCampos() {
        view.setDescripcionTA(diagnostico.getDescripcion());
        view.setTituloDiag(diagnostico.getDiagnostico());
        iniciarTabla();
    }

    private void crear() {
        String titulo = view.getTituloDiag().trim();
        String descripcion = view.getDescripcionTA().trim();

        try {
            // Validar campos vacíos
            if (Utils.hayVacios(titulo, descripcion)) {
                throw new Exception("El título y descripción son obligatorios.");
            }

            // Validar que haya un gato seleccionado
            if (gato == null) {
                throw new Exception("No hay gato seleccionado.");
            }

            // Validar que haya un historial
            if (gato.getHistorial() == null) {
                throw new Exception("El gato no tiene historial médico.");
            }

            
            diagnostico.setDiagnostico(titulo);
            diagnostico.setDescripcion(descripcion);
            diagnostico.setFecha_diagnostico(LocalDate.now());

            // Agregar diagnóstico al historial del gato
            gato.getHistorial().addDiagnostico(diagnostico);

            try {
                dao.create(diagnostico);

                // Actualizar gato para que se guarde el diagnostico
                daoG.edit(gato);

                // Resetear diagnóstico para nuevo uso
                diagnostico = new Diagnostico();

                // Reiniciar vista
                iniciar();

                // Mensaje de éxito
                view.mostrarInfoMensaje("Diagnóstico creado exitosamente.");

            } catch (Exception e) {
                throw new Exception("Error al guardar en la base de datos: " + e.getMessage(), e);
            }

        } catch (Exception e) {
            e.printStackTrace();
            view.mostrarErrorMensaje(e.getMessage());
        }
    }

    public void iniciarTabla() {
        List<Tratamiento> lista = obtenerLista();

        view.reloadTable(lista);
    }

    private List<Tratamiento> obtenerLista() {
        return diagnostico.getTratamientos();
    }
    public void setDiagnostico(Diagnostico diagnostico) {
        this.diagnostico = diagnostico;
    }

    public void setGato(Gato gato) {
        this.gato = gato;
    }

    private void mostrarTratamientoView() {

        TratamientoView viewT = new TratamientoView();
        TratamientoController controller = new TratamientoController(viewT);
        viewT.setCrearListener(l -> {
            controller.crear();

            this.diagnostico.addTratamiento(controller.getTratamiento());
            controller.setTratamiento(null);

            controller.cerrarView();
            iniciarTabla();
        });

    }

    private void limpiar() {
        view.limpiarComponentes();
    }

    private void eliminarTratamiento() {
        try {
            if (tratamiento == null) {
                throw new Exception("No se ha seleccionado un tratamiento.");
            }
            diagnostico.getTratamientos().removeIf(e -> e.getDescripcion().equals(tratamiento.getDescripcion()));
            view.mostrarInfoMensaje("Tratamiento eliminado exitosamente.");
            view.activarEliminacion(false);
            iniciarTabla();
        } catch (Exception e) {
            e.printStackTrace();
            view.mostrarErrorMensaje(e.getMessage());
        }
    }

    private void seleccion() {
        int fila = view.obtenerIndiceFila();
        if (fila != -1) {
            String descrip = view.obtenerValorTabla(fila, 1);//segundo parametro indice correspondiente a la columna del encabezado
            int indice = obtenerIndiceTratamiento(descrip);
            if (indice != -1) {
                view.resaltarFila(indice);
                tratamiento = obtenerLista().get(indice);
                view.activarEliminacion(true);
            }
        }
    }

    private int obtenerIndiceTratamiento(String descripcionT) {
        List<Tratamiento> lista = obtenerLista();
        return lista.indexOf(lista.stream().filter(v -> v.getDescripcion().equals(descripcionT)).findFirst().orElse(null));
    }
}
