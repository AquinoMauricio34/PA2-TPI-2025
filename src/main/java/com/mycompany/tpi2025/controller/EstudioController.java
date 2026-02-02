/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.DAOImpl.EstudioJpaController;
import com.mycompany.tpi2025.model.Estudio;
import com.mycompany.tpi2025.model.Gato;
import com.mycompany.tpi2025.view.EstudiosView;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author aquin
 */
public class EstudioController {
    private EstudiosView view;
    private EstudioJpaController daoE;
    private Gato gato = null;
    private Estudio estudio = null;
    private int editarOpc=0;
    //por defecto es 1 para que inicie con la posibilidad directa de cargar el estudio
    private int anidirOpc=1;

    public EstudioController(EstudiosView view, Gato gato, EntityManagerFactory emf) {
        this.view = view;
        this.gato = gato;
        this.daoE = new EstudioJpaController(emf);
        view.setAniadirListener(l -> aniadir(anidirOpc));
        view.setEditarListener(l -> editar(editarOpc));
        view.setSeleccionListaListener(l -> seleccionar());
        view.setEliminarListener(l -> eliminar());
        iniciarView();
    }

    private void aniadir(int opc) {
        if (opc == 0) {
            view.activarComponentes(true);
            limpiar();
            iniciarTabla();
            anidirOpc = 1;
            view.setAniadirText("Añadir Estudio");
        }else{
            try {
                Estudio e = new Estudio(gato.getId(),view.getTitulo(), view.getDescripcion());
                daoE.create(e);
                limpiar();
                iniciarTabla();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }

    private void editar(int opc) {
        if(estudio != null){
            if (opc == 0) {
                view.setEditarText("Guardar cambios");
                view.activarComponentes(true);
                view.activarAniadir(false);
                view.activarEliminar(false);
                editarOpc = 1;
            }else{
                try {
                    estudio.setDescripcion(view.getDescripcion());
                    estudio.setTitulo(view.getTitulo());
                    daoE.edit(estudio);
                    iniciarTabla();
                    limpiar();
                    view.activarAniadir(true);
                    view.activarEdicion(false);
                    view.setAniadirText("Añadir Estudio");
                    view.setEditarText("Editar Estudio");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        
    }
    
    public void iniciarTabla(){
        List<Estudio> lista = obtenerLista();
        view.reloadTable(lista);
    }
    
    private List<Estudio> obtenerLista(){
        List<Estudio> lista = daoE.findEstudioEntities();
        return lista.stream().filter(e -> e.getIdGato() == gato.getId()).collect(Collectors.toList());
    }
    
    private void seleccionar() {
        int fila = view.obtenerIndiceFila();
        if (fila != -1) {
            String id = view.obtenerValorTabla(fila, 0);
            int indice = obtenerIndiceEstudio(Long.parseLong(id));
            if(indice != -1){
                estudio = obtenerLista().get(indice);
                view.activarEdicion(true);
                view.activarEliminar(true);
                cargarDatosEstudio();
                view.activarComponentes(false);
                //como los componentes estan bloqueados, primero hay que presionar el boton de anidir una vez para que habilite los componentes
                anidirOpc=0;
                view.setAniadirText("Nuevo Estudio");
            }
        }
    }
    
    private int obtenerIndiceEstudio(long idDiag){
        List<Estudio> lista = obtenerLista();
        return lista.indexOf(lista.stream().filter(v -> v.getId()==idDiag).findFirst().orElse(null));
    }
    
    private void cargarDatosEstudio(){
        view.setTitulo(estudio.getTitulo());
        view.setDescripcion(estudio.getDescripcion());
    }

    private void iniciarView() {
        view.setVisible(true);view.toFront();
        view.setLocationRelativeTo(view);
    }
    
    private void limpiar(){
        view.limpiarComponentes();
    }

    private void eliminar() {
        if(estudio != null){
            
            try {
                daoE.destroy(estudio.getId());
                view.activarEliminar(false);
                iniciarTabla();
                limpiar();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }
    
}
