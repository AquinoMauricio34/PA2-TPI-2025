/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.DAOImpl.DiagnosticoJpaController;
import com.mycompany.tpi2025.DAOImpl.GatoJpaController;
import com.mycompany.tpi2025.DAOImpl.TratamientoJpaController;
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

    public CrearDiagnosticoController(CrearDiagnosticoView view, Diagnostico diagnostico, EntityManagerFactory emf) {
        this.view = view;
        this.daoT = new TratamientoJpaController(emf);
        this.dao = new DiagnosticoJpaController(emf);
        this.daoG = new GatoJpaController(emf);
        iniciar();
        if (diagnostico == null) {
            this.diagnostico = new Diagnostico();
        }else if (diagnostico != null) {
            this.diagnostico = diagnostico;
            cargarCampos();
        }
        //System.out.println("AB6");
        //se crea y guarda el diagnostico para poder luego cargar los tratamientos
        //dao.create(diagnostico);
        view.setCreacionListener(l -> crear());
        //view.setSeleccionListaListener(l -> seleccionar());
        view.setAniadirTratamientoListener(l -> mostrarTratamientoView());
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

    private void crear() {//se crea y se envia al padre
        System.out.println("titulo diag crear: "+view.getTituloDiag());
        diagnostico.setDiagnostico(view.getTituloDiag());
        diagnostico.setDescripcion(view.getDescripcionTA());
        diagnostico.setFecha_diagnostico(LocalDate.now());
        //se setea el historial del diagnostico antes de persistir el diag
        gato.getHistorial().addDiagnostico(diagnostico);
        try {
            System.out.println("TRYcreate diag-----------------------------------------------------------------------");
            dao.create(diagnostico);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            System.out.println("TRYedit-----------------------------------------------------------------------");
            daoG.edit(gato);
            diagnostico = null;
            diagnostico = new Diagnostico();
            iniciar();
        } catch (Exception e) {
            //System.out.println("Error---------------------------------------------------");
            e.printStackTrace();
        }
    }

    public void iniciarTabla() {
        List<Tratamiento> lista = obtenerLista();
        //System.out.println(lista);
        view.reloadTable(lista);
    }

    private List<Tratamiento> obtenerLista() {
        return diagnostico.getTratamientos();
    }

    private int obtenerIndiceTratamiento(long idTratamiento) {
        List<Tratamiento> lista = obtenerLista();
        return lista.indexOf(lista.stream().filter(v -> v.getId() == idTratamiento).findFirst().orElse(null));
    }

    public void setDiagnostico(Diagnostico diagnostico) {
        this.diagnostico = diagnostico;
    }

    public void setGato(Gato gato) {
        this.gato = gato;
    }

    private void mostrarTratamientoView() {
        //System.out.println("AB0.1");
        TratamientoView viewT = new TratamientoView();
        TratamientoController controller = new TratamientoController(viewT);
        viewT.setCrearListener(l -> {
            controller.crear();
            //System.out.println("AB0.2");
            this.diagnostico.addTratamiento(controller.getTratamiento());
            controller.setTratamiento(null);
            //System.out.println("AB0.3");
            controller.cerrarView();
            iniciarTabla();
        });

    }

    private void limpiar() {
        view.limpiarComponentes();
    }
}
