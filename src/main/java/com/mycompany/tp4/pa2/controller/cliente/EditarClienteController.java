/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tp4.pa2.controller.cliente;

import com.mycompany.tp4.pa2.DAOImpl.ClienteDAOImpl;
import com.mycompany.tp4.pa2.entidades.Cliente;
import com.mycompany.tp4.pa2.view.clientes.EditarClienteView;
import java.util.Optional;
import javax.swing.JOptionPane;

/**
 *
 * @author aquin
 */
public class EditarClienteController {
    private EditarClienteView editarC;
    private ClienteDAOImpl clienteDao = ClienteDAOImpl.getInstancia();

    public EditarClienteController(EditarClienteView editarC) {
        this.editarC = editarC;
        
        this.editarC.getBuscarDniBtn().addActionListener(e -> buscarDniCliente());
        this.editarC.getLimpiarBtn().addActionListener(e -> limpiarCampos());
        this.editarC.getCancelarBtn().addActionListener(e -> cerrarVentana());
        this.editarC.getEditarClienteBtn().addActionListener(e -> editarCliente());
    }
    
    private void buscarDniCliente() {
        Optional<Cliente> cliente = clienteDao.findCliente(editarC.getDniBuscarTf().getText());
        // se verifica que la lista no este vacia
        if(cliente.isPresent()){
            editarC.setNombreCliTf(cliente.get().getNombre());
            editarC.setApellidoCliTf(cliente.get().getApellido());
            editarC.setDniCliTf(cliente.get().getDni());
            editarC.setDireccionCliTf(cliente.get().getDireccion());
            editarC.setTelefonoCliTf(cliente.get().getTelefono());
        }else
            JOptionPane.showMessageDialog(editarC, "No existen un cliente registrado con el dni "+editarC.getDniBuscarTf().getText(),"Error",JOptionPane.ERROR_MESSAGE);
    }
    
    private void limpiarCampos(){
        editarC.setDniBuscarTf("");
        editarC.setNombreCliTf("");
        editarC.setApellidoCliTf("");
        editarC.setDniCliTf("");
        editarC.setDireccionCliTf("");
        editarC.setTelefonoCliTf("");
    }
    
    private void cerrarVentana() {
        editarC.dispose();
    }

    private void editarCliente() {
        try {
            Cliente clienteEditado = new Cliente(editarC.getNombreCliTf().getText(),editarC.getApellidoCliTf().getText(),editarC.getDniCliTf().getText(),editarC.getDireccionCliTf().getText(),editarC.getTelefonoCliTf().getText());
            clienteDao.updateCliente(clienteEditado);
            JOptionPane.showMessageDialog(editarC,"Cliente actualizado.","Actualización",JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(editarC, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
}
