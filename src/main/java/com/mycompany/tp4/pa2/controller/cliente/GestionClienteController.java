/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tp4.pa2.controller.cliente;

import com.mycompany.tp4.pa2.view.clientes.BorrarClienteView;
import com.mycompany.tp4.pa2.view.clientes.BuscarClienteView;
import com.mycompany.tp4.pa2.view.clientes.CrearClienteView;
import com.mycompany.tp4.pa2.view.clientes.EditarClienteView;
import com.mycompany.tp4.pa2.view.clientes.GestionClienteView;

/**
 *
 * @author aquin
 */
public class GestionClienteController {
    private GestionClienteView gCli;

    public GestionClienteController(GestionClienteView gCli) {
        this.gCli = gCli;
        
        this.gCli.getCrearClienteBtn().addActionListener(e -> abrirCrearCliente());
        this.gCli.getEliminarClienteBtn().addActionListener(e -> abrirBorrarCliente());
        this.gCli.getBuscarClienteBtn().addActionListener(e -> abrirBuscarCliente());
        this.gCli.getEditarClienteBtn().addActionListener(e -> abrirEditarCliente());
        this.gCli.getAtrasBtn().addActionListener(e -> cerrarVentana());
    }

    private void abrirCrearCliente() {
        CrearClienteView crearClienteV = new CrearClienteView();
        CrearClienteController crearClienteC= new CrearClienteController(crearClienteV);
        crearClienteV.setVisible(true);
        crearClienteV.setLocationRelativeTo(null);
    }

    private void abrirBorrarCliente() {
        BorrarClienteView borrarClienteV = new BorrarClienteView();
        BorrarClienteController borrarClienteC = new BorrarClienteController(borrarClienteV);
        borrarClienteV.setVisible(true);
        borrarClienteV.setLocationRelativeTo(null);
    }

    private void abrirBuscarCliente() {
        BuscarClienteView buscarClienteV = new BuscarClienteView();
        BuscarClienteController buscarClienteC = new BuscarClienteController(buscarClienteV);
        buscarClienteV.setVisible(true);
        buscarClienteV.setLocationRelativeTo(null);
    }

    private void abrirEditarCliente() {
        EditarClienteView editarClienteV = new EditarClienteView();
        EditarClienteController editarClienteC = new EditarClienteController(editarClienteV);
        editarClienteV.setVisible(true);
        editarClienteV.setLocationRelativeTo(null);
    }

    private void cerrarVentana() {
        gCli.dispose();
    }
    
    
}
