/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.DAOImpl.UsuarioJpaController;
import com.mycompany.tpi2025.model.Usuario;
import com.mycompany.tpi2025.view.CrearUsuarioView;
import jakarta.persistence.EntityManagerFactory;

/**
 *
 * @author aquin
 * @param <T>
 */
public class CrearUsuarioController<T extends Usuario> {
    private final CrearUsuarioView view;
    private final UsuarioJpaController dao;
    private final Class<T> tipoUsuario;

    public CrearUsuarioController(CrearUsuarioView view,Class<T> tipoUsuario, EntityManagerFactory emf) {
        this.view = view;
        this.dao = new UsuarioJpaController(emf);
        this.tipoUsuario = tipoUsuario;
        System.out.println("Controller------------------------------------------------");
        iniciarView();
        view.setRegistrarListener(l -> guardarUsuario());
    }
    
    public void guardarUsuario(){
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

    private void iniciarView() {
        view.setVisible(true);
        view.setTitulo("Registro de "+tipoUsuario.getSimpleName());
    }
    
    
}

