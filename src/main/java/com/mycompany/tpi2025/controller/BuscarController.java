/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.DAOImpl.UsuarioJpaController;
import com.mycompany.tpi2025.controller.enums.AccionBuscar;
import com.mycompany.tpi2025.model.Familia;
import com.mycompany.tpi2025.model.Hogar;
import com.mycompany.tpi2025.model.Usuario;
import com.mycompany.tpi2025.view.BuscarView;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author aquin
 */
public class BuscarController<T extends Usuario> {
    private BuscarView view;
    private UsuarioJpaController dao;
    private final Class<T> tipoUsuario;
    private Usuario usuario = null;
    private String[] encabezados;
    private AccionBuscar tipoAccion;
    
     public BuscarController(BuscarView view, Class<T> tipoUsuario, AccionBuscar tipoAccion,String[] encabezados , EntityManagerFactory emf) {
        this.view = view;
        this.dao = new UsuarioJpaController(emf);
        this.tipoUsuario = tipoUsuario;
        this.encabezados = encabezados;
        this.tipoAccion = tipoAccion;
        view.setTitulo(tipoAccion + " " + tipoUsuario.getSimpleName());
        view.setAccionTexto(tipoAccion.getTexto());
        iniciarTabla(encabezados);
        view.setAccionListener(l -> accion(tipoAccion));
        view.setBuscarListener(l -> buscarUsuario());
        view.setSeleccionTablaListener(l -> filaSeleccionada());
        if(tipoAccion == AccionBuscar.DETALLES){
            view.activarAccion(false);
        }else
            view.activarAccion(true);
    }
    
    public void iniciarTabla(String[] encabezados){
        List<Usuario> lista = dao.findUsuariosByTipo(tipoUsuario.getSimpleName());
        view.reloadTable(lista, encabezados);
    }
     

    private void accion(AccionBuscar tipo) {
        switch (tipo) {
            case DETALLES -> {}
            case ELIMINAR -> eliminar();
            case ESTABLECER_APTITUD -> cambiarAptitud();
            case SELECCION -> {}
            default -> throw new AssertionError(tipo.name());
        }
    }

    private void buscarUsuario() {
        String nombreUsuario = view.getBuscarUsuarioTf();
        List<Usuario> lista = obtenerLista();
        int indice = lista.indexOf(lista.stream().filter(v -> v.getNombreUsuario().toLowerCase().equals(nombreUsuario.toLowerCase()) || v.getNombre().toLowerCase().equals(nombreUsuario.toLowerCase())).findFirst().orElse(null));
        if(indice != -1){
            view.resaltarFila(indice); //aunque aquí ya se haga la verificación del -1 se mantiene en el view por si se utiliza en otro momento.  
            usuario = lista.get(indice);
            view.activarAccion(true);
        }else
            usuario = null;
    }
    
    private void eliminar(){
        if(usuario != null){
            int resultado = JOptionPane.showConfirmDialog(view, "El usuario será eliminado permanentemente. ¿Confirma la eliminación?","Confirmación",JOptionPane.OK_CANCEL_OPTION);
            try {
                if(resultado == JOptionPane.OK_OPTION){
                    dao.destroy(usuario.getNombreUsuario());
                }
                iniciarTabla(encabezados);
                view.activarAccion(false);
            } catch (Exception ex) {
                view.mostrarMensaje("Error", "Error eliminar usuario", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }else
            view.mostrarMensaje("Error", "Error eliminar usuario", JOptionPane.ERROR_MESSAGE);
    }
    
    private int obtenerIndiceUsuario(String nombreUsuario){
        List<Usuario> lista = obtenerLista();
        return lista.indexOf(lista.stream().filter(v -> v.getNombreUsuario().toLowerCase().equals(nombreUsuario.toLowerCase()) || v.getNombre().toLowerCase().equals(nombreUsuario.toLowerCase())).findFirst().orElse(null));
    }
    
    private List<Usuario> obtenerLista(){
        return dao.findUsuariosByTipo(tipoUsuario.getSimpleName());
    }
    
    private void filaSeleccionada() {
        int fila = view.obtenerIndiceFila();
        if (fila != -1) {
            String nombre = view.obtenerValorTabla(fila, 1);//1 es el indice correspondiente a la columna del encabezado nombreUsuario
            int indice = obtenerIndiceUsuario(nombre);
            if(indice != -1){
                usuario = obtenerLista().get(indice);
                if(tipoAccion != AccionBuscar.DETALLES){
                    view.activarAccion(true);
                    //System.out.println("sldajklñsdjklfsdklklsdfkasdfasdflñkasdfkkasdfñjklasdfñjklasdfafsdlñjklñsfadjklñasdfjklñ");
                }else
                    view.activarAccion(false);
            }
        }
    }

    public T getUsuario() {
        return tipoUsuario.cast(usuario);
    }

    private void cambiarAptitud() {
        if(usuario != null){
            try {
                if (usuario instanceof Hogar hogar) {
                    hogar.setAptoAdopcion(!hogar.isAptoAdopcion());
                }else if(usuario instanceof Familia familia){
                    familia.setAptoAdopcion(!familia.isAptoAdopcion());
                }else
                    throw new Exception("Error clase no es ni familia ni hogar.");
                dao.edit(usuario);
                iniciarTabla(encabezados);
                view.activarAccion(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }

    
}
