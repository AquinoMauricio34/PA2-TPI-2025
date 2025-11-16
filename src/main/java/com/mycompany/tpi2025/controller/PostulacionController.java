/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.DAOImpl.GatoJpaController;
import com.mycompany.tpi2025.DAOImpl.PostulacionJpaController;
import com.mycompany.tpi2025.model.Gato;
import com.mycompany.tpi2025.model.Postulacion;
import com.mycompany.tpi2025.view.PostulacionView;
import jakarta.persistence.EntityManagerFactory;
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

    public PostulacionController(PostulacionView view, String nombreUsuario, EntityManagerFactory emf) {
        System.out.println("ggggggggggggggg1");
        this.view = view;
        this.dao = new GatoJpaController(emf);
        this.daoP = new PostulacionJpaController(emf);
        this.nombreUsuario = nombreUsuario;
        System.out.println("ggggggggggggggg2");
        iniciarView();
        System.out.println("ggggggggggggggg3");
        iniciarTabla();
        System.out.println("ggggggggggggggg4");
        view.setSeleccionListaListener(l -> seleccionar());
        view.setPostulacionListener(l -> postular());
    }
    
    private void postular(){
        System.out.println("ggggggggggggggg5");
        Postulacion p = new Postulacion(nombreUsuario, gato.getId());
        try {
            daoP.create(p);
            iniciarTabla();
        } catch (Exception e) {
        }
    }
    
    public void iniciarView(){
        view.setVisible(true);
    }
    
    public void iniciarTabla(){
        System.out.println("ggggggggggggggg6");
        List<Gato> lista = obtenerLista();
        System.out.println(lista);
        System.out.println("ggggggggggggggg7");
        view.reloadTable(lista);
        System.out.println("ggggggggggggggg8");
    }
    
    private List<Gato> obtenerLista() {
        List<Gato> listaGatos = dao.findGatoEntities();
        System.out.println(listaGatos);
        //se quitan los gatos que ya tienen duenio
        listaGatos = listaGatos.stream()
            .filter(g -> g.getUsuario() == null)
            .collect(Collectors.toList());
        System.out.println(listaGatos);
        List<Postulacion> listaPostulaciones = daoP.findPostulacionesByPostulante(nombreUsuario);
        System.out.println(listaPostulaciones);
        //obtener los idGato de cada postulacion
        List<Long> idsPostulados = listaPostulaciones.stream()
                .map(Postulacion::getIdGato)
                .collect(Collectors.toList());
        System.out.println(listaPostulaciones);
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
            if(indice != -1){
                view.resaltarFila(indice);
                gato = obtenerLista().get(indice);
                view.activarPostulacion(true);
            }
        }
    }
    
    private int obtenerIndiceGato(long idGato){
        List<Gato> lista = obtenerLista();
        return lista.indexOf(lista.stream().filter(v -> v.getId()==idGato).findFirst().orElse(null));
    }

    public Gato getGato() {
        return gato;
    }
}
