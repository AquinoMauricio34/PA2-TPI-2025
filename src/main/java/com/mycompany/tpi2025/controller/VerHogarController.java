/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.DAOImpl.HogarJpaController;
import com.mycompany.tpi2025.model.Hogar;
import com.mycompany.tpi2025.view.VerHogarView;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

/**
 *
 * @author aquin
 */
public class VerHogarController {

    private VerHogarView view;
    private HogarJpaController dao;
    private Hogar hogar = null;

    public VerHogarController(VerHogarView view, EntityManagerFactory emf) {
        this.view = view;
        this.dao = new HogarJpaController(emf);

        iniciarView();

        iniciarTabla();

        view.setSeleccionListaListener(l -> seleccionar());
        view.setCerrarListener(l -> cerrar());
    }

    public void iniciarView() {

        view.setVisible(true);
        view.toFront();
        view.setLocationRelativeTo(null);
    }

    public void iniciarTabla() {

        List<Hogar> lista = obtenerLista();

        view.reloadTable(lista);

    }

    private List<Hogar> obtenerLista() {
        List<Hogar> fam;

        try {
            fam = dao.findHogarEntities();
            return fam;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void seleccionar() {
        int fila = view.obtenerIndiceFila();
        if (fila != -1) {
            String id = view.obtenerValorTabla(fila, 0);//segundo parametro indice correspondiente a la columna del encabezado
            int indice = obtenerIndiceHogar(id);
            if (indice != -1) {

                view.resaltarFila(indice);
                hogar = obtenerLista().get(indice);
                view.activarSeleccion(true);
            }
        }
    }

    private int obtenerIndiceHogar(String idHogar) {
        List<Hogar> lista = obtenerLista();
        return lista.indexOf(lista.stream().filter(v -> v.getNombreUsuario().equals(idHogar)).findFirst().orElse(null));
    }

    private void cerrar() {
        view.dispose();
        view = null;
    }

    public Hogar getHogar() {
        return hogar;
    }

}
