/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.JPAUtil;
import com.mycompany.tpi2025.Tpi2025;
import com.mycompany.tpi2025.controller.enums.AccionBuscar;
import com.mycompany.tpi2025.controller.enums.AccionUsuario;
import com.mycompany.tpi2025.model.Administrador;
import com.mycompany.tpi2025.model.Diagnostico;
import com.mycompany.tpi2025.model.Familia;
import com.mycompany.tpi2025.model.Gato;
import com.mycompany.tpi2025.model.Hogar;
import com.mycompany.tpi2025.model.Usuario;
import com.mycompany.tpi2025.model.Veterinario;
import com.mycompany.tpi2025.model.Voluntario;
import com.mycompany.tpi2025.view.AdministradorViews.AdministradorPrincipalView;
import com.mycompany.tpi2025.view.AdministradorViews.PanelesAdministrador;
import com.mycompany.tpi2025.view.JPanels.ABZonaView;
import com.mycompany.tpi2025.view.JPanels.AMUsuarioView;
import com.mycompany.tpi2025.view.JPanels.BuscarView;
import com.mycompany.tpi2025.view.JPanels.CrearDiagnosticoView;
import com.mycompany.tpi2025.view.JPanels.CrearGatoView;
import com.mycompany.tpi2025.view.JPanels.GatosDeFamiliaView;
import com.mycompany.tpi2025.view.JPanels.GatosDeHogarView;
import com.mycompany.tpi2025.view.JPanels.PostulacionView;
import com.mycompany.tpi2025.view.JPanels.ReporteView;
import com.mycompany.tpi2025.view.JPanels.TareaRealizadaView;
import com.mycompany.tpi2025.view.JPanels.VerHistorialGatoView;
import com.mycompany.tpi2025.view.JPanels.VerPostulacionFamiliaView;
import com.mycompany.tpi2025.view.JPanels.VerPostulacionHogarView;
import com.mycompany.tpi2025.view.JPanels.VerPostulacionesView;
import com.mycompany.tpi2025.view.JPanels.VerTareasRealizadasView;
import com.mycompany.tpi2025.view.JPanels.VerVisitasSeguimientoView;
import com.mycompany.tpi2025.view.JPanels.VisitaSeguimientoView;
import com.mycompany.tpi2025.view.QRGatoView;
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
public class AdministradorPrincipalController {

    //vista
    private AdministradorPrincipalView view;
    //administrador usuario
    private Administrador miUsu;
    private final EntityManagerFactory emf;
    private final Map<JPanel, AMUsuarioController> AMUsuarioControllers = new HashMap<>();
    //CAMBIAR AMUsuarioController
    private final Map<JPanel, BuscarController> BuscarControllers = new HashMap<>();
    private CrearGatoController crearGatoController = null;
    private CrearGatoController vmGatoController = null;
    private VerHistorialGatoController verHistorialGatoController = null;
    private CrearDiagnosticoController crearDiagnosticoController = null;
    private CrearDiagnosticoController verDiagnosticoController = null;
    private PostulacionController postulacionController = null;
    private PostulacionHogarController postulacionHogarController = null;
    private VerPostulacionFamiliaController verPostulacionFamiliaController = null;
    private VerPostulacionHogarController verPostulacionHogarController = null;
    private TareaRealizadaController tareaRealizadaController = null;
    private ReporteController reporteController = null;
    private VisitaSeguimientoController visitaSeguimientoController = null;
    private AMUsuarioController miPerfilController = null;
    private GatoDeFamiliaController gatoDeFamiliaController = null;
    private GatoDeHogarController gatoDeHogarController = null;
    private CrearZonaController zonaController = null;
    private VerTareasRealizadaController verTareasRealizadaController = null;
    private VerVisitasSeguimientoController verVisitasSeguimientoController = null;
    private VerPostulacionesController verPostulacionesController = null;

