/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.JPAUtil;
import com.mycompany.tpi2025.Tpi2025;
import com.mycompany.tpi2025.controller.enums.AccionBuscar;
import com.mycompany.tpi2025.controller.enums.AccionUsuario;
import com.mycompany.tpi2025.model.Diagnostico;
import com.mycompany.tpi2025.model.Familia;
import com.mycompany.tpi2025.model.Gato;
import com.mycompany.tpi2025.model.Hogar;
import com.mycompany.tpi2025.model.Usuario;
import com.mycompany.tpi2025.model.Veterinario;
import com.mycompany.tpi2025.view.JPanels.AMUsuarioView;
import com.mycompany.tpi2025.view.JPanels.BuscarView;
import com.mycompany.tpi2025.view.JPanels.CrearDiagnosticoView;
import com.mycompany.tpi2025.view.JPanels.CrearGatoView;
import com.mycompany.tpi2025.view.JPanels.VerHistorialGatoView;
import com.mycompany.tpi2025.view.VeterinarioViews.PanelesVeterinario;
import com.mycompany.tpi2025.view.VeterinarioViews.VeterinarioPrincipalView;
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
public class VeterinarioPrincipalController {

    //vista
    private VeterinarioPrincipalView view;
    //veterinario usuario
    private Veterinario miUsu;
    private final EntityManagerFactory emf;
    private final Map<JPanel, AMUsuarioController> AMUsuarioControllers = new HashMap<>();
    //CAMBIAR AMUsuarioController
    private final Map<JPanel, BuscarController> BuscarControllers = new HashMap<>();
    private CrearGatoController crearGatoController = null;
    private CrearGatoController vmGatoController = null;
    private VerHistorialGatoController verHistorialGatoController = null;
    private CrearDiagnosticoController crearDiagnosticoController=null;
    private CrearDiagnosticoController verDiagnosticoController = null;
    private PostulacionController postulacionController=null;
    private VerPostulacionFamiliaController verPostulacionFamiliaController = null;
    private VerPostulacionHogarController verPostulacionHogarController = null;
    private TareaRealizadaController tareaRealizadaController = null;
    private ReporteController reporteController = null;
    private VisitaSeguimientoController visitaSeguimientoController = null;
    private AMUsuarioController miPerfilController = null;
    private GatoDeFamiliaController gatoDeFamiliaController = null;
    private GatoDeHogarController gatoDeHogarController = null;

