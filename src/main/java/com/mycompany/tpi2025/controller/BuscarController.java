/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.DAOImpl.UsuarioJpaController;
import com.mycompany.tpi2025.model.Usuario;
import com.mycompany.tpi2025.view.BuscarView;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

/**
 *
 * @author aquin
 */
public class BuscarController<T extends Usuario> {
    private BuscarView view;
    private UsuarioJpaController dao;
    private final Class<T> tipoUsuario;
    
     public BuscarController(BuscarView view, Class<T> tipoUsuario, AccionBuscar tipoAccion,String[] encabezados , EntityManagerFactory emf) {
        this.view = view;
        this.dao = new UsuarioJpaController(emf);
        this.tipoUsuario = tipoUsuario;
        view.setTitulo(tipoAccion + " " + tipoUsuario.getSimpleName());
        view.setAccionTexto(tipoAccion.getTexto());
        iniciarTabla(encabezados);
        view.setAccionListener(l -> accion(tipoAccion));
        view.setBuscarListener(l -> buscarUsuario());
    }
    
    public void iniciarTabla(String[] encabezados){
        List<Usuario> lista = dao.findUsuariosByTipo(tipoUsuario.getSimpleName());
        view.reloadTable(lista, encabezados);
    }
     

    private void accion(AccionBuscar tipo) {
        switch (tipo) {
            case DETALLES -> {}
            case ELIMINAR -> {}
            case SELECCIONAR -> {}
            default -> throw new AssertionError(tipo.name());

        }
    }

    private void buscarUsuario() {
        String nombreUsuario = view.getBuscarUsuarioTf();
        List<Usuario> lista = dao.findUsuariosByTipo(tipoUsuario.getSimpleName());
        int indice = lista.indexOf(lista.stream().filter(v -> v.getNombreUsuario().toLowerCase().equals(nombreUsuario.toLowerCase()) || v.getNombre().toLowerCase().equals(nombreUsuario.toLowerCase())).findFirst().orElse(null));
        if(indice != -1){
            view.resaltarFila(indice); //aunque aquí ya se haga la verificación del -1 se mantiene en el view por si se utiliza en otro momento.  
        }
    }
}
