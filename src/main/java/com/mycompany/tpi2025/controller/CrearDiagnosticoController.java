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
import com.mycompany.tpi2025.view.CrearDiagnosticoView;
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

    public CrearDiagnosticoController(CrearDiagnosticoView view, EntityManagerFactory emf) {
        this.view = view;
        this.daoT = new TratamientoJpaController(emf);
        this.dao = new DiagnosticoJpaController(emf);
        this.daoG = new GatoJpaController(emf);
        diagnostico = new Diagnostico();
        System.out.println("AB6");
        //se crea y guarda el diagnostico para poder luego cargar los tratamientos
        //dao.create(diagnostico);
        iniciar();
        view.setCreacionListener(l -> crear());
        //view.setSeleccionListaListener(l -> seleccionar());
        view.setAniadirTratamientoListener(l -> mostrarTratamientoView());
    }

    private void iniciar() {//muestra la view
        view.setVisible(true);
        limpiar();
    }

    private void crear() {//se crea y se envia al padre
            diagnostico.setDiagnostico(view.getTituloDiag());
            diagnostico.setDescripcion(view.getDescripcionTA());
            diagnostico.setFecha_diagnostico(LocalDate.now());
            gato.getHistorial().addDiagnostico(diagnostico);
            try {
                daoG.edit(gato);
                limpiar();
            } catch (Exception e) {
                System.out.println("Error---------------------------------------------------");
                e.printStackTrace();
            }
    }
    
    public void iniciarTabla(){
        List<Tratamiento> lista = obtenerLista();
        System.out.println(lista);
        view.reloadTable(lista);
    }
    
    private List<Tratamiento> obtenerLista(){
        return diagnostico.getTratamientos();
    }
    private int obtenerIndiceTratamiento(long idTratamiento){
        List<Tratamiento> lista = obtenerLista();
        return lista.indexOf(lista.stream().filter(v -> v.getId()==idTratamiento).findFirst().orElse(null));
    }

    public void setDiagnostico(Diagnostico diagnostico) {
        this.diagnostico = diagnostico;
    }

    public void setGato(Gato gato) {
        this.gato = gato;
    }
    
    private void mostrarTratamientoView() {
        System.out.println("AB0.1");
        TratamientoView viewT = new TratamientoView();
        TratamientoController controller = new TratamientoController(viewT);
        viewT.setCrearListener(l -> {
            controller.crear();
            System.out.println("AB0.2");
            this.diagnostico.addTratamiento(controller.getTratamiento());
            System.out.println("AB0.3");
            controller.cerrarView();
            iniciarTabla();
        });
        
    }

    private void limpiar() {
        view.limpiarComponentes();
    }
}
