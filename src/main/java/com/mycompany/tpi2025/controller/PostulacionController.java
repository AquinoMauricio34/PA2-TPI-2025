/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.DAOImpl.GatoJpaController;
import com.mycompany.tpi2025.DAOImpl.PostulacionJpaController;
import com.mycompany.tpi2025.model.Gato;
import com.mycompany.tpi2025.model.Postulacion;
import com.mycompany.tpi2025.view.JPanels.PostulacionView;
import jakarta.persistence.EntityManagerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author aquin
 */
public class PostulacionController {

    private PostulacionView view;
    private GatoJpaController dao;
    private PostulacionJpaController daoP;
    private Gato gato = null;
    private String nombreUsuario;
    private List<Gato> listaGatosDisponibles = null;

    public PostulacionController(PostulacionView view, String nombreUsuario, EntityManagerFactory emf) {

        this.view = view;
        this.dao = new GatoJpaController(emf);
        this.daoP = new PostulacionJpaController(emf);
        this.nombreUsuario = nombreUsuario;
        this.listaGatosDisponibles = new ArrayList<>();

        iniciarView();

        iniciarTabla();

        view.setSeleccionListaListener(l -> seleccionar());
        view.setPostulacionListener(l -> postular());
    }

    private void postular() {

        Postulacion p = new Postulacion(nombreUsuario, gato.getId());
        try {
            daoP.create(p);
            iniciarTabla();
        } catch (Exception e) {
        }
    }

    public void iniciarView() {
        view.setVisible(true);
    }

    public void iniciarTabla() {

        listaGatosDisponibles = obtenerLista();

        view.reloadTable(listaGatosDisponibles);

    }

    private List<Gato> obtenerLista() {
        List<Gato> listaGatos = dao.findGatoEntities();

        //se quitan los gatos que ya tienen duenio
        listaGatos = listaGatos.stream()
                .filter(g -> g.getUsuario() == null)
                .collect(Collectors.toList());

        List<Postulacion> listaPostulaciones = daoP.findPostulacionesByPostulante(nombreUsuario);

        //obtener los idGato de cada postulacion
        List<Long> idsPostulados = listaPostulaciones.stream()
                .map(Postulacion::getIdGato)
                .collect(Collectors.toList());

        //filtrar los gatos que NO tengan el mismo id que idsPostulados
        return listaGatos.stream()
                .filter(g -> !idsPostulados.contains(g.getId()))
                .collect(Collectors.toList());
    }

    private void seleccionar() {
        int fila = view.obtenerIndiceFila();
        if (fila != -1) {
            String id = view.obtenerValorTabla(fila, 0);//segundo parametro indice correspondiente a la columna del encabezado
            int indice = obtenerIndiceGato(Long.parseLong(id));
            if (indice != -1) {
                view.resaltarFila(indice);
                gato = listaGatosDisponibles.get(indice);
                view.activarPostulacion(true);
            }
        }
    }

    private int obtenerIndiceGato(long idGato) {
        return listaGatosDisponibles.indexOf(listaGatosDisponibles.stream().filter(v -> v.getId() == idGato).findFirst().orElse(null));
    }

    public Gato getGato() {
        return gato;
    }
}
