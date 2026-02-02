/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.JPAUtil;
import com.mycompany.tpi2025.Tpi2025;
import com.mycompany.tpi2025.controller.enums.AccionBuscar;
import com.mycompany.tpi2025.controller.enums.AccionUsuario;
import com.mycompany.tpi2025.model.Familia;
import com.mycompany.tpi2025.model.Hogar;
import com.mycompany.tpi2025.model.Voluntario;
import com.mycompany.tpi2025.view.AMUsuarioView;
import com.mycompany.tpi2025.view.BuscarView;
import com.mycompany.tpi2025.view.CrearGatoView;
import com.mycompany.tpi2025.view.TareaRealizadaView;
import com.mycompany.tpi2025.view.VerPostulacionFamiliaView;
import com.mycompany.tpi2025.view.VerPostulacionHogarView;
import com.mycompany.tpi2025.view.VisitaSeguimientoView;
import com.mycompany.tpi2025.view.VoluntarioViews.PanelesVoluntario;
import com.mycompany.tpi2025.view.VoluntarioViews.VoluntarioPrincipalView;
import jakarta.persistence.EntityManagerFactory;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author aquin
 */
public class VoluntarioPrincipalController {

    //vista
    private VoluntarioPrincipalView view;
    //voluntario usuario
    private Voluntario miUsu;
    private final EntityManagerFactory emf;
    private final Map<JPanel, AMUsuarioController> AMUsuarioControllers = new HashMap<>();
    //CAMBIAR AMUsuarioController
    private final Map<JPanel, BuscarController> BuscarControllers = new HashMap<>();
    private CrearGatoController crearGatoController = null;
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
    private CrearGatoController vmGatoController = null;

    public VoluntarioPrincipalController(VoluntarioPrincipalView principal, Voluntario voluntario, EntityManagerFactory emf) {
        this.view = principal;
        this.miUsu = voluntario;
        this.emf = emf;
        iniciarView();
        mostrarDatosPrincipales();
        view.setMiPerfilListener(l -> mostrarMiPerfil());
        view.setCerrarAplicacionListener(l -> cerrarView());
        view.setCerrarSesionListener(l -> cerrarSesion());
        //CREACION
        //GATOS
        view.setCrearGatoListener(l -> mostrarCrearGatoView(PanelesVoluntario.CREAR_GATO,"GUARDAR"));
        
        //ASIGNACION GATO
        view.setAsignacionFamiliaListener(l -> establecerComunicacionBuscarView_VerPostulacion());
        view.setAsignacionHogarListener(l -> establecerComunicacionBuscarHogarView_VerPostulacion());
        //TAREA REALIZADA
        view.setTareaRealizadaListener(l -> establecerComunicacionBuscarVoluntarioView_TareaRealizada());
        //REPORTE
        view.setVisitaListener(l -> mostrarVisita());
        view.setModificarGatoListener(l -> mostrarVMGatoView(PanelesVoluntario.MODIFICAR_GATO,"MODIFICAR"));
        
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
        view.mostrarPanel(PanelesVoluntario.DATOS_PRINCIPALES);
        view.cargarDatosPrincipales(
                "Datos " + miUsu.getClass().getSimpleName(), 
                miUsu.getNombre(),
                miUsu.getTelefono(),
                miUsu.getNombreUsuario()
        );
    }

    
    private void mostrarCrearGatoView(PanelesVoluntario identificador, String tipoAccion) {
        view.mostrarPanel(identificador);
        try {
            CrearGatoView panel = view.getPanel(identificador, CrearGatoView.class);
            if (panel == null) {
                    throw new Exception("No existe el panel");
            }
            if(crearGatoController == null) crearGatoController = new CrearGatoController(panel,tipoAccion, emf);
            if(!tipoAccion.equals("GUARDAR")){
                crearGatoController.abrirSeleccion();
            }
        } catch (Exception e) {
        }
    }
    
    
    
