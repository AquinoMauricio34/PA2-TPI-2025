/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tp4.pa2.controller;

import com.mycompany.tp4.pa2.controller.articulo.GestionArticuloController;
import com.mycompany.tp4.pa2.controller.cliente.GestionClienteController;
import com.mycompany.tp4.pa2.controller.factura.GestionFacturaController;
import com.mycompany.tp4.pa2.view.clientes.GestionClienteView;
import com.mycompany.tp4.pa2.view.MenuView;
import com.mycompany.tp4.pa2.view.articulos.GestionArticuloView;
import com.mycompany.tp4.pa2.view.facturas.GestionFacturaView;

/**
 *
 * @author aquin
 */
public class MenuController {
    private MenuView menu;

    public MenuController(MenuView menu) {
        this.menu = menu;
        
        this.menu.getgClientesBtn().addActionListener(e -> abrirGestionCliente());
        this.menu.getgArticulosBtn().addActionListener(e -> abrirGestionArticulo());
        this.menu.getgFacturaBtn().addActionListener(e -> abrirGestionFactura());
        this.menu.getCerrarBtn().addActionListener(e -> cerrarPrograma());
    }

    private void abrirGestionCliente() {
        GestionClienteView gCli = new GestionClienteView();
        GestionClienteController gClic = new GestionClienteController(gCli);
        gCli.setLocationRelativeTo(null);
        gCli.setVisible(true);
    }

    private void abrirGestionArticulo() {
        GestionArticuloView gArtV = new GestionArticuloView();
        GestionArticuloController gArtC = new GestionArticuloController(gArtV);
        gArtV.setVisible(true);
        gArtV.setLocationRelativeTo(null);
    }
    
    private void abrirGestionFactura() {
        GestionFacturaView gFacturaV = new GestionFacturaView();
        GestionFacturaController gFacturaC = new GestionFacturaController(gFacturaV);
        gFacturaV.setVisible(true);
        gFacturaV.setLocationRelativeTo(null);
    }


    private void cerrarPrograma() {
        System.exit(0);
    }
    
    
}
