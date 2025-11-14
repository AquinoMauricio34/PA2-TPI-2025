/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.DAOImpl.DiagnosticoJpaController;
import com.mycompany.tpi2025.DAOImpl.TratamientoJpaController;
import com.mycompany.tpi2025.model.Diagnostico;
import com.mycompany.tpi2025.model.Tratamiento;
import com.mycompany.tpi2025.view.CrearDiagnosticoView;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

/**
 *
 * @author aquin
 */
public class CrearDiagnosticoController {
    private CrearDiagnosticoView view;
    private Diagnostico diagnostico;
    private TratamientoJpaController dao;
    private Tratamiento tratamiento = null;

    public CrearDiagnosticoController(CrearDiagnosticoView view, EntityManagerFactory emf) {
        this.view = view;
        this.dao = new TratamientoJpaController(emf);
        iniciar();
        view.setCreacionListener(l -> crear());
        view.setSeleccionListaListener(l -> seleccionar());
    }

    private void iniciar() {//muestra la view
        view.setVisible(true);
    }

    private void crear() {//se crea y se envia al padre
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public void iniciarTabla(){
        List<Tratamiento> lista = obtenerLista();
        view.reloadTable(lista);
    }
    
    private List<Tratamiento> obtenerLista(){
        return diagnostico.getTratamientos();
    }
    
    private void seleccionar() {
        //System.out.println("AB2.2");
        int fila = view.obtenerIndiceFila();
        if (fila != -1) {
            //System.out.println("AB3");
            String id = view.obtenerValorTabla(fila, 0);//1 es el indice correspondiente a la columna del encabezado nombreUsuario
            int indice = obtenerIndiceTratamiento(Long.parseLong(id));
            if(indice != -1){
                //System.out.println("AB14");
                tratamiento = obtenerLista().get(indice);
                view.activarCreacion(true);
                //System.out.println("USUUUUUUUUUUU");
            }
        }
    }
    
    private int obtenerIndiceTratamiento(long idTratamiento){
        List<Tratamiento> lista = obtenerLista();
        return lista.indexOf(lista.stream().filter(v -> v.getId()==idTratamiento).findFirst().orElse(null));
    }

    public void setDiagnostico(Diagnostico diagnostico) {
        this.diagnostico = diagnostico;
    }
    
    
    
}
