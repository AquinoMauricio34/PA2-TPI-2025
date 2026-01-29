/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.model.Tratamiento;
import com.mycompany.tpi2025.view.TratamientoView;

/**
 *
 * @author aquin
 */
public class TratamientoController {
    private TratamientoView view;
    private Tratamiento tratamiento;

    public TratamientoController(TratamientoView view) {
        //System.out.println("AB1");
        this.view = view;
        iniciarView();
    }
    
    public void iniciarView(){
        view.setVisible(true);
        view.setLocationRelativeTo(null);
    }
    
    public void crear(){
        tratamiento = new Tratamiento(view.getDescripcion(), view.getFechaInicio(), view.getFechaFin());
    }

    public Tratamiento getTratamiento() {
        return tratamiento;
    }
    
    public void cerrarView(){
        view.dispose();
        view = null;
    }
    
    
}
