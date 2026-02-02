/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.DAOImpl.GatoJpaController;
import com.mycompany.tpi2025.DAOImpl.HogarJpaController;
import com.mycompany.tpi2025.DAOImpl.PostulacionJpaController;
import com.mycompany.tpi2025.model.Gato;
import com.mycompany.tpi2025.model.Hogar;
import com.mycompany.tpi2025.model.Postulacion;
import com.mycompany.tpi2025.view.VerPostulacionHogarView;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

/**
 *
 * @author aquin
 */
public class VerPostulacionHogarController {

    private VerPostulacionHogarView view;
    private PostulacionJpaController daoP;
    private HogarJpaController daoF;
    private GatoJpaController daoG;
    private Hogar hogar = null;
    private long idGatoAsignar;

    public VerPostulacionHogarController(VerPostulacionHogarView view, Hogar familia, EntityManagerFactory emf) {
        //System.out.println("MM11-------------------------------------------");
        this.view = view;
        this.daoP = new PostulacionJpaController(emf);
        this.daoF = new HogarJpaController(emf);
        this.daoG = new GatoJpaController(emf);
        this.hogar = familia;
        //System.out.println("MM112-------------------------------------------");
        iniciarView();
        //System.out.println("MM113-------------------------------------------");
        iniciarTabla();
        //System.out.println("MM-------------------------------------------");
        view.setSeleccionListaListener(l -> seleccionar());
        view.setAsignarListener(l -> asignar());
    }

    public void iniciarView() {
        view.setVisible(true);
        view.setTitulo("Hogar: " + hogar.getNombre());
    }

    public void iniciarTabla() {
        List<Postulacion> lista = obtenerLista();
        //System.out.println("MM114-------------------------------------------");
        //System.out.println(lista);
        view.reloadTable(lista);
    }

    private List<Postulacion> obtenerLista() {
        try {
            List<Postulacion> lista = daoP.findPostulacionesByPostulante(hogar.getNombreUsuario());
            return lista;
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println("asdflkjaskljasdfjklasdfjklñ");
        }
        return null;
    }

    private void seleccionar() {
        int fila = view.obtenerIndiceFila();
        if (fila != -1) {
//            idGatoAsignar = Long.parseLong(view.obtenerValorTabla(fila, 1));//segundo parametro indice correspondiente a la columna del encabezado
            String id = view.obtenerValorTabla(fila, 0);//segundo parametro indice correspondiente a la columna del encabezado
            int indice = obtenerIndicePostulacion(Long.parseLong(id));
            if (indice != -1) {
                view.resaltarFila(indice);
                idGatoAsignar = obtenerLista().get(indice).getIdGato();
                view.activarAsignacion(true);
                //iniciarTabla();
            }
        }
    }

    private int obtenerIndicePostulacion(long idPostulacion) {
        List<Postulacion> lista = obtenerLista();
        return lista.indexOf(lista.stream().filter(v -> v.getId() == idPostulacion).findFirst().orElse(null));
    }

    private void asignar() {
        try {
            Gato g = daoG.findGato(idGatoAsignar);
            hogar.addGato(g);
            daoF.edit(hogar);
            //eliminar todas las postulaciones con el idGato
            List<Postulacion> lista = daoP.findPostulacionesByIdGato(idGatoAsignar);
            for (Postulacion elem : lista) {
                daoP.destroy(elem.getId());
            }
            iniciarTabla();
        } catch (Exception e) {
        }
    }

    public void setHogar(Hogar h) {
        this.hogar = h;
    }
}
