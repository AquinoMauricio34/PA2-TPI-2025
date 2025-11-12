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
import com.mycompany.tpi2025.view.AMUsuarioView;
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
    private final Map<JPanel, AMUsuarioController> AMUsuarioControllers = new HashMap<>();
    //CAMBIAR AMUsuarioController
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
        view.setCrearAdminListener(l -> mostrarAMUsuarioView(PanelesAdministrador.CREAR_ADMINISTRADOR, Administrador.class,AccionUsuario.GUARDAR));
        view.setCrearVetListener(l -> mostrarAMUsuarioView(PanelesAdministrador.CREAR_VETERINARIO, Veterinario.class,AccionUsuario.GUARDAR));
        view.setCrearVolListener(l -> mostrarAMUsuarioView(PanelesAdministrador.CREAR_VOLUNTARIO, Voluntario.class,AccionUsuario.GUARDAR));
        view.setCrearFamListener(l -> mostrarAMUsuarioView(PanelesAdministrador.CREAR_FAMILIA, Familia.class,AccionUsuario.GUARDAR));
        view.setCrearHogarListener(l -> mostrarAMUsuarioView(PanelesAdministrador.CREAR_HOGAR, Hogar.class,AccionUsuario.GUARDAR));
        //BUSQUEDA
        String[] encabezados = {"Nombre","Nombre de Usuario","Telefono"};
        view.setBuscarAdminListener(l -> mostrarBuscarView(PanelesAdministrador.BUSCAR_ADMINISTRADOR, Administrador.class,AccionBuscar.DETALLES,encabezados));
        view.setBuscarVetListener(l -> mostrarBuscarView(PanelesAdministrador.BUSCAR_VETERINARIO, Veterinario.class,AccionBuscar.DETALLES,encabezados));
        view.setBuscarVolListener(l -> mostrarBuscarView(PanelesAdministrador.BUSCAR_VOLUNTARIO, Voluntario.class,AccionBuscar.DETALLES,encabezados));
        view.setBuscarFamListener(l -> mostrarBuscarView(PanelesAdministrador.BUSCAR_FAMILIA, Familia.class,AccionBuscar.DETALLES,encabezados));
        view.setBuscarHogarListener(l -> mostrarBuscarView(PanelesAdministrador.BUSCAR_HOGAR, Hogar.class,AccionBuscar.DETALLES,encabezados));
        //ELIMINACION
        view.setEliminarAdminListener(l -> mostrarBuscarView(PanelesAdministrador.ELIMINAR_ADMINISTRADOR, Administrador.class,AccionBuscar.ELIMINAR,encabezados));
        view.setEliminarVetListener(l -> mostrarBuscarView(PanelesAdministrador.ELIMINAR_VETERINARIO, Veterinario.class,AccionBuscar.ELIMINAR,encabezados));
        view.setEliminarVolListener(l -> mostrarBuscarView(PanelesAdministrador.ELIMINAR_VOLUNTARIO, Voluntario.class,AccionBuscar.ELIMINAR,encabezados));
        view.setEliminarFamListener(l -> mostrarBuscarView(PanelesAdministrador.ELIMINAR_FAMILIA, Familia.class,AccionBuscar.ELIMINAR,encabezados));
        view.setEliminarHogarListener(l -> mostrarBuscarView(PanelesAdministrador.ELIMINAR_HOGAR, Hogar.class,AccionBuscar.ELIMINAR,encabezados));
        //MODIFICACION
        view.setModificarAdminListener(l -> gestionModificacion(PanelesAdministrador.BUSCAR_ADMINISTRADOR,PanelesAdministrador.MODIFICAR_ADMINISTRADOR, Administrador.class,encabezados));
        view.setModificarVetListener(l -> gestionModificacion(PanelesAdministrador.BUSCAR_VETERINARIO,PanelesAdministrador.MODIFICAR_VETERINARIO, Veterinario.class,encabezados));
        view.setModificarVolListener(l -> gestionModificacion(PanelesAdministrador.BUSCAR_VOLUNTARIO,PanelesAdministrador.MODIFICAR_VOLUNTARIO, Voluntario.class,encabezados));
        view.setModificarFamListener(l -> gestionModificacion(PanelesAdministrador.BUSCAR_FAMILIA,PanelesAdministrador.MODIFICAR_FAMILIA, Familia.class,encabezados));
        view.setModificarHogarListener(l -> gestionModificacion(PanelesAdministrador.BUSCAR_HOGAR,PanelesAdministrador.MODIFICAR_HOGAR, Hogar.class,encabezados));
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

    public <T extends Usuario> void mostrarAMUsuarioView(PanelesAdministrador identificador, Class<T> tipoUsuario, AccionUsuario tipoAccion) {
        view.mostrarPanel(identificador);
        try {
            AMUsuarioView panel = view.getPanel(identificador, AMUsuarioView.class);
            if (panel == null) {
                throw new Exception("No existe el panel");
            }
            //si todavía no tiene controller, se crea y guarda en el HashMap
            if(!AMUsuarioControllers.containsKey(panel)){
                //if(tipoAccion == AccionUsuario.MODIFICAR) AMUsuarioControllers.put(panel, new AMUsuarioController<T>(panel, tipoUsuario, emf,tipoAccion));
                AMUsuarioControllers.put(panel, new AMUsuarioController<T>(panel, tipoUsuario, emf,tipoAccion));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public <T extends Usuario> void mostrarModificacionUsuarioView(PanelesAdministrador identificador,T usuario, Class<T> tipoUsuario) {
        view.mostrarPanel(identificador);
        try {
            AMUsuarioView panel = view.getPanel(identificador, AMUsuarioView.class);
            if (panel == null) {
                throw new Exception("No existe el panel");
            }
            //si todavía no tiene controller, se crea y guarda en el HashMap
            if(!AMUsuarioControllers.containsKey(panel)){
                AMUsuarioControllers.put(panel, new AMUsuarioController<T>(panel, usuario, tipoUsuario, emf,AccionUsuario.MODIFICAR));
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
                BuscarControllers.get(panel).iniciarTabla(encabezados);//siempre que se muestra, se actualiza la tabla
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private <T extends Usuario> void gestionModificacion(PanelesAdministrador identificadorBusqueda, PanelesAdministrador identificadorModificacion, Class<T> tipoUsuario, String[] encabezados){
        mostrarBuscarView(identificadorBusqueda,tipoUsuario,AccionBuscar.DETALLES,encabezados);
        BuscarView panel = view.getPanel(identificadorBusqueda, BuscarView.class);
        //se obtiene el controller de la vista creada
        BuscarController<T> controller = BuscarControllers.get(panel);
        panel.setAccionListener(l -> {
            mostrarModificacionUsuarioView(identificadorModificacion,controller.getUsuario(), tipoUsuario);
        });
        
    }

}
