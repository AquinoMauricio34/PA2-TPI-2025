/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.DAOImpl.UsuarioJpaController;
import com.mycompany.tpi2025.Utils;
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

    public AMUsuarioController(AMUsuarioView view, Class<T> tipoUsuario, boolean mostrarContrasenia, EntityManagerFactory emf, AccionUsuario tipoAccion) {
        this(view, null, tipoUsuario, mostrarContrasenia, emf, tipoAccion);
    }

    public AMUsuarioController(AMUsuarioView view, T usuario, Class<T> tipoUsuario, boolean mostrarContrasenia, EntityManagerFactory emf, AccionUsuario tipoAccion) {
        this.view = view;
        this.dao = new UsuarioJpaController(emf);
        this.tipoUsuario = tipoUsuario;
        this.usuario = usuario;
        this.tipoAccion = tipoAccion;
        iniciar(tipoAccion, mostrarContrasenia);
    }

    private void iniciar(AccionUsuario tipoAccion, boolean mostrarContrasenia) {
        view.setAccionListener(l -> accion(tipoAccion));
        view.setTitulo(tipoAccion + " " + tipoUsuario.getSimpleName());
        view.setAccionTexto(tipoAccion.getTexto());
        if (tipoAccion == AccionUsuario.MODIFICAR) {
            view.estadoNombreUsuario(false);
            view.setNombre(usuario.getNombre());
            view.setNombreDeUsuiario(usuario.getNombreUsuario());
            view.setTelefono(usuario.getTelefono());
            if (!mostrarContrasenia) {
                view.setContrasenia("---");
                view.estadoContrasenia(false);
            } else {
                view.setContrasenia(usuario.getContrasena());
            }
        }
        if (tipoUsuario == Hogar.class) {
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
        String usuarioU = view.getNombreDeUsuiario().trim();
        String contraU = view.getContrasenia().trim();
        String telU = view.getTelefono().trim();
        String nombreU = view.getNombre().trim();
        try {
            if (Utils.hayVacios(usuarioU, contraU, telU, nombreU)) {
                throw new Exception("Todos los campos deben ser rellenados.");
            }
            if (dao.findUsuario(usuarioU) != null) {
                throw new Exception("El nombre de usuario no se puede utilizar\nporque ya se encuentra registrado en el sistema.");
            }
            if (!Utils.isLong(telU)) {
                throw new Exception("El telefono no es válido. Solo numeros son validos.");
            }
            T usuario = tipoUsuario.getDeclaredConstructor().newInstance();
            usuario.setNombre(nombreU);
            usuario.setContrasenia(contraU);
            usuario.setTelefono(telU);
            usuario.setNombreUsuario(usuarioU);
            if (usuario instanceof Hogar hog) {
                hog.setTransitorio(view.isTransitorio());
            }
            dao.create(usuario);
            view.mostrarMensaje("Usuario creado correctamente");
        } catch (Exception ex) {
            ex.printStackTrace();
            view.mostrarMensaje(ex.getMessage());
        }
    }

    private void modificarUsuario() {
        String usuarioU = view.getNombreDeUsuiario().trim();
        String telU = view.getTelefono().trim();
        String nombreU = view.getNombre().trim();
        try {
            if (Utils.hayVacios(usuarioU, telU, nombreU)) {
                throw new Exception("Todos los campos deben ser rellenados.");
            }
            if (!Utils.isLong(telU)) {
                throw new Exception("El telefono no es válido. Solo numeros son validos.");
            }
            usuario.setNombre(nombreU);
            usuario.setTelefono(telU);
            usuario.setNombreUsuario(usuarioU);
            if (usuario instanceof Hogar hog) {
                hog.setTransitorio(view.isTransitorio());
            }
            dao.edit(usuario);
            view.mostrarMensaje("Usuario modificado correctamente");
        } catch (Exception ex) {
            ex.printStackTrace();
            view.mostrarMensaje(ex.getMessage());
        }
    }

    public void setUsuario(T usuario) {
        this.usuario = usuario;
        iniciar(tipoAccion, true);
    }

}
