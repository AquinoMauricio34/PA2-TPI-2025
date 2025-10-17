/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tp4.pa2.controller.factura;

import com.mycompany.tp4.pa2.DAOImpl.ClienteDAOImpl;
import com.mycompany.tp4.pa2.entidades.Domiciliaria;
import com.mycompany.tp4.pa2.entidades.Factura;
import com.mycompany.tp4.pa2.entidades.Herramienta;
import com.mycompany.tp4.pa2.entidades.Industrial;
import com.mycompany.tp4.pa2.entidades.Item;
import com.mycompany.tp4.pa2.view.facturas.DetalleFacturaView;
import com.mycompany.tp4.pa2.view.facturas.jpanels.ItemDomiciliariaPanel;
import com.mycompany.tp4.pa2.view.facturas.jpanels.ItemHerramientaPanel;
import com.mycompany.tp4.pa2.view.facturas.jpanels.ItemIndustrialPanel;
import java.awt.Dimension;

/**
 *
 * @author aquin
 */
public class DetalleFacturaController {
    private final DetalleFacturaView detalleF;
    private Factura factura;

    public DetalleFacturaController(DetalleFacturaView detalleF, Factura factura) {
        this.detalleF = detalleF;
        this.factura = factura;
        
        cargarDatos();
        this.detalleF.getAtrasBtn().addActionListener(e -> cerrarVentana());
    }

    private void cerrarVentana() {
        detalleF.dispose();
    }

    private void cargarDatos() {
        detalleF.setDniClienteTf(factura.getDniCliente());
        detalleF.setNroFacturaTf(String.valueOf(factura.getNroFactura()));
        detalleF.setFechaEmisionFacturaTf(factura.getFecha().toString());
        detalleF.setTipoFactura(String.valueOf(factura.getLetra()));
        detalleF.setTotalPagoTf("$"+String.valueOf(factura.informarTotalPagar()));
        for(Item i: factura.getItems()){
            switch (i.getArticulo()) {
                case Herramienta h -> {
                    ItemHerramientaPanel bloque = new ItemHerramientaPanel();
                    bloque.setNombreArticuloTf(h.getNombre());
                    bloque.setCantidadTf(String.valueOf(i.getCantidad()));
                    bloque.setDescripcionTA(h.getDescripcion());
                    bloque.setTipoTf("Herramienta");
                    bloque.setPrecioUtf("$"+String.valueOf(h.getPrecio()));
                    bloque.setPrecioTTf(String.valueOf(h.getPrecio()*i.getCantidad()));
                    bloque.setPreferredSize(new Dimension(612, 219));
                    this.detalleF.agregarItemPanel(bloque);
                }
                case Industrial indu -> {
                    ItemIndustrialPanel bloque = new ItemIndustrialPanel();
                    bloque.setNombreArticuloTf(indu.getNombre());
                    bloque.setCantidadTf(String.valueOf(i.getCantidad()));
                    bloque.setTipoTf("Electricidad Industrial");
                    bloque.setTempMinTf(String.valueOf(indu.getTemperaturaMinima())+" °C");
                    bloque.setTempMTf(String.valueOf(indu.getTemperaturaMaxima())+" °C");
                    bloque.setPotenciaTf(String.valueOf(indu.getPotenciaMaxima())+" V");
                    bloque.setPrecioUtf(String.valueOf("$"+indu.getPrecio()));
                    bloque.setPrecioTTf(String.valueOf(indu.getPrecio()*i.getCantidad()));
                    bloque.setPreferredSize(new Dimension(612, 74));
                    this.detalleF.agregarItemPanel(bloque);
                }
                case Domiciliaria d -> {
                    ItemDomiciliariaPanel bloque = new ItemDomiciliariaPanel();
                    bloque.setNombreArticuloTf(d.getNombre());
                    bloque.setCantidadTf(String.valueOf(i.getCantidad()));
                    bloque.setTipoTf("Electricidad Domiciliaria");
                    bloque.setPotenciaTf(String.valueOf(d.getPotenciaMaxima())+" V");
                    bloque.setPrecioUtf(String.valueOf("$"+d.getPrecio()));
                    bloque.setPrecioTTf(String.valueOf(d.getPrecio()*i.getCantidad()));
                    bloque.setPreferredSize(new Dimension(612, 74));
                    this.detalleF.agregarItemPanel(bloque);
                }
                default -> {
                    System.err.println("Tipo de artículo no reconocido: " + i.getArticulo().getClass().getSimpleName());
                }

            }
        }
    }

    
    
    
}
