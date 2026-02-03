/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.DAOImpl.FamiliaJpaController;
import com.mycompany.tpi2025.DAOImpl.GatoJpaController;
import com.mycompany.tpi2025.DAOImpl.PostulacionJpaController;
import com.mycompany.tpi2025.model.Familia;
import com.mycompany.tpi2025.model.Gato;
import com.mycompany.tpi2025.model.Postulacion;
import com.mycompany.tpi2025.view.JPanels.VerPostulacionFamiliaView;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author aquin
 */
public class VerPostulacionFamiliaController {
    private VerPostulacionFamiliaView view;
    private PostulacionJpaController daoP;
    private FamiliaJpaController daoF;
    private GatoJpaController daoG;
    private Familia familia = null;
    private long idGatoAsignar;

    public VerPostulacionFamiliaController(VerPostulacionFamiliaView view, Familia familia, EntityManagerFactory emf) {
        //System.out.println("MM11-------------------------------------------");
        this.view = view;
        this.daoP = new PostulacionJpaController(emf);
        this.daoF = new FamiliaJpaController(emf);
        this.daoG = new GatoJpaController(emf);
        this.familia = familia;
        //System.out.println("MM112-------------------------------------------");
        iniciarView();
        //System.out.println("MM113-------------------------------------------");
        iniciarTabla();
        //System.out.println("MM-------------------------------------------");
        
        view.setSeleccionListaListener(l -> seleccionar());
        view.setAsignarListener(l -> asignar());
    }
    
    public void iniciarView(){
        view.setVisible(true);
        view.setTitulo("Familia: "+familia.getNombre());
    }
    
    public void iniciarTabla(){
        List<Postulacion> lista = obtenerLista();
        //System.out.println("MM114-------------------------------------------");
        //System.out.println(lista);
        view.reloadTable(lista);
    }
    
    private List<Postulacion> obtenerLista(){
        try {
            List<Postulacion> lista = daoP.findPostulacionesByPostulante(familia.getNombreUsuario());
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
            if(indice != -1){
                view.resaltarFila(indice);
                idGatoAsignar = obtenerLista().get(indice).getIdGato();
                view.activarAsignacion(true);
                //iniciarTabla();
            }
        }
    }
    
    private int obtenerIndicePostulacion(long idPostulacion){
        List<Postulacion> lista = obtenerLista();
        return lista.indexOf(lista.stream().filter(v -> v.getId()==idPostulacion).findFirst().orElse(null));
    }
    
    private void asignar(){
        try {
            if(!familia.isAptoAdopcion()) throw new Exception("La familia no es apta para adoptar.\nSolicitar emisión de adopción al veterinario.");
            Gato g = daoG.findGato(idGatoAsignar);
            familia.addGato(g);
            daoG.edit(g);
            familia.setAptoAdopcion(false);
            daoF.edit(familia);
            //eliminar todas las postulaciones con el idGato
            List<Postulacion> lista = daoP.findPostulacionesByIdGato(idGatoAsignar);
            for(Postulacion elem: lista){
                daoP.destroy(elem.getId());
            }
            iniciarTabla();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, e.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void setFamilia(Familia f){
        this.familia = f;
    }
}
