/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.DAOImpl.GatoJpaController;
import com.mycompany.tpi2025.model.Gato;
import com.mycompany.tpi2025.view.VerGatoView;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

/**
 *
 * @author aquin
 */
public class VerGatoController {
    private VerGatoView view;
    private GatoJpaController dao;
    private VerHistorialGatoController padre;
    private Gato gato = null;

    public VerGatoController(VerGatoView view, EntityManagerFactory emf) {
        this.view = view;
        this.dao = new GatoJpaController(emf);
        iniciarView();
        iniciarTabla();
        view.setSeleccionListaListener(l -> seleccionar());
        view.setCerrarListener(l -> cerrar());
    }
    
    public void iniciarView(){
        view.setVisible(true);
        view.setLocationRelativeTo(null);
    }
    
    public void iniciarTabla(){
        List<Gato> lista = obtenerLista();
        view.reloadTable(lista);
    }
    
    private List<Gato> obtenerLista(){
        return dao.findGatoEntities();
    }
    
    private void seleccionar() {
        int fila = view.obtenerIndiceFila();
        if (fila != -1) {
            String id = view.obtenerValorTabla(fila, 0);//segundo parametro indice correspondiente a la columna del encabezado
            int indice = obtenerIndiceGato(Long.parseLong(id));
            if(indice != -1){
                view.resaltarFila(indice);
                gato = obtenerLista().get(indice);
                view.activarSeleccion(true);
            }
        }
    }
    
    private int obtenerIndiceGato(long idGato){
        List<Gato> lista = obtenerLista();
        return lista.indexOf(lista.stream().filter(v -> v.getId()==idGato).findFirst().orElse(null));
    }
    
    private void cerrar(){
        view.dispose();
        view = null;
    }

    public Gato getGato() {
        return gato;
    }
    
    
}
