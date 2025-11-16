/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.DAOImpl.GatoJpaController;
import com.mycompany.tpi2025.model.Familia;
import com.mycompany.tpi2025.model.Gato;
import com.mycompany.tpi2025.view.GatosDeFamiliaView;
import com.mycompany.tpi2025.view.VerFamiliaView;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

/**
 *
 * @author aquin
 */
public class GatoDeFamiliaController {
    private GatosDeFamiliaView view;
    private GatoJpaController dao;
    private Familia familia = null;
    private EntityManagerFactory emf;

    public GatoDeFamiliaController(GatosDeFamiliaView view, EntityManagerFactory emf) {
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
        return familia.getAllGatos();
    }
    
    private void abrirSeleccion() {
        VerFamiliaView vview = new VerFamiliaView();
        VerFamiliaController controller = new VerFamiliaController(vview, emf);
        vview.setSeleccionListener(l -> {
            familia = controller.getFamilia();
            vview.dispose();
            iniciarTabla();
        });
    }
    
    
}
