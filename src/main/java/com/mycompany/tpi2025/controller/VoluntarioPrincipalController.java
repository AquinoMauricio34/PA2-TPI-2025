/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.model.Voluntario;
import com.mycompany.tpi2025.view.VoluntarioViews.VoluntarioPrincipalView;
import jakarta.persistence.EntityManagerFactory;

/**
 *
 * @author aquin
 */
public class VoluntarioPrincipalController {
    //vista
    private VoluntarioPrincipalView principal;
    //voluntario usuario
    private Voluntario voluntario;
    private final EntityManagerFactory emf;

    public VoluntarioPrincipalController(VoluntarioPrincipalView principal,Voluntario voluntario, EntityManagerFactory emf) {
        this.principal = principal;
        this.voluntario = voluntario;
        this.emf = emf;
    }
    
    public void iniciar(){
        principal.setVisible(true);
    }

    public void cerrarVentana() {
        principal.dispose();
        principal = null;
    }
    
    
    
}
