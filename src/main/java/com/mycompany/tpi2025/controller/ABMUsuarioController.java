/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.DAOImpl.UsuarioJpaController;
import com.mycompany.tpi2025.model.Usuario;
import com.mycompany.tpi2025.view.ABMUsuarioView;
import jakarta.persistence.EntityManagerFactory;

/**
 *
 * @author aquin
 * @param <T>
 */
public class ABMUsuarioController<T extends Usuario> {

    private final ABMUsuarioView view;
    private final UsuarioJpaController dao;
    private final Class<T> tipoUsuario;

    public ABMUsuarioController(ABMUsuarioView view, Class<T> tipoUsuario, EntityManagerFactory emf, AccionUsuario tipoAccion) {
        this.view = view;
        this.dao = new UsuarioJpaController(emf);
        this.tipoUsuario = tipoUsuario;
        System.out.println("Controller------------------------------------------------");
        view.setTitulo(tipoAccion + " " + tipoUsuario.getSimpleName());
        view.setAccionTexto(tipoAccion.getTexto());
        view.setRegistrarListener(l -> accion(tipoAccion));
    }

    public void accion(AccionUsuario tipo) {
        switch (tipo) {
            case GUARDAR ->
                guardarUsuario();
            case ELIMINAR ->
                eliminarUsuario();
            case MODIFICAR ->{}
            default -> {
            }
        }
    }

    public void guardarUsuario() {
        try {
            System.out.println("DENTRO-----------------------");
            T usuario = tipoUsuario.getDeclaredConstructor().newInstance();
            usuario.setNombre(view.getNombre());
            usuario.setContrasenia(view.getContrasenia());
            usuario.setTelefono(view.getTelefono());
            usuario.setNombreUsuario(view.getNombreDeUsuiario());
            dao.create(usuario);
            view.mostrarMensaje("Usuario creado correctamente");
        } catch (Exception ex) {
            ex.printStackTrace();
            view.mostrarMensaje("Error al crear usuario");
        }
    }

    private void eliminarUsuario() {
        
    }

}
