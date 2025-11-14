/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.DAOImpl.ZonaJpaController;
import com.mycompany.tpi2025.model.Gato;
import com.mycompany.tpi2025.model.Zona;
import com.mycompany.tpi2025.view.ZonaView;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

/**
 *
 * @author aquin
 */
public class ZonaController {
    private ZonaView view;
    private ZonaJpaController dao;
    private CrearGatoController padre;
    private Zona zona = null;

    public ZonaController(ZonaView view, CrearGatoController padre, EntityManagerFactory emf) {
        this.view = view;
        view.setVisible(true);
        view.setLocationRelativeTo(null);
        this.dao = new ZonaJpaController(emf);
        this.padre = padre;
        iniciarTabla();
        view.setSeleccionListaListener(l -> seleccionar());
        view.setSeleccionListener(l -> enviarZona());
        view.setCerrarListener(l -> cerrar());
    }
    
    public void iniciarTabla(){
        List<Zona> lista = obtenerLista();
        view.reloadTable(lista, new String[]{"ID","Localización"});
    }
    
    private List<Zona> obtenerLista(){
        return dao.findZonaEntities();
    }
    
    private void seleccionar() {
        System.out.println("AB2.2");
        int fila = view.obtenerIndiceFila();
        if (fila != -1) {
            System.out.println("AB3");
            String id = view.obtenerValorTabla(fila, 0);//1 es el indice correspondiente a la columna del encabezado nombreUsuario
            int indice = obtenerIndiceZona(Long.parseLong(id));
            if(indice != -1){
                System.out.println("AB14");
                zona = obtenerLista().get(indice);
                view.activarSeleccion(true);
                System.out.println("USUUUUUUUUUUU");
            }
        }
    }
    
    private int obtenerIndiceZona(long idZona){
        List<Zona> lista = obtenerLista();
        return lista.indexOf(lista.stream().filter(v -> v.getId()==idZona).findFirst().orElse(null));
    }
    
    private void cerrar(){
        view.dispose();
        view = null;
    }

    private void enviarZona() {
        padre.setZonaGato(zona);
        padre.setZonaElegidaTexto("Zona N° "+zona.getId());
        cerrar();
    }
    
}
