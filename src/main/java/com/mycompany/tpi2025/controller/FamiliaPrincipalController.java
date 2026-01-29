/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.JPAUtil;
import com.mycompany.tpi2025.Tpi2025;
import com.mycompany.tpi2025.controller.enums.AccionUsuario;
import com.mycompany.tpi2025.model.Familia;
import com.mycompany.tpi2025.view.AMUsuarioView;
import com.mycompany.tpi2025.view.FamiliaViews.FamiliaPrincipalView;
import com.mycompany.tpi2025.view.FamiliaViews.PanelesFamilia;
import com.mycompany.tpi2025.view.PostulacionView;
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
public class FamiliaPrincipalController {

    //vista
    private FamiliaPrincipalView view;
    //familia usuario
    private Familia miUsu;
    private final EntityManagerFactory emf;
    private final Map<JPanel, AMUsuarioController> AMUsuarioControllers = new HashMap<>();
    //CAMBIAR AMUsuarioController
    private final Map<JPanel, BuscarController> BuscarControllers = new HashMap<>();
    private CrearGatoController crearGatoController = null;
    private CrearGatoController vmGatoController = null;
    private VerHistorialGatoController verHistorialGatoController = null;
    private CrearDiagnosticoController crearDiagnosticoController=null;
    private PostulacionController postulacionController=null;
    private VerPostulacionFamiliaController verPostulacionFamiliaController = null;
    private VerPostulacionHogarController verPostulacionHogarController = null;
    private TareaRealizadaController tareaRealizadaController = null;
    private ReporteController reporteController = null;
    private VisitaSeguimientoController visitaSeguimientoController = null;
    private AMUsuarioController miPerfilController = null;
    private GatoDeFamiliaController gatoDeFamiliaController = null;
    private GatoDeHogarController gatoDeHogarController = null;

    public FamiliaPrincipalController(FamiliaPrincipalView principal, Familia familia, EntityManagerFactory emf) {
        this.view = principal;
        this.miUsu = familia;
        this.emf = emf;
        iniciarView();
        mostrarDatosPrincipales();
        view.setMiPerfilListener(l -> mostrarMiPerfil());
        view.setCerrarAplicacionListener(l -> cerrarView());
        view.setCerrarSesionListener(l -> cerrarSesion());
        //CREACION
        //POSTULACION
        view.setPostulacionListener(l -> mostrarPostulacionView(PanelesFamilia.POSTULARSE));
        
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
        view.mostrarPanel(PanelesFamilia.DATOS_PRINCIPALES);
        view.cargarDatosPrincipales(
                "Datos " + miUsu.getClass().getSimpleName(), 
                miUsu.getNombre(),
                miUsu.getTelefono(),
                miUsu.getNombreUsuario()
        );
    }

    
    
    private void mostrarPostulacionView(PanelesFamilia identificador) {
        view.mostrarPanel(identificador);
        try {
            PostulacionView panel = view.getPanel(identificador, PostulacionView.class);
            if (panel == null) {
                    throw new Exception("No existe el panel");
            }
            if(postulacionController == null){
                postulacionController = new PostulacionController(panel,miUsu.getNombreUsuario(), emf);
            }else // esta en el else para no iniciar la tabla 2 veces si se crea el controller por primera vez
                postulacionController.iniciarTabla();
        } catch (Exception e) {
        }
    }
    
    
    
    private void mostrarMiPerfil(){
        view.mostrarPanel(PanelesFamilia.MI_PERFIL);
        try {
            //System.out.println("MM0.001-------------------------------------------");
            AMUsuarioView panel = view.getPanel(PanelesFamilia.MI_PERFIL, AMUsuarioView.class);
            //System.out.println("MM0.01-------------------------------------------");
            if (panel == null) {
                    throw new Exception("No existe el panel");
            }
            if(miPerfilController == null){
                //System.out.println("MM0.1-------------------------------------------");
                miPerfilController = new AMUsuarioController(panel, miUsu, miUsu.getClass(),true, emf, AccionUsuario.MODIFICAR);
            }
            //System.out.println("MM0.2-------------------------------------------");
        } catch (Exception e) {
            //System.out.println("MMm1-------------------------------------------");
        }
    }
    
    
    
}
