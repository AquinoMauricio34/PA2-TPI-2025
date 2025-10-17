/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tp4.pa2.controller.factura;

import com.mycompany.tp4.pa2.view.facturas.CrearFacturaView;
import com.mycompany.tp4.pa2.view.facturas.GestionFacturaView;
import com.mycompany.tp4.pa2.view.facturas.ListarFacturaView;

/**
 *
 * @author aquin
 */
public class GestionFacturaController {
    private GestionFacturaView gFac;

    public GestionFacturaController(GestionFacturaView gFac) {
        this.gFac = gFac;
        
        this.gFac.getCrearBtn().addActionListener(e -> abrirCrearView());
        this.gFac.getListarBtn().addActionListener(e -> abrirListarView());
        this.gFac.getAtrasBtn().addActionListener(e -> cerrarVentana());
    }

    private void abrirCrearView() {
        CrearFacturaView cFacturaV = new CrearFacturaView();
        CrearFacturaController cFacturaC = new CrearFacturaController(cFacturaV);
        cFacturaV.setVisible(true);
        cFacturaV.setLocationRelativeTo(null);
    }

    private void abrirListarView() {
        ListarFacturaView lFacturaV = new ListarFacturaView();
        ListarFacturaController lFacturaC = new ListarFacturaController(lFacturaV);
        lFacturaV.setVisible(true);
        lFacturaV.setLocationRelativeTo(null);
    }

    private void cerrarVentana() {
        gFac.dispose();
    }
}
