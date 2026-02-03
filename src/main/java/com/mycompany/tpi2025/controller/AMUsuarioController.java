/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.DAOImpl.UsuarioJpaController;
import com.mycompany.tpi2025.controller.enums.AccionUsuario;
import com.mycompany.tpi2025.model.Hogar;
import com.mycompany.tpi2025.model.Usuario;
import com.mycompany.tpi2025.view.JPanels.AMUsuarioView;
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
    private AccionUsuario tipoAccion;

    public AMUsuarioController(AMUsuarioView view, Class<T> tipoUsuario, boolean mostrarContrasenia , EntityManagerFactory emf, AccionUsuario tipoAccion) {
        this(view,null,tipoUsuario,mostrarContrasenia, emf,tipoAccion);
    }
    
    public AMUsuarioController(AMUsuarioView view,T usuario, Class<T> tipoUsuario,boolean mostrarContrasenia, EntityManagerFactory emf, AccionUsuario tipoAccion){
        this.view = view;
        this.dao = new UsuarioJpaController(emf);
        this.tipoUsuario = tipoUsuario;
        this.usuario = usuario;
        this.tipoAccion = tipoAccion;
        iniciar(tipoAccion,mostrarContrasenia);
    }
    
    private void iniciar(AccionUsuario tipoAccion,boolean mostrarContrasenia){
        view.setAccionListener(l -> accion(tipoAccion));
        view.estadoNombreUsuario(false);
        view.setTitulo(tipoAccion + " " + tipoUsuario.getSimpleName());
        view.setAccionTexto(tipoAccion.getTexto());
        if(tipoAccion == AccionUsuario.MODIFICAR){
            view.setNombre(usuario.getNombre());
            view.setNombreDeUsuiario(usuario.getNombreUsuario());
            view.setTelefono(usuario.getTelefono());
            if(!mostrarContrasenia){
                view.setContrasenia("---");
                view.estadoContrasenia(false);
            }else
                view.setContrasenia(usuario.getContrasena());
        }
        if(tipoUsuario == Hogar.class){
            view.visibilizarTransitorio(true);
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
            T usuario = tipoUsuario.getDeclaredConstructor().newInstance();
            usuario.setNombre(view.getNombre());
            usuario.setContrasenia(view.getContrasenia());
            usuario.setTelefono(view.getTelefono());
            usuario.setNombreUsuario(view.getNombreDeUsuiario());
            if(usuario instanceof Hogar hog){
                hog.setTransitorio(view.isTransitorio());
            }
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
            usuario.setTelefono(view.getTelefono());
            usuario.setNombreUsuario(view.getNombreDeUsuiario());
            if(usuario instanceof Hogar hog){
                hog.setTransitorio(view.isTransitorio());
            }
            dao.edit(usuario);
            view.mostrarMensaje("Usuario modificado correctamente");
        } catch (Exception ex) {
            ex.printStackTrace();
            view.mostrarMensaje("Error al modificar usuario");
        }
    }
    
    public void setUsuario(T usuario) {
        this.usuario = usuario;
        iniciar(tipoAccion, true);
    }

}
