/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.DAOImpl.UsuarioJpaController;
import com.mycompany.tpi2025.JPAUtil;
import com.mycompany.tpi2025.model.Administrador;
import com.mycompany.tpi2025.model.Familia;
import com.mycompany.tpi2025.model.Usuario;
import com.mycompany.tpi2025.model.Veterinario;
import com.mycompany.tpi2025.model.Voluntario;
import com.mycompany.tpi2025.view.AdministradorViews.AdministradorPrincipalView;
import com.mycompany.tpi2025.view.LoginView;
import jakarta.persistence.EntityManagerFactory;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author aquin
 */
public class LoginController {

    private LoginView view;
    private final EntityManagerFactory emf;
    private UsuarioJpaController usuarioDao;

    public LoginController(LoginView login, EntityManagerFactory emf) {
        this.view = login;
        this.emf = emf;
        this.usuarioDao = new UsuarioJpaController(emf);
        iniciarView();
        login.setIniciarSesionListener(e -> iniciarSesion());
        login.setCerrarListener(e -> cerrarView());
        
        
        
    }

    private void iniciarSesion() {
        String usuario = view.getNombreUsuario();
        String contrasenia = view.getContraseniaUsuario();

        Usuario usu = usuarioDao.findUsuario(usuario);
        if (usu == null) {
            JOptionPane.showMessageDialog(view, "NO EXISTE EL USUARIO.");
        } else {
            if (usu.getContrasena().equals(contrasenia)) {
                //asignar a como usuario principal
                switch (usu) {
                    case Administrador admin -> {
                        AdministradorPrincipalView view = new AdministradorPrincipalView();
                        AdministradorPrincipalController contr = new AdministradorPrincipalController(view, admin, emf);
                    }
                    case Voluntario vol -> {
                    }
                    case Veterinario vet -> {
                    }
                    case Familia fam -> {
                    }
                    default -> {
                    }
                }
                cerrarView();
            }
        }
        

    }

    private void iniciarView() {
        view.setVisible(true);
        view.setLocationRelativeTo(null);
        view.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JPAUtil.close(emf);
                System.exit(0);
            }
        });
    }

    private void cerrarView() {
        view.dispose();
        view = null;
    }

}
