/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.DAOImpl.FamiliaJpaController;
import com.mycompany.tpi2025.model.Familia;
import com.mycompany.tpi2025.view.VerFamiliaView;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

/**
 *
 * @author aquin
 */
public class VerFamiliaController {
    private VerFamiliaView view;
    private FamiliaJpaController dao;
    private Familia familia = null;

    public VerFamiliaController(VerFamiliaView view, EntityManagerFactory emf) {
        this.view = view;
        this.dao = new FamiliaJpaController(emf);
        System.out.println("Q1-----------------------------------------------------------------------------------------------------");
        iniciarView();
        System.out.println("Q2-----------------------------------------------------------------------------------------------------");
        iniciarTabla();
        System.out.println("Q3-----------------------------------------------------------------------------------------------------");
        view.setSeleccionListaListener(l -> seleccionar());
        view.setCerrarListener(l -> cerrar());
    }
    
    public void iniciarView(){
        System.out.println("Q4-----------------------------------------------------------------------------------------------------");
        view.setVisible(true);
        view.setLocationRelativeTo(null);
    }
    
    public void iniciarTabla(){
        System.out.println("Q5-----------------------------------------------------------------------------------------------------");
        List<Familia> lista = obtenerLista();
        System.out.println("Q6-----------------------------------------------------------------------------------------------------");
        view.reloadTable(lista);
        System.out.println("Q7-----------------------------------------------------------------------------------------------------");
    }
    
    private List<Familia> obtenerLista(){
        List<Familia> fam;
        System.out.println("Q8-----------------------------------------------------------------------------------------------------");
        try {
             fam = dao.findFamiliaEntities();
            return fam;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Q81-----------------------------------------------------------------------------------------------------");
        return null;
    }
    
    private void seleccionar() {
        int fila = view.obtenerIndiceFila();
        if (fila != -1) {
            String id = view.obtenerValorTabla(fila, 0);//segundo parametro indice correspondiente a la columna del encabezado
            int indice = obtenerIndiceFamilia(id);
            if(indice != -1){
                System.out.println("Q9-----------------------------------------------------------------------------------------------------");
                view.resaltarFila(indice);
                familia = obtenerLista().get(indice);
                view.activarSeleccion(true);
            }
        }
    }
    
    private int obtenerIndiceFamilia(String idFamilia){
        List<Familia> lista = obtenerLista();
        return lista.indexOf(lista.stream().filter(v -> v.getNombreUsuario().equals(idFamilia)).findFirst().orElse(null));
    }
    
    private void cerrar(){
        view.dispose();
        view = null;
    }

    public Familia getFamilia() {
        return familia;
    }
    
    
}
