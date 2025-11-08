/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.JPAUtil;
import com.mycompany.tpi2025.Tpi2025;
import com.mycompany.tpi2025.model.Administrador;
import com.mycompany.tpi2025.model.Familia;
import com.mycompany.tpi2025.model.Hogar;
import com.mycompany.tpi2025.model.Usuario;
import com.mycompany.tpi2025.model.Veterinario;
import com.mycompany.tpi2025.model.Voluntario;
import com.mycompany.tpi2025.view.AdministradorViews.AdministradorPrincipalView;
import com.mycompany.tpi2025.view.CrearUsuarioView;
import jakarta.persistence.EntityManagerFactory;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;

/**
 *
 * @author aquin
 */
public class AdministradorPrincipalController {

    //vista
    private AdministradorPrincipalView view;
    //administrador usuario
    private Administrador administrador;
    private final EntityManagerFactory emf;
    private final Map<JPanel, CrearUsuarioController> controllers = new HashMap<>();

    public AdministradorPrincipalController(AdministradorPrincipalView principal, Administrador administrador, EntityManagerFactory emf) {
        this.view = principal;
        this.administrador = administrador;
        this.emf = emf;
        iniciarView();
        mostrarDatosPrincipales();
        view.setCerrarAplicacionListener(l -> cerrarView());
        view.setCerrarSesionListener(l -> cerrarSesion());
        view.setCrearAdminListener(l -> mostrarCrearUsuarioView("CREAR_ADMINISTRADOR", Administrador.class));
        view.setCrearVetListener(l -> mostrarCrearUsuarioView("CREAR_VETERINARIO", Veterinario.class));
        view.setCrearVolListener(l -> mostrarCrearUsuarioView("CREAR_VOLUNTARIO", Voluntario.class));
        view.setCrearFamListener(l -> mostrarCrearUsuarioView("CREAR_FAMILIA", Familia.class));
        view.setCrearHogarListener(l -> mostrarCrearUsuarioView("CREAR_HOGAR", Hogar.class));
    }

    public void cerrarView() {
        view.dispose();
        view = null;
        JPAUtil.close(emf);
        System.exit(0);
    }
    
    public void cerrarSesion(){
        view.dispose();
        view = null;
        Tpi2025.login(emf);
    }

    public void iniciarView() {
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

    public void mostrarDatosPrincipales() {
        view.mostrarPanel("DATOS_PRINCIPALES");
        view.cargarDatosPrincipales("Datos " + administrador.getClass().getSimpleName(), administrador.getNombre(), administrador.getTelefono(), administrador.getNombreUsuario());
    }

    public <T extends Usuario> void mostrarCrearUsuarioView(String identificador, Class<T> tipoUsuario) {
        view.mostrarPanel(identificador);
        try {
            CrearUsuarioView panel = view.getPanel(identificador, CrearUsuarioView.class);
            if (panel == null) {
                throw new Exception("No existe el panel");
            }
            if(!controllers.containsKey(panel)){
                controllers.put(panel, new CrearUsuarioController<T>(panel, tipoUsuario, emf));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
