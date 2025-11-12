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
        System.out.println("Controller------------------------------------------------");
        view.setTitulo(tipoAccion + " " + tipoUsuario.getSimpleName());
        view.setAccionTexto(tipoAccion.getTexto());
        iniciarTabla(encabezados);
        view.setAccionListener(l -> accion(tipoAccion));
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
}