    private void establecerComunicacionBuscarView_VerPostulacion() {
        view.mostrarPanel(PanelesVoluntario.BUSCAR_FAMILIA_ASIGNACION);
        try {
            BuscarView panel = view.getPanel(PanelesVoluntario.BUSCAR_FAMILIA_ASIGNACION, BuscarView.class);
            if (panel == null) {
                throw new Exception("No existe el panel");
            }
            //si todavía no tiene controller, se crea y guarda en el HashMap
            if (!BuscarControllers.containsKey(panel)) {
                //System.out.println("crear controller..........-----------------------------------------------");
                BuscarControllers.put(panel, new BuscarController<Familia>(panel, Familia.class, AccionBuscar.SELECCION, new String[]{"Nombre", "Nombre de Usuario", "Telefono"}, emf));
                panel.setAccionListener(l -> {
                    Familia f = (Familia) BuscarControllers.get(panel).getUsuario();
                    System.out.println("familia nombre: " + f.getNombre());
                    if (f.isAptoAdopcion()) {
                        mostrarVerPostulacionFamiliaView(f);
                    } else {
                        JOptionPane.showMessageDialog(panel, "El usuario \"" + f.getNombreUsuario() + "\" no tiene emitido la aptitud para la adopción.");
                    }
                });
            } else {
                BuscarControllers.get(panel).iniciarTabla(new String[]{"Nombre", "Nombre de Usuario", "Telefono"});//siempre que se muestra, se actualiza la tabla
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    private void mostrarVerPostulacionFamiliaView(Familia familia) {
        //System.out.println(familia);
        view.mostrarPanel(PanelesVoluntario.ASIGNAR_GATO_FAMILIA);
        try {
            VerPostulacionFamiliaView panel = view.getPanel(PanelesVoluntario.ASIGNAR_GATO_FAMILIA, VerPostulacionFamiliaView.class);
            //System.out.println("MM0.01-------------------------------------------");
            if (panel == null) {
                throw new Exception("No existe el panel");
            }
            if (verPostulacionFamiliaController == null) {
                //System.out.println("MM0.1-------------------------------------------");
                verPostulacionFamiliaController = new VerPostulacionFamiliaController(panel, familia, emf);
            } else {// esta en el else para no iniciar la tabla 2 veces si se crea el controller por primera vez
                verPostulacionFamiliaController.setFamilia(familia);
                verPostulacionFamiliaController.iniciarView();
                verPostulacionFamiliaController.iniciarTabla();
            }
            //System.out.println("MM0.2-------------------------------------------");
        } catch (Exception e) {
            //System.out.println("MMm1-------------------------------------------");
        }
    }
    private void establecerComunicacionBuscarHogarView_VerPostulacion() {
        view.mostrarPanel(PanelesVoluntario.BUSCAR_HOGAR_ASIGNACION);
        try {
            BuscarView panel = view.getPanel(PanelesVoluntario.BUSCAR_HOGAR_ASIGNACION, BuscarView.class);
            if (panel == null) {
                throw new Exception("No existe el panel");
            }
            //si todavía no tiene controller, se crea y guarda en el HashMap
            if (!BuscarControllers.containsKey(panel)) {
                //System.out.println("crear controller..........-----------------------------------------------");
                BuscarControllers.put(panel, new BuscarController<Hogar>(panel, Hogar.class, AccionBuscar.SELECCION, new String[]{"Nombre", "Nombre de Usuario", "Telefono"}, emf));
                panel.setAccionListener(l -> {
                    Hogar h = (Hogar) BuscarControllers.get(panel).getUsuario();
                    if (h.isAptoAdopcion()) {
                        mostrarVerPostulacionHogarView(h);
                    } else {
                        JOptionPane.showMessageDialog(panel, "El usuario \"" + h.getNombreUsuario() + "\" no tiene emitido la aptitud para la adopción.");
                    }
                });
            } else {
                BuscarControllers.get(panel).iniciarTabla(new String[]{"Nombre", "Nombre de Usuario", "Telefono"});//siempre que se muestra, se actualiza la tabla
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    private void mostrarVerPostulacionHogarView(Hogar hogar) {
        //System.out.println(hogar);
        view.mostrarPanel(PanelesVoluntario.ASIGNAR_GATO_HOGAR);
        //System.out.println("MM0.0001-------------------------------------------");
        try {
            //System.out.println("MM0.001-------------------------------------------");
            VerPostulacionHogarView panel = view.getPanel(PanelesVoluntario.ASIGNAR_GATO_HOGAR, VerPostulacionHogarView.class);
            //System.out.println("MM0.01-------------------------------------------");
            if (panel == null) {
                throw new Exception("No existe el panel");
            }
            if (verPostulacionHogarController == null) {
                //System.out.println("MM0.1-------------------------------------------");
                verPostulacionHogarController = new VerPostulacionHogarController(panel, hogar, emf);
            } else // esta en el else para no iniciar la tabla 2 veces si se crea el controller por primera vez
            {
                verPostulacionHogarController.setHogar(hogar);
            }
            verPostulacionHogarController.iniciarView();
            verPostulacionHogarController.iniciarTabla();
            //System.out.println("MM0.2-------------------------------------------");
        } catch (Exception e) {
            //System.out.println("MMm1-------------------------------------------");
        }
    }
    
    private void establecerComunicacionBuscarVoluntarioView_TareaRealizada(){
        view.mostrarPanel(PanelesVoluntario.BUSCAR_VOLUNTARIO_TAREA_REALIZADA);
        try {
            BuscarView panel = view.getPanel(PanelesVoluntario.BUSCAR_VOLUNTARIO_TAREA_REALIZADA, BuscarView.class);
            if (panel == null) {
                    throw new Exception("No existe el panel");
            }
            //si todavía no tiene controller, se crea y guarda en el HashMap
            if(!BuscarControllers.containsKey(panel)){
                //System.out.println("crear controller..........-----------------------------------------------");
                BuscarControllers.put(panel, new BuscarController<Voluntario>(panel, Voluntario.class, AccionBuscar.DETALLES, new String[]{"Nombre","Nombre de Usuario","Telefono"}, emf));
                panel.setAccionListener(l -> {
                    Voluntario h = (Voluntario) BuscarControllers.get(panel).getUsuario();
                    mostrarTareaRealizadaView(h);
                });
            }else
                BuscarControllers.get(panel).iniciarTabla(new String[]{"Nombre","Nombre de Usuario","Telefono"});//siempre que se muestra, se actualiza la tabla
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    private void mostrarVMGatoView(PanelesVoluntario identificador, String tipoAccion) {
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

    private void mostrarTareaRealizadaView(Voluntario h) {
        view.mostrarPanel(PanelesVoluntario.TAREA_REALIZADA);
            //System.out.println("MM0.0001-------------------------------------------");
        try {
            //System.out.println("MM0.001-------------------------------------------");
            TareaRealizadaView panel = view.getPanel(PanelesVoluntario.TAREA_REALIZADA, TareaRealizadaView.class);
            //System.out.println("MM0.01-------------------------------------------");
            if (panel == null) {
                    throw new Exception("No existe el panel");
            }
            if(tareaRealizadaController == null){
                //System.out.println("MM0.1-------------------------------------------");
                tareaRealizadaController = new TareaRealizadaController(panel,h, emf);
            }
            //System.out.println("MM0.2-------------------------------------------");
        } catch (Exception e) {
            //System.out.println("MMm1-------------------------------------------");
        }
    }
    
    
    
    private void mostrarVisita(){
        view.mostrarPanel(PanelesVoluntario.VISITA);
        try {
            //System.out.println("MM0.001-------------------------------------------");
            VisitaSeguimientoView panel = view.getPanel(PanelesVoluntario.VISITA, VisitaSeguimientoView.class);
            //System.out.println("MM0.01-------------------------------------------");
            if (panel == null) {
                    throw new Exception("No existe el panel");
            }
            if(visitaSeguimientoController == null){
                //System.out.println("MM0.1-------------------------------------------");
                visitaSeguimientoController = new VisitaSeguimientoController(panel,miUsu.getNombreUsuario(), emf);
            }else
                visitaSeguimientoController.abrirSeleccion();
            //System.out.println("MM0.2-------------------------------------------");
        } catch (Exception e) {
            //System.out.println("MMm1-------------------------------------------");
        }
    }
    
    private void mostrarMiPerfil(){
        view.mostrarPanel(PanelesVoluntario.MI_PERFIL);
        try {
            //System.out.println("MM0.001-------------------------------------------");
            AMUsuarioView panel = view.getPanel(PanelesVoluntario.MI_PERFIL, AMUsuarioView.class);
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