    public AdministradorPrincipalController(AdministradorPrincipalView principal, Administrador administrador, EntityManagerFactory emf) {
        this.view = principal;
        this.miUsu = administrador;
        this.emf = emf;
        iniciarView();
        mostrarDatosPrincipales();
        view.setMiPerfilListener(l -> mostrarMiPerfil());
        view.setCerrarAplicacionListener(l -> cerrarView());
        view.setCerrarSesionListener(l -> cerrarSesion());
        //CREACION
        view.setCrearAdminListener(l -> mostrarAMUsuarioView(PanelesAdministrador.CREAR_ADMINISTRADOR, Administrador.class, AccionUsuario.GUARDAR));
        view.setCrearVetListener(l -> mostrarAMUsuarioView(PanelesAdministrador.CREAR_VETERINARIO, Veterinario.class, AccionUsuario.GUARDAR));
        view.setCrearVolListener(l -> mostrarAMUsuarioView(PanelesAdministrador.CREAR_VOLUNTARIO, Voluntario.class, AccionUsuario.GUARDAR));
        view.setCrearFamListener(l -> mostrarAMUsuarioView(PanelesAdministrador.CREAR_FAMILIA, Familia.class, AccionUsuario.GUARDAR));
        view.setCrearHogarListener(l -> mostrarAMUsuarioView(PanelesAdministrador.CREAR_HOGAR, Hogar.class, AccionUsuario.GUARDAR));
        //BUSQUEDA
        String[] encabezados = {"Nombre", "Nombre de Usuario", "Telefono"};
        view.setBuscarAdminListener(l -> mostrarBuscarView(PanelesAdministrador.BUSCAR_ADMINISTRADOR, Administrador.class, AccionBuscar.DETALLES, encabezados));
        view.setBuscarVetListener(l -> mostrarBuscarView(PanelesAdministrador.BUSCAR_VETERINARIO, Veterinario.class, AccionBuscar.DETALLES, encabezados));
        view.setBuscarVolListener(l -> mostrarBuscarView(PanelesAdministrador.BUSCAR_VOLUNTARIO, Voluntario.class, AccionBuscar.DETALLES, encabezados));
        view.setBuscarFamListener(l -> mostrarBuscarView(PanelesAdministrador.BUSCAR_FAMILIA, Familia.class, AccionBuscar.DETALLES, encabezados));
        view.setBuscarHogarListener(l -> mostrarBuscarView(PanelesAdministrador.BUSCAR_HOGAR, Hogar.class, AccionBuscar.DETALLES, encabezados));
        //ELIMINACION
        view.setEliminarAdminListener(l -> mostrarBuscarView(PanelesAdministrador.ELIMINAR_ADMINISTRADOR, Administrador.class, AccionBuscar.ELIMINAR, encabezados));
        view.setEliminarVetListener(l -> mostrarBuscarView(PanelesAdministrador.ELIMINAR_VETERINARIO, Veterinario.class, AccionBuscar.ELIMINAR, encabezados));
        view.setEliminarVolListener(l -> mostrarBuscarView(PanelesAdministrador.ELIMINAR_VOLUNTARIO, Voluntario.class, AccionBuscar.ELIMINAR, encabezados));
        view.setEliminarFamListener(l -> mostrarBuscarView(PanelesAdministrador.ELIMINAR_FAMILIA, Familia.class, AccionBuscar.ELIMINAR, encabezados));
        view.setEliminarHogarListener(l -> mostrarBuscarView(PanelesAdministrador.ELIMINAR_HOGAR, Hogar.class, AccionBuscar.ELIMINAR, encabezados));
        //MODIFICACION
        view.setModificarAdminListener(l -> gestionModificacion(PanelesAdministrador.BUSCAR_ADMINISTRADOR_MODIFICAR, PanelesAdministrador.MODIFICAR_ADMINISTRADOR, Administrador.class, encabezados));
        view.setModificarVetListener(l -> gestionModificacion(PanelesAdministrador.BUSCAR_VETERINARIO_MODIFICAR, PanelesAdministrador.MODIFICAR_VETERINARIO, Veterinario.class, encabezados));
        view.setModificarVolListener(l -> gestionModificacion(PanelesAdministrador.BUSCAR_VOLUNTARIO_MODIFICAR, PanelesAdministrador.MODIFICAR_VOLUNTARIO, Voluntario.class, encabezados));
        view.setModificarFamListener(l -> gestionModificacion(PanelesAdministrador.BUSCAR_FAMILIA_MODIFICAR, PanelesAdministrador.MODIFICAR_FAMILIA, Familia.class, encabezados));
        view.setModificarHogarListener(l -> gestionModificacion(PanelesAdministrador.BUSCAR_HOGAR_MODIFICAR, PanelesAdministrador.MODIFICAR_HOGAR, Hogar.class, encabezados));
        //GATOS
        view.setCrearGatoListener(l -> mostrarCrearGatoView(PanelesAdministrador.CREAR_GATO, "GUARDAR"));
        view.setHistorialGatoListener(l -> establecerComunicacionHistorial_CrearDiagnosticoView());
        view.setModificarGatoListener(l -> mostrarVMGatoView(PanelesAdministrador.MODIFICAR_GATO, "MODIFICAR"));
        //EMISION APTITUD
        view.setEmitirAptitudFamiliaListener(l -> mostrarBuscarView(PanelesAdministrador.BUSCAR_FAMILIA_APTITUD, Familia.class, AccionBuscar.ESTABLECER_APTITUD, new String[]{"Nombre", "Usuario", "Telefono", "Apta Adopción"}));
        view.setEmitirAptitudHogarListener(l -> mostrarBuscarView(PanelesAdministrador.BUSCAR_HOGAR_APTITUD, Hogar.class, AccionBuscar.ESTABLECER_APTITUD, new String[]{"Nombre", "Usuario", "Telefono", "Apto Adopción"}));
        //POSTULACION
        view.setPostulacionListener(l -> mostrarPostulacionView(PanelesAdministrador.POSTULARSE));
        view.setVerPostulacionesListener(l -> mostrarVerPostulaciones());
        view.setPostulacionHogarListener(l -> mostrarPostulacionHogarView(PanelesAdministrador.POSTULAR_HOGAR));
        //ASIGNACION GATO
        view.setAsignacionFamiliaListener(l -> establecerComunicacionBuscarView_VerPostulacion());
        view.setAsignacionHogarListener(l -> establecerComunicacionBuscarHogarView_VerPostulacion());
        //TAREA REALIZADA
        view.setTareaRealizadaListener(l -> establecerComunicacionBuscarVoluntarioView_TareaRealizada());
        view.setVerTareaRealizadaListener(l -> mostrarTareasRealizadas());
        //REPORTE
        view.setReporteListener(l -> mostrarReporte());
        view.setVisitaListener(l -> mostrarVisita());
        view.setVerVisitasListener(l -> mostrarVerVisitas());
        view.setQRGatoListener(l -> mostrarQRGato());
        view.setGatosFamiliaListener(l -> mostrarGatosDeFamilia());
        view.setGatosHogarListener(l -> mostrarGatosDeHogar());
        view.setZonasListener(l -> mostrarZonas());
    }

