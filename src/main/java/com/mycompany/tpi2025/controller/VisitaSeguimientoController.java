/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.DAOImpl.VisitaSeguimientoJpaController;
import com.mycompany.tpi2025.model.Gato;
import com.mycompany.tpi2025.model.VisitaSeguimiento;
import com.mycompany.tpi2025.view.VerGatoView;
import com.mycompany.tpi2025.view.VisitaSeguimientoView;
import jakarta.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author aquin
 */
public class VisitaSeguimientoController {
    
    private VisitaSeguimientoView view;
    private Gato gato=null;
    private VisitaSeguimientoJpaController daoG;
    private EntityManagerFactory emf;
    
    public VisitaSeguimientoController(VisitaSeguimientoView view, String nombreUsuario,EntityManagerFactory emf) {
        this.view = view;
        this.daoG = new VisitaSeguimientoJpaController(emf);
        this.emf = emf;
        iniciarView();
        abrirSeleccion();
        view.setRegistrarListener(l -> registrar(nombreUsuario));
    }
    
    public void iniciarView(){
        view.setVisible(true);
    }

    public void abrirSeleccion() {
        VerGatoView vview = new VerGatoView();
        VerGatoController controller = new VerGatoController(vview, emf);
        vview.setSeleccionListener(l -> {
            gato = controller.getGato();
            vview.dispose();
        });
    }

    private void registrar(String nombreUsuario) {
        try {
            VisitaSeguimiento v = new VisitaSeguimiento(nombreUsuario, gato.getId(), LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), view.getDescripcion());
            daoG.create(v);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
}
