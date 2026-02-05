/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.DAOImpl.DiagnosticoJpaController;
import com.mycompany.tpi2025.DAOImpl.GatoJpaController;
import com.mycompany.tpi2025.model.Diagnostico;
import com.mycompany.tpi2025.model.Gato;
import com.mycompany.tpi2025.view.EstudiosView;
import com.mycompany.tpi2025.view.JPanels.VerHistorialGatoView;
import com.mycompany.tpi2025.view.VerGatoView;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

/**
 *
 * @author aquin
 */
public class VerHistorialGatoController {

    private VerHistorialGatoView view;
    private DiagnosticoJpaController dao;
    private Gato gato = null;
    private GatoJpaController daoG;
    private EntityManagerFactory emf;
    private Diagnostico diagnostico = null;

    public VerHistorialGatoController(VerHistorialGatoView view, EntityManagerFactory emf) {
        this.view = view;
        this.dao = new DiagnosticoJpaController(emf);
        this.daoG = new GatoJpaController(emf);
        this.emf = emf;
        iniciarView();
        abrirSeleccion();
        //listener para detectar si se selecciona una fila de diagnostico
        view.setSeleccionListaListener(l -> seleccionar());
        //seleccionar gato para mostrar su historia
        view.setSeleccionarGatoListener(l -> abrirSeleccion());
        view.setEstudiosListener(l -> abrirEstudios());
    }

    public void iniciarView() {
        view.setVisible(true);
    }

    public void setGato(Gato g) {
        gato = g;
    }

    private void abrirSeleccion() {
        VerGatoView vview = new VerGatoView();
        VerGatoController controller = new VerGatoController(vview, emf);
        vview.setSeleccionListener(l -> {
            gato = controller.getGato();
            vview.dispose();
            iniciarTabla();
        });
    }

    public Gato getGato() {
        return gato;
    }

    public void iniciarTabla() {
        List<Diagnostico> lista = obtenerLista();
        view.reloadTable(lista);
    }

    private List<Diagnostico> obtenerLista() {
        return gato.getHistorial().getDiagnosticos();
    }

    private void seleccionar() {
        
        int fila = view.obtenerIndiceFila();
        if (fila != -1) {
            String id = view.obtenerValorTabla(fila, 0);//segundo parametro indice correspondiente a la columna del encabezado
            int indice = obtenerIndiceDiagnostico(Long.parseLong(id));
            
            if (indice != -1) {
                diagnostico = obtenerLista().get(indice);
                view.activarSeleccion(true);
            }
        }
    }

    private int obtenerIndiceDiagnostico(long idDiag) {
        List<Diagnostico> lista = obtenerLista();
        return lista.indexOf(lista.stream().filter(v -> v.getId() == idDiag).findFirst().orElse(null));
    }

    public Diagnostico getDiagnostico() {
        return diagnostico;
    }

    private void abrirEstudios() {
        EstudiosView eview = new EstudiosView();
        EstudioController controller = new EstudioController(eview, gato, emf);
    }

}
