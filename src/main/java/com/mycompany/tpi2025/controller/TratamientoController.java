/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tpi2025.controller;

import com.mycompany.tpi2025.Utils;
import com.mycompany.tpi2025.model.Tratamiento;
import com.mycompany.tpi2025.view.TratamientoView;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 *
 * @author aquin
 */
public class TratamientoController {

    private TratamientoView view;
    private Tratamiento tratamiento;

    public TratamientoController(TratamientoView view) {

        this.view = view;
        iniciarView();
        view.setCancelarListener(l -> cerrarView());
    }

    public void iniciarView() {
        view.setVisible(true);
        view.toFront();
        view.setLocationRelativeTo(null);
    }

    public void crear() {
        String descripcion = view.getDescripcion().trim();
        String fechaInicio = view.getFechaInicio();
        String fechaFin = view.getFechaFin();
        Boolean abandono = view.getAbandono();

        try {
            // Validar campos vacíos
            if (Utils.hayVacios(descripcion)) {
                throw new Exception("La descripción es obligatoria.");
            }

            // Validar fechas
            if (fechaInicio == null) {
                throw new Exception("La fecha de inicio es obligatoria.");
            }
            if (fechaFin == null) {
                throw new Exception("La fecha de fin es obligatoria.");
            }

            // Validar que fechas sean válidas
            if (!Utils.isFechaValida(fechaInicio)) {
                throw new Exception("La fecha de inicio no es válida.");
            }
            if (!Utils.isFechaValida(fechaFin)) {
                throw new Exception("La fecha de fin no es válida.");
            }

            // Validar que fecha fin sea posterior o igual a fecha inicio
            if (compararFechas(fechaInicio, fechaFin) >= 0) {
                throw new Exception("La fecha de fin no puede ser anterior a la fecha de inicio.");
            }

            // Crear tratamiento
            tratamiento = new Tratamiento(descripcion, fechaInicio, fechaFin);
            tratamiento.setAbandono_tratamiento(abandono);

            view.mostrarInfoMensaje("Tratamiento creado exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();
            view.mostrarErrorMensaje(e.getMessage());
            tratamiento = null;
        }
    }

    public int compararFechas(String fechaStr1, String fechaStr2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            LocalDate fecha1 = LocalDate.parse(fechaStr1, formatter);
            LocalDate fecha2 = LocalDate.parse(fechaStr2, formatter);

            return fecha1.compareTo(fecha2);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Use dd/mm/yyyy", e);
        }
    }

    public Tratamiento getTratamiento() {
        return tratamiento;
    }

    public void cerrarView() {
        view.dispose();
        view = null;
    }

    public void setTratamiento(Tratamiento t) {
        this.tratamiento = t;
    }

}