    public void cerrarView() {
        view.dispose();
        view = null;
        JPAUtil.close(emf);
        System.exit(0);
    }

    public void cerrarSesion() {
        view.dispose();
        view = null;
        Tpi2025.login(emf);
    }

    public void iniciarView() {
        view.setVisible(true);
        view.toFront();
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
                "Datos " + miUsu.getClass().getSimpleName(),
                miUsu.getNombre(),
                miUsu.getTelefono(),
                miUsu.getNombreUsuario()
        );
    }

    public <T extends Usuario> void mostrarAMUsuarioView(PanelesAdministrador identificador, Class<T> tipoUsuario, AccionUsuario tipoAccion) {
        view.mostrarPanel(identificador);
        try {
            AMUsuarioView panel = view.getPanel(identificador, AMUsuarioView.class);
            if (panel == null) {
                throw new Exception("No existe el panel");
            }
            //si todavía no tiene controller, se crea y guarda en el HashMap
            if (!AMUsuarioControllers.containsKey(panel)) {
                AMUsuarioControllers.put(panel, new AMUsuarioController<T>(panel, tipoUsuario, false, emf, tipoAccion));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public <T extends Usuario> void mostrarModificacionUsuarioView(PanelesAdministrador identificador, T usuario, Class<T> tipoUsuario) {
        view.mostrarPanel(identificador);
        try {
            AMUsuarioView panel = view.getPanel(identificador, AMUsuarioView.class);
            if (panel == null) {
                throw new Exception("No existe el panel");
            }
            //si todavía no tiene controller, se crea y guarda en el HashMap
            if (!AMUsuarioControllers.containsKey(panel)) {
                AMUsuarioControllers.put(panel, new AMUsuarioController<T>(panel, usuario, tipoUsuario, false, emf, AccionUsuario.MODIFICAR));
            } else {
                AMUsuarioControllers.get(panel).setUsuario(usuario);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private <T extends Usuario> void mostrarBuscarView(PanelesAdministrador identificador, Class<T> tipoUsuario, AccionBuscar tipoAccion, String[] encabezados) {
        view.mostrarPanel(identificador);
        try {
            BuscarView panel = view.getPanel(identificador, BuscarView.class);
            if (panel == null) {
                throw new Exception("No existe el panel");
            }
            //si todavía no tiene controller, se crea y guarda en el HashMap
            if (!BuscarControllers.containsKey(panel)) {
                BuscarControllers.put(panel, new BuscarController<T>(panel, tipoUsuario, tipoAccion, encabezados, emf));
            } else {
                BuscarControllers.get(panel).iniciarTabla(encabezados);//siempre que se muestra, se actualiza la tabla
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private <T extends Usuario> void gestionModificacion(PanelesAdministrador identificadorBusqueda, PanelesAdministrador identificadorModificacion, Class<T> tipoUsuario, String[] encabezados) {
//      
        mostrarBuscarView(identificadorBusqueda, tipoUsuario, AccionBuscar.SELECCION, encabezados);
        BuscarView panel = view.getPanel(identificadorBusqueda, BuscarView.class);
        //se obtiene el controller de la vista creada
        BuscarController<T> controller = BuscarControllers.get(panel);
        panel.setAccionListener(l -> {
            mostrarModificacionUsuarioView(identificadorModificacion, controller.getUsuario(), tipoUsuario);
        });

    }

    private void mostrarCrearGatoView(PanelesAdministrador identificador, String tipoAccion) {
        view.mostrarPanel(identificador);
        try {
            CrearGatoView panel = view.getPanel(identificador, CrearGatoView.class);
            if (panel == null) {
                throw new Exception("No existe el panel");
            }
            if (crearGatoController == null) {
                crearGatoController = new CrearGatoController(panel, tipoAccion, emf);
            }
            if (!tipoAccion.equals("GUARDAR")) {
                crearGatoController.abrirSeleccion();
            }
        } catch (Exception e) {
        }
    }

    private void mostrarVMGatoView(PanelesAdministrador identificador, String tipoAccion) {
        view.mostrarPanel(identificador);
        try {
            CrearGatoView panel = view.getPanel(identificador, CrearGatoView.class);
            if (panel == null) {
                throw new Exception("No existe el panel");
            }
            if (vmGatoController == null) {
                vmGatoController = new CrearGatoController(panel, tipoAccion, emf);
            }
            if (!tipoAccion.equals("GUARDAR")) {
                vmGatoController.abrirSeleccion();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void establecerComunicacionHistorial_CrearDiagnosticoView() {
        view.mostrarPanel(PanelesAdministrador.VER_HISTORIAL);
        try {
            VerHistorialGatoView panel = view.getPanel(PanelesAdministrador.VER_HISTORIAL, VerHistorialGatoView.class);
            if (panel == null) {
                throw new Exception("No existe el panel");
            }
            if (verHistorialGatoController == null) {
                verHistorialGatoController = new VerHistorialGatoController(panel, emf);
                panel.setAniadirListener(l -> {
                    //abrir el panel de crear y pasarle el diagnostico
                    mostrarCrearDiagnosticoView(PanelesAdministrador.CREAR_DIAGNOSTICO, verHistorialGatoController.getGato(), null);
                });
                panel.setSeleccionListener(l -> {
                    mostrarVerDiagnosticoView(PanelesAdministrador.VER_DIAGNOSTICO, verHistorialGatoController.getGato(), verHistorialGatoController.getDiagnostico());
                });
            } else {
                verHistorialGatoController.iniciarTabla();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void mostrarVerDiagnosticoView(PanelesAdministrador identificador, Gato gato, Diagnostico diagnostico) {
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
                } else {
                    verDiagnosticoController.setDiagnostico(new Diagnostico());
                }
            }
            verDiagnosticoController.setGato(gato);
            if (identificador == PanelesAdministrador.VER_DIAGNOSTICO) {
                panel.activarComponentes(false);
            } else {
                panel.activarComponentes(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostrarCrearDiagnosticoView(PanelesAdministrador identificador, Gato gato, Diagnostico diagnostico) {
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
                } else {
                    crearDiagnosticoController.setDiagnostico(new Diagnostico());
                }
            }
            crearDiagnosticoController.setGato(gato);
            if (identificador == PanelesAdministrador.VER_DIAGNOSTICO) {
                panel.activarComponentes(false);
            } else {
                panel.activarComponentes(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostrarPostulacionView(PanelesAdministrador identificador) {
        view.mostrarPanel(identificador);
        try {
            PostulacionView panel = view.getPanel(identificador, PostulacionView.class);
            if (panel == null) {
                throw new Exception("No existe el panel");
            }
            if (postulacionController == null) {
                postulacionController = new PostulacionController(panel, miUsu.getNombreUsuario(), emf);
            } else // esta en el else para no iniciar la tabla 2 veces si se crea el controller por primera vez
            {
                postulacionController.iniciarTabla();
            }
        } catch (Exception e) {
        }
    }

    private void mostrarPostulacionHogarView(PanelesAdministrador identificador) {
        view.mostrarPanel(identificador);
        try {
            PostulacionView panel = view.getPanel(identificador, PostulacionView.class);
            if (panel == null) {
                throw new Exception("No existe el panel");
            }
            if (postulacionHogarController == null) {
                postulacionHogarController = new PostulacionHogarController(panel, emf);
            } else // esta en el else para no iniciar la tabla 2 veces si se crea el controller por primera vez
            {
                postulacionHogarController.abrirSeleccion();
            }
        } catch (Exception e) {
        }
    }

    private void establecerComunicacionBuscarView_VerPostulacion() {
        view.mostrarPanel(PanelesAdministrador.BUSCAR_FAMILIA_ASIGNACION);
        try {
            BuscarView panel = view.getPanel(PanelesAdministrador.BUSCAR_FAMILIA_ASIGNACION, BuscarView.class);
            if (panel == null) {
                throw new Exception("No existe el panel");
            }
            //si todavía no tiene controller, se crea y guarda en el HashMap
            if (!BuscarControllers.containsKey(panel)) {
                BuscarControllers.put(panel, new BuscarController<Familia>(panel, Familia.class, AccionBuscar.SELECCION, new String[]{"Nombre", "Nombre de Usuario", "Telefono"}, emf));
                panel.setAccionListener(l -> {
                    Familia f = (Familia) BuscarControllers.get(panel).getUsuario();
                    
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
        view.mostrarPanel(PanelesAdministrador.ASIGNAR_GATO_FAMILIA);
        try {
            VerPostulacionFamiliaView panel = view.getPanel(PanelesAdministrador.ASIGNAR_GATO_FAMILIA, VerPostulacionFamiliaView.class);
            if (panel == null) {
                throw new Exception("No existe el panel");
            }
            if (verPostulacionFamiliaController == null) {
                verPostulacionFamiliaController = new VerPostulacionFamiliaController(panel, familia, emf);
            } else {// esta en el else para no iniciar la tabla 2 veces si se crea el controller por primera vez
                verPostulacionFamiliaController.setFamilia(familia);
                verPostulacionFamiliaController.iniciarView();
                verPostulacionFamiliaController.iniciarTabla();
            }
        } catch (Exception e) {
        }
    }

    private void establecerComunicacionBuscarHogarView_VerPostulacion() {
        view.mostrarPanel(PanelesAdministrador.BUSCAR_HOGAR_ASIGNACION);
        try {
            BuscarView panel = view.getPanel(PanelesAdministrador.BUSCAR_HOGAR_ASIGNACION, BuscarView.class);
            if (panel == null) {
                throw new Exception("No existe el panel");
            }
            //si todavía no tiene controller, se crea y guarda en el HashMap
            if (!BuscarControllers.containsKey(panel)) {
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
        view.mostrarPanel(PanelesAdministrador.ASIGNAR_GATO_HOGAR);
        try {
            VerPostulacionHogarView panel = view.getPanel(PanelesAdministrador.ASIGNAR_GATO_HOGAR, VerPostulacionHogarView.class);
            if (panel == null) {
                throw new Exception("No existe el panel");
            }
            if (verPostulacionHogarController == null) {
                verPostulacionHogarController = new VerPostulacionHogarController(panel, hogar, emf);
            } else // esta en el else para no iniciar la tabla 2 veces si se crea el controller por primera vez
            {
                verPostulacionHogarController.setHogar(hogar);
            }
            verPostulacionHogarController.iniciarView();
            verPostulacionHogarController.iniciarTabla();
        } catch (Exception e) {
        }
    }

    private void establecerComunicacionBuscarVoluntarioView_TareaRealizada() {
        view.mostrarPanel(PanelesAdministrador.BUSCAR_VOLUNTARIO_TAREA_REALIZADA);
        try {
            BuscarView panel = view.getPanel(PanelesAdministrador.BUSCAR_VOLUNTARIO_TAREA_REALIZADA, BuscarView.class);
            if (panel == null) {
                throw new Exception("No existe el panel");
            }
            //si todavía no tiene controller, se crea y guarda en el HashMap
            if (!BuscarControllers.containsKey(panel)) {
                BuscarControllers.put(panel, new BuscarController<Voluntario>(panel, Voluntario.class, AccionBuscar.SELECCION, new String[]{"Nombre", "Nombre de Usuario", "Telefono"}, emf));
                panel.setAccionListener(l -> {
                    Voluntario h = (Voluntario) BuscarControllers.get(panel).getUsuario();
                    mostrarTareaRealizadaView(h);
                });
            } else {
                BuscarControllers.get(panel).iniciarTabla(new String[]{"Nombre", "Nombre de Usuario", "Telefono"});//siempre que se muestra, se actualiza la tabla
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void mostrarTareaRealizadaView(Voluntario h) {
        view.mostrarPanel(PanelesAdministrador.TAREA_REALIZADA);
        try {
            TareaRealizadaView panel = view.getPanel(PanelesAdministrador.TAREA_REALIZADA, TareaRealizadaView.class);
            if (panel == null) {
                throw new Exception("No existe el panel");
            }
            if (tareaRealizadaController == null) {

                tareaRealizadaController = new TareaRealizadaController(panel, h, emf);
            } else {
                tareaRealizadaController.setVoluntario(h);
                tareaRealizadaController.iniciarView();
            }
        } catch (Exception e) {
        }
    }

    private void mostrarReporte() {
        view.mostrarPanel(PanelesAdministrador.REPORTE);
        try {
            ReporteView panel = view.getPanel(PanelesAdministrador.REPORTE, ReporteView.class);
            if (panel == null) {
                throw new Exception("No existe el panel");
            }
            if (reporteController == null) {
                reporteController = new ReporteController(panel, emf);
            }
            reporteController.cargarDatos();
        } catch (Exception e) {
        }
    }

    private void mostrarVisita() {
        view.mostrarPanel(PanelesAdministrador.VISITA);
        try {
            VisitaSeguimientoView panel = view.getPanel(PanelesAdministrador.VISITA, VisitaSeguimientoView.class);
            if (panel == null) {
                throw new Exception("No existe el panel");
            }
            if (visitaSeguimientoController == null) {
                visitaSeguimientoController = new VisitaSeguimientoController(panel, miUsu.getNombreUsuario(), emf);
            } else {
                visitaSeguimientoController.abrirSeleccion();
            }
        } catch (Exception e) {
        }
    }

    private void mostrarMiPerfil() {
        view.mostrarPanel(PanelesAdministrador.MI_PERFIL);
        try {

            AMUsuarioView panel = view.getPanel(PanelesAdministrador.MI_PERFIL, AMUsuarioView.class);

            if (panel == null) {
                throw new Exception("No existe el panel");
            }
            if (miPerfilController == null) {

                miPerfilController = new AMUsuarioController(panel, miUsu, miUsu.getClass(), true, emf, AccionUsuario.MODIFICAR);
            }

        } catch (Exception e) {

        }
    }

    private void mostrarGatosDeFamilia() {
        view.mostrarPanel(PanelesAdministrador.GATOS_FAMILIA);
        try {

            GatosDeFamiliaView panel = view.getPanel(PanelesAdministrador.GATOS_FAMILIA, GatosDeFamiliaView.class);

            if (panel == null) {
                throw new Exception("No existe el panel");
            }
            if (gatoDeFamiliaController == null) {

                gatoDeFamiliaController = new GatoDeFamiliaController(panel, emf);
            } else {
                gatoDeFamiliaController.iniciar();
            }
        } catch (Exception e) {

        }
    }

    private void mostrarGatosDeHogar() {
        view.mostrarPanel(PanelesAdministrador.GATOS_HOGAR);
        try {

            GatosDeHogarView panel = view.getPanel(PanelesAdministrador.GATOS_HOGAR, GatosDeHogarView.class);

            if (panel == null) {
                throw new Exception("No existe el panel");
            }
            if (gatoDeHogarController == null) {

                gatoDeHogarController = new GatoDeHogarController(panel, emf);
            } else {
                gatoDeHogarController.iniciar();
            }

        } catch (Exception e) {

        }
    }

    private void mostrarQRGato() {
        QRGatoView qrView = new QRGatoView();
        VerQRGatoController verQRGatoController = new VerQRGatoController(emf);
    }

    private void mostrarZonas() {
        
        view.mostrarPanel(PanelesAdministrador.ZONAS);
        try {
            
            ABZonaView panel = view.getPanel(PanelesAdministrador.ZONAS, ABZonaView.class);
            
            if (panel == null) {
                throw new Exception("No existe el panel");
            }
            if (zonaController == null) {
                
                zonaController = new CrearZonaController(panel, emf);
            }
            zonaController.iniciarTabla();
            
        } catch (Exception e) {

        }
    }

    private void mostrarTareasRealizadas() {
        view.mostrarPanel(PanelesAdministrador.VER_TAREA_REALIZADA);
        try {
            
            VerTareasRealizadasView panel = view.getPanel(PanelesAdministrador.VER_TAREA_REALIZADA, VerTareasRealizadasView.class);
            
            if (panel == null) {
                throw new Exception("No existe el panel");
            }
            if (verTareasRealizadaController == null) {
                
                verTareasRealizadaController = new VerTareasRealizadaController(panel, emf);
            }
            verTareasRealizadaController.iniciarTabla();
            
        } catch (Exception e) {

        }
    }

    private void mostrarVerVisitas() {
        view.mostrarPanel(PanelesAdministrador.VER_VISITAS);
        try {
            
            VerVisitasSeguimientoView panel = view.getPanel(PanelesAdministrador.VER_VISITAS, VerVisitasSeguimientoView.class);
            
            if (panel == null) {
                throw new Exception("No existe el panel");
            }
            if (verVisitasSeguimientoController == null) {
                
                verVisitasSeguimientoController = new VerVisitasSeguimientoController(panel, emf);
            }
            verVisitasSeguimientoController.iniciarTabla();
            
        } catch (Exception e) {

        }
    }

    private void mostrarVerPostulaciones() {
        view.mostrarPanel(PanelesAdministrador.VER_POSTULACIONES);
        try {
            
            VerPostulacionesView panel = view.getPanel(PanelesAdministrador.VER_POSTULACIONES, VerPostulacionesView.class);
            
            if (panel == null) {
                throw new Exception("No existe el panel");
            }
            if (verPostulacionesController == null) {
                
                verPostulacionesController = new VerPostulacionesController(panel, emf);
            }
            verPostulacionesController.iniciarTabla();
            
        } catch (Exception e) {

        }
    }
}
