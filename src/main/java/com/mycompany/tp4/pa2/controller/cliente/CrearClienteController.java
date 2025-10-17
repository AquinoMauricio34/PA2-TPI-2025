/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tp4.pa2.controller.cliente;

import com.mycompany.tp4.pa2.DAOImpl.ClienteDAOImpl;
import com.mycompany.tp4.pa2.entidades.Cliente;
import com.mycompany.tp4.pa2.view.clientes.CrearClienteView;
import javax.swing.JOptionPane;

/**
 *
 * @author aquin
 */
public class CrearClienteController {
    // se va a controlar esta view
    private CrearClienteView crearview;
    //se obtiene la instancia global
    private ClienteDAOImpl clienteDao = ClienteDAOImpl.getInstancia();
    
    // al inicializar el controlador, se le pasa la view que va a controlar.
    public CrearClienteController(CrearClienteView crearview) {
        this.crearview = crearview;
        
        
        this.crearview.getCrearBtn().addActionListener(e -> crearCliente());
        this.crearview.getCancelarBtn().addActionListener(e -> cerrarVentana());
    }

    private void crearCliente() {
        Cliente cliente = new Cliente(crearview.getNombreCliTf().getText(),crearview.getApellidoCliTf().getText(), crearview.getDniCliTf().getText(), crearview.getDireccionCliTf().getText(), crearview.getTelefonoCliTf().getText());
        try {
            clienteDao.addCliente(cliente);
            JOptionPane.showMessageDialog(crearview,"Cliente ha sido registrado","Registro de cliente",JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(crearview, ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cerrarVentana() {
        this.crearview.dispose();
    }
    
    
}
