/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tp4.pa2.controller.articulo;

import com.mycompany.tp4.pa2.view.articulos.BorrarArticuloView;
import com.mycompany.tp4.pa2.view.articulos.BuscarArticuloView;
import com.mycompany.tp4.pa2.view.articulos.CrearArticuloView;
import com.mycompany.tp4.pa2.view.articulos.EditarArticuloView;
import com.mycompany.tp4.pa2.view.articulos.GestionArticuloView;

/**
 *
 * @author aquin
 */
public class GestionArticuloController {
    private GestionArticuloView gArt;

    public GestionArticuloController(GestionArticuloView gArt) {
        this.gArt = gArt;
        
        gArt.getCrearBtn().addActionListener(e -> abrirCrearArticuloVentana());
        gArt.getBuscarBtn().addActionListener(e -> abrirBuscarArticuloVentana());
        gArt.getEditarBtn().addActionListener(e -> abrirEditarArticuloVentana());
        gArt.getEliminarBtn().addActionListener(e -> abrirBorrarArticuloVentana());
        gArt.getAtrasBtn().addActionListener(e -> cerrarVentana());
    }

    private void cerrarVentana() {
        gArt.dispose();
    }

    private void abrirCrearArticuloVentana() {
        CrearArticuloView crearV = new CrearArticuloView();
        CrearArticuloController crearC = new CrearArticuloController(crearV);
        crearV.setVisible(true);
        crearV.setLocationRelativeTo(null);
    }

    private void abrirBuscarArticuloVentana() {
        BuscarArticuloView buscarV = new BuscarArticuloView();
        BuscarArticuloController buscarC = new BuscarArticuloController(buscarV);
        buscarV.setVisible(true);
        buscarV.setLocationRelativeTo(null);
    }

    private void abrirEditarArticuloVentana() {
        EditarArticuloView editarV = new EditarArticuloView();
        EditarArticuloController editarC = new EditarArticuloController(editarV);
        editarV.setVisible(true);
        editarV.setLocationRelativeTo(null);
    }

    private void abrirBorrarArticuloVentana() {
        BorrarArticuloView borrarV = new BorrarArticuloView();
        BorrarArticuloController borrarC = new BorrarArticuloController(borrarV);
        borrarV.setVisible(true);
        borrarV.setLocationRelativeTo(null);
    }


}
