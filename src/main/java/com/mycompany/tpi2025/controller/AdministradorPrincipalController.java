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
import com.mycompany.tpi2025.view.BuscarView;
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
    private final Map<JPanel, ABMUsuarioController> ABMUsuarioControllers = new HashMap<>();
    //CAMBIAR ABMUsuarioController
    private final Map<JPanel, BuscarController> BuscarControllers = new HashMap<>();

    public AdministradorPrincipalController(AdministradorPrincipalView principal, Administrador administrador, EntityManagerFactory emf) {
        this.view = principal;
        this.administrador = administrador;
        this.emf = emf;
        iniciarView();
        mostrarDatosPrincipales();
        view.setCerrarAplicacionListener(l -> cerrarView());
        view.setCerrarSesionListener(l -> cerrarSesion());
        //CREACION
        view.setCrearAdminListener(l -> mostrarABMUsuarioView(PanelesAdministrador.CREAR_ADMINISTRADOR, Administrador.class,AccionUsuario.GUARDAR));
        view.setCrearVetListener(l -> mostrarABMUsuarioView(PanelesAdministrador.CREAR_VETERINARIO, Veterinario.class,AccionUsuario.GUARDAR));
        view.setCrearVolListener(l -> mostrarABMUsuarioView(PanelesAdministrador.CREAR_VOLUNTARIO, Voluntario.class,AccionUsuario.GUARDAR));
        view.setCrearFamListener(l -> mostrarABMUsuarioView(PanelesAdministrador.CREAR_FAMILIA, Familia.class,AccionUsuario.GUARDAR));
        view.setCrearHogarListener(l -> mostrarABMUsuarioView(PanelesAdministrador.CREAR_HOGAR, Hogar.class,AccionUsuario.GUARDAR));
        //BUSQUEDA
        String[] encabezados = {"Nombre","Nombre de Usuario","Telefono"};
        view.setBuscarAdminListener(l -> mostrarBuscarView(PanelesAdministrador.BUSCAR_ADMINISTRADOR, Administrador.class,AccionBuscar.DETALLES,encabezados));
        view.setBuscarVetListener(l -> mostrarBuscarView(PanelesAdministrador.BUSCAR_VETERINARIO, Veterinario.class,AccionBuscar.DETALLES,encabezados));
        view.setBuscarVolListener(l -> mostrarBuscarView(PanelesAdministrador.BUSCAR_VOLUNTARIO, Voluntario.class,AccionBuscar.DETALLES,encabezados));
        view.setBuscarFamListener(l -> mostrarBuscarView(PanelesAdministrador.BUSCAR_FAMILIA, Familia.class,AccionBuscar.DETALLES,encabezados));
        view.setBuscarHogarListener(l -> mostrarBuscarView(PanelesAdministrador.BUSCAR_HOGAR, Hogar.class,AccionBuscar.DETALLES,encabezados));
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
        view.mostrarPanel(PanelesAdministrador.DATOS_PRINCIPALES);
        view.cargarDatosPrincipales(
                "Datos " + administrador.getClass().getSimpleName(), 
                administrador.getNombre(),
                administrador.getTelefono(),
                administrador.getNombreUsuario()
        );
    }

    public <T extends Usuario> void mostrarABMUsuarioView(PanelesAdministrador identificador, Class<T> tipoUsuario, AccionUsuario tipoAccion) {
        view.mostrarPanel(identificador);
        try {
            ABMUsuarioView panel = view.getPanel(identificador, ABMUsuarioView.class);
            if (panel == null) {
                throw new Exception("No existe el panel");
            }
            //si todavía no tiene controller, se crea y guarda en el HashMap
            if(!ABMUsuarioControllers.containsKey(panel)){
                ABMUsuarioControllers.put(panel, new ABMUsuarioController<T>(panel, tipoUsuario, emf,tipoAccion));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private <T extends Usuario> void mostrarBuscarView(PanelesAdministrador identificador, Class<T> tipoUsuario, AccionBuscar tipoAccion,String[] encabezados) {
        view.mostrarPanel(identificador);
        try {
            BuscarView panel = view.getPanel(identificador, BuscarView.class);
            if (panel == null) {
                    throw new Exception("No existe el panel");
            }
            //si todavía no tiene controller, se crea y guarda en el HashMap
            if(!BuscarControllers.containsKey(panel)){
                BuscarControllers.put(panel, new BuscarController<T>(panel, tipoUsuario, tipoAccion, encabezados, emf));
            }else
                BuscarControllers.get(panel).iniciarTabla(encabezados);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
