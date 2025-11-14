/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.controller.enums.AccionUsuario;
import com.mycompany.tpi2025.DAOImpl.UsuarioJpaController;
import com.mycompany.tpi2025.model.Usuario;
import com.mycompany.tpi2025.view.AMUsuarioView;
import jakarta.persistence.EntityManagerFactory;

/**
 *
 * @author aquin
 * @param <T>
 */
public class AMUsuarioController<T extends Usuario> {

    private final AMUsuarioView view;
    private final UsuarioJpaController dao;
    private final Class<T> tipoUsuario;
    private T usuario;

    public AMUsuarioController(AMUsuarioView view, Class<T> tipoUsuario, EntityManagerFactory emf, AccionUsuario tipoAccion) {
        this(view,null,tipoUsuario, emf,tipoAccion);
    }
    
    public AMUsuarioController(AMUsuarioView view,T usuario, Class<T> tipoUsuario, EntityManagerFactory emf, AccionUsuario tipoAccion){
        this.view = view;
        this.dao = new UsuarioJpaController(emf);
        this.tipoUsuario = tipoUsuario;
        this.usuario = usuario;
        System.out.println("Controller------------------------------------------------");
        iniciar(tipoAccion);
    }
    
    private void iniciar(AccionUsuario tipoAccion){
        view.setAccionListener(l -> accion(tipoAccion));
        view.setTitulo(tipoAccion + " " + tipoUsuario.getSimpleName());
        view.setAccionTexto(tipoAccion.getTexto());
        if(tipoAccion == AccionUsuario.MODIFICAR){
            view.setNombre(usuario.getNombre());
            view.setNombreDeUsuiario(usuario.getNombreUsuario());
            view.setTelefono(usuario.getTelefono());
            view.setContrasenia("---");
            view.estadoContrasenia(false);
        }
    }

    public void accion(AccionUsuario tipo) {
        switch (tipo) {
            case GUARDAR ->
                guardarUsuario();
            case MODIFICAR ->
                modificarUsuario();
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

    private void modificarUsuario() {
        try {
            usuario.setNombre(view.getNombre());
            usuario.setContrasenia(view.getContrasenia());
            usuario.setTelefono(view.getTelefono());
            usuario.setNombreUsuario(view.getNombreDeUsuiario());
            dao.edit(usuario);
            view.mostrarMensaje("Usuario modificado correctamente");
        } catch (Exception ex) {
            ex.printStackTrace();
            view.mostrarMensaje("Error al modificar usuario");
        }
    }

}
