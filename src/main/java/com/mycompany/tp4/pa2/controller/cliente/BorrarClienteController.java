/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tp4.pa2.controller.cliente;

import com.mycompany.tp4.pa2.DAOImpl.ClienteDAOImpl;
import com.mycompany.tp4.pa2.entidades.Cliente;
import com.mycompany.tp4.pa2.view.clientes.BorrarClienteView;
import java.util.Optional;
import javax.swing.JOptionPane;

/**
 *
 * @author aquin
 */
public class BorrarClienteController {
    private BorrarClienteView borrarC;
    private ClienteDAOImpl clienteDao = ClienteDAOImpl.getInstancia();

    public BorrarClienteController(BorrarClienteView borrarC) {
        this.borrarC = borrarC;
        
        this.borrarC.getBuscarDniBtn().addActionListener(e -> buscarDniCliente());
        this.borrarC.getCancelarBtn().addActionListener(e -> cerrarVentana());
        this.borrarC.getBorrarClienteBtn().addActionListener(e -> borrarCliente());
        this.borrarC.getLimpiarBtn().addActionListener(e -> limpiarCampos());
    }

    private void buscarDniCliente() {
        Optional<Cliente> cliente = clienteDao.findCliente(borrarC.getDniBuscarTf().getText());
        // se verifica que la lista no este vacia
        if(cliente.isPresent()){
            borrarC.setNombreCliTf(cliente.get().getNombre());
            borrarC.setApellidoCliTf(cliente.get().getApellido());
            borrarC.setDniCliTf(cliente.get().getDni());
            borrarC.setDireccionCliTf(cliente.get().getDireccion());
            borrarC.setTelefonoCliTf(cliente.get().getTelefono());
        }else
            JOptionPane.showMessageDialog(borrarC, "No existen un cliente registrado con el dni "+borrarC.getDniBuscarTf().getText(),"Error",JOptionPane.ERROR_MESSAGE);
        clienteDao.findAllClientes().forEach(System.out::print);
    }

    private void cerrarVentana() {
        borrarC.dispose();
    }

    private void borrarCliente() {
        try {
            // borra el cliente con el dni que se encuentra en el dnibuscartf
            clienteDao.removeCliente(borrarC.getDniBuscarTf().getText());
            limpiarCampos();
        } catch (Exception ex) { //si no encuentra lo avisa
            JOptionPane.showMessageDialog(borrarC, "No existe cliente con dni "+borrarC.getDniBuscarTf().getText()+" registrado en el sistema.","Error",JOptionPane.ERROR_MESSAGE);
        }
        //clienteDao.findAllClientes().forEach(System.out::println);
    }
    
    private void limpiarCampos(){
        borrarC.setDniBuscarTf("");
        borrarC.setNombreCliTf("");
        borrarC.setApellidoCliTf("");
        borrarC.setDniCliTf("");
        borrarC.setDireccionCliTf("");
        borrarC.setTelefonoCliTf("");
    }
    
    
}
