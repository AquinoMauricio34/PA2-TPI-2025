/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.DAOImpl.UsuarioJpaController;
import com.mycompany.tpi2025.model.Familia;
import com.mycompany.tpi2025.view.SignInView;
import jakarta.persistence.EntityManagerFactory;
import javax.swing.JOptionPane;

/**
 *
 * @author aquin
 */
public class SignInController {
    private SignInView view;
    private UsuarioJpaController dao;

    public SignInController(SignInView view, EntityManagerFactory emf) {
        this.view = view;
        this.dao = new UsuarioJpaController(emf);
        view.setAccionListener(l -> registrar());
        iniciarView();
    }

    private void registrar() {
        try {
            Familia v = new Familia(view.getNombre(), view.getContrasenia(), view.getTelefono(), view.getNombreDeUsuiario());
            dao.create(v);
            JOptionPane.showMessageDialog(view, "Familia registrada.");
            cerrar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void iniciarView() {
        view.setVisible(true);view.toFront();
        view.setLocationRelativeTo(null);
    }

    private void cerrar() {
        view.dispose();
        view = null;
    }
    
    
}
