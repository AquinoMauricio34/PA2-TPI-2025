/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.DAOImpl.GatoJpaController;
import com.mycompany.tpi2025.model.Gato;
import com.mycompany.tpi2025.model.Hogar;
import com.mycompany.tpi2025.view.GatosDeHogarView;
import com.mycompany.tpi2025.view.VerHogarView;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

/**
 *
 * @author aquin
 */
public class GatoDeHogarController {
    private GatosDeHogarView view;
    private GatoJpaController dao;
    private Hogar hogar = null;
    private EntityManagerFactory emf;

    public GatoDeHogarController(GatosDeHogarView view, EntityManagerFactory emf) {
        this.view = view;
        this.dao = new GatoJpaController(emf);
        this.emf = emf;
        abrirSeleccion();
    }
    
    public void iniciarTabla(){
        List<Gato> lista = obtenerLista();
        view.reloadTable(lista);
    }
    
    private List<Gato> obtenerLista(){
        return hogar.getAllGatos();
    }
    
    private void abrirSeleccion() {
        VerHogarView vview = new VerHogarView();
        VerHogarController controller = new VerHogarController(vview, emf);
        vview.setSeleccionListener(l -> {
            hogar = controller.getHogar();
            vview.dispose();
            iniciarTabla();
        });
    }
    
    
}