    public VeterinarioPrincipalController(VeterinarioPrincipalView principal, Veterinario veterinario, EntityManagerFactory emf) {
        this.view = principal;
        this.miUsu = veterinario;
        this.emf = emf;
        iniciarView();
        mostrarDatosPrincipales();
        view.setMiPerfilListener(l -> mostrarMiPerfil());
        view.setCerrarAplicacionListener(l -> cerrarView());
        view.setCerrarSesionListener(l -> cerrarSesion());
        //CREACION
        
        view.setHistorialGatoListener(l -> establecerComunicacionHistorial_CrearDiagnosticoView());
        view.setModificarGatoListener(l -> mostrarVMGatoView(PanelesVeterinario.MODIFICAR_GATO,"MODIFICAR"));
        //EMISION APTITUD
        view.setEmitirAptitudFamiliaListener(l -> mostrarBuscarView(PanelesVeterinario.BUSCAR_FAMILIA_APTITUD, Familia.class,AccionBuscar.ESTABLECER_APTITUD,new String[]{"Nombre","Usuario","Telefono","Apta Adopción"}));
        view.setEmitirAptitudHogarListener(l -> mostrarBuscarView(PanelesVeterinario.BUSCAR_HOGAR_APTITUD, Hogar.class,AccionBuscar.ESTABLECER_APTITUD,new String[]{"Nombre","Usuario","Telefono","Apto Adopción"}));
        
        
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
        view.setVisible(true);view.toFront();
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
        view.mostrarPanel(PanelesVeterinario.DATOS_PRINCIPALES);
        view.cargarDatosPrincipales(
                "Datos " + miUsu.getClass().getSimpleName(), 
                miUsu.getNombre(),
                miUsu.getTelefono(),
                miUsu.getNombreUsuario()
        );
    }

    
    private <T extends Usuario> void mostrarBuscarView(PanelesVeterinario identificador, Class<T> tipoUsuario, AccionBuscar tipoAccion,String[] encabezados) {
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
                BuscarControllers.get(panel).iniciarTabla(encabezados);//siempre que se muestra, se actualiza la tabla
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
   
    private void mostrarVMGatoView(PanelesVeterinario identificador, String tipoAccion) {
        view.mostrarPanel(identificador);
        try {
            CrearGatoView panel = view.getPanel(identificador, CrearGatoView.class);
            if (panel == null) {
                    throw new Exception("No existe el panel");
            }
            if(vmGatoController == null) vmGatoController = new CrearGatoController(panel,tipoAccion, emf);
            if(!tipoAccion.equals("GUARDAR")){
                vmGatoController.abrirSeleccion();
            }
        } catch (Exception e) {
        }
    }
    
    
    private void establecerComunicacionHistorial_CrearDiagnosticoView() {
        view.mostrarPanel(PanelesVeterinario.VER_HISTORIAL);
        try {
            VerHistorialGatoView panel = view.getPanel(PanelesVeterinario.VER_HISTORIAL, VerHistorialGatoView.class);
            if (panel == null) {
                throw new Exception("No existe el panel");
            }
            if (verHistorialGatoController == null) {
                verHistorialGatoController = new VerHistorialGatoController(panel, emf);
                panel.setAniadirListener(l -> {
                    //abrir el panel de crear y pasarle el diagnostico
                    //System.out.println("F01");
                    mostrarCrearDiagnosticoView(PanelesVeterinario.CREAR_DIAGNOSTICO, verHistorialGatoController.getGato(), null);
                });
                panel.setSeleccionListener(l -> {
                    //System.out.println("F1");
                    mostrarVerDiagnosticoView(PanelesVeterinario.VER_DIAGNOSTICO, verHistorialGatoController.getGato(), verHistorialGatoController.getDiagnostico());
                });
            }else
                verHistorialGatoController.iniciarTabla();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    private void mostrarCrearDiagnosticoView(PanelesVeterinario identificador, Gato gato, Diagnostico diagnostico) {
        view.mostrarPanel(identificador);
        try {
            CrearDiagnosticoView panel = view.getPanel(identificador, CrearDiagnosticoView.class);
            if (panel == null) {
                throw new Exception("No existe el panel");
            }
            if (crearDiagnosticoController == null) {
                crearDiagnosticoController = new CrearDiagnosticoController(panel, diagnostico, emf);
            } else {
                if (diagnostico != null) {
                    crearDiagnosticoController.setDiagnostico(diagnostico);
                    crearDiagnosticoController.cargarCampos();
                }else{
                    crearDiagnosticoController.setDiagnostico(new Diagnostico());
                }
            }
            crearDiagnosticoController.setGato(gato);
            if (identificador == PanelesVeterinario.VER_DIAGNOSTICO) {
                panel.activarComponentes(false);
            } else {
                panel.activarComponentes(true);
                //crearDiagnosticoController.setDiagnostico(null);
                //crearDiagnosticoController.iniciar();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void mostrarVerDiagnosticoView(PanelesVeterinario identificador, Gato gato, Diagnostico diagnostico) {
        view.mostrarPanel(identificador);
        try {
            CrearDiagnosticoView panel = view.getPanel(identificador, CrearDiagnosticoView.class);
            if (panel == null) {
                throw new Exception("No existe el panel");
            }
            if (verDiagnosticoController == null) {
                verDiagnosticoController = new CrearDiagnosticoController(panel, diagnostico, emf);
            } else {
                if (diagnostico != null) {
                    verDiagnosticoController.setDiagnostico(diagnostico);
                    verDiagnosticoController.cargarCampos();
                }else{
                    verDiagnosticoController.setDiagnostico(new Diagnostico());
                }
            }
            verDiagnosticoController.setGato(gato);
            if (identificador == PanelesVeterinario.VER_DIAGNOSTICO) {
                panel.activarComponentes(false);
            } else {
                panel.activarComponentes(true);
                //crearDiagnosticoController.setDiagnostico(null);
                //crearDiagnosticoController.iniciar();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void mostrarMiPerfil(){
        view.mostrarPanel(PanelesVeterinario.MI_PERFIL);
        try {
            //System.out.println("MM0.001-------------------------------------------");
            AMUsuarioView panel = view.getPanel(PanelesVeterinario.MI_PERFIL, AMUsuarioView.class);
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
