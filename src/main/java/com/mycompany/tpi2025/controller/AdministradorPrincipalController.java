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
import com.mycompany.tpi2025.view.ABMUsuarioView;
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
    private final Map<JPanel, ABMUsuarioController> controllers = new HashMap<>();

    public AdministradorPrincipalController(AdministradorPrincipalView principal, Administrador administrador, EntityManagerFactory emf) {
        this.view = principal;
        this.administrador = administrador;
        this.emf = emf;
        iniciarView();
        mostrarDatosPrincipales();
        view.setCerrarAplicacionListener(l -> cerrarView());
        view.setCerrarSesionListener(l -> cerrarSesion());
        view.setCrearAdminListener(l -> mostrarABMUsuarioView(Paneles.CREAR_ADMINISTRADOR, Administrador.class,AccionUsuario.GUARDAR));
        view.setCrearVetListener(l -> mostrarABMUsuarioView(Paneles.CREAR_VETERINARIO, Veterinario.class,AccionUsuario.GUARDAR));
        view.setCrearVolListener(l -> mostrarABMUsuarioView(Paneles.CREAR_VOLUNTARIO, Voluntario.class,AccionUsuario.GUARDAR));
        view.setCrearFamListener(l -> mostrarABMUsuarioView(Paneles.CREAR_FAMILIA, Familia.class,AccionUsuario.GUARDAR));
        view.setCrearHogarListener(l -> mostrarABMUsuarioView(Paneles.CREAR_HOGAR, Hogar.class,AccionUsuario.GUARDAR));
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
        view.mostrarPanel(Paneles.DATOS_PRINCIPALES);
        view.cargarDatosPrincipales(
                "Datos " + administrador.getClass().getSimpleName(), 
                administrador.getNombre(),
                administrador.getTelefono(),
                administrador.getNombreUsuario()
        );
    }

    public <T extends Usuario> void mostrarABMUsuarioView(Paneles identificador, Class<T> tipoUsuario, AccionUsuario tipoAccion) {
        view.mostrarPanel(identificador);
        try {
            ABMUsuarioView panel = view.getPanel(identificador, ABMUsuarioView.class);
            if (panel == null) {
                throw new Exception("No existe el panel");
            }
            if(!controllers.containsKey(panel)){
                controllers.put(panel, new ABMUsuarioController<T>(panel, tipoUsuario, emf,tipoAccion));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
