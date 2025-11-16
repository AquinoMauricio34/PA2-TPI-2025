/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.DAOImpl.TareaJpaController;
import com.mycompany.tpi2025.model.Tarea;
import com.mycompany.tpi2025.model.Voluntario;
import com.mycompany.tpi2025.view.TareaRealizadaView;
import jakarta.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author aquin
 */
public class TareaRealizadaController {
    private TareaRealizadaView view;
    private Voluntario voluntario;
    private TareaJpaController dao;

    public TareaRealizadaController(TareaRealizadaView view, Voluntario voluntario,EntityManagerFactory emf) {
        this.view = view;
        this.dao = new TareaJpaController(emf);
        this.voluntario = voluntario;
        iniciarView();
        view.setTitulo("Voluntario: "+voluntario.getNombre());
        view.setRegistrarListener(l -> registrar());
    }
    
    private void iniciarView(){
        view.setVisible(true);
    }

    private void registrar() {
        try {
            Tarea t = new Tarea(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")), voluntario.getNombreUsuario(), view.getUbicacion(),view.getTareaRealizada());
            dao.create(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
