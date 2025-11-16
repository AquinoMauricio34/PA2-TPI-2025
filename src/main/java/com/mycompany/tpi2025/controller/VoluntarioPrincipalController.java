/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.JPAUtil;
import com.mycompany.tpi2025.Tpi2025;
import com.mycompany.tpi2025.model.Voluntario;
import com.mycompany.tpi2025.view.CrearGatoView;
import com.mycompany.tpi2025.view.VoluntarioViews.PanelesVoluntario;
import com.mycompany.tpi2025.view.VoluntarioViews.VoluntarioPrincipalView;
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
public class VoluntarioPrincipalController {
    
    //vista
    private VoluntarioPrincipalView view;
    //administrador usuario
    private Voluntario administrador;
    private final EntityManagerFactory emf;
    private final Map<JPanel, AMUsuarioController> AMUsuarioControllers = new HashMap<>();
    //CAMBIAR AMUsuarioController
    private final Map<JPanel, BuscarController> BuscarControllers = new HashMap<>();
    private CrearGatoController crearGatoController = null;
    private VerHistorialGatoController verHistorialGatoController = null;
    private CrearDiagnosticoController crearDiagnosticoController=null;

    public VoluntarioPrincipalController(VoluntarioPrincipalView principal, Voluntario administrador, EntityManagerFactory emf) {
        this.view = principal;
        this.administrador = administrador;
        this.emf = emf;
        iniciarView();
        mostrarDatosPrincipales();
        view.setCerrarAplicacionListener(l -> cerrarView());
        view.setCerrarSesionListener(l -> cerrarSesion());
        //GATOS
        view.setCrearGatoListener(l -> mostrarCrearGatoView(PanelesVoluntario.CREAR_GATO));
        
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
        view.mostrarPanel(PanelesVoluntario.DATOS_PRINCIPALES);
        view.cargarDatosPrincipales(
                "Datos " + administrador.getClass().getSimpleName(), 
                administrador.getNombre(),
                administrador.getTelefono(),
                administrador.getNombreUsuario()
        );
    }

    private void mostrarCrearGatoView(PanelesVoluntario identificador) {
        view.mostrarPanel(identificador);
        try {
            CrearGatoView panel = view.getPanel(identificador, CrearGatoView.class);
            if (panel == null) {
                    throw new Exception("No existe el panel");
            }
            if(crearGatoController == null) crearGatoController = new CrearGatoController(panel,"GUARDAR", emf);
            
        } catch (Exception e) {
        }
    }
    
    
    
}
